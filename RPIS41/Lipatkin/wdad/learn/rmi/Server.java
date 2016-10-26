package RPIS41.Lipatkin.wdad.learn.rmi;

import RPIS41.Lipatkin.wdad.data.managers.PreferencesManager;
import RPIS41.Lipatkin.wdad.utils.PreferencesConstantManager;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

import java.io.IOException;
import javax.xml.parsers.ParserConfigurationException;
import org.xml.sax.SAXException;

public class Server {
    static private PreferencesManager pm;
    private static final String DATA_MANAGER_NAME = "XmlDataManager";
    private static final String DATA_MANAGER_PATH = "RPIS41.Lipatkin.wdad.learn.rmi.XmlDataManagerImpl";
    private static final int DATA_MANAGER_PORT = 49100;
    
    public static void main(String[] args) {
        try {
            pm = PreferencesManager.getInstance();
        } catch (ParserConfigurationException | IOException | SAXException ex) {
            System.err.println("appconfig.xml is damaged");
            ex.printStackTrace();
        }
        System.setProperty("java.rmi.server.codebase", pm.getProperty(PreferencesConstantManager.CLASS_PROVIDER));
        System.setProperty("java.rmi.server.useCodebaseOnly", String.valueOf(pm.getProperty(PreferencesConstantManager.USE_CODE_BASE_ONLY)));
        System.setProperty("java.rmi.server.logCalls", "true");
        System.setProperty("java.security.policy", pm.getProperty(PreferencesConstantManager.POLICY_PATH));
        Registry registry = null;
        try {
            if (pm.getCreateRegistry()) {
                registry = LocateRegistry.createRegistry(Integer.parseInt(pm.getProperty(PreferencesConstantManager.REGISTRY_PORT)));
            } else {
                registry = LocateRegistry.getRegistry(
                        pm.getProperty(PreferencesConstantManager.REGISTRY_ADDRESS), 
                        Integer.parseInt(pm.getProperty(PreferencesConstantManager.REGISTRY_PORT))
                );
            }
        } catch (RemoteException re) {
            System.err.println("Cant locate registry");
            re.printStackTrace();
        }
        if (registry != null) {
            try {
                System.out.println("Exporting object...");
                XmlDataManagerImpl xdmi = new XmlDataManagerImpl();
                UnicastRemoteObject.exportObject(xdmi, DATA_MANAGER_PORT);
                registry.rebind(DATA_MANAGER_NAME, xdmi);
                pm.addBindedObject(DATA_MANAGER_NAME, DATA_MANAGER_PATH);
                System.out.println("Idling");
            } catch (RemoteException re) {
                System.err.println("Cant export or bind object");
                re.printStackTrace();
            } catch (IOException ioe) {
                System.err.println("Cant add binded object to appconfig");
                ioe.printStackTrace();
            }
        }
    }
}
