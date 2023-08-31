package model;

import exception.ValidationException;
import org.hibernate.annotations.GenericGenerator;

import jakarta.persistence.*;

import java.io.Serializable;
import java.util.*;

import static model.Book.booksFromDb;

@Entity(name = "model.Library")
@Table(name = "library")
public class Library implements Serializable {

    public static List<Library> librariesFromDb = new ArrayList<>();

    private long id;
    private String name;
    private String street;
    private String city;
    private String postalCode;

    private List<Director> directors = new ArrayList<>(); // kolekcja do przetrzymywania powiazan z Pracownikami
    private List<Restorer> restorers = new ArrayList<>(); // kolekcja do przetrzymywania powiazan z Pracownikami
    private List<Librarian> librarians = new ArrayList<>(); // kolekcja do przetrzymywania powiazan z Pracownikami

    //private Map<String, Employee> employeeQualif = new TreeMap<>(); // w jednej bibliotece moze pracowac wielu pracownikow

    Set<String> ssnDictionary = new HashSet<>();  // zadbanie o unikalność numerów ssn każdego pracownika


    public Library(String name, String street, String city, String postalCode) {
        setName(name);
        setStreet(street);
        setCity(city);
        setPostalCode(postalCode);
    }

    public Library(){}

    @Id()
    @GeneratedValue(generator="increment")
    @GenericGenerator(name="increment", strategy = "increment")
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @OneToMany(mappedBy = "library", fetch = FetchType.EAGER)
    public List<Director> getDirectors () {
        return directors;
    }

    public void setDirectors (List<Director> directors) {
        this.directors = directors;
    }

    public void addDirector(Director director) {
        if (director == null) {
            throw new ValidationException("Director cannot be empty");
        }
        else if (!directors.contains(director)) {
            if(ssnDictionary.contains(director.getSsn()) && (!director.isChangeFlag())){
                throw new ValidationException("Employee with given SSN number is already assigned");
            }
            if(!director.isChangeFlag()) {
                addSSNToDictionary(director.getSsn());
            }
            directors.add(director);
            director.addLibraryToDirector(this); // polaczenie zwrotne
        }
    }

    public void removeDirector(Director director) {
        getDirectors().remove(director); // usuwanie polaczenia
        director.removeLibrary();
        removeSSNFromDictionary(director.getSsn());
    }

    @OneToMany(mappedBy = "library", fetch = FetchType.EAGER)
    public List<Restorer> getRestorers () {
        return restorers;
    }

    public void setRestorers (List<Restorer> restorers) {
        this.restorers = restorers;
    }

    public void addRestorer(Restorer restorer) {
        if (restorer == null) {
            throw new ValidationException("Restorer cannot be empty");
        }
        else if (!restorers.contains(restorer)) {
            if(ssnDictionary.contains(restorer.getSsn()) && (!restorer.isChangeFlag())){
                throw new ValidationException("Employee with given SSN number is already assigned");
            }
            if(!restorer.isChangeFlag()) {
                addSSNToDictionary(restorer.getSsn());
            }
            restorers.add(restorer);
            restorer.addLibraryToRestorer(this); // polaczenie zwrotne
        }
    }

    public void removeRestorer(Restorer restorer) {
        getRestorers().remove(restorer); // usuwanie polaczenia
        restorer.removeLibrary();
        removeSSNFromDictionary(restorer.getSsn());
    }

    @OneToMany(mappedBy = "library", fetch = FetchType.EAGER)
    public List<Librarian> getLibrarians () { return librarians;
    }

    public void setLibrarians (List<Librarian> librarians) {
        this.librarians = librarians;
    }


    public void addLibrarian(Librarian librarian) throws Exception {
        if (librarian == null) {
            throw new ValidationException("Librarian cannot be empty");
        }
        else if (!librarians.contains(librarian)) {
            if(ssnDictionary.contains(librarian.getSsn()) && (!librarian.isChangeFlag())){
                throw new ValidationException("Employee with given SSN number is already assigned");
            }
            if(!librarian.isChangeFlag()) {
                addSSNToDictionary(librarian.getSsn());
            }
            librarians.add(librarian);
            librarian.addLibraryToLibrarian(this); // polaczenie zwrotne
        }
    }

    public void removeLibrarian(Librarian librarian) {
        getLibrarians().remove(librarian); // usuwanie polaczenia
        librarian.removeLibrary();
        removeSSNFromDictionary(librarian.getSsn());
    }

    @Basic
    public String getName() {
        return name;
    }

    public void setName(String name) {
        if (name == null || name.trim().isBlank()){
            throw new ValidationException("Name cannot be empty");
        }
        this.name = name;
    }

    @Basic
    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        if (street == null || street.trim().isBlank()){
            throw new ValidationException("Street cannot be empty");
        }
        this.street = street;
    }

    @Basic
    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        if (city == null || city.trim().isBlank()){
            throw new ValidationException("City cannot be empty");
        }
        this.city = city;
    }

    @Basic
    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        if (postalCode == null || postalCode.trim().isBlank()){
            throw new ValidationException("Postal code cannot be empty");
        }
        this.postalCode = postalCode;
    }

    @ElementCollection
    public Set<String> getSsnDictionary () {
        return ssnDictionary;
    }

    public void setSsnDictionary (Set<String> ssnDictionary) {
            this.ssnDictionary = ssnDictionary;
    }

    public void addSSNToDictionary(String ssnToAdd) {
        if (ssnToAdd == null || ssnToAdd.trim().isBlank()) {
            throw new ValidationException("SSN cannot be empty");
        }
        this.ssnDictionary.add(ssnToAdd);
    }
    public void removeSSNFromDictionary(String SSNToRemove) {
        this.ssnDictionary.remove(SSNToRemove);
    }


//    public void addEmployeeQualif(Employee newEmployee) {
//        if (!employeeQualif.containsKey(newEmployee.getSsn())) {
//            employeeQualif.put(newEmployee.getSsn(), newEmployee);
//
//            newEmployee.addLibrary(this);  // polaczenie zwrotne
//        }
//
//    }
//
    public Librarian findLibrarian (String ssn) {
        for (Librarian lib : librarians) {
            if (lib.getSsn().equals(ssn)) {
                return lib;
            }
        }
        return null;
    }

    public Director findDirector (String ssn) {
        for (Director dir : directors) {
            if (dir.getSsn().equals(ssn)) {
                return dir;
            }
        }
        return null;
    }

    public Restorer findRestorer (String ssn) {
        for (Restorer res : restorers) {
            if (res.getSsn().equals(ssn)) {
                return res;
            }
        }
        return null;
    }

    @Override
    public String toString () {
        return "Library{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", street='" + street + '\'' +
                ", city='" + city + '\'' +
                ", postalCode='" + postalCode + '\'' +
                ", directors=" + directors +
                ", restorers=" + restorers +
                ", librarians=" + librarians +
                '}';
    }

    public static List<Library> getLibrariesFromDb () {
        return librariesFromDb;
    }
}
