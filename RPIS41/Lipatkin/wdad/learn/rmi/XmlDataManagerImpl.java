package RPIS41.Lipatkin.wdad.learn.rmi;

import RPIS41.Lipatkin.wdad.learn.xml.XmlTask;

import java.io.IOException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
        HashMap<String, String> orderMap = xmlTask.getOrders(calendar);
        List<Order> orderList = new ArrayList<>();
        Officiant officiant;
        List<Item> itemList = new ArrayList<>();
        String[] splitter, itemsSplitter;

        for (Map.Entry<String, String> entry : orderMap.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();
            if ("".equals(key)) continue;
            
            splitter = value.split(" ");
            officiant = new Officiant(splitter[0], splitter[1]);
            
            itemsSplitter = key.split("##");
            for (int i = 0; i < itemsSplitter.length; i++) {
                splitter = itemsSplitter[i].split("%");
                itemList.add(new Item(splitter[0], Integer.parseInt(splitter[1])));
            }
            orderList.add(new Order(officiant, itemList));
        }
        return orderList;
    }

    @Override
    public Calendar lastOfficiantWorkDate(Officiant officiant) throws NoSuchOfficiantException {
        Calendar result = xmlTask.lastOfficiantWorkDate(officiant.getFirstName(), officiant.getSecondName());
        if (result == null) {
            throw new NoSuchOfficiantException("Officiant not found: " + officiant.getFirstName() + " " + officiant.getSecondName());
        } else {
            return result;
        }
    }
}
