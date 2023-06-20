package gui;

import model.Book;
import model.Renovation;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.query.Query;
import org.hibernate.service.ServiceRegistry;

import javax.swing.*;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

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
    private JButton dokonajRenowacjiButton;
    private JTabbedPane tabbedPane;
    private JList lstBooks;
    private JTextArea txtTitle;
    private JTextArea txtCategory;
    private JTextArea txtCondition;
    private JTextArea txtPages;
    private JTextArea textPublishing;
    private JTextArea txtAge;
    private JList lstRestorerRenovations;
    private JTextArea sadadasdaTextArea;


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
                       loadBookRenovations((Book) lstBooks.getSelectedValue());
                       loadRestorerRenovations();
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
//        Session session = createSession();
//        Query query = session.createQuery("FROM model.Book b WHERE b.renovations.id = :id");
//        query.setParameter("id", renovation);
//        Book bookForRenovation = (Book) query.uniqueResult();
//        System.out.println(bookForRenovation.toString());
        //session.close();
    }

    public void loadBookRenovations(Book book){
        Session session = createSession();
        Query query = session.createQuery("FROM model.Renovation r WHERE r.book.id = :id");
        query.setParameter("id", book.getId());
        List<Renovation> renovations = query.list();
        RenovationObjectsModel<Renovation> modelRenovation = new RenovationObjectsModel<Renovation>(renovations);
        lstBookRenovations.setModel(modelRenovation);
        //session.close();
    }

    public void loadRestorerRenovations() {
        Session session = createSession();
        Query query = session.createQuery("FROM model.Renovation r WHERE r.restorer.id = :id");
        query.setParameter("id", loggedRestorer.getId());
        List<Renovation> renovations = query.list();
        RenovationObjectsModel<Renovation> modelRenovation = new RenovationObjectsModel<Renovation>(renovations);
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
        //this.setLocationRelativeTo(null);

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
