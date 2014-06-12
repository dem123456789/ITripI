package edu.gatech.cs2340.ITripCS2340.Controller;

import edu.gatech.cs2340.ITripCS2340.Model.Hash;
import edu.gatech.cs2340.ITripCS2340.Model.Username;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by Jonathan on 6/11/2014.
 */

@WebServlet("/AccountManagement/")

public class AccountManagement extends SharedServletMethods {
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
        if (request.getParameter(
                JSPStringConstants.MANAGE_ACCOUNT_FLAG) != null) {
            goToFileWithUser(request, response, user,
                    JSPStringConstants.MANAGER_JSP);
        } else if (request.getParameter(
                JSPStringConstants.CHANGE_USERNAME_FLAG) != null) {
            goToFileWithUser(request, response, user,
                    JSPStringConstants.USERNAME_JSP);
        } else if (request.getParameter(
                JSPStringConstants.CHANGE_PASSWORD_FLAG) != null) {
            goToFileWithUser(request, response, user,
                    JSPStringConstants.PASSWORD_JSP);
        } else {
            goToFileWithUser(request, response, user,
                    JSPStringConstants.MAIN_JSP);
        }
    }
}
