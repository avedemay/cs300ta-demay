import Model.Database;
import Model.Task;
import Model.Users;
import java.util.Date;

import java.sql.SQLException;

/**
 * Avery DeMay
 * CS 300
 * Task Master
 * Test Database class
 * class used for testing connecting to the database,
 * adding users and tasks and closing the connection
 */
public class TestDatabase {
    public static void main(String[] args)  {

        System.out.println("Running database test");

        Database db = new Database();

        try {
            db.connect();
        } catch (Exception e) {
            e.printStackTrace();
        }

        db.addUsers(new Users(13332, "Mulder","TheTruthIsOutThere"));
        db.addUsers(new Users(8832, "Scully","Science"));

        db.addTask(new Task(232,  2, "Clean Car","Take car to the wash","Mike"));
        db.addTask(new Task(932,  3, "Laundry","Wash all clothes","Joe"));
        db.addTask(new Task(42, 1, "Go to the Store","bananas, apples, pears, ice cream","Joe"));


        try {
            db.saveUser();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        try {
            db.loadUser();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        try {
            db.saveTask();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        try {
            db.loadTask("Mike");
        } catch (SQLException e) {
            e.printStackTrace();
        }


        db.disconnect();

    }
}
