package gui;

import model.Book;

import javax.swing.table.AbstractTableModel;
import java.util.List;

public class BookTableModel extends AbstractTableModel {

    private List<Book> bookList;
    private String[] columns = {"Tytuł", "Kategorie", "Konycja", "Liczba stron", "Rok wydania", "Wydawnictwo", "Nazwa wypożyczalni", "wiek"};

    public BookTableModel (List<Book> bookList) {
        this.bookList = bookList;
    }

    @Override
    public int getRowCount () {
        return bookList.size();
    }

    @Override
    public int getColumnCount () {
        return columns.length;
    }

    @Override
    public String getColumnName (int index) {
        return columns[index];
    }

    @Override
    public Object getValueAt (int rowIndex, int columnIndex) {
        Book book = bookList.get(rowIndex);
        switch (columnIndex) {
            case 0:
                return book.getTitle();
            case 1:
                return book.getCategory().toString();
            case 2:
                return book.getBookCondition();
            case 3:
                return book.getNumberOfPages();
            case 4:
                return book.getYearOfEdition();
            case 5:
                return book.getPublishingHouse();
            case 6:
                return book.getBookRentalName();
            case 7:
                return book.getAgeOfBook();
            default:
                return null;
        }
    }

}