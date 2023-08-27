package model;

import exception.ValidationException;
import jakarta.persistence.*;
import org.hibernate.annotations.GenericGenerator;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity(name = "model.RestorerReport")
@Table(name = "restorer_report")
public class RestorerReport implements Serializable {

    public static List<RestorerReport> restorerReportsFromDb = new ArrayList<>();

    private long id;
    private LocalDate creationDate;
    private String topic;
    private boolean confidential;
    private int doneRenovations;
    private double suggestedBonus;
    private Restorer restorer;
    private double avgCost;

    public RestorerReport (String topic, boolean confidential, double avgCost) {
        setTopic(topic);
        setConfidential(confidential);
        creationDate = LocalDate.now();
        setAvgCost(avgCost);
    }

    public RestorerReport(){};

    @Id
    @GeneratedValue(generator = "increment")
    @GenericGenerator(name = "increment", strategy = "increment")
    @Column(name = "restorer_report_id", nullable = false)
    public long getId () {
        return id;
    }

    public void setId (long id) {
        if (id < 0) {
            throw new ValidationException("ID cannot be negative");
        }
        this.id = id;
    }

    @ManyToOne
    @JoinColumn(name = "restorer_id")
    public Restorer getRestorer () {
        return restorer;
    }

    public void setRestorer (Restorer restorer) {
        if (restorer == null){
            throw new ValidationException("Restorer cannot be empty");
        }
        this.restorer = restorer;
    }

    public void addRestorerToReport(Restorer restorerReport){
        if ( restorerReport != null && this.restorer != restorerReport ) {
            this.restorer = restorerReport;
            restorerReport.generateReport(this); // polaczenie zwrotne
        }
    }

    @Basic
    public LocalDate getCreationDate () {
        return creationDate;
    }

    public void setCreationDate (LocalDate creationDate) {
        if (creationDate == null){
            throw new ValidationException("Creation date cannot be empty");
        }
        else if (creationDate.getYear() < 1900 ) {
            throw new ValidationException("Provide valid renovation date");
        }
        this.creationDate = creationDate;
    }

    @Basic
    public String getTopic () {
        return topic;
    }

    public void setTopic (String topic) {
        if (topic == null || topic.trim().isBlank()){
            throw new ValidationException("Topic cannot be empty");
        }
        this.topic = topic;
    }

    @Basic
    public boolean isConfidential () {
        return confidential;
    }

    public void setConfidential (boolean confidential) {
        this.confidential = confidential;
    }

    @Basic
    public double getAvgCost () {
        return avgCost;
    }

    public void setAvgCost (double avgCost) {
        if (avgCost < 0){
            throw new ValidationException("Average cost cannot be negative");
        }
        this.avgCost = avgCost;
    }

    @Basic
    public int getDoneRenovations () {
        return doneRenovations;
    }

    public void setDoneRenovations (int doneRenovations) {
        this.doneRenovations = doneRenovations;
    }

    @Basic
    public double getSuggestedBonus () {
        return suggestedBonus;
    }

    public void setSuggestedBonus (double suggestedBonus) {
        this.suggestedBonus = suggestedBonus;
    }

    public void showReport(){
        System.out.println(super.toString()  +
                "Summary for " + "restorer: " + restorer.getName() + " "  + restorer.getSurname() + '\n' +
                "Suggested bonus: " + (restorer.getBaseSalary() + (restorer.getRenovations().size() * 100)) + '\n' +
                "Estimated cost of done renovations: " + restorer.getRenovations().size() * avgCost  + '\n' +
                "Done renovations: " +  '\n' +
                restorer.getRenovations()
        );
    }

    @Override
    public String toString () {
        return "RestorerReport{" +
                "id=" + id +
                ", creationDate=" + creationDate +
                ", topic='" + topic + '\'' +
                ", confidential=" + confidential +
                ", doneRenovations=" + doneRenovations +
                ", suggestedBonus=" + suggestedBonus +
                ", restorer=" + restorer +
                ", avgCost=" + avgCost +
                '}';
    }

    public static List<RestorerReport> getRestorerReportsFromDb () {
        return restorerReportsFromDb;
    }
}

