package edu.gatech.cs2340.ITripCS2340.Model;

import org.json.simple.JSONObject;

/**
 * This class represent the even stored in the list of activity
 *
 * @author Dreamsoul
 * @version 1.0
 */
public class Event {

    private final String startTime;
    private final String endTime;
    private final String type;
    private final JSONObject business;

    /**
     * The Constructor
     *
     * @param startTime the start time from the user for this event
     * @param endTime the end time from the user for this event
     * @param type the type from the user for this event
     * @param business the JSONObject for the business associated with this
     * event
     */
    public Event(String startTime, String endTime, String type,
            JSONObject business) {
        this.startTime = startTime;
        this.endTime = endTime;
        this.type = type;
        this.business = business;
    }

    /**
     * The getter for the start time
     *
     * @return the start time from the user for this event
     */
    public String getStartTime() {
        return this.startTime;
    }

    /**
     * The getter for the end time
     *
     * @return the end time from the user for this event
     */
    public String getEndTime() {
        return this.endTime;
    }

    /**
     * The getter for the start time
     *
     * @return the start time from the user for this event
     */
    public int getStartTimeInt() {
        return Integer.parseInt(this.startTime);
    }

    /**
     * The getter for the end time
     *
     * @return the end time from the user for this event
     */
    public int getEndTimeInt() {
        return Integer.parseInt(this.endTime);
    }

    /**
     * The getter for type of this event like restaraunt, hotel
     *
     * @return type the type of the event
     */
    public String getType() {
        return this.type;
    }

    /**
     * The getter for JSONObject for the business
     *
     * @return JSONObject for the business
     */
    public JSONObject getBusiness() {
        return this.business;
    }

    /**
     * Hash function used for comparison. In the future it will be modified to
     * be more advanced Future modification should points to SHA512 or similar.
     * Also with java.security.SecureRandom
     *
     * @return the hashcode
     */
    public int hashCode() {
        int result = 31;
        if (startTime != null) {
            result = 31 * result + startTime.hashCode();
        }
        if (endTime != null) {
            result = 31 * result + endTime.hashCode();
        }
        if (type != null) {
            result = 31 * result + type.hashCode();
        }
        result = 31 * result;
        return result;
    }

    /**
     * The equals method inherits from Object class
     *
     * @param obj The one to be compared with
     * @return if two passwords are the same
     */
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if ((obj == null) || (obj.getClass() != this.getClass())) {
            return false;
        }
        // object must be Password at this point
        Event test = (Event) obj;
        return startTime != null && startTime.equals(test.getStartTime())
                && endTime != null && endTime.equals(test.getEndTime())
                && type != null && type.equals(test.getType())
                && business != null && business.equals(test.getBusiness());
    }
}
