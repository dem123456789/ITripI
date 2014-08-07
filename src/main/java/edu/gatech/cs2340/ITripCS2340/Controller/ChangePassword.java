package edu.gatech.cs2340.ITripCS2340.Controller;

import edu.gatech.cs2340.ITripCS2340.Model.Account;
import edu.gatech.cs2340.ITripCS2340.Model.Database;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Handles Password.jsp Created by Jonathan on 6/11/2014.
 *
 * @author Jonathan
 * @version 1.0
 */
@WebServlet("/ChangePassword/")

public class ChangePassword extends SharedServletMethods {

    @Override
    public void doPost(HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }

    /**
     * Handles Password.jsp
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
        String oldPass = getStringParameterSafely(request,
                JSPStringConstants.PASSWORD_PARAM);
        String newPass = getStringParameterSafely(request,
                JSPStringConstants.CONFIRM_PASSWORD_PARAM);
        if (isParamThere(request, JSPStringConstants.GO_BACK_FLAG)) {
            goToFile(request, response, JSPStringConstants.MANAGER_JSP);
        } else if (!user.checkPassword(oldPass)) {
            request.setAttribute(
                    JSPStringConstants.PASSWORD_INCORRECT_ERROR, 1);
            request.setAttribute(
                    JSPStringConstants.CONFIRM_PASSWORD_PARAM, newPass);
            goToFile(request, response, JSPStringConstants.PASSWORD_JSP);
        } else {
            Database.changePassword(user.getUserName(), newPass);
            user = Database.getAccount(user.getUserName());
            addAccountToSession(request, user);
            goToFile(request, response, JSPStringConstants.MANAGER_JSP);
        }
    }
}
