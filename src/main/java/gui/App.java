package gui;

import model.Book;
import model.Renovation;
import model.Restorer;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.service.ServiceRegistry;

import javax.swing.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import static model.Book.booksFromDb;
import static model.Restorer.loggedRestorer;

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
    private JPanel renovationsTab;
    private JTextField bookInfo;
    private JList lstBookRenovations;
    private JButton button1;
    private JButton btnCreateRenovation;
    private JTabbedPane tabbedPane;
    private JList lstBooks;
    private JTextArea txtTitle;
    private JTextArea txtCategory;
    private JTextArea txtCondition;
    private JTextArea txtPages;
    private JTextArea textPublishing;
    private JTextArea txtAge;
    private JList lstRestorerRenovations;
    private JTextArea loggedUser;

    private Restorer selectedRestorer;
    private Book selectedBook;


    public App () {

        JPanel mainTab = new JPanel();
        JPanel booksTab = new JPanel();
        JPanel renovationTab = new JPanel();
        JTabbedPane tabbedPane = new JTabbedPane();


        selectedRestorer = loggedRestorer;
        loggedUser.setText(loggedRestorer.getName() + " " + loggedRestorer.getSurname());

        loadBooks();
        loadRestorerRenovations();

        lstBooks.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked (MouseEvent e) {
                super.mouseClicked(e);
                if (e.getClickCount() == 2){
                   loadBook((Book) lstBooks.getSelectedValue());
                   if(renovationOption()){
                       bookInfo.setText(((Book) lstBooks.getSelectedValue()).getTitle());
                       loadBookRenovations((Book) lstBooks.getSelectedValue());
                       selectedBook = ((Book) lstBooks.getSelectedValue());
                   }

                }
            }
        });

        lstRestorerRenovations.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked (MouseEvent e) {
                super.mouseClicked(e);
                if (e.getClickCount() == 2){
                        showBookForRenovation(((Renovation) lstRestorerRenovations.getSelectedValue()));
                }
            }
        });

        lstBookRenovations.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked (MouseEvent e) {
                super.mouseClicked(e);
                if (e.getClickCount() == 2){
                    showRestorerForRenovation(((Renovation) lstBookRenovations.getSelectedValue()));
                }
            }
        });


        btnCreateRenovation.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed (ActionEvent e) {
                openRenovationForm();
            }
        });
    }

    public void openRenovationForm(){
        RenovationFormFrame renovationFormFrame = new RenovationFormFrame(selectedBook, selectedRestorer);
        renovationFormFrame.start();
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
    public void showBookForRenovation(Renovation renovation){
        BookFrame bookFrame = new BookFrame(renovation.getBook());
        bookFrame.start();
    }

    public void showRestorerForRenovation(Renovation renovation){
        RestorerFrame restorerFrame = new RestorerFrame(renovation.getRestorer());
        restorerFrame.start();
    }

    public void loadBookRenovations(Book book){
//        Session session = createSession();
//        Query query = session.createQuery("FROM model.Renovation r WHERE r.book.id = :id");
//        query.setParameter("id", book.getId());
//        List<Renovation> renovations = query.list();

        RenovationObjectsModel<Renovation> modelRenovation = new RenovationObjectsModel<Renovation>(book.getRenovations());
        lstBookRenovations.setModel(modelRenovation);
        //session.close();
    }

    public void loadRestorerRenovations() {
//        Session session = createSessloadRestorerRenovationsion();
//        Query query = session.createQuery("FROM model.Renovation r WHERE r.restorer.id = :id");
//        query.setParameter("id", loggedRestorer.getId());
//        List<Renovation> renovations = query.list();
        RenovationObjectsModel<Renovation> modelRenovation = new RenovationObjectsModel<Renovation>(loggedRestorer.getRenovations());
        lstRestorerRenovations.setModel(modelRenovation);
        //session.close();

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
        this.setLocationRelativeTo(null);

    }

    public Session createSession() {
        ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
                .configure()
                .build();

        SessionFactory sessionFactory = new MetadataSources(serviceRegistry)
                .buildMetadata()
                .buildSessionFactory();

        Session session = sessionFactory.openSession();
        session.beginTransaction();

        return session;
    }
}
