package model;

import java.io.Serializable;
import java.time.LocalDate;

public class LibrarianReport extends Report implements Serializable {

    private Librarian librarian;

    public LibrarianReport (String topic, boolean confidential, Librarian librarian) {
        super(topic, confidential);
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

