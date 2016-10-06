package RPIS41.Lipatkin.wdad.learn.xml;

import java.util.Calendar;
import java.text.SimpleDateFormat;

class TestXmlTask {
    public static void main(String[] args) {
        try {
            XmlTask testDocument = new XmlTask();
            Calendar calendar = Calendar.getInstance();
            SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
            
            //получаем сумму, заработанную официантом в заданный день
            calendar.setTime(sdf.parse("2-10-2016"));
            System.out.println(testDocument.earningsTotal("ivanov", calendar));
            
            //удаляем узел с заданной датой
            calendar.setTime(sdf.parse("9-9-2016"));
            //testDocument.removeDay(calendar);
            
            //меняем имя официанта
            //testDocument.changeOfficiantName("Михаил", "ivanov", "Михан", "ivanov");
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }
}
