package model;

import exception.ValidationException;

import java.io.Serializable;
import java.util.Set;

public class BookReport extends Report implements Serializable {

    private String propositions;

    public BookReport (String topic, boolean confidential) {
        super(topic, confidential);
    }

    public String getPropositions () {
        return propositions;
    }

    public void setPropositions (String propositions) {
        if (propositions == null || propositions.trim().isBlank()){
            throw new ValidationException("Propositions cannot be empty");
        }
        this.propositions = propositions;
    }

    public void showReport () throws Exception {
        System.out.println("List of all books:");
        Book.showExtent();
        System.out.println(Book.printBooksConditon());
    }

}

