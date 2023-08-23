package model;

import exception.ValidationException;
import jakarta.persistence.*;
import org.hibernate.annotations.GenericGenerator;

import java.io.Serializable;
import java.time.LocalDate;

@Entity(name = "model.LibrarianReport")
@Table(name = "librarian_report")
public class LibrarianReport implements Serializable {

    private long id;
    private LocalDate creationDate;
    private String topic;
    private boolean confidential;
    private Librarian librarian; // Kazdy raport jest dedykowany pracownikowi
    private int doneInventories;
    private double suggestedBonus;
    private String summary;

    public LibrarianReport (String topic, boolean confidential, String summary) {
        setTopic(topic);
        setConfidential(confidential);
        creationDate = LocalDate.now();
        setSummary(summary);
    }

    public LibrarianReport(){}

    @Id
    @GeneratedValue(generator = "increment")
    @GenericGenerator(name = "increment", strategy = "increment")
    @Column(name = "librarian_report_id", nullable = false)
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
    @JoinColumn(name = "librarian_id")
    public Librarian getLibrarian () {
        return librarian;
    }

    public void setLibrarian (Librarian librarian) {
        if (librarian == null){
            throw new ValidationException("Librarian cannot be empty");
        }
        this.librarian = librarian;
    }

    public void addLibrarianToReport(Librarian librarianReport){
        if ( librarianReport != null && this.librarian != librarianReport ) {
            this.librarian = librarianReport;
            librarianReport.generateReport(this); // polaczenie zwrotne
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

    public String getSummary () {
        return summary;
    }

    public void setSummary (String summary) {
        if (summary == null || summary.trim().isBlank()){
            throw new ValidationException("Summary cannot be empty");
        }
        this.summary = summary;
    }

    @Basic
    public int getDoneInventories () {
        return doneInventories;
    }

    public void setDoneInventories (int doneInventories) {
        this.doneInventories = doneInventories;
    }

    @Basic
    public double getSuggestedBonus () {
        return suggestedBonus;
    }

    public void setSuggestedBonus (double suggestedBonus) {
        this.suggestedBonus = suggestedBonus;
    }

    public void showReport(){
        System.out.println (
                super.toString()  +
                        "Summary for " + "librarian: " + librarian.getName() + " "  + librarian.getSurname() + '\n' +
                        "Suggested bonus: " + librarian.getBaseSalary() + '\n' +
                        "Done inventories: "  + '\n' +
                        librarian.getInventories()
        );
    }

}

