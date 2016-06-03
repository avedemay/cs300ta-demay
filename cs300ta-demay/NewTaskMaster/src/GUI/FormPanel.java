package GUI;

import Model.Database;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Avery DeMay
 * CS 300
 * Task Master
 *
 * Form Panel class for the task creation form. This is where the
 * user will enter the task information for creating new tasks.
 * They can enter a task name, task details, and select the list they
 * want to save the task to.
 */

public class FormPanel extends JPanel {
    private JLabel nameLabel;
    private JLabel detailsLabel;
    private JLabel priorityLabel;
    private FormListener formListener;
    private JTextField nameField;
    private JTextField detailsField;
    private JList priorityList;
    private JButton okButton;
    private String userID;
    private Database db;

    //constructor
    public FormPanel(String user) {
        db = new Database();
        userID = user;

        Dimension dim = getPreferredSize();
        dim.width = 250;
        setPreferredSize(dim);
        setMinimumSize(dim);


        nameLabel = new JLabel("Name: ");
        detailsLabel = new JLabel("Details: ");
        priorityLabel = new JLabel("Task Type: ");


        nameField = new JTextField(10);
        detailsField = new JTextField(10);

        priorityList = new JList();

        okButton = new JButton("OK");

        DefaultListModel priorityModel = new DefaultListModel();
        priorityModel.addElement(new Priority(0, "To Do"));
        priorityModel.addElement(new Priority(1, "In Progress"));
        priorityModel.addElement(new Priority(2,"Done"));

        priorityList.setModel(priorityModel);

        priorityList.setPreferredSize(new Dimension(110,70));
        priorityList.setBorder(BorderFactory.createEtchedBorder());
        priorityList.setSelectedIndex(0);

        //OK Button Action Listener
        okButton.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                String name = nameField.getText();
                String details = detailsField.getText();
                Priority priority = (Priority) priorityList.getSelectedValue();

                System.out.println("New Task for " + userID + " created");

                FormEvent ev = new FormEvent(this, name, details, priority.getId(), userID);

                if(formListener != null){
                    formListener.formEventOccurred(ev);
                }

            }
        });


        Border innerBorder = BorderFactory.createTitledBorder("Add Person");
        Border outerBorder =  BorderFactory.createEmptyBorder(5,5,5,5);
        setBorder(BorderFactory.createCompoundBorder(outerBorder,innerBorder));

        layoutComponents();
    }

    //layout for the components on the form panel
    public void layoutComponents() {
        //IMPORTANT
        setLayout(new GridBagLayout());

        GridBagConstraints gc = new GridBagConstraints();

        gc.fill = GridBagConstraints.NONE;

        ////////////////////First Row////////////////////////////
        gc.gridy = 0;

        gc.weightx = 1;
        gc.weighty = 1;

        gc.gridx = 0;
        gc.anchor = GridBagConstraints.LINE_END;
        gc.insets = new Insets(0, 0, 0, 5);
        add(nameLabel, gc);


        gc.gridx = 1;
        gc.anchor = GridBagConstraints.LINE_START;
        gc.insets = new Insets(0, 0, 0, 0);
        add(nameField, gc);


        ////////////////////Second Row////////////////////////////
        gc.gridy++;

        gc.weightx = 1;
        gc.weighty = 1;

        gc.gridx = 0;
        gc.anchor = GridBagConstraints.FIRST_LINE_END;
        gc.insets = new Insets(0, 0, 0, 5);
        add(detailsLabel, gc);

        gc.gridx = 1;
        gc.anchor = GridBagConstraints.FIRST_LINE_START;
        gc.insets = new Insets(0, 0, 0, 0);
        add(detailsField, gc);

        ////////////////////Third Row////////////////////////////
        gc.gridy++;


        gc.weightx = 1;
        gc.weighty = 1;

        gc.gridx = 0;
        gc.anchor = GridBagConstraints.FIRST_LINE_END;
        gc.insets = new Insets(0, 0, 0, 5);
        add(priorityLabel, gc);


        gc.gridx = 1;
        gc.anchor = GridBagConstraints.FIRST_LINE_START;
        gc.insets = new Insets(0, 0, 0, 0);
        add(priorityList, gc);

        ////////////////////Next Row////////////////////////////
        gc.gridy++;

        gc.weightx = 1;
        gc.weighty = 2;

        gc.gridx = 1;
        gc.anchor = GridBagConstraints.FIRST_LINE_START;
        gc.insets = new Insets(0, 10, 0, 0);
        add(okButton, gc);


    }

    public void setFormListener(FormListener listener)
    {
        this.formListener = listener;
    }

}


class Priority{
    private String text;
    private int id;

    public Priority(int id, String text){
        this.id = id;
        this.text = text;
    }

    public String toString(){
        return text;
    }
    public int getId(){
        return id;
    }
}
