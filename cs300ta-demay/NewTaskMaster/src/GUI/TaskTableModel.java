package GUI;

import Model.Task;

import javax.swing.table.AbstractTableModel;
import java.util.List;

/**
 * Avery DeMay
 * CS 300
 * TaskMaster program
 *
 *
 * This class is the TaskTableModel class that we will use to define the behavior of the
 * table model used by the table panel. This class extends the Abstract Table Model class
 */
public class TaskTableModel  extends AbstractTableModel {

    //list of tasks in each table
    private List<Task> db;

    //table section labels
    private String[] colNames = {"Name", "Details"};

    //get the name of the column
    @Override
    public String getColumnName(int column) {
        return colNames[column];
    }

    //make the cells editable
    @Override
    public boolean isCellEditable(int row, int col) { return true; }

    //define what the cells can be edited to
    @Override
    public void setValueAt(Object value, int row, int col) {

        if(db == null) return;

        Task task = db.get(row);

        switch (col) {
            case 1:
                task.setTaskName((String)value);
                break;
            case 2:
                task.setDetails((String)value);
                break;
            default:
                break;
        }
    }

    public void setData(List<Task> db){
        this.db = db;
    }

    @Override
    public int getRowCount() {
        return db.size();
    }

    @Override
    public int getColumnCount() {
        return 2;
    }

    //get the value of the data at the row and column given
    @Override
    public Object getValueAt(int row, int col) {
        Task task= db.get(row);

        switch (col)
        {
            case 0:
                return task.getTaskName();
            case 1:
                return task.getDetails();
        }
        return null;
    }

    //get the name from the row selected
    public String getNameAt(int row, int col) {
        Task task= db.get(row);

        switch (col){
            case 0:
                return task.getTaskName();
        }
        return null;
    }

    //get the details from the row selected
    public String getDetailsAt(int row, int col) {
        Task task= db.get(row);

        switch (col){
            case 1:
                return task.getDetails();
        }
        return null;
    }

    //get the task id from the row selected
    public int getTaskId(int row) {
        Task task= db.get(row);
        return task.getId();
    }
}
