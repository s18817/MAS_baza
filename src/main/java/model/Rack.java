package model;

import exception.ValidationException;
import jakarta.persistence.*;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.GenericGenerator;

import java.io.Serializable;
import java.util.*;

@Entity(name = "model.Rack")
@Table(name = "rack")
public class Rack implements Serializable {

    enum RackType {RegałStandardowy, RegałRuchomy, RegałInteligenty};

    public static List<Rack> racksFromDb = new ArrayList<>();

    private List<Book> books = new ArrayList<>(); // regal przetrzymuje wiele ksiazek

    private static Set<Book> allBooks = new HashSet<>();

    //private EnumSet<RackType> rackKind = EnumSet.of(RackType.RegałStandardowy); // overlapping
    private Set<RackType> rackKind = new HashSet<>();

    private long id;
    private int floor;
    private String marking;
    private String subject;

    private String softwareVersion; // dla reguału smart
    private boolean working; // dla reguału smart

    private double maxWeight; // dla regału ruchomego
    private boolean hasBrake; // dla regału ruchomego

    private List<Inventory> inventories = new ArrayList<>(); // asocjacja z atrybutem ; kolekcja do przetrzymywania historii renowacji ksiazki ; kolekcja, poniewaz jedna ksiazka moze byc poddana renowacji wielkrotnie



    public Rack(int floor, String marking, String subject, String softwareVersion, boolean working, double maxWeight, boolean hasBrake) { // regał ruchomy smart
        rackKind.add(RackType.RegałRuchomy);
        rackKind.add(RackType.RegałInteligenty);
        setFloor(floor);
        setMarking(marking);
        setSubject(subject);
        setSoftwareVersion(softwareVersion);
        setWorking(working);
        setMaxWeight(maxWeight);
        setHasBrake(hasBrake);;
    }

    public Rack(int floor, String marking, String subject, String softwareVersion, boolean working) { // regał smart
        rackKind.add(RackType.RegałInteligenty);
        setFloor(floor);
        setMarking(marking);
        setSubject(subject);
        setSoftwareVersion(softwareVersion);
        setWorking(working);
    }

    public Rack(int floor, String marking, String subject, double maxWeight, boolean hasBrake) { // regał ruchomy
        rackKind.add(RackType.RegałRuchomy);
        setFloor(floor);
        setMarking(marking);
        setSubject(subject);
        setMaxWeight(maxWeight);
        setHasBrake(hasBrake);;
    }

    public Rack(int floor, String marking, String subject) { // regał zwykły
        setFloor(floor);
        setMarking(marking);
        setSubject(subject);
        rackKind.add(RackType.RegałStandardowy);
    }

    public Rack() {}

    @Id
    @GeneratedValue(generator = "increment")
    @GenericGenerator(name = "increment", strategy = "increment")
    @Column(name = "rack_id", nullable = false)
    public long getId () {
        return id;
    }

    public void setId (long id) {
        if (id < 0) {
            throw new ValidationException("ID cannot be negative");
        }
        this.id = id;
    }

    @OneToMany(mappedBy = "rack", fetch = FetchType.EAGER)
    public List<Book> getBooks () { return books;
    }

    public void setBooks (List<Book> books) {
        this.books = books;
    }

    public void addBookToRack(Book book) {
        if (!books.contains(book)) {
            if (allBooks.contains(book)) {
                throw new ValidationException("This book is already in another rack");
            }
            books.add(book); // dodanie ksiazki do regalu
            book.addRackToBook(this); // polaczenie zwrotne
            allBooks.add(book); // zapamietanie, ze ksiazka juz ma swoj regal
        }
    }

    public void changeRackForBook(Book book) {
        if (this.books.contains(book)) {
            this.books.remove(book); // usuniecie ze starego regalu
            books.add(book); // dodanie ksiazki do nowego regalu
        }

    }

    public void removeBookFromRack(Book book) {
        if (this.books.contains(book)) {
            this.books.remove(book); // usuniecie z regalu
        }

    }

    @OneToMany(mappedBy = "rack", fetch = FetchType.EAGER)
    @OrderBy("date")
    public List<Inventory> getInventories () { return inventories;
    }

    public void setInventories (List<Inventory> inventoryHistory) {
        this.inventories = inventoryHistory;
    }

    public void addInventoryToRack(Librarian librarian, Inventory inventory) {

        if (!inventories.contains(inventory)) {
            inventories.add(inventory);
            if(inventory.getLibrarian() == null && inventory.getRack() == null) {
                inventory.setRack(this);
                inventory.setLibrarian(librarian);
            }
             librarian.addInventoryToLibrarian(this, inventory); // polaczenie zwrotne
            }
        }

//    public void addInventory(List<Librarian> librariansList, Inventory inventory) {
//
//        if (!inventoryHistory.contains(inventory)) {
//
//            if (librariansList.size() < 2 || librariansList == null){
//                throw new ValidationException("Inventory can be done by minimum 2 librarians");
//            }
//            inventoryHistory.add(inventory);
//
//            for (Librarian librarian : librariansList){
//                librarian.addInventory(this, inventory); // polaczenie zwrotne
//            }
//        }
//    }


    @Basic
    public boolean isWorking () {
        return working;
    }

    public void setWorking (boolean working) {
        this.working = working;
    }

    @ElementCollection
    @Enumerated(EnumType.STRING)
    //@Fetch(FetchMode.SELECT)
    public Set<RackType> getRackKind() {
        return rackKind;
    }

    public void setRackKind (Set<RackType> rackKind) {
        if (rackKind == null) {
            throw new ValidationException("Rack kind cannot be empty");
        }
        this.rackKind = rackKind;
    }

    @Basic
    public int getFloor() {
        return floor;
    }

    public void setFloor(int floor) {
        if (floor >= 50 || floor <= -50){
            throw new ValidationException("Provide valid floor");
        }
        this.floor = floor;
    }

    @Basic
    public String getMarking() {
        return marking;
    }

    public void setMarking(String marking) {
        if (marking == null || marking.trim().isBlank()){
            throw new ValidationException("Marking cannot be empty");
        }
        this.marking = marking;
    }

    @Basic
    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        if (subject == null || subject.trim().isBlank()){
            throw new ValidationException("Subject cannot be empty");
        }
        this.subject = subject;
    }

    @Basic
    public String getSoftwareVersion () {
        return softwareVersion;
    }

    public void setSoftwareVersion (String softwareVersion) {
        if(this.rackKind.contains(RackType.RegałInteligenty)) {
            if (softwareVersion == null || softwareVersion.trim().isBlank()) {
                throw new ValidationException("Software version cannot be empty");
            }
        this.softwareVersion = softwareVersion;
        }
    }


    @Basic
    public boolean isHasBrake () {
        return hasBrake;
    }

    public void setHasBrake (boolean hasBrake) {
        this.hasBrake = hasBrake;
    }

    @Basic
    public double getMaxWeight () {
        return maxWeight;
    }

    public void setMaxWeight (double maxWeight) {
        if (maxWeight < 0){
            throw new ValidationException("Max weight cannot be negative");
        }
        this.maxWeight = maxWeight;
    }

    @Override
    public String toString () {
        return "Rack{" +
                "books=" + books +
                ", rackKind=" + rackKind +
                ", id=" + id +
                ", floor=" + floor +
                ", marking='" + marking + '\'' +
                ", subject='" + subject + '\'' +
                ", softwareVersion='" + softwareVersion + '\'' +
                ", working=" + working +
                ", maxWeight=" + maxWeight +
                ", hasBrake=" + hasBrake +
                ", inventories=" + inventories +
                '}';
    }

    public static List<Rack> getRacksFromDb () {
        return racksFromDb;
    }
}
