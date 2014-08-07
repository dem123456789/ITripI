package edu.gatech.cs2340.ITripCS2340.Controller;

import edu.gatech.cs2340.ITripCS2340.Model.Account;
import edu.gatech.cs2340.ITripCS2340.Model.GoogleAPI;
import edu.gatech.cs2340.ITripCS2340.Model.Database;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Handles Centralocation.jsp
 *
 * @author Jonathan Hunter and Enmao Diao
 * @version 1.0
 */
@WebServlet("/Preferences/")
public class Preferences extends SharedServletMethods {

    @Override
    public void doPost(HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }

    /**
     * Handles Centralocation.jsp
     *
     * @param request HTTP request
     * @param response HTTP response
     * @throws javax.servlet.ServletException
     * @throws java.io.IOException
     */
    @Override
    public void doGet(HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        if (isParamThere(request, JSPStringConstants.GO_BACK_FLAG)) {
            goToFile(request, response, JSPStringConstants.MAIN_JSP);
            return;
        }

        if (isParamThere(request, JSPStringConstants.SAVEPLACES)) {
            setItinerary(request, getStringParameterSafely(request,
                    JSPStringConstants.ITINERARY_PARAM));
            Account temp = getAccountFromSession(request);
            Database.changeItinerary(temp.getUserName(), temp.getItinerary());
            goToFile(request, response, JSPStringConstants.CENTRALLOCATION_JSP);
            return;
        }

        if (checkForParams(request)) {
            goToFile(request, response, JSPStringConstants.CENTRALLOCATION_JSP);
            return;
        }

        String location = getStringParameterSafely(request,
                JSPStringConstants.CENTRAL_LOCATION);
        String jsonPlaces = getStringParameterSafely(request,
                JSPStringConstants.PLACES_FOUND_IN_CENTRAL_LOCATION);
        String priceString = getStringParameterSafely(request,
                JSPStringConstants.PRICE);
        String ratingString = getStringParameterSafely(request,
                JSPStringConstants.RATING);
        String startTimeString = getStringParameterSafely(
                request, JSPStringConstants.STARTTIME);
        String endTimeString = getStringParameterSafely(
                request, JSPStringConstants.ENDTIME);
        String type = getStringParameterSafely(
                request, JSPStringConstants.INTEREST_PLACE);

        int price = Integer.parseInt(priceString);
        int rating = Integer.parseInt(ratingString);
        int startTime = Integer.parseInt(startTimeString);
        int endTime = Integer.parseInt(endTimeString);

        GoogleAPI google = new GoogleAPI(startTime, endTime, rating, price);
        request.setAttribute(JSPStringConstants.BUSINESSES,
                google.getFilteredJSON(jsonPlaces));
        request.setAttribute(JSPStringConstants.INTEREST_PLACE, type);
        request.setAttribute(JSPStringConstants.CENTRAL_LOCATION, location);
        goToFile(request, response, JSPStringConstants.MAP_JSP);
    }

    /**
     * Checks to see if all the parameters are there
     *
     * @param request
     * @return true if a parameter is missing
     */
    public boolean checkForParams(HttpServletRequest request) {
        if (!isParamThere(request, JSPStringConstants.CENTRAL_LOCATION)) {
            return true;
        }
        if (!isParamThere(request,
                JSPStringConstants.PLACES_FOUND_IN_CENTRAL_LOCATION)) {
            return true;
        }
        if (!isParamThere(request, JSPStringConstants.PRICE)) {
            return true;
        }
        if (!isParamThere(request, JSPStringConstants.RATING)) {
            return true;
        }
        if (!isParamThere(request, JSPStringConstants.STARTTIME)) {
            return true;
        }
        return !isParamThere(request, JSPStringConstants.INTEREST_PLACE);
    }
}
