package model;

import exception.ValidationException;

import java.io.Serializable;

public class RestorerReport extends Report implements Serializable {

    private Restorer restorer;
    private double avgCost;

    public RestorerReport (String topic, boolean confidential, Restorer restorer, double avgCost) {
        super(topic, confidential);
        setAvgCost(avgCost);
        setRestorer(restorer);
    }

    public Restorer getRestorer () {
        return restorer;
    }

    public void setRestorer (Restorer restorer) {
        if (restorer == null){
            throw new ValidationException("Restorer cannot be empty");
        }
        this.restorer = restorer;
    }

    public double getAvgCost () {
        return avgCost;
    }

    public void setAvgCost (double avgCost) {
        if (avgCost< 0){
            throw new ValidationException("Average cost cannot be negative");
        }
        this.avgCost = avgCost;
    }


    public void showReport(){
        System.out.println(super.toString()  +
                "Summary for " + "restorer: " + restorer.getName() + " "  + restorer.getSurname() + '\n' +
                "Suggested bonus: " + restorer.getBaseSalary() + '\n' +
                "Estimated cost of done renovations: " + restorer.getRenovations().size() * avgCost  + '\n' +
                "Done renovations: " +  '\n' +
                restorer.getRenovations()
        );
    }

    public static void showExtent() throws Exception {
        ObjectPlus.showExtent(RestorerReport.class);
    }

    public static void getExtent() throws Exception {
        ObjectPlus.getExtent(RestorerReport.class);
    }
}

