package RPIS41.Lipatkin.wdad.learn.rmi;

import java.io.Serializable;

public class Officiant implements Serializable {
    private final String firstName;
    private final String secondName;
    
    public Officiant(String firstName, String secondName) {
        this.firstName = firstName;
        this.secondName = secondName;
    }
    
    public String getFirstName() {
        return firstName;
    }
    
    public String getSecondName() {
        return secondName;
    }
}
