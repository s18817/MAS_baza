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
import java.time.LocalDate;
import java.util.*;
import static model.Restorer.loggedRestorer;

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

                if (finalCondition.equals("NOWA") && (book.getBookCondition().toString().equals("NOWA"))){
                    JOptionPane.showMessageDialog(null, "Nie można zmienić statusu na nowy - książka już nie jest nowa");
                    conditionChoice();
                }

                return finalCondition;
            } else
                return null;

        }

    public boolean validateDate (String formDate) {

        if (formDate.matches("^\\d{4}-(0[1-9]|1[0-2])-(0[1-9]|[12]\\d|3[01])$")) {
            return true;
        } else {
            JOptionPane.showMessageDialog(null, "Podaj prawidłową datę (RRRR-MM-DD)");
            return false;
        }
    }

    public boolean validateMaterials(Set<String> formMaterials){
        if (formMaterials.size() >= 1){
            return true;
        }
        else{
            JOptionPane.showMessageDialog(null, "Podaj przynajmniej jeden materiał");
            return false;
        }
    }

    public boolean validateResult (String formResult) {
        if (formResult.length() <= 100 && formResult.length() > 0) {
            return true;
        } else if (formResult == null || formResult.trim().isBlank()) {
            JOptionPane.showMessageDialog(null, "Wynik renowacji nie może być pusty");
            return false;
        } else {
            JOptionPane.showMessageDialog(null, "Podany tekst jest zbyt długi. Maksymalna ilość znaków to 100. Podano: " + formResult.length());
            return false;
        }
    }

        public void saveRenovationToDb(){

            Set<String> materials = new HashSet<>();

            for (int i = 0; i < model.getSize(); i++) {
                materials.add(model.getElementAt(i));
            }

            if (validateDate(txtRenovationDate.getText()) && validateMaterials(materials) && validateResult(txtResult.getText())){
                String newCondition = conditionChoice();
                Session session = App.createSession();
                Status renovationStatus;
                LocalDate renovationDate = LocalDate.parse(txtRenovationDate.getText());
                if (radioPlanned.isSelected()) {
                    renovationStatus = Status.ZAPLANOWANA;
                } else if (radioInProgress.isSelected()) {
                    renovationStatus = Status.W_TRAKCIE;
                    book.setState(State.RENOWACJA);
                } else {
                    renovationStatus = Status.ZAKOŃCZONA;
                }
                Renovation newRenovation = new Renovation(1000, renovationDate, materials, renovationStatus, txtResult.getText());
                newRenovation.setRestorer(loggedRestorer);
                newRenovation.setBook(book);
                session.save(newRenovation);


                if (!(newCondition == null || newCondition.trim().isBlank())) {
                    book.updateBookCondition(Condition.valueOf(newCondition));
                    session.update(book);

                }
                session.getTransaction().commit();

                if (newCondition != null){
                    session.refresh(book);
                    loggedRestorer = session.get(Restorer.class, loggedRestorer.getId());
                    session.close();
                }else{
                    Session session2 = App.createSession();
                    session2.refresh(book);
                    loggedRestorer = session2.get(Restorer.class, loggedRestorer.getId());
                    session2.close();
                }


                JOptionPane.showMessageDialog(null, "Renowacja została zapisana");

                System.out.println(restorer.getRenovations().toString());
                System.out.println(loggedRestorer.getRenovations().toString());
                System.out.println(book.getRenovations().toString());
                App.closeForm();

            }
        }

    public void start () {
            this.setSize(300, 300);
            this.setVisible(true);
            this.setContentPane(RenovationFormPanel);
            this.pack();
            this.setLocationRelativeTo(null);

        }
}
