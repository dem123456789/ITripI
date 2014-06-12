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

@WebServlet("/ChangeUserName/")
public class ChangeUserName extends SharedServletMethods {
    private Hash table;

    @Override
    public void doPost(HttpServletRequest request,
                       HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }

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
        Password pass = new Password(
                getStringParameterSafely(request,
                        JSPStringConstants.PASSWORD_PARAM));
        Username newUser = new Username(
                getStringParameterSafely(request,
                        JSPStringConstants.NEW_USERNAME_PARAM));
        if (request.getParameter(JSPStringConstants.GO_BACK_FLAG) != null) {
            goToFileWithUser(request, response, user,
                    JSPStringConstants.MANAGER_JSP);
        } else if (!table.checkCorrectPassword(user, pass)) {
            request.setAttribute(
                    JSPStringConstants.PASSWORD_INCORRECT_ERROR, 1);
            request.setAttribute(
                    JSPStringConstants.NEW_USERNAME_PARAM,
                    newUser.getUsername());
            goToFileWithUser(request, response, user,
                    JSPStringConstants.USERNAME_JSP);
        } else if (table.containsUsername(newUser)) {
            request.setAttribute(
                    JSPStringConstants.USERNAME_TAKEN_ERROR, 1);
            request.setAttribute(
                    JSPStringConstants.PASSWORD_PARAM, pass.getPassword());
            request.setAttribute(
                    JSPStringConstants.NEW_USERNAME_PARAM,
                    newUser.getUsername());
            goToFileWithUser(request, response, user,
                    JSPStringConstants.USERNAME_JSP);
        } else {
            table.removeAccount(user, pass);
            table.addAccount(newUser, pass);
            goToFileWithUser(request, response, newUser,
                    JSPStringConstants.MANAGER_JSP);
        }
    }
}
