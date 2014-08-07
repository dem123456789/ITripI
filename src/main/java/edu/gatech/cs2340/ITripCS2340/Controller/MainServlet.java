/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.gatech.cs2340.ITripCS2340.Controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Handles Main.jsp Created by Jonathan on 6/11/2014.
 *
 * @author Jonathan
 * @version 1.0
 */
@WebServlet("/Main/")
public class MainServlet extends SharedServletMethods {

    @Override
    public void doPost(HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }

    /**
     * Handles Login.jsp
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
        if (isParamThere(request, JSPStringConstants.LOGOUT_FLAG)) {
            request.getSession().invalidate();
            response.sendRedirect(
                    "/ITripCS2340" + JSPStringConstants.INDEX_HTML);
        } else if (isParamThere(request,
                JSPStringConstants.MANAGE_ACCOUNT_FLAG)) {
            goToFile(request, response, JSPStringConstants.MANAGER_JSP);
        } else if (isParamThere(request, JSPStringConstants.GO_TO_MAP_FLAG)) {
            goToFile(request, response, JSPStringConstants.CENTRALLOCATION_JSP);
        } else if (isParamThere(request, JSPStringConstants.SHOW_ROUTE_FLAG)) {
            goToFile(request, response,
                    JSPStringConstants.DISPLAY_ITINERARY_JSP);
        } else if (isParamThere(request, JSPStringConstants.GO_TO_YELP_FLAG)) {
            goToFile(request, response,
                    JSPStringConstants.YELP_JSP);
        }
    }
}
