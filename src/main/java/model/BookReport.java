package model;

import java.io.Serializable;
import java.util.Set;

public class BookReport extends Report implements Serializable {

    public BookReport (String topic, boolean confidential) {
        super(topic, confidential);
    }


    public void showReport () throws Exception {
        System.out.println("List of all books:");
        Book.showExtent();
        System.out.println(Book.printBooksConditon());
    }

}

