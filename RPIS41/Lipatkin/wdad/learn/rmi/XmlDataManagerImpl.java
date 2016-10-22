package RPIS41.Lipatkin.wdad.learn.rmi;

import RPIS41.Lipatkin.wdad.learn.xml.XmlTask;

import java.io.IOException;
import java.util.Calendar;
import java.util.List;

import java.rmi.Remote;


public class XmlDataManagerImpl implements XmlDataManager {
    private XmlTask xmlTask;

    @Override
    public int earningsTotal(Officiant officiant, Calendar calendar) {
        return xmlTask.earningsTotal(officiant.getSecondName(), calendar);
    }

    @Override
    public void removeDay(Calendar calendar) throws IOException {
        xmlTask.removeDay(calendar);
    }

    @Override
    public void changeOfficiantName(Officiant oldOfficiant, Officiant newOfficiant) throws IOException {
        xmlTask.changeOfficiantName(oldOfficiant.getFirstName(), oldOfficiant.getSecondName(), 
                newOfficiant.getFirstName(), newOfficiant.getSecondName());
    }

    @Override
    public List<Order> getOrders(Calendar calendar) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Calendar lastOfficiantWorkDate(Officiant officiant) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
