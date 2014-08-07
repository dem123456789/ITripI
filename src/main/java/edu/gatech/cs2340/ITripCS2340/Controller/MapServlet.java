package edu.gatech.cs2340.ITripCS2340.Controller;

import edu.gatech.cs2340.ITripCS2340.Model.Database;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Jonathan
 */
@WebServlet("/Map/")
public class MapServlet extends SharedServletMethods {

    @Override
    public void doPost(HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }

    /**
     * Handles Map.jsp
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

            if (isParamThere(request, JSPStringConstants.SAVE_HOME_BASE_FLAG)) {
                setCentralLocation(request, getStringParameterSafely(request,
                        JSPStringConstants.CENTRAL_LOCATION));
                Database.changeHomeBase(getUsername(request),
                        getStringParameterSafely(request,
                                JSPStringConstants.CENTRAL_LOCATION));
            }

            request.setAttribute(JSPStringConstants.BUSINESSES,
                    getStringParameterSafely(request,
                            JSPStringConstants.BUSINESSES));
            request.setAttribute(JSPStringConstants.INTEREST_PLACE,
                    getStringParameterSafely(request,
                            JSPStringConstants.INTEREST_PLACE));
            request.setAttribute(JSPStringConstants.CENTRAL_LOCATION,
                    getStringParameterSafely(request,
                            JSPStringConstants.CENTRAL_LOCATION));
            goToFile(request, response, JSPStringConstants.MAP_JSP);
        } else if (isParamThere(request, JSPStringConstants.GO_TO_MAP_FLAG)) {
            goToFile(request, response, JSPStringConstants.CENTRALLOCATION_JSP);
        } else if (isParamThere(request, JSPStringConstants.GO_BACK_FLAG)) {
            goToFile(request, response, JSPStringConstants.MAIN_JSP);
        }
    }

}
