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
        this.sector = sector;
        this.status = status;
        this.notes = notes;
        this.date = date;
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
