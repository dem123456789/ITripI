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

        String places = getStringParameterSafely(request,
                JSPStringConstants.PlacesFoundInCentralLocation);

        String price = getStringParameterSafely(request,
                JSPStringConstants.PRICE);

        String rating = getStringParameterSafely(request,
                JSPStringConstants.RATING);


        request.setAttribute(JSPStringConstants.CENTRAL_LOCATION, "[{\"location\": \""+location+"\"}]");
        goToFileWithUser(request, response, user,
                JSPStringConstants.MAP_JSP);
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
                "https://maps.googleapis.com/maps/api/place/details/js?key="+
                        JSPStringConstants.GOOGLE_API_KEY);
            request.addBodyParameter("placeid" , 
                    (String)place.get("placeid"));
            Response response = request.send();
            JSONParser parser = new JSONParser();
            try {
                place = (JSONObject) parser.parse(response.getBody());
            } catch (ParseException pe) {
                System.out.println("Error: could not parse JSON response:");
                System.exit(1);
            }
            details.add(place.get("result"));
        }
        return details;
    }
}