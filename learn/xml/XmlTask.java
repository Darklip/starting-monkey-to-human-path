package RPIS41.Lipatkin.wdad.learn.xml;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Calendar;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.*;
import org.w3c.dom.ls.DOMImplementationLS;
import org.w3c.dom.ls.LSOutput;
import org.w3c.dom.ls.LSSerializer;
import org.xml.sax.SAXException;

public class XmlTask {
    
    private Document document;
    private String path;
    
    public XmlTask()
            throws ParserConfigurationException, IOException, SAXException {
        this("src/RPIS41/Lipatkin/wdad/learn/xml/newXMLDocument.xml");
    }
    
    public XmlTask(String path)
            throws ParserConfigurationException, IOException, SAXException {
        this.path = path;
        generateDocument();
    }
    
    private void generateDocument()
            throws ParserConfigurationException, IOException, SAXException {
        File fXmlFile = new File(path);
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
        document = dBuilder.parse(fXmlFile);
    }
    
    private void rewriteDocument() throws IOException {
        DOMImplementationLS domImplementationLS =
                (DOMImplementationLS) document.getImplementation().getFeature("LS", "3.0");
        LSOutput lsOutput = domImplementationLS.createLSOutput();
        FileOutputStream outputStream = new FileOutputStream(path);
        lsOutput.setByteStream(outputStream);
        LSSerializer lsSerializer = domImplementationLS.createLSSerializer();
        lsSerializer.write(document, lsOutput);
        outputStream.close();
    }
    
    /**
     * Возвращает суммарную выручку заданного официанта в заданный день.
     * @param officiantSecondName Имя официанта
     * @param calendar Дата в формате дд-мм-гггг
     * @return
     */
    public int earningsTotal(String officiantSecondName, Calendar calendar) {
        int result = 0;
        // dummy
        return result;
    }
    
    /**
     * Удаляет информацию по заданному дню.
     * @param calendar Дата в формате дд-мм-гггг
     */
    public void removeDay(Calendar calendar) {
        // dummy
    }
    
    /**
     * Изменяет имя и фамилию официанта во всех днях и записывает результат 
     * в этот же xml-файл.
     * @param oldFirstName Текущее имя официанта
     * @param oldSecondName Текущая фамилия официанта
     * @param newFirstName Новое имя официанта
     * @param newSecondName Новая фамилия официанта
     * @throws java.io.IOException
     */
    public void changeOfficiantName(String oldFirstName, String oldSecondName, 
            String newFirstName, String newSecondName) throws IOException {
        NodeList officiantList = document.getElementsByTagName("officiant");
        
        for (int i = 0; i < officiantList.getLength(); i++) {
            NamedNodeMap officiantAttributes = officiantList.item(i).getAttributes();
            if ((officiantAttributes.getNamedItem("firstname").getNodeValue().equals(oldFirstName)) &&
                    (officiantAttributes.getNamedItem("secondname").getNodeValue().equals(oldSecondName))) {
                officiantAttributes.getNamedItem("firstname").setNodeValue(newFirstName);
                officiantAttributes.getNamedItem("secondname").setNodeValue(newSecondName);
            }
        }
        rewriteDocument();
    }
    
}
