package gui;

import javax.swing.*;
import java.awt.event.*;

import static model.Book.booksFromDb;

public class App extends JFrame {
    private JPanel panel1;
    private JTabbedPane tabbedPane1;
    private JPanel mainTab;
    private JPanel bookTab;
    private JPanel borrowTab;
    private JPanel renovationTab;
    private JPanel inventoryTab;
    private JPanel reportsTab;

    private JTable booksTable;

    private JButton button1;
    private JButton btn;


    public App () {}

    private void createBooksTable(){
        BookTableModel bookModel = new BookTableModel(booksFromDb);
        booksTable = new JTable(bookModel);
        booksTable.setModel(bookModel);
        bookTab.add(booksTable);
    }

    private void createUIComponents () {
        // TODO: place custom component creation code here
        bookTab = new JPanel();
        tabbedPane1.addTab("Książki", bookTab); // Add bookTab to the tabbedPane1 JTabbedPane
        createBooksTable(); // Create JTable instance
    }

    public void start () {
        this.setSize(300, 300);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setVisible(true);
        this.setContentPane(panel1);
        this.pack();
        this.setLocationRelativeTo(null);
    }
}
