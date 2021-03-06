package edu.gatech.cs2340.ITripCS2340.Controller;

import edu.gatech.cs2340.ITripCS2340.Model.Database;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Handles Login.jsp Created by Jonathan on 6/11/2014.
 *
 * @author Jonathan
 * @version 1.0
 */
@WebServlet("/Login/")
public class Login extends SharedServletMethods {

    /**
     * initializes the ORMlite database
     *
     * @throws javax.servlet.ServletException
     */
    @Override
    public void init() throws ServletException {
        Database.setupDatabase(getServletContext().getRealPath("/"));

    }

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
        if (isParamThere(request, JSPStringConstants.GO_BACK_FLAG)) {
            goToFile(request, response, JSPStringConstants.REGISTER_JSP);
            return; //can't let other code execute
        }
        String user = getStringParameterSafely(request,
                JSPStringConstants.USERNAME_PARAM);
        String pass = getStringParameterSafely(request,
                JSPStringConstants.PASSWORD_PARAM);
        synchronized (this) {
            logIn(request, response, user, pass);
        }

    }

    /**
     * Error checks User input and Logs them in if it is correct
     *
     * @param request HTTP request
     * @param response HTTP response
     * @param user The username that was entered
     * @param pass The password that was entered
     * @throws javax.servlet.ServletException
     * @throws java.io.IOException
     */
    private void logIn(HttpServletRequest request,
            HttpServletResponse response,
            String user, String pass)
            throws ServletException, IOException {
        if (!Database.checkIfUserExists(user)) {
            request.setAttribute(
                    JSPStringConstants.USERNAME_NOT_FOUND_ERROR, 1);
            request.setAttribute(
                    JSPStringConstants.USERNAME_PARAM, user);
            request.setAttribute(
                    JSPStringConstants.PASSWORD_PARAM, pass);
            goToFile(request, response, JSPStringConstants.LOGIN_JSP);
        } else if (!Database.checkPassword(user, pass)) {
            request.setAttribute(
                    JSPStringConstants.PASSWORD_INCORRECT_ERROR, 1);
            request.setAttribute(
                    JSPStringConstants.USERNAME_PARAM, user);
            request.setAttribute(
                    JSPStringConstants.PASSWORD_PARAM, pass);
            goToFile(request, response, JSPStringConstants.LOGIN_JSP);
        } else {
            addAccountToSession(request, Database.getAccount(user));
            goToFile(request, response, JSPStringConstants.MAIN_JSP);
        }
    }
}
