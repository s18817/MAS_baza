package model;

import enums.Status;
import exception.ValidationException;
import jakarta.persistence.*;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.GenericGenerator;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity(name = "model.Renovation")
@Table(name = "renovation")

public class Renovation implements Serializable {

    private long id;
    private LocalDate renovationDate;
    private Set<String> materials = new HashSet<>();
    private Status status;
    private String result;
    private Book book;
    private Restorer restorer;

    public static List<Renovation> renovationsForBookFromDb = new ArrayList<>();
    public static List<Renovation> renovationsFromDb = new ArrayList<>();


    public Renovation(int id, LocalDate renovationDate, Set<String> materials, Status status, String result) {
        setId(id);
        setRenovationDate(renovationDate);
        setMaterials(materials);
        setStatus(status);
        setResult(result);
    }

    public Renovation(){};



    @Id()
    @GeneratedValue(generator="increment")
    @GenericGenerator(name="increment", strategy = "increment")
    @Column(name = "renovation_id", nullable = false)
    public long getId() {
        return id;
    }

    public void setId(long id) {
        if (id < 0){
            throw new ValidationException("ID cannot be negative");
        }
        this.id = id;
    }
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "restorer_id")
    public Restorer getRestorer () {
        return restorer;
    }

    public void setRestorer (Restorer restorer) {
        this.restorer = restorer;
    }

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "book_id")
    public Book getBook () {
        return book;
    }

    public void setBook (Book book) {
        this.book = book;
    }


    @ElementCollection(fetch = FetchType.EAGER)
    @Fetch(FetchMode.SELECT)
    public Set<String> getMaterials () {
        return materials;
    }
    public void setMaterials (Set<String> materials) {
        if (materials == null) {
            throw new ValidationException("Materials cannot be empty");
        }
        this.materials = materials;
    }

    @Basic
    public LocalDate getRenovationDate () {
        return renovationDate;
    }
    public void setRenovationDate (LocalDate renovationDate) {
        if (renovationDate == null){
            throw new ValidationException("Renovation date date cannot be empty");
        }
        else if (renovationDate.getYear() < 1900 ) {
            throw new ValidationException("Provide valid renovation date");
        }
        this.renovationDate = renovationDate;
    }


    @Enumerated(EnumType.STRING)
    public Status getStatus () {
        return status;
    }
    public void setStatus (Status status) {
        if (status == null){
            throw new ValidationException("Status cannot be empty");
        }
        this.status = status;
    }

    @Basic
    public String getResult () {
        return result;
    }
    public void setResult (String result) {
        if (result == null || result.trim().isBlank()) {
            throw new ValidationException("Result cannot be empty");
        }
        this.result = result;
    }


    @Override
    public String toString () {
        return
                "Data wykonania: " + renovationDate +
                ", wykorzystane materiały: " + materials +
                ", status: " + status +
                ", wynik: '" + result + '\'' +
                '}';
    }

}
