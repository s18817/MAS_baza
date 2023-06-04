package model;

import exception.ValidationException;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public abstract class Report implements Serializable {


    private LocalDate creationDate;
    private String topic;
    private boolean confidential;
    private Director reportAuthor; // Kazdy raport ma jednego autora

    public Report(String topic, boolean confidential){
        creationDate = LocalDate.now();
        this.setTopic(topic);
        this.confidential = confidential;
    }

    public abstract void showReport() throws Exception;


    @Override
    public String toString () {
        return  "Report name: " + topic + '\n' +
                "Creation date: " + creationDate + '\n' +
                "Confidentiality: " + confidential + '\n' +
                "Author: " + reportAuthor.getName() + " " + reportAuthor.getSurname() + '\n';
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
