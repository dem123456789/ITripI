package edu.gatech.cs2340.ITripCS2340.Controller;

import edu.gatech.cs2340.ITripCS2340.Model.Account;
import edu.gatech.cs2340.ITripCS2340.Model.Database;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Handles Username.jsp Created by Jonathan on 6/11/2014.
 *
 * @author Jonathan
 * @version 1.0
 */
@WebServlet("/ChangeUserName/")
public class ChangeUserName extends SharedServletMethods {

    @Override
    public void doPost(HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }

    /**
     * Handles Username.jsp
     *
     * @param request HTTP request
     * @param response HTTP response
     * @throws javax.servlet.ServletException
     * @throws java.io.IOException
     */
    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Account user = getAccountFromSession(request);
        String pass = getStringParameterSafely(request,
                JSPStringConstants.PASSWORD_PARAM);
        String newUser = getStringParameterSafely(request,
                JSPStringConstants.NEW_USERNAME_PARAM);
        if (isParamThere(request, JSPStringConstants.GO_BACK_FLAG)) {
            goToFile(request, response, JSPStringConstants.MANAGER_JSP);
        } else if (!user.checkPassword(pass)) {
            request.setAttribute(
                    JSPStringConstants.PASSWORD_INCORRECT_ERROR, 1);
            request.setAttribute(
                    JSPStringConstants.NEW_USERNAME_PARAM, newUser);
            goToFile(request, response, JSPStringConstants.USERNAME_JSP);
        } else if (Database.checkIfUserExists(newUser)) {
            request.setAttribute(
                    JSPStringConstants.USERNAME_TAKEN_ERROR, 1);
            request.setAttribute(
                    JSPStringConstants.PASSWORD_PARAM, pass);
            request.setAttribute(
                    JSPStringConstants.NEW_USERNAME_PARAM, newUser);
            goToFile(request, response, JSPStringConstants.USERNAME_JSP);
        } else {
            Database.changeUserName(user.getUserName(), newUser);
            user = Database.getAccount(newUser);
            addAccountToSession(request, user);
            goToFile(request, response, JSPStringConstants.MANAGER_JSP);
        }
    }
}
