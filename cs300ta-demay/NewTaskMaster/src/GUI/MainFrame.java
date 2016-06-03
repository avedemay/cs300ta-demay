package GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.sql.SQLException;
import java.util.prefs.Preferences;
import Controller.*;

/**
 * Avery DeMay
 * CS 300
 * Task Master
 * Main Frame Class
 * this class defines the main gui page that will
 * contain the frame panel and the table panel.
 * This will also be the primary go between
 * for the controller and the GUI
 */

public class MainFrame extends JFrame{
    private JButton btn;
    private FormPanel formPanel;
    public Controller controller;
    private Preferences prefs;
    private JSplitPane splitPane;
    private JTabbedPane tabPane;
    public String usr;
    private TablePanel tablePaneltoDo;
    private TablePanel tablePanelinProgress;
    private TablePanel tablePaneldone;
    private Toolbar toolbar;


    public MainFrame(String user)
    {
        super("TaskMaster");


        //initialize data members
        controller = new Controller();
        this.usr = user;
        setLayout(new BorderLayout());
        toolbar = new Toolbar();
        formPanel = new FormPanel(user);
        tablePaneltoDo = new TablePanel(MainFrame.this, user, controller);
        tablePanelinProgress = new TablePanel(MainFrame.this, user,controller);
        tablePaneldone = new TablePanel(MainFrame.this, user,controller);


        tabPane = new JTabbedPane();
        splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, formPanel,tabPane);

        splitPane.setOneTouchExpandable(true);

        //add tables to the tab pane
        tabPane.addTab("To Do", tablePaneltoDo);
        tabPane.addTab("In Progress", tablePanelinProgress);
        tabPane.addTab("Done", tablePaneldone);

        /////////////////////////////////////////////////////////
        //////////Table Panel Controls and Listeners/////////////
        /////////////////////////////////////////////////////////

        tablePaneltoDo.setData(controller.getTaskstoDo());
        tablePanelinProgress.setData(controller.getTasksinProgress());
        tablePaneldone.setData(controller.getTasksdone());


        tablePaneltoDo.setTaskTableListener(new TaskTableListener() {
            public void rowDeleted(int row,int id){
                controller.removeTasktDo(row);
                saveTask();
                deleteTask(id);
                loadTasks(user);

            }
            public void rowEdited(int id){
                saveTask();
                deleteTask(id);
                loadTasks(user);

                tablePaneltoDo.refresh();
                tablePanelinProgress.refresh();
                tablePaneldone.refresh();
            }

        });

        tablePanelinProgress.setTaskTableListener(new TaskTableListener() {
            public void rowDeleted(int row, int id){
                controller.removeTaskinProgress(row);
                saveTask();
                deleteTask(id);
                loadTasks(user);

            }
            public void rowEdited(int id){
                saveTask();
                deleteTask(id);
                loadTasks(user);

                tablePaneltoDo.refresh();
                tablePanelinProgress.refresh();
                tablePaneldone.refresh();
            }

        });

        tablePaneldone.setTaskTableListener(new TaskTableListener() {
            public void rowDeleted(int row, int id){
                controller.removeTaskdone(row);
                saveTask();
                deleteTask(id);
                loadTasks(user);
                tablePaneltoDo.refresh();
                tablePanelinProgress.refresh();
                tablePaneldone.refresh();

            }
            public void rowEdited(int id){

                saveTask();
                deleteTask(id);
                loadTasks(user);

                tablePaneltoDo.refresh();
                tablePanelinProgress.refresh();
                tablePaneldone.refresh();
            }

        });

        //tool bar listeners
        toolbar.setToolbarListener(new ToolbarListener() {
            public void saveEventOccurred() {
                connect();
                saveTask();
            }

            public void refreshEventOccurred(){
                connect();
                loadTasks(user);

                tablePaneltoDo.refresh();
                tablePanelinProgress.refresh();
                tablePaneldone.refresh();
            }
        });

        //form panel listeners
        formPanel.setFormListener(new FormListener() {

            public void formEventOccurred(FormEvent e) {
                if(e.getPriorityID() == 0) {
                    System.out.println("Adding task to TO DO");
                    controller.addTasktoDo(e);
                    tablePaneltoDo.refresh();
                }
                if(e.getPriorityID() == 1) {
                    System.out.println("Adding task to INPROGRESS");
                    controller.addTaskinProgress(e);
                    tablePanelinProgress.refresh();
                }
                if(e.getPriorityID() == 2) {
                    System.out.println("Adding task to Done");
                    controller.addTaskdone(e);
                    tablePaneldone.refresh();
                }

            }
        });


        add(toolbar, BorderLayout.NORTH);
        add(splitPane, BorderLayout.CENTER);

        refresh();

        setMinimumSize(new Dimension(500,400));
        setSize(900,800);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    /////////////////////////////////////////////////////////
    //////////////Controller Wrapper Functions///////////////
    /////////////////////////////////////////////////////////

    private void connect(){
        try {
            controller.connect();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(MainFrame.this, "Cannot connect to Database", "Database Connection Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void saveTask(){
        try {
            controller.saveTask();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(MainFrame.this, "Cannot save to Database", "Database Connection Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void refresh(){
        try {
            controller.connect();
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            controller.loadTask(usr);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        tablePaneltoDo.refresh();
        tablePanelinProgress.refresh();
        tablePaneldone.refresh();

    }

    private void deleteTask(int id){
        try {
            controller.deleteTask(id);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void loadTasks(String user){
        try {
            System.out.println("Calling load for user: " + user);
            controller.loadTask(user);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(MainFrame.this, "Cannot load from Database", "Database Connection Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private JMenuBar createMenuBar(){
        JMenuBar menuBar = new JMenuBar();

        JMenu fileMenu = new JMenu("File");

        JMenuItem exitItem = new JMenuItem("Exit");

        fileMenu.addSeparator();
        fileMenu.add(exitItem);

        JMenu windowMenu = new JMenu("Window");
        JMenu showMenu = new JMenu("Show");
        JMenuItem prefsItem = new JMenuItem("preferences...");

        JCheckBoxMenuItem showFormItem = new JCheckBoxMenuItem("Person Form:");

        showFormItem.setSelected(true);

        showMenu.add(showFormItem);
        windowMenu.add(showMenu);
        windowMenu.add(prefsItem);

        menuBar.add(fileMenu);
        menuBar.add(windowMenu);

        showFormItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ev) {
                JCheckBoxMenuItem menuItem =  (JCheckBoxMenuItem)ev.getSource();
                formPanel.setVisible(menuItem.isSelected());
            }
        });

        fileMenu.setMnemonic(KeyEvent.VK_F);
        exitItem.setMnemonic(KeyEvent.VK_X);

        exitItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_X, ActionEvent.CTRL_MASK));

        exitItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                //this is the message dialog that asks for a name with a text box
                String text = JOptionPane.showInputDialog(MainFrame.this, "Enter your User Name.", "Enter User Name", JOptionPane.OK_OPTION|JOptionPane.QUESTION_MESSAGE);
                System.out.print(text);

                //this dialog
                int action = JOptionPane.showConfirmDialog(MainFrame.this, "Do you really want to quit?", "Confirm Exit", JOptionPane.OK_CANCEL_OPTION);

                if(action == JOptionPane.OK_OPTION){
                    WindowListener[]  listeners = getWindowListeners();
                    for(WindowListener listener: listeners){
                        listener.windowClosing(new WindowEvent(MainFrame.this,0));
                    }
                }
            }
        });

        return menuBar;
    }
}