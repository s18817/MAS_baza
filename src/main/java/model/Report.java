package model;

import exception.ValidationException;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public abstract class Report extends ObjectPlus implements Serializable {

    private LocalDate creationDate;
    private String topic;
    private boolean confidential;
    private Director reportAuthor; // Kazdy raport ma jednego autora

    public Report(String topic, boolean confidential){
        super();
        creationDate = LocalDate.now();
        this.setTopic(topic);
        this.confidential = confidential;
    }

    public abstract void showReport() throws Exception;


    @Override
    public String toString () {
        return  "\nReport name: " + topic + '\n' +
                "Creation date: " + creationDate + '\n' +
                "Confidentiality: " + confidential + '\n';
    }


    public void addReportAuthor(Director reportAuthor){
        if ( reportAuthor != null && this.reportAuthor != reportAuthor ) {
            this.reportAuthor = reportAuthor;
            reportAuthor.generateReport(this); // polacze
        }
    }

    public void setTopic (String topic) {
        if (topic == null || topic.trim().isBlank()){
            throw new ValidationException("Topic cannot be empty");
        }
        this.topic = topic;
    }

    public void setCreationDate (LocalDate creationDate) {
        this.creationDate = creationDate;
    }

    public void setConfidential (boolean confidential) {
        this.confidential = confidential;
    }

    public Director getReportAuthor () {
        return reportAuthor;
    }

    public void setReportAuthor (Director reportAuthor) {
        this.reportAuthor = reportAuthor;
    }

    public LocalDate getCreationDate() {
        return creationDate;
    }

    public String getTopic() {
        return topic;
    }

    public boolean isConfidential() {
        return confidential;
    }



}
