package model;

import interfaces.IClientLibrarian;

import java.time.LocalDate;
import java.time.Year;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class ClientLibrarian extends Librarian implements IClientLibrarian {

    private boolean regularClient;
    private String opinion;
    private String email;
    private String address;

    private List<Borrow> borrowDetails = new ArrayList<>();

    public ClientLibrarian(String name, String surname, LocalDate birthDate, String gender, String nationality, String opinion, String email, String ssn , LocalDate hiringDate, double baseSalary, String address, Set languages, boolean regularClient){
        super(name, surname, birthDate, gender, nationality, ssn, hiringDate, baseSalary, address, languages);
        this.regularClient = regularClient;
        this.opinion = opinion;
        this.email = email;
        this.address = address;
    }

    @Override
    public int getSeniority() {
        return Year.now().getValue() - this.getHiringDate().getYear();
    }

    @Override
    public boolean isRegularClient() {
        return this.regularClient;
    }

    @Override
    public List<Borrow> getBorrowDetails() {
        return null;
    }

    @Override
    public void addBorrowToClient(Book book, Borrow newBorrow) {
        if (!borrowDetails.contains(newBorrow)) {
            borrowDetails.add(newBorrow);

            book.addBorrowToBook(this, newBorrow);
        }
    }

    @Override
    public double getSalary(){
        return ( this.getBaseSalary() + (getSeniority() * 500) + this.getLanguages().size() * 500);
    }

    public Set<String> getLanguages(){
        return super.getLanguages();
    };

    @Override
    public String toString() {
        return super.toString() + " ClientLibrarian{" +
                "regularClient=" + regularClient +
                ", borrowDetails=" + borrowDetails +
                '}';
    }
}
