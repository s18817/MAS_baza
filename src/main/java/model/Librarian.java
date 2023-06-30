package model;

import exception.ValidationException;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.Year;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Librarian extends Employee implements Serializable {

    private Set<String> languages = new HashSet<>();

    private List<Inventory> doneInventories = new ArrayList<>();


    public Librarian (String name, String surname, LocalDate birthDate, String gender, String nationality, String ssn, LocalDate hiringDate, double baseSalary, String address, Set languages){
        super(name, surname, birthDate, gender, nationality, ssn, hiringDate, baseSalary, address);
        this.setLanguages(languages);
    }

    public Librarian(Employee prevEmployee, Set languages){
        super (prevEmployee.getName(), prevEmployee.getSurname(), prevEmployee.getBirthDate(), prevEmployee.getGender(),prevEmployee.getNationality(), prevEmployee.getSsn(), prevEmployee.getHiringDate(), prevEmployee.getBaseSalary(), prevEmployee.getAddress(), true); // kopiowanie danych z poprzedniego obiektu
        this.setLanguages(languages); // zapisanie nowych danych
    }

    public void setLanguages (Set<String> languages) {
        if (languages.size() < 2){
            throw new ValidationException("Librarian has to know minimum two foreign languages");
        } else if (languages.contains("polski")) {
            throw new ValidationException("Polish language is not taken into consideration");
        }
        this.languages = languages;
    }

    public int getSeniority() { // wyliczalny
        return Year.now().getValue() - this.getHiringDate().getYear();
    }

    public void addInventory(Rack rack, Inventory inventory){
        if (!doneInventories.contains(inventory)) {
            doneInventories.add(inventory);
        }
    }

    public List<Inventory> getDoneInventories () {
        return doneInventories;
    }

    public double getSalary(){

        return ( this.getBaseSalary() + (getSeniority() * 500) + this.languages.size() * 500); // bonus za staz i znajomosc jezykow
    }

    public Set<String> getLanguages() {
        return languages;
    }

    @Override
    public String toString() {
        return super.toString() + " Librarian{" +
                "languages=" + languages +
                '}';
    }

    public static void showExtent() throws Exception {
        ObjectPlus.showExtent(Librarian.class);
    }

    public static void getExtent() throws Exception {
        ObjectPlus.getExtent(Librarian.class);
    }
}
