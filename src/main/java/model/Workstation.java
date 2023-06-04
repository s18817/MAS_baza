package model;

import model.Library;
import org.hibernate.annotations.GenericGenerator;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

@Entity(name = "model.Workstation")
public class Workstation {

    private long id;
    private String name;
    private String operatingSystem;
    private String dedication;

    private Library library;

    public Workstation(String name, String operatingSystem, String dedication) {
        this.name = name;
        this.operatingSystem = operatingSystem;
        this.dedication = dedication;
    }

    @Id()
    @GeneratedValue(generator="increment")
    @GenericGenerator(name="increment", strategy = "increment")
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOperatingSystem() {
        return operatingSystem;
    }

    public void setOperatingSystem(String operatingSystem) {
        this.operatingSystem = operatingSystem;
    }

    public String getDedication() {
        return dedication;
    }

    public void setDedication(String dedication) {
        this.dedication = dedication;
    }

    @ManyToOne
    public Library getLibrary() {
        return library;
    }

    public void setLibrary(Library library) {
        this.library = library;
    }

}
