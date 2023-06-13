package model;

import exception.ValidationException;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.Year;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

public abstract class Employee extends Person implements Serializable {

    private String ssn; // social security number // kwalifikator
    private Library library; // dla pracownika tylko jedna biblioteka
    private double baseSalary;
    private LocalDate hiringDate; // data  zatrudnienia
    private String address; // dane adresowe

    private static Set<String> ssnDictionary = new HashSet<>();  // zadbanie o unikalność numerów ssn każdego pracownika


    public Employee(String name, String surname, LocalDate birthDate, String gender, String nationality, String ssn, LocalDate hiringDate, double baseSalary, String address) {
        super(name, surname, birthDate, nationality, gender );
        //this.setSsn(ssn);
        this.setHiringDate(hiringDate);
        //this.addLibrary(library);
        this.setBaseSalary(baseSalary);
        this.setAddress(address);
    }

    //public abstract double getSalary();


    public void setSsn(String ssn) {
        if (ssn == null || ssn.trim().isBlank()){
            throw new ValidationException("SSN cannot be empty");
        }
        else if (ssnDictionary.contains(ssn)) { // unikalnosc ssn
            throw new ValidationException("Given SSN is already used");
        }

        ssnDictionary.add(ssn);
        this.ssn = ssn;
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

    public void addLibrary(Library library){
        if ( library != null && this.library != library ) {
            this.library = library;
            library.addEmployee(this);
        }
    }

    public void setBaseSalary (double baseSalary) {
        if (baseSalary < 3490) {
            throw new ValidationException("Salary cannot be lower than 3490");
        } else if (baseSalary < 0) {
            throw new ValidationException("Salary cannot be negative");
        }
        this.baseSalary = baseSalary;
    }

    public void setAddress (String address) {
        if (address == null || address.trim().isBlank()) {
            throw new ValidationException("Address cannot be empty");
        } else if (address.length() > 100) {
            throw new ValidationException("Address cannot be longer that 100 digits");
        }
        this.address = address;
    }

    public String getSsn() {
        return ssn;
    }

    public LocalDate getHiringDate() {
        return hiringDate;
    }

    public Library getLibrary() {
        return library;
    }

    public double getBaseSalary() {
        return baseSalary;
    }

    public String getAddress () {
        return address;
    }

    public static Set<String> getSsnDictionary () {
        return ssnDictionary;
    }

    public static Employee findEmployee(String ssn) throws ClassNotFoundException { // na bazie ssn
        for (Librarian lib : ObjectPlus.getExtent(Librarian.class)) {
            if (lib.getSsn().equals(ssn)) {
                return lib;
            }
        }
        for (Restorer res : ObjectPlus.getExtent(Restorer.class)) {
            if (res.getSsn().equals(ssn)) {
                return res;
            }
        }
        for (Director dir : ObjectPlus.getExtent(Director.class)) {
            if (dir.getSsn().equals(ssn)) {
                return dir;
            }
        }
        return null;
    }

    @Override
    public String toString() {
        return super.toString() + " Employee{" +
                "ssn='" + ssn + '\'' +
                ", library=" + library +
                ", baseSalary=" + baseSalary +
                ", hiringDate=" + hiringDate +
                '}';
    }
}
