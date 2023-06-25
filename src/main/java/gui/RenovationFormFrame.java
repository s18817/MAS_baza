package gui;

import enums.Condition;
import enums.State;
import enums.Status;
import model.Book;
import model.Renovation;
import model.Restorer;
import org.hibernate.Session;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.time.LocalDate;
import java.util.*;

public class RenovationFormFrame extends JFrame {
    private JTextArea txtRestorer;
    private JTextArea txtBook;
    private JTextArea txtResult;
    private JList lstMaterials;
    private JTextArea txtRenovationDate;
    private JButton btnRemoveMaterial;
    private JButton btnAddMaterial;
    private JButton btnCancel;
    private JButton btnSaveRenovation;
    private JPanel RenovationFormPanel;
    private JTextArea txtMaterial;
    private JRadioButton radioPlanned;
    private JRadioButton radioFinished;
    private JRadioButton radioInProgress;

    private  Book book;
    private  Restorer restorer;
    private List<String> materials = new ArrayList<>();
    private MaterialsObjectsModel model = new MaterialsObjectsModel<String>(materials);

    public RenovationFormFrame (Book book, Restorer restorer){
        this.book = book;
        this.restorer = restorer;

        fillDetails();
        ButtonGroup group = new ButtonGroup();
        group.add(radioPlanned);
        group.add(radioFinished);
        group.add(radioInProgress);



        btnAddMaterial.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed (ActionEvent e) {
                if(!(txtMaterial.getText() == null || txtMaterial.getText().trim().isBlank())){
                    addMaterial(txtMaterial.getText());
                }
            }
        });

        btnRemoveMaterial.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed (ActionEvent e) {
                if(!(lstMaterials.getSelectedValue() == null)){
                    removeMaterial((String) lstMaterials.getSelectedValue());
                }

            }
        });

        btnSaveRenovation.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed (ActionEvent e) {
                saveRenovationToDb();
                App.closeForm();
                //App.loadRestorerRenovations();
            }
        });

        btnCancel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed (ActionEvent e) {
                App.closeForm();
            }
        });

    }
        public void fillDetails(){
            txtBook.setText(book.getTitle());
            txtRestorer.setText(restorer.getName() + " " + restorer.getSurname());
        }

        public void addMaterial(String materialToAdd){
            //materials.add(materialToAdd);
            model.addObject(txtMaterial.getText());
            lstMaterials.setModel(model);
            txtMaterial.setText("");
        }

        public void removeMaterial(String materialToRemove){
        model.removeObject(materialToRemove);
        lstMaterials.setModel(model);
        }

        public String conditionChoice(){
            String[] options = {"Tak", "Nie"};
            int choice = JOptionPane.showOptionDialog(null, "Czy chcesz zmienić kondycję książki?", "Możliwość zmiany kondycji", 0, 3, null, options, options[0]);
            if (choice == 0) {
                EnumSet<Condition> possibleConditions = EnumSet.allOf(Condition.class);
                String[] conditionOptions = new String[possibleConditions.size()];
                int i = 0;
                for ( Condition condition : possibleConditions ) {
                    conditionOptions[i++] = condition.toString();
                }
                int selectedCondition = JOptionPane.showOptionDialog(null, "Podaj nową kondycję książki", "Zmiana kondycji książki",
                        JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE, null, conditionOptions,
                        conditionOptions[0]);
                String finalCondition = conditionOptions[selectedCondition];

                if (finalCondition.equals("NEW") && (book.getBookCondition().toString().equals("NEW"))){
                    JOptionPane.showMessageDialog(null, "Nie można zmienić statusu na nowy - książka już nie jest nowa");
                    conditionChoice();
                }

                return finalCondition;
            } else
                return null;

        }

        public void saveRenovationToDb(){
            String newCondition = conditionChoice();
            Session session = App.createSession();
            LocalDate renovationDate = LocalDate.parse(txtRenovationDate.getText());
            Set<String> materials = new HashSet<>();
            Status renovationStatus;
            for (int i = 0; i < model.getSize(); i++) {
                materials.add(model.getElementAt(i));
            }
            if (radioPlanned.isSelected()) {
                renovationStatus = Status.TODO;
            } else if (radioInProgress.isSelected()) {
                renovationStatus = Status.STARTED;
            } else {
                renovationStatus = Status.FINISHED;
            }

            Renovation newRenovation = new Renovation(1000, renovationDate, materials, renovationStatus, txtResult.getText());
            newRenovation.setBook(book);
            newRenovation.setRestorer(restorer);
            session.save(newRenovation);

            if (!(newCondition == null || newCondition.trim().isBlank())) {
                book.updateBookCondition(Condition.valueOf(newCondition));
                session.update(book);
            }
            session.getTransaction().commit();
            JOptionPane.showMessageDialog(null, "Renowacja została zapisana");
        }


    public void start () {
            this.setSize(300, 300);
            this.setVisible(true);
            this.setContentPane(RenovationFormPanel);
            this.pack();
            this.setLocationRelativeTo(null);

        }
}
