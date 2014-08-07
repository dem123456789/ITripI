package edu.gatech.cs2340.ITripCS2340.Model;

import edu.gatech.cs2340.ITripCS2340.Controller.JSPStringConstants;
import edu.gatech.cs2340.ITripCS2340.Controller.SharedServletMethods;
import java.util.ArrayList;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

/**
 * This class represent the list of activity
 *
 * @author Dreamsoul
 * @version 1.0
 */
public class List {

    private ArrayList<Event> list;

    /**
     * The Constructor of the list
     */
    public List() {
        list = new ArrayList<>();
    }

    /**
     * The Constructor with the first event added
     *
     * @param startTime the start time from the user for this event
     * @param endTime the end time from the user for this event
     * @param type the type from the user for this event
     * @param business the JSONObject for the business associated with this
     * event
     */
    public List(String startTime, String endTime, String type,
            JSONObject business) {
        Event temp = new Event(startTime, endTime, type, business);
        list.add(temp);
    }

    /**
     * Add one more event to the list
     *
     * @param startTime the start time from the user for this event
     * @param endTime the end time from the user for this event
     * @param type the type from the user for this event
     * @param business the JSONObject for the business associated with this
     * event
     */
    public void addEvent(String startTime, String endTime, String type,
            JSONObject business) {
        Event temp = new Event(startTime, endTime, type, business);
        list.add(temp);
        list.trimToSize();
    }

    /**
     * Add one more event to the list
     *
     * @param event the event
     */
    public void addEvent(Event event) {
        list.add(event);
        list.trimToSize();
    }

    /**
     * Add one more event to the list at an index
     *
     * @param index the index
     * @param event the event
     */
    public void addEventByIndex(int index, Event event) {
        list.add(index, event);
        list.trimToSize();
    }

    /**
     * remove one event by index
     *
     * @param index the index of the event
     */
    public void removeEventByIndex(int index) {
        if (list.size() > index) {
            list.remove(index);
        }
    }

    /**
     * remove one event by object
     *
     * @param startTime the start time from the user for this event
     * @param endTime the end time from the user for this event
     * @param type the type from the user for this event
     * @param business the JSONObject for the business associated with this
     * event
     */
    public void removeEventByObject(String startTime, String endTime,
            String type, JSONObject business) {
        Event temp = new Event(startTime, endTime, type, business);
        list.remove(temp);
    }

    /**
     * returns the event at the given index
     *
     * @param index
     * @return null if out of bounds
     */
    public Event getEventByIndex(int index) {
        if (list.size() <= index) {
            return null;
        }
        return list.get(index);
    }

    /**
     * get the size of the list
     *
     * @return the size of the list
     */
    public int size() {
        return list.size();
    }

    /**
     * Check if the list is empty
     *
     * @return if the list is empty
     */
    public boolean isEmpty() {
        return list.isEmpty();
    }

    public void sort() {
        if (list.isEmpty()) {
            return;
        }
        ArrayList<Event> temp = new ArrayList<>();
        int j = 0;
        for (Event e : list) {
            j = 0;
            while (j < temp.size()
                    && temp.get(j).getStartTimeInt()
                    < e.getStartTimeInt()) {
                j++;
            }
            temp.add(j, e);
        }
        list = temp;
    }

    /**
     * Returns the current list as a JSONArray
     *
     * @return The JSONArray
     */
    public String toJSON() {
        JSONArray arr = new JSONArray();
        for (Event e : list) {
            JSONObject event = new JSONObject();
            event.put(JSPStringConstants.STARTTIME, e.getStartTime());
            event.put(JSPStringConstants.ENDTIME, e.getEndTime());
            event.put(JSPStringConstants.INTEREST_PLACE, e.getType());
            event.put(JSPStringConstants.BUSINESS_PARAM,
                    e.getBusiness().toJSONString());
            arr.add(event);
        }
        return arr.toJSONString();
    }

    /**
     * Creates a list from a JSONArray of Events
     *
     * @param jsonArray The JSONArray (must be of Events)
     * @return The list
     */
    public static List getListFromJSON(String jsonArray) {
        List ls = new List();
        JSONArray arr = SharedServletMethods.parseJSONArray(jsonArray);
        for (Object obj : arr) {
            JSONObject event = (JSONObject) obj;
            ls.addEvent(new Event(
                    (String) event.get(JSPStringConstants.STARTTIME),
                    (String) event.get(JSPStringConstants.ENDTIME),
                    (String) event.get(JSPStringConstants.INTEREST_PLACE),
                    SharedServletMethods.parseJSONObject(
                            (String) event.get(
                                    JSPStringConstants.BUSINESS_PARAM))));
        }
        return ls;
    }
}
