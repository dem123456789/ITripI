package edu.gatech.cs2340.ITripCS2340.Controller;

import java.io.*;
import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import edu.gatech.cs2340.ITripCS2340.Model.*;

/**
 * Created by Jonathan on 6/11/2014.
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
            LogIn(request, response, user, pass);
        }
    }

    private void LogIn(HttpServletRequest request,
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