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
 * Created by Jonathan on 6/11/2014.
 */

@WebServlet("/ChangePassword/")

public class ChangePassword extends SharedServletMethods {
    private Hash table;

    @Override
    public void doPost(HttpServletRequest request,
                       HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }
    /**
     * Manages Password.jsp
     * @param request  HTTP request
     * @param response HTTP response
     * @throws javax.servlet.ServletException
     * @throws java.io.IOException
     */
    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            table = new Hash(getServletContext().getRealPath("/"));
        } catch (IOException e) {
            System.out.println("Path Error");
        }
        Username user = new Username(
                getStringParameterSafely(request,
                        JSPStringConstants.USERNAME_PARAM));
        Password oldPass = new Password(
                getStringParameterSafely(request,
                        JSPStringConstants.PASSWORD_PARAM));
        Password newPass = new Password(
                getStringParameterSafely(request,
                        JSPStringConstants.CONFIRM_PASSWORD_PARAM));
        if (request.getParameter(JSPStringConstants.GO_BACK_FLAG) != null) {
            goToFileWithUser(request, response, user,
                    JSPStringConstants.MANAGER_JSP);
        } else if (!table.checkCorrectPassword(user, oldPass)) {
            request.setAttribute(
                    JSPStringConstants.PASSWORD_INCORRECT_ERROR, 1);
            request.setAttribute(
                    JSPStringConstants.CONFIRM_PASSWORD_PARAM,
                    newPass.getPassword());
            goToFileWithUser(request, response, user,
                    JSPStringConstants.PASSWORD_JSP);
        } else {
            table.removeAccount(user, oldPass);
            table.addAccount(user, newPass);
            goToFileWithUser(request, response, user,
                    JSPStringConstants.MANAGER_JSP);
        }
    }
}
