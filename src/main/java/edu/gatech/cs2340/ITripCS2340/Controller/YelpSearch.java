/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.gatech.cs2340.ITripCS2340.Controller;

import edu.gatech.cs2340.ITripCS2340.Model.Account;
import edu.gatech.cs2340.ITripCS2340.Model.Database;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Handles Yelp.jsp
 *
 * @author Jonathan
 * @version 1.0
 */
@WebServlet("/Yelp/")
public class YelpSearch extends SharedServletMethods {

    @Override
    public void doPost(HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }

    /**
     * Handles Yelp.jsp
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
            goToFile(request, response, JSPStringConstants.YELP_JSP);
            return;
        }

        if (checkForParams(request)) {
            goToFile(request, response, JSPStringConstants.YELP_JSP);
            return;
        }

        String location = getStringParameterSafely(request,
                JSPStringConstants.CENTRAL_LOCATION);
        String type = getStringParameterSafely(
                request, JSPStringConstants.INTEREST_PLACE);
        String radius = getStringParameterSafely(request,
                JSPStringConstants.DISTANCE);

        YelpAPI yelp = new YelpAPI(JSPStringConstants.YELP_API_KEY,
                JSPStringConstants.YELP_API_SECRET,
                JSPStringConstants.YELP_TOKEN,
                JSPStringConstants.YELP_TOKEN_SECRET);
        request.setAttribute(JSPStringConstants.BUSINESSES, yelp.queryAPI(
                type, location, radius));

        request.setAttribute(JSPStringConstants.STARTTIME,
                getStringParameterSafely(request,
                        JSPStringConstants.STARTTIME));
        request.setAttribute(JSPStringConstants.ENDTIME,
                getStringParameterSafely(request,
                        JSPStringConstants.ENDTIME));
        request.setAttribute(JSPStringConstants.INTEREST_PLACE, type);
        request.setAttribute(JSPStringConstants.CENTRAL_LOCATION, location);
        goToFile(request, response, JSPStringConstants.YELP_RESULTS_JSP);
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
        if (!isParamThere(request, JSPStringConstants.DISTANCE)) {
            return true;
        }
        return !isParamThere(request, JSPStringConstants.INTEREST_PLACE);
    }
}
