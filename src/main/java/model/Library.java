package model;

import org.hibernate.annotations.GenericGenerator;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

@Entity(name = "model.Library")
public class Library {

    private long id;
    private String name;
    private String street;
    private String city;
    private String postalCode;

    private List<Employee> employees = new ArrayList<>(); // kolekcja do przetrzymywania powiazan z Pracownikami

    private Map<String, Employee> employeeQualif = new TreeMap<>(); // w jednej bibliotece moze pracowac wielu pracownikow

    public Library(long id, String name, String street, String city, String postalCode) {
        this.id = id;
        this.name = name;
        this.street = street;
        this.city = city;
        this.postalCode = postalCode;
    }

    private List<Workstation> workstation = new ArrayList<Workstation>();


    @Id()
    @GeneratedValue(generator="increment")
    @GenericGenerator(name="increment", strategy = "increment")
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    @OneToMany(mappedBy="library")
    public List<Workstation> getWorkstation() {
        return workstation;
    }

    public void setWorkstation(List<Workstation> workstation) {
        this.workstation = workstation;
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




}
