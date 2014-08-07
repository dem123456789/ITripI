package edu.gatech.cs2340.ITripCS2340.Controller;

import edu.gatech.cs2340.ITripCS2340.Model.Account;
import edu.gatech.cs2340.ITripCS2340.Model.List;
import java.io.IOException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/**
 * Holds methods common to all the servlets in this project Created by Jonathan
 * on 6/11/2014.
 *
 * @author Jonathan
 * @version 1.0
 */
public abstract class SharedServletMethods extends HttpServlet {

    /**
     * Only calls doGet
     *
     * @param request HTTP request
     * @param response HTTP response
     * @throws javax.servlet.ServletException
     * @throws java.io.IOException
     */
    @Override
    public abstract void doPost(HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException;

    /**
     * @param request HTTP request
     * @param response HTTP response
     * @throws javax.servlet.ServletException
     * @throws java.io.IOException
     */
    @Override
    public abstract void doGet(HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException;

    /**
     * Checks for null then returns the string
     *
     * @param request HTTP request
     * @param paramName Parameter you want
     * @return the requested parameter
     */
    protected String getStringParameterSafely(
            HttpServletRequest request, String paramName) {
        if (!isParamThere(request, paramName)) {
            return paramName + "+does+not+exist";
        }
        return request.getParameter(paramName);
    }

    /**
     * checks if the parameter is there
     *
     * @param request HTTP request
     * @param paramName the parameter in question
     * @return true if the parameter is there
     */
    protected boolean isParamThere(HttpServletRequest request,
            String paramName) {
        if (request.getParameter(paramName) == null
                || request.getParameter(paramName).equals("")) {
            return false;
        }
        return true;
    }

    /**
     * Redirects to the given file
     *
     * @param request HTTP request
     * @param response HTTP response
     * @param fileName the file you want
     * @throws javax.servlet.ServletException
     * @throws java.io.IOException
     */
    protected void goToFile(HttpServletRequest request,
            HttpServletResponse response,
            String fileName)
            throws ServletException, IOException {
        RequestDispatcher dispatcher
                = getServletContext().getRequestDispatcher(fileName);
        dispatcher.forward(request, response);
    }

    /**
     * Adds the account to the HTTP session
     *
     * @param request HTTP request
     * @param account the account you want to add
     */
    public static void addAccountToSession(HttpServletRequest request,
            Account account) {
        request.getSession().setAttribute(JSPStringConstants.ACCOUNT_PARAM,
                account);
    }

    /**
     * Retrieves the account from the HTTP session
     *
     * @param request HTTP request
     * @return the account
     */
    public static Account getAccountFromSession(HttpServletRequest request) {
        return (Account) request.getSession().getAttribute(
                JSPStringConstants.ACCOUNT_PARAM);
    }

    /**
     * for use in JSP's returns the user name as a string
     *
     * @param request HTTP request
     * @return the username you want
     */
    public static String getUsername(HttpServletRequest request) {
        return getAccountFromSession(request).getUserName();
    }

    /**
     * Returns a JSONArray of the itinerary
     *
     * @param request HTTP request
     * @return a JSONArray of the itinerary
     */
    public static String getItinerary(HttpServletRequest request) {
        return getAccountFromSession(request).getItinerary().toJSON();
    }

    /**
     * Updates the itinerary from a JSONArray
     *
     * @param request HTTP request
     * @param jsonArray the itinerary in JSON form
     */
    public static void setItinerary(HttpServletRequest request,
            String jsonArray) {
        getAccountFromSession(request).setItinerary(
                List.getListFromJSON(jsonArray));
    }

    /**
     * Updates the centralLocation
     *
     * @param request HTTP request
     * @param centralLocation the centralLocation
     */
    public static void setCentralLocation(HttpServletRequest request,
            String centralLocation) {
        getAccountFromSession(request).setHomeBase(centralLocation);
    }

    /**
     * Returns the string of the home base
     *
     * @param request HTTP request
     * @return a string of the home base
     */
    public static String getCentralLocation(HttpServletRequest request) {
        return getAccountFromSession(request).getHomeBase();
    }

    /**
     * parses the JSON string to a JSONObject
     *
     * @param json the JSON string
     * @return the JSONObject
     */
    public static JSONObject parseJSONObject(String json) {
        JSONParser parser = new JSONParser();
        try {
            return (JSONObject) parser.parse(json);
        } catch (ParseException pe) {
            System.out.println("Error: could not parse JSON object: " + pe);
            return null;
        }
    }

    /**
     * parses the JSON string to a JSONArray
     *
     * @param json the JSON string
     * @return the array
     */
    public static JSONArray parseJSONArray(String json) {
        JSONParser parser = new JSONParser();
        try {
            return (JSONArray) parser.parse(json);
        } catch (ParseException pe) {
            System.out.println("Error: could not parse JSON Array: " + pe);
            return null;
        }
    }
}
