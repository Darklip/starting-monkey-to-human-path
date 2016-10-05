package RPIS41.Lipatkin.wdad.learn.xml;

class TestXmlTask {
    public static void main(String[] args) {
        try {
            XmlTask testDocument = new XmlTask();
            
            testDocument.changeOfficiantName("Михаил", "ivanov", "Михан", "ivanov");
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }
}
