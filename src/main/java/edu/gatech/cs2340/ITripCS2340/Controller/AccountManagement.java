package edu.gatech.cs2340.ITripCS2340.Controller;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Handles Manager.jsp Created by Jonathan on 6/11/2014.
 *
 * @author Jonathan
 * @version 1.0
 */
@WebServlet("/AccountManagement/")

public class AccountManagement extends SharedServletMethods {

    @Override
    public void doPost(HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }

    /**
     * Handles Manager.jsp
     *
     * @param request HTTP request
     * @param response HTTP response
     * @throws javax.servlet.ServletException
     * @throws java.io.IOException
     */
    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        if (isParamThere(request, JSPStringConstants.CHANGE_USERNAME_FLAG)) {
            goToFile(request, response, JSPStringConstants.USERNAME_JSP);
        } else if (isParamThere(request,
                JSPStringConstants.CHANGE_PASSWORD_FLAG)) {
            goToFile(request, response, JSPStringConstants.PASSWORD_JSP);
        } else {
            goToFile(request, response, JSPStringConstants.MAIN_JSP);
        }
    }
}
