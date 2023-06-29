package model;

import exception.ValidationException;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Inventory extends ObjectPlus implements Serializable {

    private String sector;
    private String status;
    private String notes;
    private LocalDate date;

    public Inventory(String sector, String status, String notes, LocalDate date) {
        setSector(sector);
        setStatus(status);
        setNotes(notes);
        setDate(date);
    }


    public String getSector() {
        return sector;
    }

    public String getStatus() {
        return status;
    }

    public String getNotes() {
        return notes;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setSector (String sector) {
        if (sector == null || sector.trim().isBlank()){
            throw new ValidationException("Sector cannot be empty");
        }
        this.sector = sector;
    }

    public void setStatus (String status) {
        if (status == null || status.trim().isBlank()){
            throw new ValidationException("Status cannot be empty");
        }
        this.status = status;
    }

    public void setNotes (String notes) {
        if (notes == null || notes.trim().isBlank()){
            throw new ValidationException("Sector cannot be empty");
        }
        this.notes = notes;
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
