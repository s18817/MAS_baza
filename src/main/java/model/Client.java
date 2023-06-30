package model;

import enums.State;
import exception.ValidationException;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Client extends Person implements Serializable {

    String regex = "^(.+)@(.+)$"; // walidacja adresu email
    Pattern pattern = Pattern.compile(regex);

    private List<Borrow> borrowDetails = new ArrayList<>(); // asocjacja z atrybutem ; kolekcja do przetrzymywania historii wypozyczen ksiazki ; kolekcja, poniewaz jedna ksiazka moze miec wiele wypozyczen

    private boolean regularClient;
    private String opinion;
    private String email;


    public Client (String name, String surname, LocalDate birthDate, String gender, String nationality, boolean regularClient, String opinion, String email ){
        super(name, surname, birthDate, nationality, gender );
        this.regularClient = regularClient;
        setOpinion(opinion);
        setEmail(email);
    }

    public void addBorrowToClient (Book book, Borrow newBorrow) {

        if (!borrowDetails.contains(newBorrow)) {
            if (book.getState() == State.DOSTĘPNA) {
                borrowDetails.add(newBorrow);
                if (this.regularClient != true && borrowDetails.size() > 9) {
                    this.setRegularClientTrue(); // klient staje sie stalym klientem przy dziesiatym wypozyczeniu
                }
                book.addBorrowToBook(this, newBorrow); // polaczenie zwrotne
            } else {
                throw new ValidationException("Book is not available and cannot be borrowed");
            }
        }
    }

    public void returnBook (Borrow borrowToReturn, Book bookToReturn){
        borrowToReturn.setActualTo(LocalDate.now());
        if(borrowToReturn.getActualTo().isAfter(borrowToReturn.getTo())) {
            borrowToReturn.setOnTime(false);
        }
        else{
            borrowToReturn.setOnTime(true);
        }
        bookToReturn.setState(State.DOSTĘPNA); // ksiazka jest juz dostepna do ponownego wypozyczenia
    }


    public void setOpinion(String opinion) {
        if (opinion == null || opinion.trim().isBlank()){
            throw new ValidationException("Opinion cannot be empty");
        } else if (opinion.length() > 50) {
            throw new ValidationException("Opinion cannot be longer that 50 digits");
        }
        this.opinion = opinion;
    }

    public void setEmail (String email) {

        if (email == null || email.trim().isBlank()) {
            throw new ValidationException("Email address cannot be empty");
        } else {
            Matcher matcher = pattern.matcher(email);
            if (!matcher.matches()) {
                throw new ValidationException("Email address has incorrect form");
            }
        }
        this.email = email;
    }


    public List<Borrow> getBorrowDetails() {
        return borrowDetails;
    }

    public boolean isRegularClient() {
        return regularClient;
    }

    public void setRegularClientTrue () {
        this.regularClient = true;
    }

    @Override
    public String toString() {
        return super.toString() + "Client{" +
                "borrowDetails=" + borrowDetails +
                ", regularClient=" + regularClient +
                ", opinion='" + opinion + '\'' +
                '}';
    }

    public static void showExtent() throws Exception {
        ObjectPlus.showExtent(Client.class);
    }

    public static void getExtent() throws Exception {
        ObjectPlus.getExtent(Client.class);
    }
}
