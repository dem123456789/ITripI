package edu.gatech.cs2340.ITripCS2340.Controller;

import edu.gatech.cs2340.ITripCS2340.Model.Hash;
import edu.gatech.cs2340.ITripCS2340.Model.Username;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.scribe.model.OAuthRequest;
import org.scribe.model.Response;
import org.scribe.model.Verb;

/**
 * Handles Login.jsp
 * Created by Jonathan on 6/11/2014.
 * @author Jonathan
 * @version 1.0
 */

@WebServlet("/Preferences/")
public class Preferences extends SharedServletMethods {
    private Hash table;

    @Override
    public void doPost(HttpServletRequest request,
                       HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }
    /**
     * Handles Login.jsp
     * @param request  HTTP request
     * @param response HTTP response
     * @throws javax.servlet.ServletException
     * @throws java.io.IOException
     */
    @Override
    public void doGet(HttpServletRequest request,
                      HttpServletResponse response)
            throws ServletException, IOException {
        try {
            table = new Hash(getServletContext().getRealPath("/"));
        } catch (IOException e) {
            System.out.println("Path Error");
        }
        
        Username user = new Username(
                getStringParameterSafely(request,
                        JSPStringConstants.USERNAME_PARAM));

        String location = getStringParameterSafely(request,
                JSPStringConstants.CENTRAL_LOCATION);

        String interestPlace = getStringParameterSafely(request,
                JSPStringConstants.INTEREST_PLACE);

        String distanceInMiles = getStringParameterSafely(request,
                JSPStringConstants.DISTANCE);

        String distanceInMeters = String.valueOf(
                JSPStringConstants.milesToMeters(Double.parseDouble(distanceInMiles)));

        String JSONplaces = getStringParameterSafely(request,
                JSPStringConstants.PlacesFoundInCentralLocation);

        String priceString = getStringParameterSafely(request,
                JSPStringConstants.PRICE);

        String ratingString = getStringParameterSafely(request,
                JSPStringConstants.RATING);
        String startTimeString=getStringParameterSafely(request, JSPStringConstants.STARTTIME);
        String endTimeString=getStringParameterSafely(request, JSPStringConstants.ENDTIME);
        int price=Integer.parseInt(priceString);
        int rating=Integer.parseInt(ratingString);
        int startTime=Integer.parseInt(startTimeString);
        int endTime=Integer.parseInt(endTimeString);
        JSONArray places=new JSONArray();
        JSONParser parser = new JSONParser();
        try {
            places = (JSONArray) parser.parse(JSONplaces);
        } catch (ParseException pe) {
            System.out.println("Error: could not parse JSON response:");
        }
        JSONArray businesses=getDetails(places);        
        JSONArray filter=new JSONArray();
        for(Object obj: businesses)
        {
            JSONObject business =(JSONObject)obj;
            System.out.println("Start");
            try {
                if ((long) business.get("price_level") <= price) {
                    parseAnything(business, filter, startTime, endTime, rating);
                }
            } catch (Exception e) {
                if ((double) business.get("price_level") <= price) {
                    parseAnything(business, filter, startTime, endTime, rating);
                }
            }
        }
        request.setAttribute(JSPStringConstants.BUSINESSES, filter);
        request.setAttribute(JSPStringConstants.CENTRAL_LOCATION, "[{\"location\": \""+location+"\"}]");
        goToFileWithUser(request, response, user,
                JSPStringConstants.MAP_JSP);
    }
    
    private void parseAnything(JSONObject business, JSONArray filter, int startTime, int endTime, int rating)
    {
        System.out.println("AAAAAAAAAAAAAAAAAAAAA");
        try {
            System.out.println("1");
            if ((double) business.get("rating") >= rating) {
                System.out.println("2");
                parseEverthing(business, filter, startTime, endTime);
            }
        } catch (Exception e) {
            System.out.println("3");
            try{                
                if ((long) business.get("rating") >= rating) {
                    System.out.println("4");
                    parseEverthing(business, filter, startTime, endTime);
                }
            }catch(Exception ex){
            }
        }
    }
    
    private void parseEverthing(JSONObject business, JSONArray filter, int startTime, int endTime)
    {
            System.out.println("BBBBBBBBBBBBBBBBBBBBBBBBBB");
            try {
                    JSONObject opening = (JSONObject) business.get("opening_hours");
                    JSONArray period = (JSONArray) opening.get("periods");
                    JSONObject monday = (JSONObject) period.get(1);
                    JSONObject open = (JSONObject) monday.get("open");
                    JSONObject closed = (JSONObject) monday.get("close");
                    int begin = Integer.parseInt((String) open.get("time"));
                    int end = Integer.parseInt((String) closed.get("time"));
                    if ((startTime <= begin && endTime >= begin) || (startTime <= end && endTime >= end)) {
                        System.out.println("CCCCCCCCCCCCCCCCCCCCCCCCC");
                        filter.add(business);
                    }
            } catch (Exception e) {
            }
            
    }
    
    
    /**
    * returns the details of all the places returned in a Place Search
    * @param places Place Search response results parameter
    * @return JSONaray of the the place details
    **/
    public JSONArray getDetails(JSONArray places)
    {
        JSONArray details =new JSONArray();
        OAuthRequest request;
        for(Object obj: places)
        {
            JSONObject place =(JSONObject)obj;
            request = new OAuthRequest(Verb.GET,
                "https://maps.googleapis.com/maps/api/place/details/json?key="+
                        "AIzaSyCtLn8udNQjOCy1uA14rduzDR88OxhL2RA&placeid="+(String)place.get("place_id"));
            Response response = request.send();
            JSONParser parser = new JSONParser();
            try {
                place = (JSONObject) parser.parse(response.getBody());
            } catch (ParseException pe) {
                System.out.println("Error: could not parse JSON response:");
            }
            details.add(place.get("result"));
        }
        return details;
    }
}