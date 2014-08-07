package edu.gatech.cs2340.ITripCS2340.Controller;

import edu.gatech.cs2340.ITripCS2340.Model.Database;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Handles YelpResults.jsp
 *
 * @author Jonathan
 */
@WebServlet("/YelpResults/")
public class YelpResultsServlet extends SharedServletMethods {

    @Override
    public void doPost(HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }

    /**
     * Handles YelpResults.jsp
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
        if (isParamThere(request, JSPStringConstants.SAVEPLACES)
                || isParamThere(request,
                        JSPStringConstants.SAVE_HOME_BASE_FLAG)) {
            setItinerary(request, getStringParameterSafely(request,
                    JSPStringConstants.ITINERARY_PARAM));
            Database.changeItinerary(getUsername(request),
                    getItinerary(request));

            request.setAttribute(JSPStringConstants.BUSINESSES,
                    getStringParameterSafely(request,
                            JSPStringConstants.BUSINESSES));
            request.setAttribute(JSPStringConstants.INTEREST_PLACE,
                    getStringParameterSafely(request,
                            JSPStringConstants.INTEREST_PLACE));
            request.setAttribute(JSPStringConstants.CENTRAL_LOCATION,
                    getStringParameterSafely(request,
                            JSPStringConstants.CENTRAL_LOCATION));
            goToFile(request, response, JSPStringConstants.YELP_RESULTS_JSP);
        } else if (isParamThere(request, JSPStringConstants.GO_TO_MAP_FLAG)) {
            goToFile(request, response, JSPStringConstants.YELP_JSP);
        } else if (isParamThere(request, JSPStringConstants.GO_BACK_FLAG)) {
            goToFile(request, response, JSPStringConstants.MAIN_JSP);
        }
    }

}
