package edu.gatech.cs2340.ITripCS2340.Controller;

import edu.gatech.cs2340.ITripCS2340.Model.Hash;
import edu.gatech.cs2340.ITripCS2340.Model.Password;
import edu.gatech.cs2340.ITripCS2340.Model.Username;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Handles Login.jsp
 * Created by Jonathan on 6/11/2014.
 * @author Jonathan
 * @version 1.0
 */

@WebServlet("/Login/")
public class Login extends SharedServletMethods {
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
        Password pass = new Password(
                getStringParameterSafely(request,
                        JSPStringConstants.PASSWORD_PARAM));
        synchronized (this) {
            logIn(request, response, user, pass);
        }
    }
    /**
     * Error checks User input and Logs them in if it is correct
     * @param request  HTTP request
     * @param response HTTP response
     * @param user The username that was entered
     * @param pass The password that was entered
     * @throws javax.servlet.ServletException
     * @throws java.io.IOException
     */
    private void logIn(HttpServletRequest request,
                       HttpServletResponse response,
                       Username user, Password pass)
            throws ServletException, IOException {
        if (!table.containsUsername(user)) {
            request.setAttribute(
                    JSPStringConstants.USERNAME_NOT_FOUND_ERROR, 1);
            request.setAttribute(
                    JSPStringConstants.PASSWORD_PARAM, pass.getPassword());
            goToFileWithUser(request, response, user,
                    JSPStringConstants.LOGIN_JSP);
        } else if (!table.checkCorrectPassword(user, pass)) {
            request.setAttribute(
                    JSPStringConstants.PASSWORD_INCORRECT_ERROR, 1);
            request.setAttribute(
                    JSPStringConstants.PASSWORD_PARAM, pass.getPassword());
            goToFileWithUser(request, response, user,
                    JSPStringConstants.LOGIN_JSP);
        } else {
            goToFileWithUser(request, response, user,
                    JSPStringConstants.MAIN_JSP);
        }
    }
}
