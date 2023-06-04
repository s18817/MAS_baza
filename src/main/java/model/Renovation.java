package model;

import enums.Status;
import exception.ValidationException;
import jakarta.persistence.*;
import org.hibernate.annotations.GenericGenerator;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity(name = "model.Renovation")
public class Renovation implements Serializable {

    private long id;
    private LocalDate renovationDate;
    private Set<String> materials = new HashSet<>();
    private Status status;
    private String result;

    public Renovation(LocalDate renovationDate, Set<String> material, Status status, String result) {
        setRenovationDate(renovationDate);
        setMaterials(materials);
        setStatus(status);
        setResult(result);
    }

    @ManyToOne
    @JoinColumn(name = "restorer_id")
    private Restorer restorer;

    ManyToOne
    @JoinColumn(name = "book_id");
    private Book book;

    @Id()
    @GeneratedValue(generator="increment")
    @GenericGenerator(name="increment", strategy = "increment")
    public long getId() {
        return id;
    }

    public void setId(long id) {
        if (id < 0){
            throw new ValidationException("ID cannot be negative");
        }
        this.id = id;
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

    @ElementCollection
    public Set<String> getMaterials () {
        return materials;
    }
    public void setMaterials (Set<String> materials) {
        if (materials == null) {
            throw new ValidationException("Materials cannot be empty");
        }
        this.materials = materials;
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
        return "Renovation{" +
                "renovationDate=" + renovationDate +
                ", materials=" + materials +
                ", status=" + status +
                ", result='" + result + '\'' +
                '}';
    }

}
