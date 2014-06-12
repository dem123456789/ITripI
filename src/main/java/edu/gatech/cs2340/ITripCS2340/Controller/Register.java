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
 * Handles Register.jsp
 * Created by Jonathan on 6/11/2014.
 * @author Jonathan
 * @version 1.0
 */

@WebServlet("/Register/")
public class Register  extends SharedServletMethods {
    private Hash table;

    @Override
    public void doPost(HttpServletRequest request,
                       HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }
    /**
     * Handles Register.jsp
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
        Password conformationPass = new Password(
                getStringParameterSafely(request,
                        JSPStringConstants.CONFIRM_PASSWORD_PARAM));
        if (request.getParameter(JSPStringConstants.GO_BACK_FLAG) != null) {
            goToFileWithUser(request, response, null, JSPStringConstants.LOGIN_JSP);
        } else {
            synchronized (this) {
                register(request, response, user, pass, conformationPass);
            }
        }
    }

    /**
     * Error checks User input and registers them in if it is correct
     * @param request  HTTP request
     * @param response HTTP response
     * @param user The username that was entered
     * @param pass The password that was entered
     * @param conformationPass The conformation password
     * that was entered should equal password
     * @throws javax.servlet.ServletException
     * @throws java.io.IOException
     */
    private void register(HttpServletRequest request,
                          HttpServletResponse response,
                          Username user, Password pass,
                          Password conformationPass)
            throws ServletException, IOException {
        if (table.containsUsername(user)) {
            request.setAttribute(
                    JSPStringConstants.USERNAME_TAKEN_ERROR, 1);
            request.setAttribute(
                    JSPStringConstants.PASSWORD_PARAM, pass.getPassword());
            request.setAttribute(
                    JSPStringConstants.CONFIRM_PASSWORD_PARAM,
                    conformationPass.getPassword());
            goToFileWithUser(request, response, user,
                    JSPStringConstants.REGISTER_JSP);
        } else if (!pass.equals(conformationPass)) {
            request.setAttribute(
                    JSPStringConstants.PASSWORDS_NOT_SAME_ERROR, 1);
            request.setAttribute(
                    JSPStringConstants.PASSWORD_PARAM, pass.getPassword());
            goToFileWithUser(request, response, user,
                    JSPStringConstants.REGISTER_JSP);
        } else {
            table.addAccount(user, pass);
            goToFileWithUser(request, response, user,
                    JSPStringConstants.MAIN_JSP);
        }
    }
}
