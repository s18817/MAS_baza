package model;

import exception.ValidationException;
import jakarta.persistence.*;
import org.hibernate.annotations.GenericGenerator;

import java.io.Serializable;
import java.time.LocalDate;

@Entity(name = "model.Borrow")
@Table(name = "borrow")
public class Borrow implements Serializable {

    private long id;
    private LocalDate dateFrom;
    private LocalDate dateTo;
    private LocalDate actualTo; // faktyczna data oddania
    private boolean onTime;
    private String remarks;
    private Client client;
    private Book book;


    public Borrow(LocalDate from, LocalDate to, LocalDate actualTo, boolean onTime, String remarks) {
        setDateFrom(from);
        setDateTo(to);
        setActualTo(actualTo);
        setOnTime(onTime);
        setRemarks(remarks);
    }

    public Borrow(){}

    @Id
    @GeneratedValue(generator = "increment")
    @GenericGenerator(name = "increment", strategy = "increment")
    @Column(name = "borrow_id", nullable = false)
    public long getId () {
        return id;
    }

    public void setId (long id) {
        if (id < 0) {
            throw new ValidationException("ID cannot be negative");
        }
        this.id = id;
    }

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "client_id")
    public Client getClient () { return client;
    }

    public void setClient (Client client) {
        this.client = client;
    }

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "book_id")
    public Book getBook() {  return book;
    }

    public void setBook (Book book) {
        this.book = book;
    }

    @Basic
    public LocalDate getDateFrom() {
        return dateFrom;
    }

    public void setDateFrom(LocalDate dateFrom) {
        if (dateFrom == null){
            throw new ValidationException("Date cannot be empty");
        }
        else if (dateFrom.getYear() < 1900 ) {
            throw new ValidationException("Provide valid date");
        }
        this.dateFrom = dateFrom;
    }

    @Basic
    public LocalDate getDateTo() {
        return dateTo;
    }

    public void setDateTo(LocalDate dateTo) {
        if (dateTo == null){
            throw new ValidationException("Date cannot be empty");
        }
        else if (dateTo.getYear() < 1900 ) {
            throw new ValidationException("Provide valid date");
        }
        this.dateTo = dateTo;
    }

    @Basic
    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        if (remarks == null || remarks.trim().isBlank()) {
            throw new ValidationException("Remarks cannot be empty");
        }
        this.remarks = remarks;
    }

    @Basic
    public LocalDate getActualTo () {
        return actualTo;
    }

    public void setActualTo (LocalDate actualTo) {
        if (actualTo == null){
            throw new ValidationException("Date cannot be empty");
        }
        else if (actualTo.getYear() < 1900 ) {
            throw new ValidationException("Provide valid date");
        }
        this.actualTo = actualTo;
    }

    @Basic
    public boolean isOnTime () {
        return onTime;
    }

    public void setOnTime (boolean onTime) {
        this.onTime = onTime;
    }

    @Override
    public String toString() {
        return "Borrow{" +
                "from=" + dateFrom +
                ", to=" + dateTo +
                ", onTime=" + onTime +
                ", remarks='" + remarks + '\'' + "}\n" ;
    }

}
