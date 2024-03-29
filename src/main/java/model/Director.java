package model;
import static model.Book.booksFromDb;
import enums.State;
import exception.ValidationException;
import jakarta.persistence.*;
import org.hibernate.annotations.GenericGenerator;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.Year;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Entity(name = "model.Director")
@Table(name = "director")
public class Director implements Serializable {

    public static List<Director> directorsFromDb = new ArrayList<>();

    private long id;
    private String name;
    private String surname;
    private String street;
    private String city;
    private String postalCode;
    private LocalDate birthDate;
    private String gender;
    private String nationality;
    private String ssn; // social security number // kwalifikator
    private Library library; // dla pracownika tylko jedna biblioteka
    private double baseSalary;
    private LocalDate hiringDate; // data  zatrudnienia
    private String education;
    private boolean changeFlag;


    private List<BookReport> bookReports = new ArrayList<>();


    public Director (String name, String surname, String street, String city, String postalCode, LocalDate birthDate, String gender, String nationality, String ssn , LocalDate hiringDate, double baseSalary, String education){
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
        setEducation(education);
    }

    public Director(Restorer prevEmployee, String education){
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
        setEducation(education);
        this.changeFlag = true;
        }

    public Director(Librarian prevEmployee, String education){
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
        setEducation(education);
        this.changeFlag = true;
    }

    public Director(){};

    @Id
    @GeneratedValue(generator = "increment")
    @GenericGenerator(name = "increment", strategy = "increment")
    @Column(name = "director_id", nullable = false)
    public long getId () {
        return id;
    }

    public void setId (long id) {
        if (id < 0) {
            throw new ValidationException("ID cannot be negative");
        }
        this.id = id;
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

    public void addLibraryToDirector(Library libraryToAdd){
        if ( libraryToAdd != null && this.library != libraryToAdd ) {
            this.library = libraryToAdd;
            libraryToAdd.addDirector(this); // polaczenie zwrotne
        }
    }

    public void removeLibrary() {
        this.library = null;
    }

    public void generateReport(BookReport reportToGenerate){
        if (reportToGenerate == null) {
            throw new ValidationException("Report cannot be empty");
        }
        else if (!bookReports.contains(reportToGenerate)) {
            bookReports.add(reportToGenerate);
            reportToGenerate.setBookAmount(booksFromDb.size());
            reportToGenerate.addReportAuthor(this); // polaczenie zwrotne
        }
    }

    @OneToMany(mappedBy = "director", fetch = FetchType.EAGER)
    public List<BookReport> getBookReports () {
        return bookReports;
    }

    public void setBookReports (List<BookReport> bookReports) {
        this.bookReports = bookReports;
    }

    @Basic
    public String getEducation () {
        return education;
    }

    public void setEducation (String education) {
        if (education == null || education.trim().isBlank() ) {
            throw new ValidationException("Education cannot be empty");
        }
        this.education = education;
    }

    @Transient
    public int getSeniority() { // wyliczalny
        return Year.now().getValue() - this.getHiringDate().getYear();
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

    @Override
    public String toString () {
        return "Director{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", street='" + street + '\'' +
                ", city='" + city + '\'' +
                ", postalCode='" + postalCode + '\'' +
                ", birthDate=" + birthDate +
                ", gender='" + gender + '\'' +
                ", nationality='" + nationality + '\'' +
                ", ssn='" + ssn + '\'' +
                //", library=" + library +
                ", baseSalary=" + baseSalary +
                ", hiringDate=" + hiringDate +
                ", education='" + education + '\'' +
                ", changeFlag=" + changeFlag +
                ", bookReports=" + bookReports +
                '}';
    }

    public static List<Director> getDirectorsFromDb () {
        return directorsFromDb;
    }
}
