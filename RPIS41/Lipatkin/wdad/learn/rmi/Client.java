package RPIS41.Lipatkin.wdad.learn.rmi;

import RPIS41.Lipatkin.wdad.data.managers.PreferencesManager;
import RPIS41.Lipatkin.wdad.utils.PreferencesConstantManager;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.xml.parsers.ParserConfigurationException;
import org.xml.sax.SAXException;
import java.io.IOException;

public class Client {
    static private PreferencesManager pm;
    private static final String DATA_MANAGER_NAME = "XmlDataManager";
    
    public static void main(String[] args) {
        try {
            pm = PreferencesManager.getInstance();
        } catch (ParserConfigurationException | IOException | SAXException ex) {
            System.err.println("appconfig.xml is damaged");
            ex.printStackTrace();
        }
        System.setProperty("java.rmi.server.codebase", pm.getProperty(PreferencesConstantManager.CLASS_PROVIDER));
        System.setProperty("java.rmi.server.useCodebaseOnly", String.valueOf(pm.getProperty(PreferencesConstantManager.USE_CODE_BASE_ONLY)));
        System.setProperty("java.security.policy", pm.getProperty(PreferencesConstantManager.POLICY_PATH));
        
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        Registry registry = null;
        try {
            registry = LocateRegistry.getRegistry(
                        pm.getProperty(PreferencesConstantManager.REGISTRY_ADDRESS), 
                        Integer.parseInt(pm.getProperty(PreferencesConstantManager.REGISTRY_PORT))
            );
        } catch (RemoteException re) {
            System.err.println("Cant locate registry");
            re.printStackTrace();
        }
        if (registry != null) {
            try {
                XmlDataManager xdm = (XmlDataManager) registry.lookup(DATA_MANAGER_NAME);
                calendar.setTime(sdf.parse("2-10-2016"));
                System.out.println(xdm.checkWork());
                xdm.changeOfficiantName(new Officiant("Василий", "petrov"), new Officiant("Михаил", "ivanov"));
                System.out.println(xdm.earningsTotal(new Officiant("Михаил", "ivanov"), calendar));
                calendar.setTime(sdf.parse("10-10-2016"));
                xdm.removeDay(calendar);
                System.out.println(xdm.lastOfficiantWorkDate(new Officiant("Михаил", "ivanov")).toString());
                calendar.setTime(sdf.parse("2-10-2016"));
                xdm.getOrders(calendar);
            } catch (NotBoundException nbe) {
                System.err.println("Cant find object");
                nbe.printStackTrace();
            } catch (RemoteException re) {
                System.err.println("Cant execute RMI");
                re.printStackTrace();
            } catch (Exception e) {
                System.err.println("User input error");
                e.printStackTrace();
            }
        }
    }
}
