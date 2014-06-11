package edu.gatech.cs2340.ITripCS2340.Model;

/**
 * Created by Dreamsoul on 2014/6/8.
 */
public class Username implements java.io.Serializable {

    /**
     *
     */
    private static final long serialVersionUID = -2569282527624522852L;
    private String username;

    public Username(String username) {
        this.username = username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    public int hashCode() {
        int result = 17;
        result = 17 * result + (username != null ? username.hashCode() : 0);
        return result;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if ((obj == null) || (obj.getClass() != this.getClass())) {
            return false;
        }
        // object must be Test at this point
        Username test = (Username) obj;
        return username != null && username.equals(test.username);
    }
}
