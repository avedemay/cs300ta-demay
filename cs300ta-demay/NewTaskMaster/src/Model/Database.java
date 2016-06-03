package Model;

import javax.xml.crypto.Data;
import java.sql.*;
import java.util.*;

import java.util.Date;

import static java.nio.file.Files.delete;

/**
 * Avery DeMay
 * CS 300
 * Task Master
 *
 * DataBase Class
 * This class handles mySQL queries and database connections
 * for storing and loading information from the database
 */

public class Database {
    private List<Task> tasks;
    private List<Users> users;
    private Connection con;

    private List<Task> taskstoDo;
    private List<Task> tasksinProgress;
    private List<Task> tasksdone;


    //constructor
    public Database(){
        tasks = new LinkedList<Task>();
        users = new LinkedList<Users>();

        taskstoDo = new LinkedList<Task>();
        tasksinProgress = new LinkedList<Task>();
        tasksdone = new LinkedList<Task>();
    }


    //database function connect to database
    public void connect() throws Exception {
        if(con != null) return;
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new Exception("Driver not found");
        }

        String url = "jdbc:mysql://localhost:3306/newtaskmaster";
        con = DriverManager.getConnection(url,"root","dCHwYsrq2890");
    }

    //database function disconnect from the database
    public void disconnect(){
        if(con != null){
            try {
                con.close();
            } catch (SQLException e) {
                System.out.println("Cant close connection");
            }
        }

    }

    ////////////////////////////////////////////////////////////////
    /////////////////User Database Functions////////////////////////
    ////////////////////////////////////////////////////////////////

    //SAve users to database function
    public void saveUser() throws SQLException {
        String checkSq1 = "select count(*) as count from users where Id=?";
        PreparedStatement checkStmt = con.prepareStatement(checkSq1);

        String insertSq1 = "insert into users (Id, Name, Password) values(?, ?, ?)";
        PreparedStatement insertStatement = con.prepareStatement(insertSq1);

        String updateSq1 = "update users set Name = ?, Password = ? where Id =?";
        PreparedStatement updateStatement = con.prepareStatement(updateSq1);


        for(Users user: users){
            int id = user.getId();
            String userName = user.getUserName();
            String password = user.getPassword();

            //noinspection JpaQueryApiInspection
            checkStmt.setInt(1,id);

            ResultSet checkResult = checkStmt.executeQuery();

            checkResult.next();

            int count = checkResult.getInt(1);

            if(count == 0){
                System.out.println("Inserting User with id: " + id);

                int col = 1;
                insertStatement.setInt(col++,id);
                insertStatement.setString(col++,userName);
                insertStatement.setString(col++,password);

                insertStatement.executeUpdate();

            }
            else{

                int col = 1;
                updateStatement.setString(col++,userName);
                updateStatement.setString(col++,password);
                updateStatement.setInt(col++,id);

                updateStatement.executeUpdate();

                System.out.println("Updating Person with id: " + id);
            }

        }
        updateStatement.close();
        insertStatement.close();
        checkStmt.close();
    }


    //load users from database function
    public void loadUser() throws SQLException {
        users.clear();

        String sql = "select Id, Name, Password from users order by Id";
        Statement selectStatement = con.createStatement();

        ResultSet results = selectStatement.executeQuery(sql);

        while (results.next()){
            int id = results.getInt("Id");
            String userName = results.getString("Name");
            String password = results.getString("Password");

            Users user = new Users(id, userName, password);
            users.add(user);

            System.out.println(user);
        }

        results.close();
        selectStatement.close();
    }

    //database add user function
    public void addUsers(Users user)
    {
        users.add(user);
    }

    //database get user function
    public List<Users> getUsers(){
        return Collections.unmodifiableList(users);
    }

    //database remove user function
    public void removeUsers(int index){
        users.remove(index);
    }


    ////////////////////////////////////////////////////////////////
    /////////////////Task Database Functions////////////////////////
    ////////////////////////////////////////////////////////////////

    //save tasks to database
    public void saveTask() throws SQLException {
        String checkSq1 = "select count(*) as count from tasks where Id=?";
        PreparedStatement checkStmt = con.prepareStatement(checkSq1);

        String insertSq1 = "insert into tasks (Id,  Priority, TaskName, Details, UserID) values(?, ?, ?, ?, ?)";
        PreparedStatement insertStatement = con.prepareStatement(insertSq1);

        String updateSq1 = "update tasks set Priority= ?, TaskName = ?, Details = ?, UserID= ? where Id =?";
        PreparedStatement updateStatement = con.prepareStatement(updateSq1);

        combineTaskLists();

        for(Task task: tasks){
            int id = task.getId();
            int priority  = task.getPriority();
            String taskName = task.getTaskName();
            String details  = task.getDetails();
            String userID = task.getUserID();

            //noinspection JpaQueryApiInspection
            checkStmt.setInt(1,id);

            ResultSet checkResult = checkStmt.executeQuery();

            checkResult.next();

            int count = checkResult.getInt(1);

            if(count == 0){
                System.out.println("Inserting task with id: " + id);

                int col = 1;
                insertStatement.setInt(col++,id);
                insertStatement.setInt(col++,priority);
                insertStatement.setString(col++,taskName);
                insertStatement.setString(col++,details);
                insertStatement.setString(col++,userID);

                insertStatement.executeUpdate();
            }
            else{
                int col = 1;
                updateStatement.setInt(col++,priority);
                updateStatement.setString(col++,taskName);
                updateStatement.setString(col++,details);
                updateStatement.setString(col++,userID);
                updateStatement.setInt(col++,id);

                updateStatement.executeUpdate();

                System.out.println("Updating Task with id: " + id);
            }
        }
        updateStatement.close();
        insertStatement.close();
        checkStmt.close();
    }


    public void loadTask(String userKey) throws SQLException {
        tasks.clear();
        taskstoDo.clear();
        tasksinProgress.clear();
        tasksdone.clear();

        String sql = "select Id, Priority, TaskName, Details, UserID from tasks order by Id";
        Statement selectStatement = con.createStatement();

        ResultSet results = selectStatement.executeQuery(sql);

        while (results.next()){
            String userID = results.getString("UserID");

            if(userID.equals(userKey)) {

                int id = results.getInt("Id");
                int priority = results.getInt("Priority");
                String taskName = results.getString("TaskName");
                String details = results.getString("Details");


                Task task = new Task(id, priority, taskName, details, userID);
                tasks.add(task);

                System.out.println(task);
            }
        }
        addTaskLists();

        tasks.clear();

        results.close();
        selectStatement.close();
    }

    public void addTask(Task task)
    {
        tasks.add(task);
    }

    public void addTasktoDo(Task task)
    {
        taskstoDo.add(task);
    }

    public void addTaskinProgress(Task task)
    {
        tasksinProgress.add(task);
    }

    public void addTaskdone(Task task)
    {
        tasksdone.add(task);
    }

    public List<Task> getTasks(){
        return Collections.unmodifiableList(tasks);
    }

    public List<Task> getTaskstoDo(){
        return Collections.unmodifiableList(taskstoDo);
    }

    public List<Task> getTasksinProgress(){
        return Collections.unmodifiableList(tasksinProgress);
    }

    public List<Task> getTasksdone(){
        return Collections.unmodifiableList(tasksdone);
    }

    public void removeTask(int index){
        tasks.remove(index);
    }

    public void removeTasktoDo(int index){
        taskstoDo.remove(index);
    }

    public void removeTaskinProgress(int index){ tasksinProgress.remove(index);}

    public void removeTaskdone(int index){tasksdone.remove(index); }

    public int getTopicID(int index){
        return tasks.get(index).getPriority();
    }

    public int getTaskListLength(){
        return tasks.size();
    }

    public void addTaskLists(){

        for(Task task: tasks) {
            int priority  = task.getPriority();
            if(priority == 0){ taskstoDo.add(task);}
            if(priority == 1){ tasksinProgress.add(task);}
            if(priority == 2){ tasksdone.add(task);}
        }
    }

    public void combineTaskLists() {
        tasks.clear();

        for(Task task: taskstoDo){
            tasks.add(task);
        }
        for(Task task: tasksinProgress){
            tasks.add(task);
        }
        for(Task task: tasksdone){
            tasks.add(task);
        }
    }

    public void deleteTask(int ID) throws SQLException {
        String query = "delete from tasks where Id = ?";
        PreparedStatement preparedStmt = con.prepareStatement(query);
        preparedStmt.setInt(1, ID);

        // execute the preparedstatement
        preparedStmt.execute();
    }

    public boolean checkLogin(String user, String password) throws SQLException{
        String query = "SELECT EXISTS(SELECT 1 FROM users WHERE Name = ? && Password = ?)";
        PreparedStatement preparedStatement = con.prepareStatement(query);

        boolean bool = false;

        preparedStatement.setString(1,user);
        //noinspection JpaQueryApiInspection
        preparedStatement.setString(2,password);

        ResultSet resultSet = preparedStatement.executeQuery();

        while (resultSet.next()){
            bool = resultSet.getBoolean(1);
        }

        resultSet.close();
        preparedStatement.close();

        return bool;
    }
}