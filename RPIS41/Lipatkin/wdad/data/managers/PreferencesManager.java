package RPIS41.Lipatkin.wdad.data.managers;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.*;

import org.w3c.dom.*;
import org.w3c.dom.ls.DOMImplementationLS;
import org.w3c.dom.ls.LSOutput;
import org.w3c.dom.ls.LSSerializer;
import org.xml.sax.SAXException;

public class PreferencesManager {

    private static PreferencesManager instance;
    private static final String PATH = "src/RPIS41/Lipatkin/wdad/resources/configuration/appconfig.xml";
    private Document document;

    private PreferencesManager() throws ParserConfigurationException, IOException, SAXException {
        generateDocument();
    }

    public static PreferencesManager getInstance() throws ParserConfigurationException, IOException, SAXException {
        if (instance == null) {
            instance = new PreferencesManager();
        }
        return instance;
    }

    private void generateDocument()
            throws ParserConfigurationException, IOException, SAXException {
        File fXmlFile = new File(PATH);
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
        document = dBuilder.parse(fXmlFile);
    }

    private void updateDocument() throws IOException {
        DOMImplementationLS domImplementationLS
                = (DOMImplementationLS) document.getImplementation().getFeature("LS", "3.0");
        LSOutput lsOutput = domImplementationLS.createLSOutput();
        FileOutputStream outputStream = new FileOutputStream(PATH);
        lsOutput.setByteStream(outputStream);
        LSSerializer lsSerializer = domImplementationLS.createLSSerializer();
        lsSerializer.write(document, lsOutput);
        outputStream.close();
    }
    
    private static String getNodePath(Node node) {
        Node parent = node.getParentNode();
        
        if (parent == null || parent.getNodeName().equals("#document")) {
            return node.getNodeName();
        }
        return getNodePath(parent) + '.' + node.getNodeName();
    }

    public void setProperty(String key, String value) throws IOException {
        String[] tags = key.split("\\.");
        int lastIndex = tags.length - 1;
        NodeList nodeList = document.getElementsByTagName(tags[lastIndex]);
        nodeList.item(0).setTextContent(value);
        updateDocument();
    }

    public String getProperty(String key) {
        String[] tags = key.split("\\.");
        int lastIndex = tags.length - 1;
        NodeList nodeList = document.getElementsByTagName(tags[lastIndex]);
        return nodeList.item(0).getTextContent();
    }
    
    public void setProperties(Properties prop) throws IOException {
        for (String key : prop.stringPropertyNames()) {
            setProperty(key, prop.getProperty(key));
        }
    }
    
    public Properties getProperties() {
        Properties props = new Properties();
        String key, value;
        XPath xPath = XPathFactory.newInstance().newXPath();
        String expression = "//*[not(*)]";  // http://stackoverflow.com/questions/3926589/how-to-select-all-leaf-nodes-using-xpath-expression
        try {
            NodeList nodeList = (NodeList) xPath.compile(expression).evaluate(document, XPathConstants.NODESET);
            
            for (int i = 0; i < nodeList.getLength(); i++) {
                key = getNodePath(nodeList.item(i));
                value = getProperty(key);
                props.put(key, value);
            }
        } catch (XPathExpressionException xPathExpressionException) {
            xPathExpressionException.getStackTrace();
        }
        return props;
    }
    
    public void addBindedObject(String name, String className) throws IOException {
        Element bindedObjectNode = document.createElement("bindedobject");
        bindedObjectNode.setAttribute("name", name);
        bindedObjectNode.setAttribute("class", className);
        document.getElementsByTagName("server").item(0).appendChild(bindedObjectNode);
        updateDocument();
    }
    
    public void removeBindedObject(String name) throws IOException {
        NodeList bindedObjectList = document.getElementsByTagName("bindedobject");
        
        for (int i = 0; i < bindedObjectList.getLength(); i++) {
            if (name.equals(bindedObjectList.item(i).getAttributes().getNamedItem("name").getNodeValue())) {
                bindedObjectList.item(i).getParentNode().removeChild(bindedObjectList.item(i));
            }
        }
        updateDocument();
    }

    @Deprecated
    public boolean getCreateRegistry() {
        NodeList nodeList = document.getElementsByTagName("createregistry");
        return nodeList.item(0).getTextContent().equals("yes");
    }

    @Deprecated
    public void setCreateRegistry(boolean createRegistry) throws IOException {
        NodeList nodeList = document.getElementsByTagName("createregistry");
        if (createRegistry) {
            nodeList.item(0).setTextContent("yes");
        } else {
            nodeList.item(0).setTextContent("no");
        }
        updateDocument();
    }

    @Deprecated
    public String getRegistryAddress() {
        NodeList nodeList = document.getElementsByTagName("registryaddress");
        return nodeList.item(0).getTextContent();
    }

    @Deprecated
    public void setRegistryAddress(String registryAdress) throws IOException {
        NodeList nodeList = document.getElementsByTagName("registryaddress");
        nodeList.item(0).setTextContent(registryAdress);
        updateDocument();
    }

    @Deprecated
    public int getRegistryPort() {
        NodeList nodeList = document.getElementsByTagName("registryport");
        return Integer.parseInt(nodeList.item(0).getTextContent());
    }

    @Deprecated
    public void setRegistryPort(int registryPort) throws IOException {
        NodeList nodeList = document.getElementsByTagName("registryport");
        nodeList.item(0).setTextContent(String.valueOf(registryPort));
        updateDocument();
    }

    @Deprecated
    public String getPolicyPath() {
        NodeList nodeList = document.getElementsByTagName("policypath");
        return nodeList.item(0).getTextContent();
    }

    @Deprecated
    public void setPolicyPath(String policyPath) throws IOException {
        NodeList nodeList = document.getElementsByTagName("policypath");
        nodeList.item(0).setTextContent(policyPath);
        updateDocument();
    }

    @Deprecated
    public boolean getUseCodeBaseOnly() {
        NodeList nodeList = document.getElementsByTagName("usecodebaseonly");
        return nodeList.item(0).getTextContent().equals("yes");
    }

    @Deprecated
    public void setUseCodeBaseOnly(boolean useCodeBaseOnly) throws IOException {
        NodeList nodeList = document.getElementsByTagName("usecodebaseonly");
        if (useCodeBaseOnly) {
            nodeList.item(0).setTextContent("yes");
        } else {
            nodeList.item(0).setTextContent("no");
        }
        updateDocument();
    }

    @Deprecated
    public String getClassProvider() {
        NodeList nodeList = document.getElementsByTagName("classprovider");
        return nodeList.item(0).getTextContent();
    }

    @Deprecated
    public void setClassProvider(String classproviderURL) throws IOException {
        NodeList nodeList = document.getElementsByTagName("classprovider");
        nodeList.item(0).setTextContent(classproviderURL);
        updateDocument();
    }
}
