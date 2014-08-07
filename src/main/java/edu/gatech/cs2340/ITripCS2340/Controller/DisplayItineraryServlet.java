package edu.gatech.cs2340.ITripCS2340.Controller;

import static edu.gatech.cs2340.ITripCS2340.Controller.
        SharedServletMethods.getAccountFromSession;
import edu.gatech.cs2340.ITripCS2340.Model.Account;
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
@WebServlet("/DisplayItinerary/")
public class DisplayItineraryServlet extends SharedServletMethods {

    @Override
    public void doPost(HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }

    /**
     * Handles DisplayItinerary.jsp
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
        if (isParamThere(request, JSPStringConstants.SAVEPLACES)) {
            setItinerary(request, getStringParameterSafely(request,
                    JSPStringConstants.ITINERARY_PARAM));
            Account temp = getAccountFromSession(request);
            Database.changeItinerary(temp.getUserName(), temp.getItinerary());
            goToFile(request, response,
                    JSPStringConstants.DISPLAY_ITINERARY_JSP);
        } else if (isParamThere(request, JSPStringConstants.GO_BACK_FLAG)) {
            goToFile(request, response, JSPStringConstants.MAIN_JSP);
        }
    }

}
