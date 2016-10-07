package RPIS41.Lipatkin.wdad.data.managers;

import java.io.IOException;
import javax.xml.parsers.ParserConfigurationException;
import org.xml.sax.SAXException;

public class testPreferencesManager {
    
    public static void main(String[] args) {
        try {
            PreferencesManager pm = PreferencesManager.getInstance();
            
            System.out.println(pm.getProperty("appconfig.rmi.server.registry.registryaddress"));
            
            pm.addBindedObject("Playlist", "playList");
            
        } catch (ParserConfigurationException | IOException | SAXException ex) {
            System.err.println(ex.getMessage());
        }
    }
}
