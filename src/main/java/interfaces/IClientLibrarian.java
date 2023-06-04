package interfaces;

import model.Book;
import model.Borrow;

import java.util.List;
import java.util.Set;

public interface IClientLibrarian {

    public abstract double getSalary();
    public abstract int getSeniority();
    public abstract Set<String> getLanguages();
    public abstract boolean isRegularClient();
    public abstract List<Borrow> getBorrowDetails();
    public abstract void addBorrowToClient(Book book, Borrow newBorrow);

}
