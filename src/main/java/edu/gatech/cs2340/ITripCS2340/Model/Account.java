package edu.gatech.cs2340.ITripCS2340.Model;

import edu.gatech.cs2340.ITripCS2340.Controller.JSPStringConstants;
import edu.gatech.cs2340.ITripCS2340.Controller.SharedServletMethods;
import java.io.Serializable;
import org.json.simple.JSONObject;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * This class wraps the Username and Password so it is easier to be passed
 * around
 *
 * @author YuYing Zhang
 * @version 1.0
 */
@DatabaseTable(tableName = "accounts")
public class Account implements Serializable {

    public static final String USERNAME_FIELD_NAME = "username";
    public static final String PASSWORD_FIELD_NAME = "password";
    private static final int FNV_32_INIT = 0x811c9dc5;
    private static final int FNV_32_PRIME = 0x01000193;
    @DatabaseField(id = true)
    private String userName;
    @DatabaseField
    private int password;
    @DatabaseField
    private String itineraryJSON;
    private List itinerary;
    @DatabaseField
    private String homeBase;

    /**
     * Constructor of Account class
     *
     * ORMLite needs a no-arg constructor
     */
    public Account() {
        this.userName = "";
        this.password = 0;
        itinerary = new List();
        this.homeBase = "Atlanta, GA, United States";
    }

    /**
     * Constructor of Account class
     *
     * @param userName the username of this acccount
     * @param password the password of this acccount
     */
    public Account(String userName, String password) {
        this.userName = userName;
        this.password = fnvHash(password);
        itinerary = new List();
        this.homeBase = "Atlanta, GA, United States";
    }

    /**
     * Constructor of Account class
     *
     * @param userName the username of this acccount
     * @param hashedPassword the password of this acccount
     */
    public Account(String userName, int hashedPassword) {
        this.userName = userName;
        this.password = hashedPassword;
        itinerary = new List();
        this.homeBase = "Atlanta, GA, United States";
    }

    /**
     * Constructor of Account class
     *
     * @param userName the username of this acccount
     * @param hashedPassword the password of this acccount
     * @param itinerary the itinerary
     */
    public Account(String userName, int hashedPassword, List itinerary) {
        this.userName = userName;
        this.password = hashedPassword;
        this.itinerary = itinerary;
        this.homeBase = "Atlanta, GA, United States";
    }

    /**
     * Do hashCode reversely to get String
     *
     * @param unHashedString the hashcode of one object
     * @return the String of the object
     */
    public static int fnvHash(String unHashedString) {
        int rv = FNV_32_INIT;
        final int len = unHashedString.length();
        for (int i = 0; i < len; i++) {
            rv ^= unHashedString.charAt(i);
            rv *= FNV_32_PRIME;
        }
        return rv;
    }

    /**
     * Returns the username for this Account
     *
     * @return The username
     */
    public String getUserName() {
        return userName;
    }

    /**
     * Changes the username
     *
     * @param username The new username
     */
    public void setUserName(String username) {
        this.userName = username;
    }

    /**
     * Getter for home base
     *
     * @return home base
     */
    public String getHomeBase() {
        return homeBase;
    }

    /**
     * setter for home base
     *
     * @param centralLocation new home base
     */
    public void setHomeBase(String centralLocation) {
        this.homeBase = centralLocation;
    }

    /**
     * Get the HashCode of the username of this account
     *
     * @return the hashcode of username
     */
    public int getHashedUserName() {
        return fnvHash(userName);
    }

    /**
     * Get the HashCode of the password of this account
     *
     * @return the hashcode of password
     */
    public int getHashedPassword() {
        return password;
    }

    /**
     * Changes the password
     *
     * @param pass The new Password
     */
    public void setPassword(String pass) {
        this.password = fnvHash(pass);
    }

    /**
     * Returns true if the password is correct
     *
     * @param pass The password to check
     * @return true if the password is correct
     */
    public boolean checkPassword(String pass) {
        return password == fnvHash(pass);
    }

    /**
     * Get the itinerary
     *
     * @return the itinerary
     */
    public List getItinerary() {
        return itinerary;
    }

    /**
     * Set the itinerary
     *
     * @param itinerary the new itinerary
     */
    public void setItinerary(List itinerary) {
        this.itinerary = itinerary;
    }

    /**
     * Get the itineraryJSON
     *
     * @return the itinerary
     */
    public String getItineraryJSON() {
        return itineraryJSON;
    }

    /**
     * Set the itineraryJSON
     *
     * @param itinerary the new itinerary
     */
    public void setItineraryJSON(String itinerary) {
        this.itineraryJSON = itinerary;
    }

    /**
     * Returns the current Account as a JSONObject
     *
     * @return The JSONObject
     */
    public String toJSON() {
        JSONObject obj = new JSONObject();
        obj.put(JSPStringConstants.USERNAME_PARAM, userName);
        obj.put(JSPStringConstants.PASSWORD_PARAM, password);
        obj.put(JSPStringConstants.ITINERARY_PARAM, itinerary.toJSON());
        return obj.toJSONString();
    }

    /**
     * Creates an Account from a JSONobject
     *
     * @param json The JSONObject
     * @return The Account
     */
    public static Account getAccountFromJSON(String json) {
        JSONObject obj = SharedServletMethods.parseJSONObject(json);
        return new Account(
                (String) obj.get(JSPStringConstants.USERNAME_PARAM),
                (int) obj.get(JSPStringConstants.PASSWORD_PARAM),
                List.getListFromJSON(
                        (String) obj.get(JSPStringConstants.ITINERARY_PARAM)));
    }
}
