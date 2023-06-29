package gui;

import enums.State;
import model.Book;
import model.Renovation;
import model.Restorer;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.service.ServiceRegistry;

import javax.swing.*;

import java.awt.event.*;

import static model.Book.booksFromDb;
import static model.Restorer.loggedRestorer;

public class App extends JFrame {

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
    private JButton btnRefresh;
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
    private JTextArea txtState;
    private JLabel Stan;
    private JButton btnSelectBookForRenovation;

    public static RenovationFormFrame renovationFormFrame;
    private Book selectedBook;


    public App () {

//        mainTab = new JPanel();
//        booksTab = new JPanel();
//        renovationsTab = new JPanel();
//        tabbedPane = new JTabbedPane();
//
//        tabbedPane.addTab("Strona główna", mainTab);
//        tabbedPane.addTab("Książki", booksTab);
//        tabbedPane.addTab("Renowacje", renovationsTab);
//        main.add(tabbedPane, BorderLayout.CENTER);


        loggedUser.setText(loggedRestorer.getName() + " " + loggedRestorer.getSurname());

        loadBooks();
        loadRestorerRenovations(loggedRestorer);

        lstBooks.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked (MouseEvent e) {
                super.mouseClicked(e);
                if (e.getClickCount() == 2){
                   loadBook((Book) lstBooks.getSelectedValue());
                   selectedBook = ((Book) lstBooks.getSelectedValue());
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
                if (!(bookInfo.getText() == null || bookInfo.getText().trim().isBlank())) {
                    if ((selectedBook.getState() != State.DOSTĘPNA)) {
                        JOptionPane.showMessageDialog(null, "Książka nie jest dostępna, nie można dokonać renowacji");
                    } else {
                        openRenovationForm();
                    }
                }
                else {
                    JOptionPane.showMessageDialog(null, "Proszę wybrać książkę, aby dokonać renowacji");
                }
            }
        });

        btnRefresh.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed (ActionEvent e) {
                if (!(bookInfo.getText() == null || bookInfo.getText().trim().isBlank())){
                    loadBookRenovations(selectedBook);
                    loadBook(selectedBook);
                }
                loadRestorerRenovations(loggedRestorer);

            }
        });
        btnSelectBookForRenovation.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed (ActionEvent e) {
                if (selectedBook == null){
                    JOptionPane.showMessageDialog(null, "Proszę wybrać książkę, aby dokonać renowacji");
                }
                else if (renovationOption()) {
                    bookInfo.setText(((Book) lstBooks.getSelectedValue()).getTitle());
                    loadBookRenovations((Book) lstBooks.getSelectedValue());
                    loadRestorerRenovations(loggedRestorer);
                    //tabbedPane.setSelectedIndex(2);
                    //tabbedPane.setSelectedComponent(renovationsTab);
                }
            }
        });
    }

    public void openRenovationForm(){
        renovationFormFrame = new RenovationFormFrame(selectedBook, loggedRestorer);
        renovationFormFrame.start();
    }

    public boolean renovationOption () {
        String[] options = {"Tak", "Nie"};
        int choice = JOptionPane.showOptionDialog(null, "Czy chcesz dokonać renowacji wybranej książki?", "Możliwość renowacji", 0, 3, null, options, options[0]);
        if (choice == 0) {
            JOptionPane.showMessageDialog(null, "Przejdź do zakładki Renowacje - książka jest wybrana do rozpoczęcia renowacji");
            return true;
        } else
            return false;
    }

    public void loadBooks() {
        if (booksFromDb.size() == 0){
            JOptionPane.showMessageDialog(null, "Brak książek do wyświetlenia");
        }
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
            RenovationObjectsModel<Renovation> modelRenovation = new RenovationObjectsModel<Renovation>(book.getRenovations());
            lstBookRenovations.setModel(modelRenovation);
    }

    public void loadRestorerRenovations(Restorer restorer) {
        RenovationObjectsModel<Renovation> modelRenovation = new RenovationObjectsModel<Renovation>(restorer.getRenovations());
        System.out.println(restorer.getRenovations().toString());
        lstRestorerRenovations.setModel(modelRenovation);

    }

    public void loadBook(Book book) {
        txtTitle.setText(book.getTitle());
        txtCategory.setText(book.getCategory().toString());
        txtCondition.setText(book.getBookCondition().toString());
        txtPages.setText(String.valueOf(book.getNumberOfPages()));
        textPublishing.setText(book.getPublishingHouse());
        txtAge.setText(String.valueOf(book.getAgeOfBook()));
        txtState.setText(book.getState().toString());
    }

    public static void closeForm (){
        renovationFormFrame.dispose();
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
        this.setContentPane(main);
        this.pack();
        this.setLocationRelativeTo(null);
        this.setVisible(true);

    }
    public JList getLstBooks () {
        return lstBooks;
    }

    public static Session createSession () {
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
