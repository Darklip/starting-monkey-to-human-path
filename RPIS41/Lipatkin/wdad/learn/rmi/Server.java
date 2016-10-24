package RPIS41.Lipatkin.wdad.learn.rmi;

import RPIS41.Lipatkin.wdad.data.managers.PreferencesManager;

import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;
import org.xml.sax.SAXException;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

public class Server {
    static private PreferencesManager pm;
    
    public static void main(String[] args) {
        try {
            pm = PreferencesManager.getInstance();
        } catch (ParserConfigurationException | IOException | SAXException e) {
            System.err.println("cant get PreferencesManager instance");
            e.printStackTrace();
        }
        System.setProperty("java.rmi.server.codebase", pm.getClassProvider());
        System.setProperty("java.rmi.server.useCodebaseOnly", String.valueOf(pm.getUseCodeBaseOnly()));
        System.setProperty("java.rmi.server.logCalls", "true");
        System.setProperty("java.security.policy", pm.getPolicyPath());
        Registry registry = null;
        try {
            if (pm.getCreateRegistry()) {
                registry = LocateRegistry.createRegistry(pm.getRegistryPort());
            } else {
                registry = LocateRegistry.getRegistry(pm.getRegistryAddress(), pm.getRegistryPort());
            }
        } catch (RemoteException re) {
            System.err.println("cant locate registry");
            re.printStackTrace();
        }
        if (registry != null) {
            try {
                System.out.println("exporting object...");
                XmlDataManagerImpl xdmi = new XmlDataManagerImpl();
                UnicastRemoteObject.exportObject(xdmi, 49100);
                registry.rebind("XmlDataManager", xdmi);
                System.out.println("idling");
            } catch (RemoteException re) {
                System.err.println("cant export or bind object");
                re.printStackTrace();
            }
        }
    }
}
