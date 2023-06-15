package model;

import enums.Condition;
import enums.State;
import exception.ValidationException;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.Year;
import java.awt.*;
import java.util.*;
import java.util.List;

import org.hibernate.annotations.GenericGenerator;
import jakarta.persistence.*;

@Entity(name = "model.Book")
@Table(name = "book")
public class Book extends ObjectPlus implements Serializable {

    private static String CurrentbookRentalName = "S18817"; // atrybut klasowy

    private long id;
    private String title;
    private Set<String> category = new HashSet<>(); // atrybut powtarzalny - ksiazka moze miec kilka kategorii
    private Condition bookCondition; // enum - z gory okreslone mozliwe wartosci
    private int numberOfPages; // atrybut opcjonalny - moze byc pusty
    private int yearOfEdition;
    private String publishingHouse;
    private String bookRentalName; // atrybut klasowy - stala wartosc dla wszystkich utworzonych obiektow w ramach tej klasy
    private int ageOfBook; // atrybut pochodny - wartosc moze byc wyliczona na podstawie roku wydania ksiazki
    private State state; // czy ksiazka dostepna do wypozyczenia

    private Rack rack;

    public static List<Book> booksFromDb = new ArrayList<>();

    private List<Author> authors = new ArrayList<>(); // asocjacja binarna ; kolekcja do przetrzymywania powiazan z Ksiazkami ; kolekcja, poniewaz jedna ksiazka moze miec wielu autorow

    private List<Borrow> borrowDetails = new ArrayList<>(); // asocjacja z atrybutem ; kolekcja do przetrzymywania historii wypozyczen ksiazki ; kolekcja, poniewaz jedna ksiazka moze miec wiele wypozyczen

    private List<Renovation> renovations = new ArrayList<>(); // asocjacja z atrybutem ; kolekcja do przetrzymywania historii renowacji ksiazki ; kolekcja, poniewaz jedna ksiazka moze byc poddana renowacji wielkrotnie

    public Book () {}

    public Book(int id, String title, Set category, Condition bookCondition, int numberOfPages, int yearOfEdition, String publishingHouse) {
        super(); // wywolanie konstrukotra z nadklasy
        this.id = id;
        setTitle(title);
        setCategory(category);
        setBookCondition(bookCondition);
        setNumberOfPages(numberOfPages);
        setYearOfEdition(yearOfEdition);
        setPublishingHouse(publishingHouse);
        setBookRentalName(CurrentbookRentalName);
        setState(State.AVAILABLE);
    }

    // jest to tez przyklad przeciazenia
    public Book(int id, String title, Set category, Condition bookCondition, int yearOfEdition, String publishingHouse) { // drugi konstruktor bez liczby stron - jest to pole opcjonalne
        super(); // wywolanie konstrukotra z nadklasy
        this.id = id;
        setTitle(title);
        setCategory(category);
        setBookCondition(bookCondition);
        setYearOfEdition(yearOfEdition);
        setPublishingHouse(publishingHouse);
        setBookRentalName(CurrentbookRentalName);
        setState(State.AVAILABLE);
    }

   @ManyToMany(mappedBy = "books")
    public List<Author> getAuthors() {
        return authors;
    }

    public void setAuthors(List<Author> authorsToAdd) {
        this.authors = authorsToAdd;
    }

    public void addAuthor(Author authorToAdd) {
        getAuthors().add(authorToAdd);
    }

    public void removeAuthor(Author authorToRemove) {
        getAuthors().remove(authorToRemove);

    }

//    public void addAuthor(Author authorToAdd) {
//
//        if (!authors.contains(authorToAdd)) {
//            authors.add(authorToAdd);
//
//            authorToAdd.addBook(this); // polaczenie zwrotne
//        }
//    }

    @Id()
    @GeneratedValue(generator="increment")
    @GenericGenerator(name="increment", strategy = "increment")
    @Column(name = "book_id", nullable = false)
    public long getId() {
        return id;
    }

    public void setId(long id) {
        if (id < 0){
            throw new ValidationException("ID cannot be negative");
        }
        this.id = id;
    }

    @OneToMany(mappedBy = "book")
    public List<Renovation> getRenovations () {
        return renovations;
    }

    public void setRenovations (List<Renovation> renovationHistory) {
        this.renovations = renovationHistory;
    }


    public void addRack (Rack rackToAdd) {
        if (this.rack != rackToAdd) {

            rackToAdd.addBook(this); // polaczenie zwrotne
            this.rack = rackToAdd;
        }

    }

    public void changeRack (Rack rackToChange) {
        if (this.rack == rackToChange) {
            throw new ValidationException("Provide different rack than current");
        }
        rackToChange.changeRackForBook(this); // polaczenie zwrotne - ksiazka z poprzednim regalem
        this.rack = rackToChange;
    }



    public void updateBookCondition (Condition newCondition){
        if (this.bookCondition != Condition.NEW && newCondition == Condition.NEW) {
            throw new ValidationException("Book cannot become new if it's already used ");
        }
        else {
            this.bookCondition = newCondition;
        }
    }

    public void addBorrowToBook(Client client, Borrow newBorrow) {

        if (!borrowDetails.contains(newBorrow)) {
            if (this.state == State.AVAILABLE) {
                borrowDetails.add(newBorrow);
                this.setState(State.BORROWED); // ksiazka zostala wypozyczena, nie jest juz dostepna dla innych
                client.addBorrowToClient(this, newBorrow); // polaczenie zwrotne
            }
            else{
                throw new ValidationException("Book is not available and cannot be borrowed");
            }
        }
    }

    public void addBorrowToBook(ClientLibrarian client, Borrow newBorrow) {

        if (!borrowDetails.contains(newBorrow)) {
            borrowDetails.add(newBorrow);

            client.addBorrowToClient(this, newBorrow); // polaczenie zwrotne
        }
    }

    public void returnBook (Borrow borrowToEnd){
        borrowToEnd.setActualTo(LocalDate.now());
        if(borrowToEnd.getActualTo().isAfter(borrowToEnd.getTo())) {
            borrowToEnd.setOnTime(false);
        }
        else{
            borrowToEnd.setOnTime(true);
        }
        this.setState(State.AVAILABLE); // ksiazka jest juz dostepna do ponownego wypozyczenia
    }

    public void addRenovationToBook(Restorer restorer, Renovation renovation) {

        if (!renovations.contains(renovation)) {
            renovations.add(renovation);

            restorer.addRenovationToRestorer(this, renovation); // polaczenie zwrotne
        }
    }

    @Basic
    public String getPublishingHouse () {
        return publishingHouse;
    }

    private void setPublishingHouse(String publishingHouse) {
        if (publishingHouse == null || publishingHouse.trim().isBlank() ) {
            throw new ValidationException("Publishing house cannot be empty");
        }
        this.publishingHouse = publishingHouse;
    }

    @Transient
    public Rack getRack() {
        return rack;
    }

    public static void showExtent() throws Exception {
        ObjectPlus.showExtent(Book.class);
    }

    public static void getExtent() throws Exception {
        ObjectPlus.getExtent(Book.class);
    }

    @Basic
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        if (title == null || title.trim().isBlank()) {
            throw new ValidationException("Title cannot be empty");
        }
        this.title = title;
    }

    @Enumerated(EnumType.STRING)
    public State getState () {
        return state;
    }

    public void setState (State state) {
        if (state == null) {
            throw new ValidationException("State of the book cannot be empty");
        }
        this.state = state;
    }

    @Enumerated(EnumType.STRING)
    public Condition getBookCondition() {
        return bookCondition;
    }

    public void setBookCondition(Condition bookCondition) {
        if (bookCondition == null) {
            throw new ValidationException("Book condition cannot be empty");
        }
        this.bookCondition = bookCondition;
    }

    @ElementCollection
    public Set<String> getCategory() {
        return Collections.unmodifiableSet(this.category); // wrapper
    }

    public void setCategory(Set<String> category) {
        if (category.isEmpty()){
            throw new ValidationException("Category cannot be empty");
        }
        this.category = category;
    }

    public void addCategory(String categoryToAdd) {
        if (categoryToAdd == null || categoryToAdd.trim().isBlank()) {
            throw new ValidationException("Category cannot be empty");
        }
        this.category.add(categoryToAdd);

    }

    public void removeCategory(String categoryToRemove) {
        if (this.category.size() < 2) {
            throw new ValidationException("Cannot remove the last category");
        }
        this.category.remove(categoryToRemove);
    }

    @Basic(optional = true)
    public int getNumberOfPages() {
        return  (numberOfPages);
    }

    public void setNumberOfPages(int numberOfPages) {
        if (numberOfPages < 0) {
            throw new ValidationException("Number of pages of book cannot be negative");
        }
        this.numberOfPages = numberOfPages;
    }

    @Basic
    public int getYearOfEdition() {
        return yearOfEdition;
    }

    public void setYearOfEdition(int yearOfEdition) {
        if (yearOfEdition < 0) {
            throw new ValidationException("Year of edition cannot be negative");
        }
        this.yearOfEdition = yearOfEdition;
    }

    @Transient
    public int getAgeOfBook() { // pochodny
        return Year.now().getValue() - this.yearOfEdition;
    }

    @Basic
    public String getBookRentalName() {
        return CurrentbookRentalName;
    }

    public void setBookRentalName(String currentbookRentalName) {
        if (currentbookRentalName == null || currentbookRentalName.trim().isBlank()) {
            throw new ValidationException("Name of book rental cannot be empty");
        }
        this.bookRentalName = currentbookRentalName;
    }

    @Override // przesloniecie metody z klasy bazowej
    public String toString() {
        return "Book{" +
                "title='" + title + '\'' +
                "', category=" + category +
                ", bookCondition=" + bookCondition +
                ", numberOfPages=" + numberOfPages +
                ", yearOfEdition=" + yearOfEdition +
                ", publishingHouse='" + publishingHouse + '\'' +
                ", bookRentalName='" + bookRentalName + '\'' +
                ", ageOfBook=" + getAgeOfBook() +
                "}";
    }

    @Transient
    public List<Borrow> getBorrowDetails() {
        return this.borrowDetails;
    }

    public void setBorrowDetails (List<Borrow> borrowDetails) {
        this.borrowDetails = borrowDetails;
    }

    public static String  printBooksConditon() throws ClassNotFoundException { // metoda klasowa
        Iterable<Book> extent = getExtent(Book.class);
        int newBooks = 0, goodBooks = 0, badBooks = 0, destroyedBooks = 0, otherBooks = 0;
        for (Book book : extent) {
            if (book.bookCondition == Condition.NEW){
                newBooks++;
            }
            else if (book.bookCondition == Condition.GOOD){
                goodBooks++;
            }
            else if (book.bookCondition == Condition.BAD){
                badBooks++;
            }
            else if (book.bookCondition == Condition.DESTROYED){
                destroyedBooks++;
            }
            else {
                otherBooks++;
            }
        }
        return "Books condition summary:  \n" +
                "New books: " + newBooks + " \n" +
                "Good books: " + goodBooks +  " \n" +
                "Bad books: " + badBooks +  " \n" +
                "Destroyed books: " + destroyedBooks +  " \n" +
                "Other books: " + otherBooks ;
    }

    public String analyzeBookCondition(){ // ogolnie przyjety limit
        if (this.getAgeOfBook() <= 10 && ( this.bookCondition == Condition.NEW) || ( this.bookCondition == Condition.GOOD) )
        {
            return "Book '" + this.title + "' should be in a adequate condition - no action required";
        }
        else if (this.getAgeOfBook() <= 20 && ( this.bookCondition == Condition.NEW) || ( this.bookCondition == Condition.GOOD) )
        {
            return "Seems that book '" + this.title + "' may require condition change or additional activities";
        }
        else
        {
            return "Book '" + this.title + "' probably needs renovation" ;
        }
    }

    public String analyzeBookCondition(int ageLimit){ // limit podany przez uzytkownika - niektore ksiazki moga byc cenniejsze i bardziej trzeba je kontrolowac
        if (this.getAgeOfBook() <= ageLimit && ( this.bookCondition == Condition.NEW) || ( this.bookCondition == Condition.GOOD) )
        {
            return "Book '" + this.title + "' should be in a adequate condition - no action required (limit:" + ageLimit + ")";
        }
        else if (this.getAgeOfBook() <= ageLimit*2 && ( this.bookCondition == Condition.NEW) || ( this.bookCondition == Condition.GOOD) )
        {
            return "Seems that book '" + this.title + "' may require condition change or additional activities (limit: " + ageLimit + ")";
        }
        else
        {
            return "Book '" + this.title + "' probably needs renovation (limit:" + ageLimit + ")";
        }
    }

   // public List<Renovation> getRenovationsForBook(Book)

}
