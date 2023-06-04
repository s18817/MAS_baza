package model;

import java.io.Serializable;

public class RestorerReport extends Report implements Serializable {

    private Restorer restorer;

    public RestorerReport (String topic, boolean confidential, Restorer restorer) {
        super(topic, confidential);
        this.restorer = restorer;
    }

    public void showReport(){
        System.out.println(super.toString()  +
                "Summary for " + "restorer: " + restorer.getName() + " "  + restorer.getSurname() + '\n' +
                "Suggested bonus: " + restorer.getBaseSalary() + '\n' +
                "Done renovations: " +  '\n' +
                restorer.getDoneRenovations()
        );
    }
}

