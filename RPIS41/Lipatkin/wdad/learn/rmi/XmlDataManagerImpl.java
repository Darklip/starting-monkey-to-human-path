package RPIS41.Lipatkin.wdad.learn.rmi;

import RPIS41.Lipatkin.wdad.learn.xml.XmlTask;

import java.io.IOException;
import java.rmi.RemoteException;
import java.util.Calendar;
import java.util.List;

public class XmlDataManagerImpl implements XmlDataManager {
    private final XmlTask xmlTask = new XmlTask();

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
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Calendar lastOfficiantWorkDate(Officiant officiant) throws NoSuchOfficiantException {
        Calendar result = xmlTask.lastOfficiantWorkDate(officiant.getFirstName(), officiant.getSecondName());
        if (result == null)
            throw new NoSuchOfficiantException("Officiant not found: "+officiant.getFirstName()+" "+officiant.getSecondName());
        else return result;
    }

    @Override
    public String checkWork() throws RemoteException {
        return "It works!!!";
    }
}
