package gui;

import model.Renovation;
import model.Restorer;

import javax.swing.*;

public class RestorerFrame extends JFrame {

    private Restorer restorer;
    private JTextArea txtName;
    private JTextArea txtSurname;
    private JTextArea txtSpecialisation;
    private JTextArea txtHiringDate;
    private JPanel restorerPanel;

    public RestorerFrame (Restorer restorer) {
        this.restorer = restorer;
        fillRestorerDetails();
    };

    public void fillRestorerDetails(){
        txtName.setText(restorer.getName());
        txtSurname.setText(restorer.getSurname());
        txtSpecialisation.setText(restorer.getSpecialisation());
        txtHiringDate.setText(restorer.getHiringDate().toString());
    }

    public void start () {
        this.setSize(300, 300);
        this.setVisible(true);
        this.setContentPane(restorerPanel);
        this.pack();
        this.setLocationRelativeTo(null);

    }
}
