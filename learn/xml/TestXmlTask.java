package RPIS41.Lipatkin.wdad.learn.xml;

import java.util.Calendar;
import java.text.SimpleDateFormat;

class TestXmlTask {
    public static void main(String[] args) {
        try {
            XmlTask testDocument = new XmlTask();
            
            //удаляем узел с заданной датой
            Calendar calendar = Calendar.getInstance();
            SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
            calendar.setTime(sdf.parse("9-9-2016"));
            testDocument.removeDay(calendar);
            
            //меняем имя официанта
            testDocument.changeOfficiantName("Михаил", "ivanov", "Михан", "ivanov");
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }
}
