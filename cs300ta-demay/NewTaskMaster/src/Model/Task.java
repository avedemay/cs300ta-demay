package Model;

import java.io.Serializable;
import java.util.Date;
import java.util.Random;

/**
 * Avery DeMay
 * CS 300
 * Task Master
 *
 * Task object class
 */
public class Task implements Serializable {

    private static int count =1;

    private int id;
    private int priority;
    private String taskName;
    private String details;
    private String userID;


    public Task( int priority, String taskName, String details, String userID) {
        this.priority = priority;
        this.taskName = taskName;
        this.details = details;
        this.userID = userID;

        Random rn = new Random();
        int randomNum = rn.nextInt((9999 - 0) + 1) + 1;

        this.id = randomNum;
    }

    public Task(int id, int priority, String taskName, String details, String userID) {
        this.id = id;
        this.priority = priority;
        this.taskName = taskName;
        this.details = details;
        this.userID = userID;
    }

    //////////////////////////////////////////////////
    /////////////Getters and Setters//////////////////
    //////////////////////////////////////////////////

    public static int getCount() {
        return count;
    }

    public static void setCount(int count) {
        Task.count = count;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }
}
