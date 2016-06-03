package GUI;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

import Controller.Controller;
import Model.Database;

/**
 * Avery DeMay
 * CS 300
 * Task Master
 *
 * Landing Page for the Task Master program.
 * This is the first page the user sees when they
 * open the program. From this pages users can log in
 * or sign up.
 */


public class LandingFrame extends JFrame{
    private JButton logInButton;
    private JButton signUpButton;
    private JTextField userField;
    private JPasswordField passwordField;
    private SignUpDialog signUpDialog;
    public String user;
    private Database db;

    //constructor
    public LandingFrame(){
        super("TaskMaster");
        setSize(600,500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);

        logInButton = new JButton("Log In");
        signUpButton = new JButton("Sign Up");
        signUpDialog = new SignUpDialog(this);
        userField = new JTextField(10);
        passwordField = new JPasswordField(10);
        db = new Database();

        passwordField.setEchoChar('*');

        layoutComponents();

        //Login button action listener
        logInButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {;
                boolean loginAccept = false;
                user = userField.getText();
                char[] password1 = passwordField.getPassword();

                String password = new String(password1);

                System.out.print(user);
                System.out.print(password);
                //if there is a matching user name and password, open main frame. Else open an error dialog.
                try {
                    db.connect();
                } catch (Exception err) {
                    err.printStackTrace();
                }

                try {
                    loginAccept = db.checkLogin(user, password);
                } catch (SQLException e1) {
                    e1.printStackTrace();
                }

                if(user.length() < 1 || password.length() <1) {
                    JOptionPane.showMessageDialog(LandingFrame.this, "Must enter a user name and password", "Log In Error", JOptionPane.ERROR_MESSAGE);
                }
                else if(loginAccept == false){
                    JOptionPane.showMessageDialog(LandingFrame.this, "Login Does Not exist", "Log In Error", JOptionPane.ERROR_MESSAGE);
                }
                else
                {   //run mainframe
                    setVisible(false);
                    SwingUtilities.invokeLater(new Runnable() {
                        public void run() {
                            new MainFrame(user);
                        }
                    });
                }
            }
        });

        //Sign up button action listener will open the sign up dialog
        signUpButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                signUpDialog.setVisible(true);
            }
        });


        setMinimumSize(new Dimension(300,200));
        setSize(500,400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    //Layout components for the landing frame for the placement of components
    private void layoutComponents(){
        JPanel logInPanel = new JPanel();
        JPanel signUpPanel = new JPanel();

        int space = 10;

        Border spaceBorder = BorderFactory.createEmptyBorder(space,space,space,space);
        Border titleBorder = BorderFactory.createTitledBorder("Task Master Log In");

        logInPanel.setBorder(BorderFactory.createCompoundBorder(spaceBorder,titleBorder));

        Border titleBorder2 = BorderFactory.createTitledBorder("Create New Account");

        signUpPanel.setBorder(BorderFactory.createCompoundBorder(spaceBorder,titleBorder2));

        logInPanel.setLayout(new GridBagLayout());

        GridBagConstraints gc = new GridBagConstraints();
        gc.gridy = 0;

        Insets rightPadding = new Insets(0,0,0,15);
        Insets noPadding = new Insets(0,0,0,0);


        ///////////////////////////////////////////////////////////////
        gc.weightx = 1;
        gc.weighty = 1;

        gc.fill = GridBagConstraints.NONE;

        gc.gridx = 0;

        gc.anchor = GridBagConstraints.EAST;
        logInPanel.add(new JLabel("TaskMaster: "), gc);

        gc.gridx++;

        gc.anchor = GridBagConstraints.WEST;
        gc.insets = noPadding;
        logInPanel.add(new JLabel("Master productivity."), gc);

        ////////////////////////////Next Row/////////////////////////////////////
        gc.gridy++;
        gc.weightx = 1;
        gc.weighty = .11;

        gc.fill = GridBagConstraints.NONE;

        gc.gridx = 0;

        gc.anchor = GridBagConstraints.EAST;
        gc.insets = rightPadding;
        logInPanel.add(new JLabel("User: "), gc);

        gc.gridx++;

        gc.anchor = GridBagConstraints.WEST;
        gc.insets = noPadding;
        logInPanel.add(userField, gc);

        /////////////////////Next Row//////////////////////
        gc.gridy++;

        gc.weightx = 1;
        gc.weighty = 1;

        gc.fill = GridBagConstraints.NONE;

        gc.gridx = 0;

        gc.anchor = GridBagConstraints.EAST;
        gc.insets = rightPadding;
        logInPanel.add(new JLabel("Password: "), gc);

        gc.gridx++;

        gc.anchor = GridBagConstraints.WEST;
        gc.insets = noPadding;
        logInPanel.add(passwordField, gc);

        /////////////////////Next Row//////////////////////
        gc.gridy++;

        gc.weightx = 1;
        gc.weighty = 1;

        gc.fill = GridBagConstraints.NONE;

        gc.gridx = 1;

        gc.anchor = GridBagConstraints.WEST;
        logInPanel.add(logInButton, gc);

        /////////////////////Buttons Panel//////////////////////

        signUpPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        signUpPanel.add(signUpButton,gc);

        Dimension btnSize = signUpButton.getPreferredSize();

        logInButton.setPreferredSize(btnSize);
        ////////////////////////////////////

        //add subpanels to dialog

        setLayout(new BorderLayout());
        add(logInPanel,BorderLayout.CENTER);
        add(signUpPanel,BorderLayout.SOUTH);
    }
}
