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
        fixTotalCost();
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
    
    private void fixTotalCost() throws IOException {
        NodeList orderList = document.getElementsByTagName("order");
        NodeList itemList;
        NodeList totalCostList;
        int totalCost;
        boolean isTotalCostFound;
        
        for (int i = 0; i < orderList.getLength(); i++) {
            itemList = ((Element)orderList.item(i)).getElementsByTagName("item");
            totalCostList = ((Element)orderList.item(i)).getElementsByTagName("totalcost");
            isTotalCostFound = false;
            totalCost = 0;
            for (int j = 0; j < itemList.getLength(); j++) {
                totalCost += Integer.parseInt(itemList.item(j).getAttributes().getNamedItem("cost").getNodeValue());
            }
            if (totalCostList.getLength() > 0) {
                isTotalCostFound = true;
                totalCostList.item(0).setTextContent(String.valueOf(totalCost));
                rewriteDocument();
            }
            if (!isTotalCostFound) {
                Element totalCostElement = document.createElement("totalcost");
                totalCostElement.setTextContent(String.valueOf(totalCost));
                orderList.item(i).appendChild(totalCostElement);
                rewriteDocument();
            }
        }
    }
    
    
    /**
     * Возвращает суммарную выручку заданного официанта в заданный день.
     * @param officiantSecondName Имя официанта
     * @param calendar Дата в формате дд-мм-гггг
     * @return
     */
    public int earningsTotal(String officiantSecondName, Calendar calendar) {
        int result = 0;
        NodeList dateList = document.getElementsByTagName("date");
        NamedNodeMap dateAttributes;
        NodeList orderList;
        NodeList orderNodesList;
        boolean isOfficiantFound = false;
        
        for (int i = 0; i < dateList.getLength(); i++) {
            dateAttributes = dateList.item(i).getAttributes();
            if ((Integer.valueOf(dateAttributes.getNamedItem("day").getNodeValue()) == calendar.get(Calendar.DAY_OF_MONTH)) &&
                    (Integer.valueOf(dateAttributes.getNamedItem("month").getNodeValue()) == (calendar.get(Calendar.MONTH)+1)) &&
                    (Integer.valueOf(dateAttributes.getNamedItem("year").getNodeValue()) == calendar.get(Calendar.YEAR))) {
                orderList = ((Element)dateList.item(i)).getElementsByTagName("order");
                for (int j = 0; j < orderList.getLength(); j++) {
                    orderNodesList = orderList.item(j).getChildNodes();
                    for (int k = 0; k < orderNodesList.getLength(); k++) {
                        if ((orderNodesList.item(k).getNodeName().equals("officiant")) &&
                                orderNodesList.item(k).getAttributes().getNamedItem("secondname").getNodeValue().equals(officiantSecondName)) {
                            isOfficiantFound = true;
                        }
                        if ((orderNodesList.item(k).getNodeName().equals("totalcost")) && isOfficiantFound) {
                            result += Integer.parseInt(orderNodesList.item(k).getTextContent());
                        }
                    }
                }
                break;
            }
        }
        return result;
    }
    
    /**
     * Удаляет информацию по заданному дню.
     * @param calendar Дата в формате дд-мм-гггг
     * @throws java.io.IOException
     */
    public void removeDay(Calendar calendar) throws IOException {
        NodeList dateList = document.getElementsByTagName("date");
        int datesAmount = dateList.getLength();
        NamedNodeMap dateAttributes;
        for (int i = 0; i < datesAmount; i++) {
            dateAttributes = dateList.item(i).getAttributes();
            if ((Integer.valueOf(dateAttributes.getNamedItem("day").getNodeValue()) == calendar.get(Calendar.DAY_OF_MONTH)) &&
                    (Integer.valueOf(dateAttributes.getNamedItem("month").getNodeValue()) == (calendar.get(Calendar.MONTH)+1)) &&
                    (Integer.valueOf(dateAttributes.getNamedItem("year").getNodeValue()) == calendar.get(Calendar.YEAR))) {
                dateList.item(i).getParentNode().removeChild(dateList.item(i));
                break;
            }
        }
        rewriteDocument();
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
