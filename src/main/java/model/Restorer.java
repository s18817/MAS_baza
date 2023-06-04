package model;

import enums.State;
import exception.ValidationException;
import jakarta.persistence.*;
import org.hibernate.annotations.GenericGenerator;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.Year;
import java.util.ArrayList;
import java.util.List;

@Entity(name = "model.Restorer")
public class Restorer extends Employee implements Serializable {

    private long id;
    private String name;
    private String surname;
    private LocalDate birthDate;
    private String gender;
    private String nationality;
    private String specialisation;
    private String ssn; // social security number // kwalifikator
    private Library library; // dla pracownika tylko jedna biblioteka
    private double baseSalary;
    private LocalDate hiringDate; // data  zatrudnienia
    private String address; // dane adresowe

    @ManyToMany(mappedBy = "books")
    private List<Renovation> doneRenovations = new ArrayList<>(); // asocjacja z atrybutem ; kolekcja do przetrzymywania wykonanych renowacji ksiazek ; kolekcja, poniewaz jeden konwserwator moze wykonac wiele renowacji

    public Restorer(int id, String name, String surname, LocalDate birthDate, String gender, String nationality, String ssn, Library library, LocalDate hiringDate, double baseSalary, String address, String specialisation){
        super(name, surname, birthDate, gender, nationality, ssn, library, hiringDate, baseSalary, address);
        setId(id);
        setName(name);
        setSurname(surname);
        setBirthDate(birthDate);
        setGender(gender);
        setNationality(nationality);
        setSsn(ssn);
        setHiringDate(hiringDate);
        addLibrary(library);
        setBaseSalary(baseSalary);
        setAddress(address);
        setSpecialisation(specialisation);
    }

    public Restorer(Employee prevEmployee, String specialisation, int booksRenovated){
        super (prevEmployee.getName(), prevEmployee.getSurname(), prevEmployee.getBirthDate(), prevEmployee.getGender(),prevEmployee.getNationality(), prevEmployee.getSsn(), prevEmployee.getLibrary(), prevEmployee.getHiringDate(),prevEmployee.getBaseSalary(), prevEmployee.getAddress()); // kopiowanie danych z poprzedniego obiektu
        this.setSpecialisation(specialisation); // zapisanie nowych danych
    }

    @Id()
    @GeneratedValue(generator = "increment")
    @GenericGenerator(name = "increment", strategy = "increment")
    public long getId () {
        return id;
    }

    public void setId (long id) {
        if (id < 0) {
            throw new ValidationException("ID cannot be negative");
        }
        this.id = id;
    }


    public List<Renovation> getDoneRenovations () {
        return doneRenovations;
    }

    public void setDoneRenovations (List<Renovation> doneRenovations) {
        this.doneRenovations = doneRenovations;
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
        this.birthDate = birthDate;
    }

    @Basic
    public String getGender() {
        return gender;
    }


    public void setGender(String gender) {
        if (gender == null){
            throw new ValidationException("Gender cannot be empty");
        }
        this.gender = gender;
    }

    @Basic
    public String getNationality() {
        return nationality;
    }


    public void setNationality(String nationality) {
        if (nationality == null){
            throw new ValidationException("Nationality cannot be empty");
        }
        this.nationality = nationality;
    }

        public void addRenovationToRestorer (Book book, Renovation renovation) {

        if (!doneRenovations.contains(renovation)) {
            if (book.getState() == State.AVAILABLE) { // nie mozna poddac renowacji ksiazki, ktora nie jest dostepna
                doneRenovations.add(renovation);
                book.addRenovationToBook(this, renovation); // polaczenie zwrotne
            } else {
                throw new ValidationException("Book is not available and cannot be borrowed");
            }
        }
    }

    @Basic
    public String getSpecialisation () {
        return specialisation;
    }

    public void setSpecialisation (String specialisation) {
        if (specialisation == null || specialisation.trim().isBlank()){
            throw new ValidationException("Specialisation cannot be empty");
        }
        this.specialisation = specialisation;
    }

    @Basic
    public double getBaseSalary(){
        return baseSalary;
    }

    public void setBaseSalary (double baseSalary) {
        if (baseSalary < 3490) {
            throw new ValidationException("Salary cannot be lower than 3490");
        } else if (baseSalary < 0) {
            throw new ValidationException("Salary cannot be negative");
        }
        this.baseSalary = baseSalary;
    }


    @Transient
    public int getSeniority() { // wyliczalny
        return Year.now().getValue() - this.getHiringDate().getYear();
    }

    public static void getExtent() throws Exception {
        ObjectPlus.getExtent(Restorer.class);
    }

    @Basic
    public String getSsn() {
        return ssn;
    }

    public void setSsn(String ssn) {
        if (ssn == null || ssn.trim().isBlank()){
            throw new ValidationException("SSN cannot be empty");
        }
        else if (getSsnDictionary().contains(ssn)) { // unikalnosc ssn
            throw new ValidationException("Given SSN is already used");
        }
        getSsnDictionary().add(ssn);
        this.ssn = ssn;
    }

    @Basic
    public LocalDate getHiringDate() {
        return hiringDate;
    }

    public void setHiringDate (LocalDate hiringDate) {
        if (hiringDate == null){
            throw new ValidationException("Hiring date cannot be empty");
        }
        else if (hiringDate.getYear() < 1900 ) {
            throw new ValidationException("Provide valid hiring date");
        }
        this.hiringDate = hiringDate;
    }

    @Transient
    public Library getLibrary() {
        return library;
    }
    public void setLibrary (Library library) {
        this.library = library;
    }

    public void addLibrary(Library library){
        if ( library != null && this.library != library ) {
            this.library = library;
            library.addEmployee(this);
        }
    }

    @Basic
    public String getAddress () {
        return address;
    }

    public void setAddress (String address) {
        if (address == null || address.trim().isBlank()) {
            throw new ValidationException("Address cannot be empty");
        } else if (address.length() > 100) {
            throw new ValidationException("Address cannot be longer that 100 digits");
        }
        this.address = address;
    }

    @Override
    public String toString () {
        return super.toString() + "Restorer{" +
                "specialisation='" + specialisation + '\'' +
                ", doneRenovations=" + doneRenovations +
                '}';
    }
}
