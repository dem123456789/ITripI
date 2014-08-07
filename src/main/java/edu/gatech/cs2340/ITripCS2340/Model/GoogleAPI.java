package edu.gatech.cs2340.ITripCS2340.Model;

import edu.gatech.cs2340.ITripCS2340.Controller.JSPStringConstants;
import static edu.gatech.cs2340.ITripCS2340.Controller.
        SharedServletMethods.parseJSONArray;
import static edu.gatech.cs2340.ITripCS2340.Controller.
        SharedServletMethods.parseJSONObject;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.scribe.model.OAuthRequest;
import org.scribe.model.Response;
import org.scribe.model.Verb;

/**
 *
 * @author Jonathan
 */
public class GoogleAPI {

    private final int startTime;
    private final int endTime;
    private final int dayOfWeek;
    private final int rating;
    private final int price;

    /**
     * Handles the google place details api
     *
     * @param startTime the user's requested starting hours
     * @param endTime the user's requested closing hours
     * @param rating the user's requested business rating
     * @param price the user's requested price range
     */
    public GoogleAPI(int startTime, int endTime, int rating, int price) {
        this.startTime = startTime;
        this.endTime = endTime;
        this.dayOfWeek = 1;
        this.rating = rating;
        this.price = price;
    }

    /**
     * Handles the google place details api
     *
     * @param startTime the user's requested starting hours
     * @param endTime the user's requested closing hours
     * @param rating the user's requested business rating
     * @param price the user's requested price range
     * @param dayOfWeek the user's requested day
     */
    public GoogleAPI(int startTime, int endTime , int dayOfWeek, int rating,
                     int price) {
        this.startTime = startTime;
        this.endTime = endTime;
        this.dayOfWeek = dayOfWeek;
        this.rating = rating;
        this.price = price;
    }

    /**
     * Filters the provided JSONArray
     *
     * @param jsonPlaces The string form of the JSONArrray of businesses
     * @return The filtered JSONArray
     */
    public JSONArray getFilteredJSON(String jsonPlaces) {
        JSONArray businesses = getDetails(parseJSONArray(jsonPlaces));
        return filterResults(businesses);
    }

    /**
     * filters out the businesses that don't meet the required parameters
     *
     * @param businesses the array of businesses
     * @return the filtered array
     */
    private JSONArray filterResults(JSONArray businesses) {
        JSONArray filter = new JSONArray();
        for (Object obj : businesses) {
            JSONObject business = (JSONObject) obj;
            if (parsePrice(business)) {
                if (parseRating(business)) {
                    if (parseHours(business)) {
                        filter.add(business);
                    }
                }
            }
        }
        return filter;
    }

    /**
     * Retrieves the price_level parameter and checks if it is in range
     *
     * @param business the business in question
     * @return true if the business passes
     */
    private boolean parsePrice(JSONObject business) {
        try {
            if ((long) business.get("price_level") <= price) {
                return true;
            }
        } catch (Exception e) {
            try {
                if ((double) business.get("price_level") <= price) {
                    return true;
                }
            } catch (Exception ex) {
                System.out.println("Error: business param"
                        + "\"price_level\" null or NaN: " + ex);
            }
        }
        return false;
    }

    /**
     * Retrieves the rating parameter and checks if it is in range
     *
     * @param business the business in question
     * @return true if the business passes
     */
    private boolean parseRating(JSONObject business) {
        try {
            if ((double) business.get("rating") >= rating) {
                return true;
            }
        } catch (Exception e) {
            try {
                if ((long) business.get("rating") >= rating) {
                    return true;
                }
            } catch (Exception ex) {
                System.out.println("Error: business param"
                        + "\"rating\" null or NaN: " + ex);
            }
        }
        return false;
    }

    /**
     * Retrieves the opening_hours parameter and checks if it is in range
     *
     * @param business the business in question
     * @return true if the business passes
     */
    private boolean parseHours(JSONObject business) {
        try {
            JSONObject opening = (JSONObject) business.get("opening_hours");
            JSONArray period = (JSONArray) opening.get("periods");
            JSONObject day = (JSONObject) period.get(dayOfWeek);
            JSONObject open = (JSONObject) day.get("open");
            JSONObject closed = (JSONObject) day.get("close");
            int begin = Integer.parseInt((String) open.get("time"));
            int end = Integer.parseInt((String) closed.get("time"));
            if ((startTime <= begin && endTime >= begin)
                    || (startTime <= end && endTime >= end)) {
                return true;
            }
        } catch (Exception e) {
            System.out.println("Error: business param"
                    + "\"opening_hours\" null or NaN: " + e);
        }
        return false;
    }

    /**
     * returns the details of all the places returned in a Place Search
     *
     * @param places Place Search response results parameter
     * @return JSONaray of the the place details
     */
    private JSONArray getDetails(JSONArray places) {
        JSONArray details = new JSONArray();
        OAuthRequest request;
        for (Object obj : places) {
            JSONObject place = (JSONObject) obj;
            request = new OAuthRequest(Verb.GET, "https://maps.googleapis.com/"
                    + "maps/api/place/details/json?key="
                    + JSPStringConstants.GOOGLE_SERVER_KEY + "&placeid="
                    + (String) place.get("place_id"));
            Response response = request.send();
            place = parseJSONObject(response.getBody());
            details.add(place.get("result"));
        }
        return details;
    }
}
