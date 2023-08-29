package model;

import exception.ValidationException;
import jakarta.persistence.*;
import org.hibernate.annotations.GenericGenerator;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import static model.Book.booksFromDb;

@Entity(name = "model.BookReport")
@Table(name = "book_report")
public class BookReport implements Serializable {

    public static List<BookReport> bookReportsFromDb = new ArrayList<>();

    private long id;
    private LocalDate creationDate;
    private String topic;
    private boolean confidential;
    private String propositions;
    private int bookAmount;
    private Director director; // Kazdy raport ma jednego autora

    public BookReport (String topic, boolean confidential, String propositions) {
        setTopic(topic);
        setConfidential(confidential);
        creationDate = LocalDate.now();
        setPropositions(propositions);
        setBookAmount(booksFromDb.size());
    }

    public BookReport(){}

    @Id
    @GeneratedValue(generator = "increment")
    @GenericGenerator(name = "increment", strategy = "increment")
    @Column(name = "book_report_id", nullable = false)
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
    @JoinColumn(name = "director_id")
    public Director getDirector () {
        return director;
    }

    public void setDirector(Director reportAuthor) {
        this.director = reportAuthor;
    }

    public void addReportAuthor(Director reportAuthor){
        if ( reportAuthor != null && this.director != reportAuthor ) {
            this.director = reportAuthor;
            reportAuthor.generateReport(this); // polaczenie zwrotne
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
    public String getPropositions () {
        return propositions;
    }

    public void setPropositions (String propositions) {
        if (propositions == null || propositions.trim().isBlank()){
            throw new ValidationException("Propositions cannot be empty");
        }
        this.propositions = propositions;
    }

    @Basic
    public int getBookAmount () {
        return bookAmount;
    }

    public void setBookAmount (int bookAmount) {
        if (bookAmount < 0) {
            throw new ValidationException("Amount cannot be negative");
        }
        this.bookAmount = bookAmount;
    }

    public void showReport () throws Exception {
        System.out.println(super.toString() +
        "List of all books:");
        System.out.println(Book.printBooksConditon());
    }

    @Override
    public String toString () {
        return "BookReport{" +
                "id=" + id +
                ", creationDate=" + creationDate +
                ", topic='" + topic + '\'' +
                ", confidential=" + confidential +
                ", propositions='" + propositions + '\'' +
                ", bookAmount=" + bookAmount +
                //", director=" + director +
                '}';
    }

    public static List<BookReport> getBookReportsFromDb () {
        return bookReportsFromDb;
    }
}

