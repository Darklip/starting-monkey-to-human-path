package RPIS41.Lipatkin.wdad.learn.rmi;

import java.io.Serializable;
import java.util.List;

public class Order implements Serializable {
    private final Officiant officiant;
    private final List<Item> items;
    
    public Order(Officiant officiant, List<Item> items) {
        this.officiant = officiant;
        this.items = items;
    }
    
    public Officiant getOfficiant() {
        return officiant;
    }
    
    public List<Item> getItems() {
        return items;
    }
}
