package RPIS41.Lipatkin.wdad.learn.rmi;

import java.io.IOException;
import java.io.Serializable;

import java.rmi.Remote;
import java.rmi.RemoteException;

import java.util.Calendar;
import java.util.List;

public interface XmlDataManager extends Remote, Serializable {
    
    public String checkWork() throws RemoteException;
    
    public int earningsTotal(Officiant officiant, Calendar calendar) throws RemoteException;
    
    public void removeDay(Calendar calendar) throws IOException, RemoteException;
    
    public void changeOfficiantName(Officiant oldOfficiant, Officiant newOfficiant) throws IOException, RemoteException;
    
    public List<Order> getOrders(Calendar calendar) throws RemoteException;
    
    public Calendar lastOfficiantWorkDate(Officiant officiant) throws NoSuchOfficiantException, RemoteException;
    
}
