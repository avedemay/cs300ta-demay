package GUI;

import Controller.Controller;
import Model.Task;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * Avery DeMay
 * CS 300
 * Task Master
 *
 * Table Panel
 *
 * This is the table panel class that will be used for displaying the
 * task name and details. From this table panel users can delete or
 * change the task category.
 */
public class TablePanel extends JPanel {
    private TaskTableModel tableModel;
    private JTable table;
    private JPopupMenu popup;
    private TaskTableListener taskTableListener;
    private String userKey;

    //constructor
    public TablePanel(JFrame parent, String key, Controller controller)
    {
        tableModel = new TaskTableModel();
        table = new JTable(tableModel);
        userKey = key;
        popup = new JPopupMenu();

        JMenuItem removeItem = new JMenuItem("Delete Task");
        popup.add(removeItem);

        JMenuItem editItem = new JMenuItem("Change Category");
        popup.add(editItem);

        //Mouse action listener
        table.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                int row = table.rowAtPoint(e.getPoint());

                table.getSelectionModel().setSelectionInterval(row,row);

                if(e.getButton() == MouseEvent.BUTTON3)
                {
                    popup.show(table,e.getX(),e.getY());
                }
            }
        });

        //remove Item action listener
        removeItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int row = table.getSelectedRow();
                int taskID  = tableModel.getTaskId(row);

                if(taskTableListener != null){
                    taskTableListener.rowDeleted(row, taskID);
                    tableModel.fireTableRowsDeleted(row,row);
                }
            }
        });
        //edit item action listener
        editItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int row = table.getSelectedRow();

                String name = tableModel.getNameAt(row,0);
                String details = tableModel.getDetailsAt(row,1);

                System.out.println("DETAILS FROM TABLE: " + details);

                ChangeTypeDialog changeTypeDialog = new ChangeTypeDialog(parent, name, details, userKey, controller);
                changeTypeDialog.setVisible(true);

                int taskID  = tableModel.getTaskId(row);

                if(taskTableListener != null){
                    taskTableListener.rowEdited(taskID);
                    taskTableListener.rowDeleted(row, taskID);
                    tableModel.fireTableRowsDeleted(row,row);
                }
            }
        });

        setLayout(new BorderLayout());
        add(new JScrollPane(table), BorderLayout.CENTER);
    }

    public void setData(java.util.List<Task> db){
        tableModel.setData(db);
    }
    public void refresh()
    {
        tableModel.fireTableDataChanged();
    }

    public void  setTaskTableListener(TaskTableListener listener)
    {
        this.taskTableListener = listener;
    }
}




