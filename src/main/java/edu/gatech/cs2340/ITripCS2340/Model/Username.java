package edu.gatech.cs2340.ITripCS2340.Model;

/**
 * This class represents the username which will be wrapped
 * in Hash class.It serves as a Key so it has hash method.
 * Created by Dreamsoul on 2014/6/8.
 * @author Dreamsoul
 * @version 1.0
 */
public class Username implements java.io.Serializable {

    /**
     * Auto-generated serial ID for serialization.
     */
    private static final long serialVersionUID = -2569282527624522852L;
    private String username;

    /**
     * The constructor for username.
     * @param username  the username in String
     */
    public Username(String username) {
        this.username = username;
    }

    /**
     * The setter for username.
     * @param username the username in String
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * The getter for username.
     * @return the username stored
     */
    public String getUsername() {
        return username;
    }

    /**
     * Hash function used for comparison.
     * In the future it will be modified to be more appropriate
     * @return the hashcode
     */
    public int hashCode() {
        int result = 17;
        if (username != null) {
            result = 17 * result + username.hashCode();
        } else {
            result = 17 * result;
        }
        return result;
    }

    /**
     * The equals method inherits from Object class
     * @param obj  The one to be compared with
     * @return     if two usernames are the same
     */
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if ((obj == null) || (obj.getClass() != this.getClass())) {
            return false;
        }
        // object must be Username at this point
        Username test = (Username) obj;
        return username != null && username.equals(test.username);
    }
}

