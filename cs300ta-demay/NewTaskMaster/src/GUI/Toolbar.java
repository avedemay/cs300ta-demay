package GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Avery DeMay
 * CS 300
 * Task Master
 * Toolbar class
 * This class controls the tool  bar in the main frame
 */

public class Toolbar extends JPanel implements ActionListener {

    private JButton saveButton;
    private JButton refreshButton;
    private ToolbarListener textListener;

    public Toolbar()
    {
        setBorder(BorderFactory.createEtchedBorder());

        //must add action listeners for the buttons
        saveButton = new JButton("Save");
        refreshButton = new JButton("Refresh");

        saveButton.addActionListener(this);
        refreshButton.addActionListener(this);

        setLayout(new FlowLayout(FlowLayout.LEFT));

        add(saveButton);
        add(refreshButton);
    }

    public void setToolbarListener(ToolbarListener listener)
    {
        this.textListener = listener;
    }

    ///want to know what button was pressed
    public void actionPerformed(ActionEvent e) {

        JButton clicked = (JButton)e.getSource();

        if(clicked == saveButton) {
            if(textListener != null) {
                textListener.saveEventOccurred();
            }
        }
        else if (clicked == refreshButton) {   //textPanel.appendText("Goodbye\n");
            if(textListener != null) {
                textListener.refreshEventOccurred();
            }
        }
    }
}