package RPIS41.Lipatkin.wdad.data.managers;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.*;
import org.w3c.dom.ls.DOMImplementationLS;
import org.w3c.dom.ls.LSOutput;
import org.w3c.dom.ls.LSSerializer;
import org.xml.sax.SAXException;

public class PreferencesManager {

    private static PreferencesManager instance;
    private static String path = "src/RPIS41/Lipatkin/wdad/resources/configuration/appconfig.xml";
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
        File fXmlFile = new File(path);
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
        document = dBuilder.parse(fXmlFile);
    }

    private void rewriteDocument() throws IOException {
        DOMImplementationLS domImplementationLS
                = (DOMImplementationLS) document.getImplementation().getFeature("LS", "3.0");
        LSOutput lsOutput = domImplementationLS.createLSOutput();
        FileOutputStream outputStream = new FileOutputStream(path);
        lsOutput.setByteStream(outputStream);
        LSSerializer lsSerializer = domImplementationLS.createLSSerializer();
        lsSerializer.write(document, lsOutput);
        outputStream.close();
    }

    public boolean getCreateRegistry() {
        NodeList nodeList = document.getElementsByTagName("createregistry");
        return nodeList.item(0).getTextContent().equals("yes");
    }

    public void setCreateRegistry(boolean createRegistry) throws IOException {
        NodeList nodeList = document.getElementsByTagName("createregistry");
        if (createRegistry) {
            nodeList.item(0).setTextContent("yes");
        } else {
            nodeList.item(0).setTextContent("no");
        }
        rewriteDocument();
    }

    public String getRegistryAddress() {
        NodeList nodeList = document.getElementsByTagName("registryaddress");
        return nodeList.item(0).getTextContent();
    }

    public void setRegistryAddress(String registryAdress) throws IOException {
        NodeList nodeList = document.getElementsByTagName("registryaddress");
        nodeList.item(0).setTextContent(registryAdress);
        rewriteDocument();
    }

    public int getRegistryPort() {
        NodeList nodeList = document.getElementsByTagName("registryport");
        return Integer.parseInt(nodeList.item(0).getTextContent());
    }

    public void setRegistryPort(int registryPort) throws IOException {
        NodeList nodeList = document.getElementsByTagName("registryport");
        nodeList.item(0).setTextContent(String.valueOf(registryPort));
        rewriteDocument();
    }

    public String getPolicyPath() {
        NodeList nodeList = document.getElementsByTagName("policypath");
        return nodeList.item(0).getTextContent();
    }

    public void setPolicyPath(String policyPath) throws IOException {
        NodeList nodeList = document.getElementsByTagName("policypath");
        nodeList.item(0).setTextContent(policyPath);
        rewriteDocument();
    }

    public boolean getUseCodeBaseOnly() {
        NodeList nodeList = document.getElementsByTagName("usecodebaseonly");
        return nodeList.item(0).getTextContent().equals("yes");
    }

    public void setUseCodeBaseOnly(boolean useCodeBaseOnly) throws IOException {
        NodeList nodeList = document.getElementsByTagName("usecodebaseonly");
        if (useCodeBaseOnly) {
            nodeList.item(0).setTextContent("yes");
        } else {
            nodeList.item(0).setTextContent("no");
        }
        rewriteDocument();
    }

    public String getClassProvider() {
        NodeList nodeList = document.getElementsByTagName("classprovider");
        return nodeList.item(0).getTextContent();
    }

    public void setClassProvider(String classproviderURL) throws IOException {
        NodeList nodeList = document.getElementsByTagName("classprovider");
        nodeList.item(0).setTextContent(classproviderURL);
        rewriteDocument();
    }

}
