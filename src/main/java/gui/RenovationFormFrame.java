package gui;

import model.Book;
import model.Renovation;
import model.Restorer;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class RenovationFormFrame extends JFrame {
    private JTextArea txtRestorer;
    private JTextArea txtBook;
    private JTextArea textArea4;
    private JList lstMaterials;
    private JTextArea textArea3;
    private JButton btnRemoveMaterial;
    private JButton btnAddMaterial;
    private JButton anulujButton;
    private JButton zapiszButton;
    private JPanel RenovationFormPanel;
    private JTextArea txtMaterial;

    private  Book book;
    private  Restorer restorer;

    public RenovationFormFrame (Book book, Restorer restorer){
        this.book = book;
        this.restorer = restorer;
        fillDetails();
        List<String> materials;
//        DefaultListModel<String> model = new MaterialsObjectsModel<String>(materials);
//        JList<String> lstMaterials = new JList<String>(model);


        btnAddMaterial.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed (ActionEvent e) {
//                model.addElement(txtMaterial.toString());
//                txtMaterial.setText("");
//                lstMaterials.setModel(model);
            }
        });
    }
        public void fillDetails(){
            txtBook.setText(book.getTitle());
            txtRestorer.setText(restorer.getName() + " " + restorer.getSurname());
        }

        public void start () {
            this.setSize(300, 300);
            this.setVisible(true);
            this.setContentPane(RenovationFormPanel);
            this.pack();
            this.setLocationRelativeTo(null);

        }
}
