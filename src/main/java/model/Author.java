package model;

import exception.ValidationException;
import org.hibernate.annotations.GenericGenerator;
import jakarta.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

//@Entity(name = "model.Author")
public class Author extends Person implements Serializable {

    private List<Book> books = new ArrayList<>(); // asocjacja binarna ; kolekcja do przetrzymywania powiazan z Autorami ; kolekcja, poniewaz jednen autor moze byc autorem wielu ksiazek

    //private long id;
    private String pseudonym;
//    private String name;
//    private String surname;
//    private LocalDate birthDate;
//    private String gender;
//    private String nationality;

    public Author(String name, String surname, LocalDate birthDate, String nationality, String gender, String pseudonym) {
        super(name, surname, birthDate, nationality, gender);
        //setId(id);
        setName(name);
        setSurname(surname);
        setBirthDate(birthDate);
        setGender(gender);
        setNationality(nationality);
        setPseudonym(pseudonym);
    }

    public Author(String name, String surname, LocalDate birthDate, String nationality, String gender ) {
        super(name, surname, birthDate, nationality, gender );
//        setId(id);
//        setName(name);
//        setSurname(surname);
//        setBirthDate(birthDate);
//        setGender(gender);
//        setNationality(nationality);
    }

    //@ManyToMany
    public List<Book> getBooks() {
        return books;
    }

    public void setBooks(List<Book> booksToAdd) {
        this.books = booksToAdd;
    }

    public void addBook(Book bookToAdd) {
        getBooks().add(bookToAdd);
    }

    public void removeBook(Book bookToRemove) {
        getBooks().remove(bookToRemove);

    }


    //@Id()
    //@GeneratedValue(generator="increment")
    //@GenericGenerator(name="increment", strategy = "increment")
//    public long getId () {
//        return id;
//    }
//    public void setId (long id) {
//        if (id < 0){
//            throw new ValidationException("ID cannot be negative");
//        }
//        this.id = id;
//    }
//
//   // @Basic
//    public String getName() {
//        return name;
//    }
//
//    public void setName(String name){
//        if (name == null || name.trim().isBlank()){
//            throw new ValidationException("Name cannot be empty");
//        }
//        this.name = name;
//    }
//
//    //@Basic
//    public String getSurname() {
//        return surname;
//    }
//
//
//    public void setSurname(String surname) {
//        if (surname == null || surname.trim().isBlank()){
//            throw new ValidationException("Surname cannot be empty");
//        }
//        this.surname = surname;
//    }
//
//    //@Basic
//    public LocalDate getBirthDate() {
//        return birthDate;
//    }
//
//
//    public void setBirthDate(LocalDate birthDate) {
//        if (birthDate == null){
//            throw new ValidationException("Birth date cannot be empty");
//        }
//        this.birthDate = birthDate;
//    }
//
//    //@Basic
//    public String getGender() {
//        return gender;
//    }
//
//
//    public void setGender(String gender) {
//        if (gender == null){
//            throw new ValidationException("Gender cannot be empty");
//        }
//        this.gender = gender;
//    }
//
//    //@Basic
//    public String getNationality() {
//        return nationality;
//    }
//
//
//    public void setNationality(String nationality) {
//        if (nationality == null){
//            throw new ValidationException("Nationality cannot be empty");
//        }
//        this.nationality = nationality;
//    }

    //@Basic(optional = true)
    public String getPseudonym () {
        return pseudonym;
    }

    public void setPseudonym (String pseudonym) {
        this.pseudonym = pseudonym;
    }


    public void addBookToAuthor(Book bookToAdd) {

        if (!books.contains(bookToAdd)) {
            books.add(bookToAdd);

            bookToAdd.addAuthor(this); // polaczenie zwrotne
        }
    }


    @Override
    public String toString() {
        return super.toString() + " Author{" +
                "books=" + books +
                ", pseudonym='" + pseudonym + '\'' +
                '}';
    }

    public static void showExtent() throws Exception {
        ObjectPlus.showExtent(Author.class);
    }

    public static void getExtent() throws Exception {
        ObjectPlus.getExtent(Author.class);
    }
}
