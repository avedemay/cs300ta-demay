package Controller;

import GUI.FormEvent;
import GUI.UserFormEvent;
import Model.Database;
import Model.PriorityModel;
import Model.Task;
import Model.Users;
import javafx.scene.layout.Priority;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

/**
 * Avery DeMay
 * CS 300
 * Task Master
 * Controller class
 * This class acts as a go between for the database and the GUI
 * and contains wrapper functions for the Database functions
 */
public class Controller {
    Database db = new Database();

    //call database function connect
    public void connect() throws Exception { db.connect(); }

    //call database function disconnect
    public void disconnect() {
        db.disconnect();
    }

    ///////////////////////////////////////////////////////////////
    ////////////User Database Wrapper Functions////////////////////
    ///////////////////////////////////////////////////////////////

    //call database function get users
    public List<Users> getUsers(){
        return db.getUsers();
    }

    //call database function remove users
    public void removeUsers(int index){
        db.removeUsers(index);
    }

    //call database function load user
    public void loadUser() throws SQLException { db.loadUser(); }

    //call database function save user
    public void saveUser() throws SQLException { db.saveUser(); }

    //call database function add user
    public void addUser(UserFormEvent ev){
        String userName = ev.getUserName();
        String password = ev.getPassword();

        Users user = new Users(userName, password);
        db.addUsers(user);
    }

    ///////////////////////////////////////////////////////////////
    ////////////Task Database Wrapper Functions////////////////////
    ///////////////////////////////////////////////////////////////


    //call database function get tasks
    public List<Task> getTasks(){
        return db.getTasks();
    }

    //call database function get to do tasks
    public List<Task> getTaskstoDo(){
        return db.getTaskstoDo();
    }

    //call database function get tasks in progress
    public List<Task> getTasksinProgress(){
        return db.getTasksinProgress();
    }

    //call database function get done tasks
    public List<Task> getTasksdone(){
        return db.getTasksdone();
    }

    //call database function remove task
    public void removeTask(int index){
        db.removeTask(index);
    }

    //call database function remove to do task
    public void removeTasktDo(int index){
        db.removeTasktoDo(index);
    }

    //call database function remove task in progress
    public void removeTaskinProgress(int index){
        db.removeTaskinProgress(index);
    }

    //call database function remove done task
    public void removeTaskdone(int index){
        db.removeTaskdone(index);
    }

    //call database function load task
    public void loadTask(String userKey) throws SQLException{ db.loadTask(userKey); }

    //call database function save task
    public void saveTask() throws SQLException { db.saveTask(); }

    //call database function add task
    public void addTask(FormEvent ev){
        String name = ev.getName();
        String details = ev.getDetails();
        int priorityId = ev.getPriorityID();
        String userID = ev.getUserID();

        PriorityModel priority =  null;
        switch(priorityId){
            case 0:
                priority = PriorityModel.toDo;
                break;
            case 1:
                priority = PriorityModel.inProgress;
                break;
            case 2:
                priority = PriorityModel.done;
                break;

        }

        Task task = new Task(priorityId,name,details,userID);
        db.addTask(task);
    }

    //call database function add to do task
    public void addTasktoDo(FormEvent ev){
        String name = ev.getName();
        String details = ev.getDetails();
        int priorityId = ev.getPriorityID();
        String userID = ev.getUserID();

        PriorityModel priority =  null;
        switch(priorityId){
            case 0:
                priority = PriorityModel.toDo;
                break;
            case 1:
                priority = PriorityModel.inProgress;
                break;
            case 2:
                priority = PriorityModel.done;
                break;
        }

        Task task = new Task(priorityId,name,details,userID);
        db.addTasktoDo(task);
    }

    //call database function add task in progress
    public void addTaskinProgress(FormEvent ev){
        String name = ev.getName();
        String details = ev.getDetails();
        int priorityId = ev.getPriorityID();
        String userID = ev.getUserID();

        PriorityModel priority =  null;
        switch(priorityId){
            case 0:
                priority = PriorityModel.toDo;
                break;
            case 1:
                priority = PriorityModel.inProgress;
                break;
            case 2:
                priority = PriorityModel.done;
                break;
        }

        Task task = new Task(priorityId,name,details,userID);
        db.addTaskinProgress(task);
    }

    //call database function add done task
    public void addTaskdone(FormEvent ev){
        String name = ev.getName();
        String details = ev.getDetails();
        int priorityId = ev.getPriorityID();
        String userID = ev.getUserID();

        PriorityModel priority =  null;
        switch(priorityId){
            case 0:
                priority = PriorityModel.toDo;
                break;
            case 1:
                priority = PriorityModel.inProgress;
                break;
            case 2:
                priority = PriorityModel.done;
                break;
        }

        Task task = new Task(priorityId,name,details,userID);
        db.addTaskdone(task);
    }

    //call database function get topic id
    public int getTopicID(int index){
        return db.getTopicID(index);
    }

    //call database function get task list length
    public int getTaskListLength(int index){
        return db.getTaskListLength();
    }

    //call database function delete task
    public void deleteTask(int id) throws SQLException { db.deleteTask(id); }
}
