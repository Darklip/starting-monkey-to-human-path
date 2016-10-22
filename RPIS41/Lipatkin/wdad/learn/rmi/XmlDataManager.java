package RPIS41.Lipatkin.wdad.learn.rmi;

import java.io.IOException;
import java.io.Serializable;

import java.rmi.Remote;

import java.util.Calendar;
import java.util.List;

public interface XmlDataManager extends Remote, Serializable {
    
    public int earningsTotal(Officiant officiant, Calendar calendar);
    
    public void removeDay(Calendar calendar) throws IOException ;
    
    public void changeOfficiantName(Officiant oldOfficiant, Officiant newOfficiant) throws IOException ;
    
    public List<Order> getOrders(Calendar calendar);
    
    public Calendar lastOfficiantWorkDate(Officiant officiant) throws NoSuchOfficiantException;
    
}
