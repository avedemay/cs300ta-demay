package GUI;

import java.util.EventObject;
/**
 * Avery DeMay
 * CS 300
 * Task Master
 *
 * Form Event class
 * Form event for storing Task information in an event object
 */
public class FormEvent extends EventObject {

    private int id;
    private String name;
    private String details;
    private String userID;
    private int priorityID;

    /**
     * Constructs a prototypical Event.
     *
     * @param source The object on which the Event initially occurred.
     * @throws IllegalArgumentException if source is null.
     */
    public FormEvent(Object source) {
        super(source);
    }

    public FormEvent(Object source, String name, String details, int priorityID, String userID) {
        super(source);

        this.name = name;
        this.details = details;
        this.priorityID = priorityID;
        this.userID = userID;
    }

    ///////////////////////////////////////////////////
    /////////Form Event Setters and Getters////////////
    ///////////////////////////////////////////////////

    public int getId() { return id; }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public int getPriorityID() {
        return priorityID;
    }

    public void setPriorityID(int priorityID) {
        this.priorityID = priorityID;
    }
}