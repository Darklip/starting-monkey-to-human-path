package RPIS41.Lipatkin.wdad.learn.rmi;

import RPIS41.Lipatkin.wdad.data.managers.PreferencesManager;
import java.io.IOException;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import javax.xml.parsers.ParserConfigurationException;
import org.xml.sax.SAXException;

public class Client {
    static private PreferencesManager pm;
    
    public static void main(String[] args) {
        try {
            pm = PreferencesManager.getInstance();
        } catch (ParserConfigurationException | IOException | SAXException ex) {
            System.err.println("appconfig.xml is damaged");
            ex.printStackTrace();
        }
        System.setProperty("java.rmi.server.codebase", pm.getClassProvider());
        System.setProperty("java.rmi.server.useCodebaseOnly", String.valueOf(pm.getUseCodeBaseOnly()));
        System.setProperty("java.security.policy", pm.getPolicyPath());
        //System.setSecurityManager(new SecurityManager());
        
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        Registry registry = null;
        try {
            registry = LocateRegistry.getRegistry(pm.getRegistryAddress(), pm.getRegistryPort());
        } catch (RemoteException re) {
            System.err.println("cant locate registry");
            re.printStackTrace();
        }
        if (registry != null) {
            try {
                XmlDataManager xdm = (XmlDataManager) registry.lookup("XmlDataManager");
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
                System.err.println("cant find object");
                nbe.printStackTrace();
            } catch (RemoteException re) {
                System.err.println("something unbelievable happens");
                re.printStackTrace();
            } catch (Exception e) {
                System.err.println("user input error");
                e.printStackTrace();
            }
        }
    }
}
