package RPIS41.Lipatkin.wdad.learn.xml;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Calendar;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.*;
import org.xml.sax.SAXException;

public class XmlTask {
    
    private Document document;
    private String path;
    
    public XmlTask()
            throws ParserConfigurationException, IOException, SAXException {
        path = "src/RPIS41/Lipatkin/wdad/learn/xml/newXMLDocument.xml";
        generateDocument();
    }
    
    private void generateDocument()
            throws ParserConfigurationException, IOException, SAXException {
        File fXmlFile = new File(path);
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
        document = dBuilder.parse(fXmlFile);
    }
    
    /**
     * Возвращает суммарную выручку заданного официанта в заданный день.
     * @param officiantSecondName Имя официанта
     * @param calendar Дата в формате гггг-мм-дд
     * @return
     */
    public int earningsTotal(String officiantSecondName, Calendar calendar) {
        int result = 0;
        
        return result;
    }
    
    /**
     * Удаляет информацию по заданному дню.
     * @param calendar Дата в формате гггг-мм-дд
     */
    public void removeDay(Calendar calendar) {
        
    }
    
    /**
     * Изменяет имя и фамилию официанта во всех днях и записывает результат. 
     * в этот же xml-файл
     * @param oldFirstName Текущее имя официанта
     * @param oldSecondName Текущая фамилия официанта
     * @param newFirstName Новое имя официанта
     * @param newSecondName Новая фамилия официанта
     */
    public void changeOfficiantName(String oldFirstName, String oldSecondName, 
            String newFirstName, String newSecondName) {
        
    }
    
}
