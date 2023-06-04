package model;

import exception.ValidationException;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.Year;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class Director extends Employee implements Serializable {

    private List<Report> generatedReports = new ArrayList<>(); // wykorzystana lista, aby zapamietac kolejnosc wygenerowanych raportow

    private String education;


    public Director (String name, String surname, LocalDate birthDate, String gender, String nationality, String ssn, Library library, LocalDate hiringDate, double baseSalary, String address, String education){
        super(name, surname, birthDate, gender, nationality, ssn, library, hiringDate, baseSalary, address);
        this.setEducation(education);
    }

    public Director(Employee prevEmployee, String education){
        super (prevEmployee.getName(), prevEmployee.getSurname(), prevEmployee.getBirthDate(), prevEmployee.getGender(),prevEmployee.getNationality(), prevEmployee.getSsn(), prevEmployee.getLibrary(), prevEmployee.getHiringDate(), prevEmployee.getBaseSalary(), prevEmployee.getAddress()); // kopiowanie danych z poprzedniego obiektu
        this.setEducation(education); // zapisanie nowych danych
    }

    public void setEducation (String education) {
        if (education == null || education.trim().isBlank() ) {
            throw new ValidationException("Education cannot be empty");
        }
        this.education = education;
    }

    public void generateReport(Report reportToGenerate){
        if (reportToGenerate == null) {
            throw new ValidationException("Report  cannot be empty");
        }
        else if (!generatedReports.contains(reportToGenerate)) {
            generatedReports.add(reportToGenerate);
            reportToGenerate.addReportAuthor(this); // polaczenie zwrotne
        }
    }

    public List<Report> getGeneratedReports() {
        return generatedReports;
    }

    public int getSeniority() { // wyliczalny
        return Year.now().getValue() - this.getHiringDate().getYear();
    }

    public double getSalary(){
        return ( this.getBaseSalary() + (getSeniority() * 500) + 5000); // bonus za staz i bonus dyrektorski
    }

    public static void getExtent() throws Exception {
        ObjectPlus.getExtent(Director.class);
    }

    @Override
    public String toString() {
        return super.toString() + " Director{" +
                "education='" + education + '\'' +
                '}';
    }
}
