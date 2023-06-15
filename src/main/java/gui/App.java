package gui;

import model.Book;

import javax.swing.*;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import static model.Book.booksFromDb;

public class App extends JFrame {
    private JTabbedPane tabbedPane1;

    private JButton btn;
    private JPanel main;
    private JPanel booksTab;
    private JPanel table;
    private JButton backButton;
    private JButton backButton1;
    private JTable booksTable;
    private JPanel mainTab;
    private JPanel renovationTab;
    private JTextField bookInfo;
    private JList list1;
    private JButton button1;
    private JButton dokonajRenowacjiButton;
    private JTabbedPane tabbedPane;
    private JButton button3;
    private JList lstBooks;
    private JTextArea txtTitle;
    private JTextArea txtCategory;
    private JTextArea txtCondition;
    private JTextArea txtPages;
    private JTextArea textPublishing;
    private JTextArea txtAge;
    private JList list2;


    public App () {

        JPanel mainTab = new JPanel();
        JPanel booksTab = new JPanel();
        JPanel renovationTab = new JPanel();
        JTabbedPane tabbedPane = new JTabbedPane();

        loadBooks();

        lstBooks.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked (MouseEvent e) {
                super.mouseClicked(e);
                if (e.getClickCount() == 2){
                   loadBook((Book) lstBooks.getSelectedValue());
                   if(renovationOption()){
                       bookInfo.setText(((Book) lstBooks.getSelectedValue()).getTitle());
                   }

                }
            }
        });
    }

    public boolean renovationOption () {
        String[] options = {"Tak", "Nie"};
        int choice = JOptionPane.showOptionDialog(null, "Czy chcesz dokonać renowacji wybranej książki?", "Możliwość renowacji", 0, 3, null, options, options[0]);
        if (choice == 0) {
            JOptionPane.showMessageDialog(null, "Przejdz do zakładki Renowacje - książka jest wybrana do rozpoczęcia renowacji");
            return true;
        } else
            return false;
    }

    public void loadBooks() {
        BookObjectsModel<Book> modelBook = new BookObjectsModel<Book>(booksFromDb);
        lstBooks.setModel(modelBook);

    }

    public void loadBook(Book book) {
        txtTitle.setText(book.getTitle());
        txtCategory.setText(book.getCategory().toString());
        txtCondition.setText(book.getBookCondition().toString());
        txtPages.setText(String.valueOf(book.getNumberOfPages()));
        textPublishing.setText(book.getPublishingHouse());
        txtAge.setText(String.valueOf(book.getAgeOfBook()));
    }

    private void setBook(Book book){
        bookInfo.setText(book.toString());
    }



    private void createUIComponents () {
        // TODO: place custom component creation code here


    }

    public void start () {
        this.setSize(300, 300);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setVisible(true);
        this.setContentPane(main);
        this.pack();
        //this.setLocationRelativeTo(null);

    }
}
