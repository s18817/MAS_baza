package gui;

import model.Book;

import javax.swing.*;

public class BookFrame extends JFrame {
    private JTextArea txtTitle;
    private JTextArea txtCategory;
    private JTextArea txtCondition;
    private JTextArea txtPages;
    private JPanel bookPanel;
    private JTextArea textPublishing;
    private JTextArea txtAge;
    private JTextArea txtState;

    private Book book;

    public BookFrame (Book book){
        this.book = book;
        fillBookDetails();
    };

    public void fillBookDetails(){
        txtTitle.setText(book.getTitle());
        txtCategory.setText(book.getCategory().toString());
        txtCondition.setText(book.getBookCondition().toString());
        txtPages.setText(String.valueOf(book.getNumberOfPages()));
        textPublishing.setText(book.getPublishingHouse());
        txtAge.setText(String.valueOf(book.getAgeOfBook()));
        txtState.setText(book.getState().toString());
    }

    public void start () {
        this.setSize(300, 300);
        this.setVisible(true);
        this.setContentPane(bookPanel);
        this.pack();
        this.setLocationRelativeTo(null);

    }
}
