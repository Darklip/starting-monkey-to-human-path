package RPIS41.Lipatkin.wdad.data.managers;

import java.io.IOException;
import javax.xml.parsers.ParserConfigurationException;
import org.xml.sax.SAXException;
import RPIS41.Lipatkin.wdad.utils.PreferencesConstantManager;
import java.util.Properties;

public class testPreferencesManager {
    
    public static void main(String[] args) {
        try {
            PreferencesManager pm = PreferencesManager.getInstance();
            System.out.println(pm.getProperty(PreferencesConstantManager.CLASS_PROVIDER));
            System.out.println(pm.getProperty(PreferencesConstantManager.CREATE_REGISTRY));
            System.out.println(pm.getProperty(PreferencesConstantManager.POLICY_PATH));
            System.out.println(pm.getProperty(PreferencesConstantManager.REGISTRY_ADDRESS));
            System.out.println(pm.getProperty(PreferencesConstantManager.REGISTRY_PORT));
            System.out.println(pm.getProperty(PreferencesConstantManager.USE_CODE_BASE_ONLY));
            
            Properties p = new Properties();
            p.put(PreferencesConstantManager.REGISTRY_ADDRESS, "mastefanov.com");
            p.put(PreferencesConstantManager.REGISTRY_PORT, "9001");
            p.put(PreferencesConstantManager.USE_CODE_BASE_ONLY, "yes");
            pm.setProperties(p);
            System.out.println(pm.getProperty(PreferencesConstantManager.REGISTRY_ADDRESS));
            System.out.println(pm.getProperty(PreferencesConstantManager.REGISTRY_PORT));
            System.out.println(pm.getProperty(PreferencesConstantManager.USE_CODE_BASE_ONLY));
            
            //pm.addBindedObject("Playlist", "playList");
            
        } catch (ParserConfigurationException | IOException | SAXException ex) {
            System.err.println(ex.getMessage());
        }
    }
}
