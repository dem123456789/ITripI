package edu.gatech.cs2340.ITripCS2340.Model;

/**
 * This class represents the password which will be wrapped
 * in Hash class.It serves as a Value. But as password,
 * it has to have hash method to encrypted itself.
 * Created by Dreamsoul on 2014/6/8.
 * @author Dreamsoul
 * @version 1.0
 */
public class Password implements java.io.Serializable {

    /**
     * Auto-generated serial ID for serialization.
     */
    private static final long serialVersionUID = 5649792483568634900L;
    private String password;

    /**
     *  The constructor for password..
     * @param password  the password in String
     */
    public Password(String password) {
        this.password = password;
    }

    /**
     * The setter for password.
     * @param password  the password in String
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * The getter for password.
     * @return the password stored
     */
    public String getPassword() {
        return password;
    }

    /**
     * Hash function used for comparison.
     * In the future it will be modified to be more advanced
     * Future modification should points to SHA512 or similar.
     * Also with java.security.SecureRandom
     * @return the hashcode
     */
    public int hashCode() {
        int result = 31;
        if (password != null) {
            result = 31 * result + password.hashCode();
        } else {
            result = 31 * result;
        }
        return result;
    }

    /**
     * The equals method inherits from Object class
     * @param obj  The one to be compared with
     * @return     if two passwords are the same
     */
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if ((obj == null) || (obj.getClass() != this.getClass())) {
            return false;
        }
        // object must be Password at this point
        Password test = (Password) obj;
        return password != null && password.equals(test.password);
    }
}

