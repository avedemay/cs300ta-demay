package GUI;

import java.util.EventObject;

/**
 * Avery DeMay
 * CS 300
 * Task Master
 *
 * User Form Event
 * this class defines the event created that stores the user information
 */
public class UserFormEvent extends EventObject{

    private int id;
    private String userName;
    private String password;


    /**
     * Constructs a prototypical Event.
     *
     * @param source The object on which the Event initially occurred.
     * @throws IllegalArgumentException if source is null.
     */
    public UserFormEvent(Object source) {
        super(source);
    }

    public UserFormEvent(Object source, String userName, String password) {
        super(source);

        this.userName = userName;
        this.password = password;
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
