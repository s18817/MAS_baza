package model;

import exception.ValidationException;

import java.io.Serializable;
import java.util.Set;

public class BookReport extends Report implements Serializable {

    private String propositions;

    public BookReport (String topic, boolean confidential, String propositions) {
        super(topic, confidential);
        setPropositions(propositions);
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
        System.out.println(super.toString() +
        "List of all books:");
        Book.showExtent();
        System.out.println(Book.printBooksConditon());
    }

    public static void showExtent() throws Exception {
        ObjectPlus.showExtent(BookReport.class);
    }

    public static void getExtent() throws Exception {
        ObjectPlus.getExtent(BookReport.class);
    }

}

