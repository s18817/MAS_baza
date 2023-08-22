package model;

import exception.ValidationException;
import jakarta.persistence.*;
import org.hibernate.annotations.GenericGenerator;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity(name = "model.Inventory")
@Table(name = "inventory")
public class Inventory  implements Serializable {

    private long id;
    private String sector;
    private String status;
    private String notes;
    private LocalDate date;
    //private List<Librarian> librariansList;
    private Librarian librarian;
    private Rack rack;

    public Inventory(String sector, String status, String notes, LocalDate date) {
        setSector(sector);
        setStatus(status);
        setNotes(notes);
        setDate(date);
    }

    public Inventory(){};

    @Id
    @GeneratedValue(generator = "increment")
    @GenericGenerator(name = "increment", strategy = "increment")
    @Column(name = "inventory_id", nullable = false)
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
    @JoinColumn(name = "librarian_id")
    public Librarian getLibrarian () { return librarian;
    }

    public void setLibrarian (Librarian librarian) {
        this.librarian = librarian;
    }

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "rack_id")
    public Rack getRack() {  return rack;
    }

    public void setRack (Rack rack) {
        this.rack = rack;
    }


    @Basic
    public String getSector() {
        return sector;
    }

    public void setSector (String sector) {
        if (sector == null || sector.trim().isBlank()){
            throw new ValidationException("Sector cannot be empty");
        }
        this.sector = sector;
    }


    @Basic
    public String getStatus() {
        return status;
    }

    public void setStatus (String status) {
        if (status == null || status.trim().isBlank()){
            throw new ValidationException("Status cannot be empty");
        }
        this.status = status;
    }

    @Basic
    public String getNotes() {
        return notes;
    }

    public void setNotes (String notes) {
        if (notes == null || notes.trim().isBlank()){
            throw new ValidationException("Sector cannot be empty");
        }
        this.notes = notes;
    }

    @Basic
    public LocalDate getDate() {
        return date;
    }

    public void setDate (LocalDate date) {
        if (date == null){
            throw new ValidationException("Date cannot be empty");
        }
        else if (date.getYear() < 1900 ) {
            throw new ValidationException("Provide valid date");
        }
        this.date = date;
    }

    @Override
    public String toString() {
        return "Inventory{" +
                ", sector='" + sector + '\'' +
                ", status='" + status + '\'' +
                ", notes='" + notes + '\'' +
                ", date=" + date +
                '}';
    }

}
