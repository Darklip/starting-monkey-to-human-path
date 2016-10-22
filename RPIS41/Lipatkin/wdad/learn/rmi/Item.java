package RPIS41.Lipatkin.wdad.learn.rmi;

import java.io.Serializable;

public class Item implements Serializable {
    private final String name;
    private final int cost;
    
    public Item(String name, int cost) {
        this.name = name;
        this.cost = cost;
    }
    
    public String getName() {
        return name;
    }
    
    public int getCost() {
        return cost;
    }
}
