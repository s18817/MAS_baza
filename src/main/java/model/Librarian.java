package model;

import exception.ValidationException;
import jakarta.persistence.*;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.GenericGenerator;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.Year;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity(name = "model.Librarian")
@Table(name = "librarian")
public class Librarian implements Serializable {

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
    private Set<String> languages = new HashSet<>();

    private List<Inventory> inventories = new ArrayList<>();


    public Librarian (String name, String surname, LocalDate birthDate, String gender, String nationality, String ssn, LocalDate hiringDate, double baseSalary, String address, Set languages){
        setId(id);
        setName(name);
        setSurname(surname);
        setBirthDate(birthDate);
        setGender(gender);
        setNationality(nationality);
        setSsn(ssn);
        setHiringDate(hiringDate);
        setBaseSalary(baseSalary);
        setAddress(address);
        setLanguages(languages);
    }

    public Librarian(Employee prevEmployee, Set languages){
        setId(id);
        setName(prevEmployee.getName());
        setSurname(prevEmployee.getSurname());
        setBirthDate(prevEmployee.getBirthDate());
        setGender(prevEmployee.getGender());
        setNationality(prevEmployee.getNationality());
        setSsn(prevEmployee.getSsn());
        setHiringDate(prevEmployee.getHiringDate());
        setBaseSalary(prevEmployee.getBaseSalary());
        setAddress(prevEmployee.getAddress());
        setLanguages(languages);
        // pamietac o SSN
    }

    public Librarian(){};

    @Id
    @GeneratedValue(generator = "increment")
    @GenericGenerator(name = "increment", strategy = "increment")
    @Column(name = "librarian_id", nullable = false)
    public long getId () {
        return id;
    }

    public void setId (long id) {
        if (id < 0) {
            throw new ValidationException("ID cannot be negative");
        }
        this.id = id;
    }

    @OneToMany(mappedBy = "librarian", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @OrderBy("date")
    public List<Inventory> getInventories() {
        return inventories;
    }

    public void setInventories (List<Inventory> doneInventories) { this.inventories = doneInventories;
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
    @ElementCollection
    //@Fetch(FetchMode.SELECT)
    public Set<String> getLanguages() {
        return languages;
    }

    public void setLanguages (Set<String> languages) {
        if (languages.size() < 2){
            throw new ValidationException("Librarian has to know minimum two foreign languages");
        } else if (languages.contains("polski")) {
            throw new ValidationException("Polish language is not taken into consideration");
        }
        this.languages = languages;
    }

    public void addInventoryToLibrarian(Rack rack, Inventory inventory){
        if (!inventories.contains(inventory)) {
            inventories.add(inventory);
            if(inventory.getLibrarian() == null && inventory.getRack() == null){
                inventory.setRack(rack);
                inventory.setLibrarian(this);
            }
            rack.addInventoryToRack(this, inventory); // polaczenie zwrotne
        }
    }

//    public double getSalary(){
//
//        return ( this.getBaseSalary() + (getSeniority() * 500) + this.languages.size() * 500); // bonus za staz i znajomosc jezykow
//    }

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


    @Basic
    public String getSsn() {
        return ssn;
    }

    public void setSsn(String ssn) {
        if (ssn == null || ssn.trim().isBlank()){
            throw new ValidationException("SSN cannot be empty");
        }
//        else if (getSsnDictionary().contains(ssn)) { // unikalnosc ssn
//            //throw new ValidationException("Given SSN is already used");
//        }
//        getSsnDictionary().add(ssn);
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

    @ManyToOne
    @JoinColumn(name = "library_id")
    public Library getLibrary() {
        return library;
    }

    public void setLibrary (Library library) {
        if ( library != null && this.library != library ) {
            this.library = library;
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
    public String toString() {
        return super.toString() + " Librarian{" +
                "languages=" + languages +
                '}';
    }

}
