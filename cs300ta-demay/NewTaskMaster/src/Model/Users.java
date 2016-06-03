package Model;

import java.io.Serializable;
import java.util.Random;

/**
 * Avery DeMay
 * CS 300
 * Task Master
 * User class
 * This class defines the user object
 */

public class Users implements Serializable {

    private static int count =1;

    private int id;
    private String userName;
    private String password;


    public Users(String userName, String password) {
        this.password = password;
        this.userName = userName;
        Random rn = new Random();
        int randomNum = rn.nextInt((9999 - 0) + 1) + 1;

        this.id = randomNum;
    }

    public Users(int id, String userName, String password) {
        this(userName,password);
        this.id = id;
    }


    ///////////////////////////////////////////////////////
    ///////////////User Setters and Getters////////////////
    ///////////////////////////////////////////////////////

    public static int getCount() {
        return count;
    }

    public static void setCount(int count) {
        Users.count = count;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
