package model;

import enums.State;
import exception.ValidationException;
import jakarta.persistence.*;
import org.hibernate.annotations.GenericGenerator;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Entity(name = "model.Client")
@Table(name = "client")
public class Client implements Serializable {

    String regex = "^(.+)@(.+)$"; // walidacja adresu email
    Pattern pattern = Pattern.compile(regex);

    private List<Borrow> borrowDetails = new ArrayList<>(); // asocjacja z atrybutem ; kolekcja do przetrzymywania historii wypozyczen ksiazki ; kolekcja, poniewaz jedna ksiazka moze miec wiele wypozyczen

    private long id;
    private String name;
    private String surname;
    private LocalDate birthDate;
    private String gender;
    private String nationality;
    private boolean regularClient;
    private String opinion;
    private String email;


    public Client (String name, String surname, LocalDate birthDate, String gender, String nationality, String opinion, String email ){
        setName(name);
        setSurname(surname);
        setBirthDate(birthDate);
        setGender(gender);
        setNationality(nationality);
        this.regularClient = false;
        setOpinion(opinion);
        setEmail(email);
    }

    public Client(){}

    @Id()
    @GeneratedValue(generator="increment")
    @GenericGenerator(name="client_id", strategy = "increment")
    public long getId () {
        return id;
    }
    public void setId (long id) {
        if (id < 0){
            throw new ValidationException("ID cannot be negative");
        }
        this.id = id;
    }

    @OneToMany(mappedBy = "client", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    public List<Borrow> getBorrowDetails() {
        return borrowDetails;
    }

    public void setBorrowDetails (List<Borrow> borrows) { this.borrowDetails = borrows;
    }

    public void addBorrowToClient (Book book, Borrow newBorrow) {

        if (!borrowDetails.contains(newBorrow)) {
            if (book.getState() == State.DOSTĘPNA) {
                borrowDetails.add(newBorrow);
                book.setState(State.WYPOŻYCZONA); // ksiazka zostala wypozyczena, nie jest juz dostepna dla innych
                if (this.regularClient != true && borrowDetails.size() > 9) {
                    this.setRegularClientTrue(); // klient staje sie stalym klientem przy dziesiatym wypozyczeniu
                }
                if(newBorrow.getBook() == null && newBorrow.getClient() == null){
                    newBorrow.setBook(book);
                    newBorrow.setClient(this);
                }
                book.addBorrowToBook(this, newBorrow); // polaczenie zwrotne
            } else {
                throw new ValidationException("Book is not available and cannot be borrowed");
            }
        }
    }

    @Basic
    public String getName() {
        return name;
    }

    public void setName(String name){
        if (name == null || name.trim().isBlank()){
            throw new ValidationException("Name cannot be empty");
        }
        this.name = name;
    }

    @Basic
    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        if (surname == null || surname.trim().isBlank()){
            throw new ValidationException("Surname cannot be empty");
        }
        this.surname = surname;
    }

    @Basic
    public LocalDate getBirthDate() {
        return birthDate;
    }


    public void setBirthDate(LocalDate birthDate) {
        if (birthDate == null){
            throw new ValidationException("Birth date cannot be empty");
        }
        else if (birthDate.getYear() < 1900 ) {
            throw new ValidationException("Provide valid birth date");
        }
        this.birthDate = birthDate;
    }

    @Basic
    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        if (gender == null || gender.trim().isBlank()){
            throw new ValidationException("Gender cannot be empty");
        }
        this.gender = gender;
    }

    @Basic
    public String getNationality() {
        return nationality;
    }

    public void setNationality(String nationality) {
        if (nationality == null || nationality.trim().isBlank()){
            throw new ValidationException("Nationality cannot be empty");
        }
        this.nationality = nationality;
    }


    public void returnBook (Borrow borrowToReturn, Book bookToReturn){
        borrowToReturn.setActualTo(LocalDate.now());
        if(borrowToReturn.getActualTo().isAfter(borrowToReturn.getDateTo())) {
            borrowToReturn.setOnTime(false);
        }
        else{
            borrowToReturn.setOnTime(true);
        }
        bookToReturn.setState(State.DOSTĘPNA); // ksiazka jest juz dostepna do ponownego wypozyczenia
    }

    @Basic
    public String getOpinion () {
        return opinion;
    }

    public void setOpinion(String opinion) {
        if (opinion == null || opinion.trim().isBlank()){
            throw new ValidationException("Opinion cannot be empty");
        } else if (opinion.length() > 50) {
            throw new ValidationException("Opinion cannot be longer that 50 digits");
        }
        this.opinion = opinion;
    }

    @Basic
    public String getEmail () {
        return email;
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

    @Basic
    public boolean isRegularClient() {
        return regularClient;
    }

    public void setRegularClient (boolean regularClient) {
        this.regularClient = regularClient;
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

}
