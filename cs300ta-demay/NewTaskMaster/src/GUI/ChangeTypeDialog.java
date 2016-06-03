package GUI;

import Model.Task;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.Random;
import Controller.Controller;
import Model.Users;

/**
 * Avery DeMay
 * CS 300
 * Task Master
 * Change Type Dialog class
 * This class controls the dialog that opens after a user selects the
 * change task type
 */
public class ChangeTypeDialog extends JDialog {
    private JButton okButton;
    private JButton cancelButton;
    private JList priorityList;
    private JLabel priorityLabel;
    private String taskName;
    private String taskDetails;
    private String userKey;
    private Controller controller;

    private ChangeTypeListener typelistener;

    //constructor
    public ChangeTypeDialog(JFrame parent, String name, String details, String Key, Controller controller1){
        super(parent, "Change Task Type", false);

        controller = controller1;

        okButton = new JButton("OK");
        cancelButton = new JButton("Cancel");

        priorityLabel = new JLabel("Task Type: ");

        priorityList = new JList();

        okButton = new JButton("OK");

        //set up for the task type list box
        DefaultListModel priorityModel = new DefaultListModel();
        priorityModel.addElement(new Priority(0, "To Do"));
        priorityModel.addElement(new Priority(1, "In Progress"));
        priorityModel.addElement(new Priority(2,"Done"));

        priorityList.setModel(priorityModel);

        priorityList.setPreferredSize(new Dimension(110,70));
        priorityList.setBorder(BorderFactory.createEtchedBorder());
        priorityList.setSelectedIndex(0);

        this.taskName = name;
        this.taskDetails = details;
        this.userKey = Key;


        layoutControls();

        //action listener for the dialog ok button
        okButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Priority priority = (Priority) priorityList.getSelectedValue();

                FormEvent ev = new FormEvent(this, name, details, priority.getId(), userKey);
                if(ev.getPriorityID() == 0) {
                    controller.addTasktoDo(ev);
                }
                if(ev.getPriorityID() == 1) {
                    controller.addTaskinProgress(ev);
                }
                if(ev.getPriorityID() == 2) {
                    controller.addTaskdone(ev);
                }

                connect();
                try {
                    controller.saveTask();
                } catch (SQLException err) {
                    JOptionPane.showMessageDialog(parent, "Cannot save to Database", "Database Connection Error", JOptionPane.ERROR_MESSAGE);
                }

                try {
                    System.out.println("Calling load for user: " + userKey);
                    controller.loadTask(userKey);
                } catch (SQLException err) {
                    JOptionPane.showMessageDialog(parent, "Cannot load from Database", "Database Connection Error", JOptionPane.ERROR_MESSAGE);
                }

                setVisible(false);
            }
        });

        //action listener for dialog cancel button
        cancelButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                setVisible(false);
            }
        });


        setSize(340,250);
        setLocationRelativeTo(parent);

    }






    public void setChangeTypeListener(ChangeTypeListener typeListener){
        this.typelistener= typeListener;
    }



    private void layoutControls(){
        JPanel controlsPanel = new JPanel();
        JPanel buttonsPanel = new JPanel();

        int space = 10;

        Border spaceBorder = BorderFactory.createEmptyBorder(space,space,space,space);
        Border titleBorder = BorderFactory.createTitledBorder("Task Type: ");

        controlsPanel.setBorder(BorderFactory.createCompoundBorder(spaceBorder,titleBorder));
        // buttonsPanel.setBorder(BorderFactory.createLineBorder(Color.darkGray));


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
        controlsPanel.add(priorityLabel, gc);

        gc.gridx++;

        gc.anchor = GridBagConstraints.WEST;
        gc.insets = noPadding;
        controlsPanel.add(priorityList, gc);


        /////////////////////Buttons Panel//////////////////////

        buttonsPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        buttonsPanel.add(okButton, gc);
       //buttonsPanel.add(cancelButton,gc);

        Dimension btnSize = cancelButton.getPreferredSize();

        okButton.setPreferredSize(btnSize);
        ////////////////////////////////////

        //add subpanels to dialog

        setLayout(new BorderLayout());
        add(controlsPanel,BorderLayout.CENTER);
        add(buttonsPanel,BorderLayout.SOUTH);

    }
    private void connect(){
        try {
            controller.connect();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(getParent(), "Cannot connect to Database", "Database Connection Error", JOptionPane.ERROR_MESSAGE);
        }
    }

}