package model;

import exception.ValidationException;
import org.hibernate.annotations.GenericGenerator;

import jakarta.persistence.*;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

//@Entity(name = "model.Library")
public class Library extends ObjectPlus implements Serializable {

    private long id;
    private String name;
    private String street;
    private String city;
    private String postalCode;

    private List<Employee> employees = new ArrayList<>(); // kolekcja do przetrzymywania powiazan z Pracownikami

    private Map<String, Employee> employeeQualif = new TreeMap<>(); // w jednej bibliotece moze pracowac wielu pracownikow

    @Override
    public String toString () {
        return "Library{" +
                "name='" + name + '\'' +
                ", street='" + street + '\'' +
                ", city='" + city + '\'' +
                ", postalCode='" + postalCode + '}';
    }

    public Library(String name, String street, String city, String postalCode) {
        setName(name);
        setStreet(street);
        setCity(city);
        setPostalCode(postalCode);
    }

//    @Id()
//    @GeneratedValue(generator="increment")
//    @GenericGenerator(name="increment", strategy = "increment")
//    public long getId() {
//        return id;
//    }
//
//    public void setId(long id) {
//        this.id = id;
//    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        if (name == null || name.trim().isBlank()){
            throw new ValidationException("Name cannot be empty");
        }
        this.name = name;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        if (street == null || street.trim().isBlank()){
            throw new ValidationException("Street cannot be empty");
        }
        this.street = street;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        if (city == null || city.trim().isBlank()){
            throw new ValidationException("City cannot be empty");
        }
        this.city = city;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        if (postalCode == null || postalCode.trim().isBlank()){
            throw new ValidationException("Postal code cannot be empty");
        }
        this.postalCode = postalCode;
    }


    public void addEmployee(Employee employeeToAdd) {

        if (!employees.contains(employeeToAdd)) {
            employees.add(employeeToAdd);

            employeeToAdd.addLibrary(this); // polaczenie zwrotne
        }
    }

    public void addEmployeeQualif(Employee newEmployee) {
        if (!employeeQualif.containsKey(newEmployee.getSsn())) {
            employeeQualif.put(newEmployee.getSsn(), newEmployee);

            newEmployee.addLibrary(this);  // polaczenie zwrotne
        }

    }

    public Employee findEmployeeQualif(String ssn) throws Exception {
        if (!employeeQualif.containsKey(ssn)) {
            throw new Exception("No such ssn number" + ssn);
        }
        return employeeQualif.get(ssn);
    }

    public List<Employee> getEmployees () {
        return employees;
    }

    public void setEmployees (List<Employee> employees) {
        this.employees = employees;
    }

    public Map<String, Employee> getEmployeeQualif () {
        return employeeQualif;
    }

    public void setEmployeeQualif (Map<String, Employee> employeeQualif) {
        this.employeeQualif = employeeQualif;
    }

    public static void showExtent() throws Exception {
        ObjectPlus.showExtent(Library.class);
    }

    public static void getExtent() throws Exception {
        ObjectPlus.getExtent(Library.class);
    }

}
