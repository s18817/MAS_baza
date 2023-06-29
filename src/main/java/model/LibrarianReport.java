package model;

import exception.ValidationException;

import java.io.Serializable;
import java.time.LocalDate;

public class LibrarianReport extends Report implements Serializable {

    private Librarian librarian;
    private String summary;

    public LibrarianReport (String topic, boolean confidential, Librarian librarian) {
        super(topic, confidential);
        this.librarian = librarian;
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

    public Librarian getLibrarian () {
        return librarian;
    }

    public void setLibrarian (Librarian librarian) {
        if (librarian == null){
            throw new ValidationException("Librarian cannot be empty");
        }
        this.librarian = librarian;
    }

    public void showReport(){
        System.out.println (
                super.toString()  +
                        "Summary for " + "librarian: " + librarian.getName() + " "  + librarian.getSurname() + '\n' +
                        "Suggested bonus: " + librarian.getSalary() + '\n' +
                        "Done inventories: "  + '\n' +
                        librarian.getDoneInventories()
        );
    }

}

