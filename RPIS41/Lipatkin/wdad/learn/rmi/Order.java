package RPIS41.Lipatkin.wdad.learn.rmi;

import java.util.List;

public class Order {
    private final Officiant officiant;
    private List<Item> items;
    
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
