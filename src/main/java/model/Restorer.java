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
@Table(name = "restorer")
public class Restorer implements Serializable {

    public static List<Restorer> restorersFromDb = new ArrayList<>();

    private long id;
    private String name;
    private String surname;
    private String street;
    private String city;
    private String postalCode;
    private LocalDate birthDate;
    private String gender;
    private String nationality;
    private String specialisation;
    private String ssn; // social security number // kwalifikator
    private Library library; // dla pracownika tylko jedna biblioteka
    private double baseSalary;
    private LocalDate hiringDate; // data  zatrudnienia
    private boolean changeFlag;

    public static Restorer loggedRestorer;

    private List<Renovation> renovations = new ArrayList<>(); // asocjacja z atrybutem ; kolekcja do przetrzymywania wykonanych renowacji ksiazek ; kolekcja, poniewaz jeden konwserwator moze wykonac wiele renowacji
    private List<RestorerReport> restorerReports = new ArrayList<>();

    public Restorer(String name, String surname, String street, String city, String postalCode, LocalDate birthDate, String gender, String nationality, String ssn, LocalDate hiringDate, double baseSalary, String specialisation){
        setName(name);
        setSurname(surname);
        setStreet(street);
        setCity(city);
        setPostalCode(postalCode);
        setBirthDate(birthDate);
        setGender(gender);
        setNationality(nationality);
        setSsn(ssn);
        setHiringDate(hiringDate);
        setBaseSalary(baseSalary);
        setSpecialisation(specialisation);
    }

    public Restorer(Director prevEmployee, String specialisation){
        setName(prevEmployee.getName());
        setSurname(prevEmployee.getSurname());
        setStreet(prevEmployee.getStreet());
        setCity(prevEmployee.getCity());
        setPostalCode(prevEmployee.getPostalCode());
        setBirthDate(prevEmployee.getBirthDate());
        setGender(prevEmployee.getGender());
        setNationality(prevEmployee.getNationality());
        setSsn(prevEmployee.getSsn());
        setHiringDate(prevEmployee.getHiringDate());
        setBaseSalary(prevEmployee.getBaseSalary());
        setSpecialisation(specialisation); // zapisanie nowych danych
        this.changeFlag = true;
    }

    public Restorer(Librarian prevEmployee, String specialisation){
        setName(prevEmployee.getName());
        setSurname(prevEmployee.getSurname());
        setStreet(prevEmployee.getStreet());
        setCity(prevEmployee.getCity());
        setPostalCode(prevEmployee.getPostalCode());
        setBirthDate(prevEmployee.getBirthDate());
        setGender(prevEmployee.getGender());
        setNationality(prevEmployee.getNationality());
        setSsn(prevEmployee.getSsn());
        setHiringDate(prevEmployee.getHiringDate());
        setBaseSalary(prevEmployee.getBaseSalary());
        setSpecialisation(specialisation); // zapisanie nowych danych
        this.changeFlag = true;
    }

    public Restorer(){};

    @Id
    @GeneratedValue(generator = "increment")
    @GenericGenerator(name = "increment", strategy = "increment")
    @Column(name = "restorer_id", nullable = false)
    public long getId () {
        return id;
    }

    public void setId (long id) {
        if (id < 0) {
            throw new ValidationException("ID cannot be negative");
        }
        this.id = id;
    }

    @OneToMany(mappedBy = "restorer", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @OrderBy("renovationDate")
    public List<Renovation> getRenovations () {
        return renovations;
    }

    public void setRenovations (List<Renovation> doneRenovations) {
        this.renovations = doneRenovations;
    }

    @OneToMany(mappedBy = "restorer", fetch = FetchType.EAGER)
    public List<RestorerReport> getRestorerReports () {
        return restorerReports;
    }

    public void setRestorerReports (List<RestorerReport> restorerReports) {
        this.restorerReports = restorerReports;
    }

    public void generateReport(RestorerReport reportToGenerate){
        if (reportToGenerate == null) {
            throw new ValidationException("Report  cannot be empty");
        }
        else if (!restorerReports.contains(reportToGenerate)) {
            reportToGenerate.setDoneRenovations(this.getRenovations().size());
            reportToGenerate.setSuggestedBonus(this.getBaseSalary() + reportToGenerate.getDoneRenovations() * 100 );
            restorerReports.add(reportToGenerate);
            reportToGenerate.addRestorerToReport(this); // polaczenie zwrotne
        }
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

    public void addLibraryToRestorer(Library libraryToAdd){
        if ( libraryToAdd != null && this.library != libraryToAdd ) {
            this.library = libraryToAdd;
            libraryToAdd.addRestorer(this); // polaczenie zwrotne
        }
    }

    public void removeLibrary() {
        this.library = null;
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

        if (!renovations.contains(renovation)) {
            if (book.getState() == State.DOSTĘPNA) { // nie mozna poddac renowacji ksiazki, ktora nie jest dostepna
                renovations.add(renovation);
                if(renovation.getRestorer() == null && renovation.getBook() == null){
                    renovation.setBook(book);
                    renovation.setRestorer(this);
                }
                book.addRenovationToBook(this, renovation); // polaczenie zwrotne
            } else {
                throw new ValidationException("Book is not available and cannot be restored");
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


    @Basic
    public String getSsn() {
        return ssn;
    }

    public void setSsn(String ssn) {
        if (ssn == null || ssn.trim().isBlank()){
            throw new ValidationException("SSN cannot be empty");
        }
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

    public boolean isChangeFlag () {
        return changeFlag;
    }

    public void setChangeFlag (boolean changeFlag) {
        this.changeFlag = changeFlag;
    }

    //    @Override
//    public String toString () {
//        return super.toString() + "Restorer{" +
//                "specialisation='" + specialisation + '\'' +
//                ", doneRenovations=" + renovations +
//                '}';
//    }


    @Override
    public String toString () {
        return "Restorer{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", street='" + street + '\'' +
                ", city='" + city + '\'' +
                ", postalCode='" + postalCode + '\'' +
                ", birthDate=" + birthDate +
                ", gender='" + gender + '\'' +
                ", nationality='" + nationality + '\'' +
                ", specialisation='" + specialisation + '\'' +
                ", ssn='" + ssn + '\'' +
                //", library=" + library +
                ", baseSalary=" + baseSalary +
                ", hiringDate=" + hiringDate +
                ", changeFlag=" + changeFlag +
                ", renovations=" + renovations +
                ", restorerReports=" + restorerReports +
                '}';
    }

    public static List<Restorer> getRestorersFromDb () {
        return restorersFromDb;
    }

}
