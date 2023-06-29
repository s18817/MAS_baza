package model;

import exception.ValidationException;

import java.io.Serializable;
import java.util.*;

public class Rack extends ObjectPlus implements Serializable {


    private List<Inventory> inventoryHistory = new ArrayList<>();

    enum RackType {RegałStandardowy, RegałRuchomy, RegałInteligenty};

    private List<Book> books = new ArrayList<>(); // regal przetrzymuje wiele ksiazek

    private static Set<Book> allBooks = new HashSet<>();

    private EnumSet<RackType> rackKind = EnumSet.of(RackType.RegałStandardowy); // overlapping
    private int floor;
    private String marking;
    private String subject;


    private String softwareVersion; // dla reguału smart
    private boolean working; // dla reguału smart



    private double maxWeight; // dla regału ruchomego
    private boolean hasBrake; // dla regału ruchomego


    public Rack(int floor, String marking, String subject, String softwareVersion, boolean working, double maxWeight, boolean hasBrake) { // regał ruchomy smart
        super();
        this.setFloor(floor);
        this.setMarking(marking);
        this.setSubject(subject);
        this.setSoftwareVersion(softwareVersion);
        this.setWorking(working);
        this.setMaxWeight(maxWeight);
        this.hasBrake = hasBrake;
        this.rackKind.add(RackType.RegałRuchomy);
        this.rackKind.add(RackType.RegałInteligenty);
        this.rackKind.remove(RackType.RegałStandardowy);
    }

    public Rack(int floor, String marking, String subject, String softwareVersion, boolean working) { // regał smart
        super();
        this.setFloor(floor);
        this.setMarking(marking);
        this.setSubject(subject);
        this.setSoftwareVersion(softwareVersion);
        this.setWorking(working);
        this.rackKind.add(RackType.RegałInteligenty);
        this.rackKind.remove(RackType.RegałStandardowy);
    }

    public Rack(int floor, String marking, String subject, double maxWeight, boolean hasBrake) { // regał ruchomy
        super();
        this.setFloor(floor);
        this.setMarking(marking);
        this.setSubject(subject);
        this.setMaxWeight(maxWeight);
        this.hasBrake = hasBrake;
        this.rackKind.add(RackType.RegałRuchomy);
        this.rackKind.remove(RackType.RegałStandardowy);
    }

    public Rack(int floor, String marking, String subject) { // regał zwykły
        super();
        this.setFloor(floor);
        this.setMarking(marking);
        this.setSubject(subject);
    }

    public void addInventory(List<Librarian> librariansList, Inventory inventory) {

        if (!inventoryHistory.contains(inventory)) {

            if (librariansList.size() < 2 || librariansList == null){
                throw new ValidationException("Inventory can be done by minimum 2 librarians");
            }
            inventoryHistory.add(inventory);

            for (Librarian librarian : librariansList){
                librarian.addInventory(this, inventory); // polaczenie zwrotne
            }
        }
    }

    public void addBook(Book book) {
        if (!books.contains(book)) {
            if (allBooks.contains(book)) {
                throw new ValidationException("This book is already in another rack");
            }
            books.add(book); // dodanie ksiazki do regalu
            book.addRack(this); // polaczenie zwrotne
            allBooks.add(book); // zapamietanie, ze ksiazka juz ma swoj regal
        }
    }

    public void changeRackForBook(Book book) {
        if (this.books.contains(book)) {
            this.books.remove(book); // usuniecie ze starego regalu
            books.add(book); // dodanie ksiazki do nowego regalu
        }

    }

    public boolean isWorking () {
        return working;
    }

    public void setWorking (boolean working) {
        this.working = working;
    }

    public List<Inventory> getInventoryHistory () {
        return inventoryHistory;
    }

    public EnumSet<RackType> getRackKind() {
        return rackKind;
    }

    public int getFloor() {
        return floor;
    }

    public void setFloor(int floor) {
        if (floor >= 50 || floor <= -50){
            throw new ValidationException("Provide valid floor");
        }
        this.floor = floor;
    }

    public String getMarking() {
        return marking;
    }

    public void setMarking(String marking) {
        if (marking == null || marking.trim().isBlank()){
            throw new ValidationException("Marking cannot be empty");
        }
        this.marking = marking;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        if (subject == null || subject.trim().isBlank()){
            throw new ValidationException("Subject cannot be empty");
        }
        this.subject = subject;
    }

    public void setSoftwareVersion (String softwareVersion) {
        if (softwareVersion == null || softwareVersion.trim().isBlank()){
            throw new ValidationException("Software version cannot be empty");
        }
        this.softwareVersion = softwareVersion;
    }

    public void setMaxWeight (double maxWeight) {
        if (maxWeight < 0){
            throw new ValidationException("Max weight cannot be negative");
        }
        this.maxWeight = maxWeight;
    }

    @Override
    public String toString() {
        String result = "Rack: " + marking + "\n";
        for (Book book : books) {
            result = result + " " + book.getTitle() + "\n";
        }
        return result;
    }

    public List<Book> getBooks() {
        return this.books;
    }
}
