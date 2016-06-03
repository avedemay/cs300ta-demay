package GUI;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ContainerAdapter;
import java.sql.SQLException;
import Controller.*;
import Model.Database;
import Model.Users;
/**
 * Avery DeMay
 * CS 300
 * Task Master
 * Sign Up Dialog
 *
 * This dialog will appear when users select the sign up button
 * from the landing page. This will prompt users to enter a new
 * user name and log in, then store this user name and password
 * in the user table in the database
 */

public class SignUpDialog extends JDialog{
    private JButton okButton;
    private JButton cancelButton;
    private JTextField userField;
    private JPasswordField passField;
    private SignUpListener signUpListener;
    private Database db;

    //Constructor
    public SignUpDialog(JFrame parent){
        super(parent, "Preferences", false);

        okButton = new JButton("OK");
        cancelButton = new JButton("Cancel");
        Database db = new Database();
        userField = new JTextField(10);
        passField = new JPasswordField(10);

        passField.setEchoChar('*');

        layoutControls();

        //OK button Action Listener
        okButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String user = userField.getText();
                char[] password1 = passField.getPassword();
                String password = new String(password1);

                if(user.length() < 1 || password.length() <1) {
                    JOptionPane.showMessageDialog(SignUpDialog.this, "Must enter valid user name and password", "Sign Up Error", JOptionPane.ERROR_MESSAGE);
                }
                else
                {
                    UserFormEvent ev = new UserFormEvent(this, user, password);
                    JOptionPane.showMessageDialog(SignUpDialog.this, "Account for " + user + " created.", "Success!", JOptionPane.INFORMATION_MESSAGE);

                    if(signUpListener!= null){
                        signUpListener.formEventOccurred(ev);
                    }

                    try {
                        db.connect();
                    } catch (Exception err) {
                        err.printStackTrace();
                    }

                    db.addUsers(new Users(user,password));
                    try {
                        db.saveUser();
                    } catch (SQLException err) {
                        err.printStackTrace();
                    }

                    setVisible(false);
                }
            }
        });


        //Cancel Button action listener
        cancelButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                setVisible(false);
            }
        });

        setSize(340,250);
        setLocationRelativeTo(parent);
    }

    //layout set up for the sign up dialog components
    private void layoutControls(){
        JPanel controlsPanel = new JPanel();
        JPanel buttonsPanel = new JPanel();

        int space = 10;

        Border spaceBorder = BorderFactory.createEmptyBorder(space,space,space,space);
        Border titleBorder = BorderFactory.createTitledBorder("Create New Account");

        controlsPanel.setBorder(BorderFactory.createCompoundBorder(spaceBorder,titleBorder));

        controlsPanel.setLayout(new GridBagLayout());

        GridBagConstraints gc = new GridBagConstraints();
        gc.gridy = 0;

        Insets rightPadding = new Insets(0,0,0,15);
        Insets noPadding = new Insets(0,0,0,0);

        ////////////////////////////First Row/////////////////////////////////////
        gc.weightx = 1;
        gc.weighty = 1;

        gc.fill = GridBagConstraints.NONE;

        gc.gridx = 0;

        gc.anchor = GridBagConstraints.EAST;
        gc.insets = rightPadding;
        controlsPanel.add(new JLabel("Create User Name: "), gc);

        gc.gridx++;

        gc.anchor = GridBagConstraints.WEST;
        gc.insets = noPadding;
        controlsPanel.add(userField, gc);

        /////////////////////Next Row//////////////////////
        gc.gridy++;

        gc.weightx = 1;
        gc.weighty = 1;

        gc.fill = GridBagConstraints.NONE;

        gc.gridx = 0;

        gc.anchor = GridBagConstraints.EAST;
        gc.insets = rightPadding;
        controlsPanel.add(new JLabel("Create Password: "), gc);

        gc.gridx++;

        gc.anchor = GridBagConstraints.WEST;
        gc.insets = noPadding;
        controlsPanel.add(passField, gc);

        /////////////////////Buttons Panel//////////////////////
        buttonsPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        buttonsPanel.add(okButton, gc);
        buttonsPanel.add(cancelButton,gc);

        Dimension btnSize = cancelButton.getPreferredSize();

        okButton.setPreferredSize(btnSize);
        ////////////////////////////////////
        //add subpanels to dialog

        setLayout(new BorderLayout());
        add(controlsPanel,BorderLayout.CENTER);
        add(buttonsPanel,BorderLayout.SOUTH);
    }
}
