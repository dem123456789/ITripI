package edu.gatech.cs2340.ITripCS2340.Controller;

import edu.gatech.cs2340.ITripCS2340.Model.Database;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Handles Register.jsp Created by Jonathan on 6/11/2014.
 *
 * @author Jonathan
 * @version 1.0
 */
@WebServlet("/Register/")
public class Register extends SharedServletMethods {

    @Override
    public void doPost(HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }

    /**
     * Handles Register.jsp
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
        String user = getStringParameterSafely(request,
                JSPStringConstants.USERNAME_PARAM);
        String pass = getStringParameterSafely(request,
                JSPStringConstants.PASSWORD_PARAM);
        String conformationPass = getStringParameterSafely(request,
                JSPStringConstants.CONFIRM_PASSWORD_PARAM);
        if (isParamThere(request, JSPStringConstants.GO_BACK_FLAG)) {
            goToFile(request, response, JSPStringConstants.LOGIN_JSP);
        } else {
            synchronized (this) {
                register(request, response, user, pass, conformationPass);
            }
        }
    }

    /**
     * Error checks User input and registers them in if it is correct
     *
     * @param request HTTP request
     * @param response HTTP response
     * @param user The username that was entered
     * @param pass The password that was entered
     * @param conformationPass The conformation password that was entered should
     * equal password
     * @throws javax.servlet.ServletException
     * @throws java.io.IOException
     */
    private void register(HttpServletRequest request,
            HttpServletResponse response,
            String user, String pass,
            String conformationPass)
            throws ServletException, IOException {
        if (Database.checkIfUserExists(user)) {
            request.setAttribute(
                    JSPStringConstants.USERNAME_TAKEN_ERROR, 1);
            request.setAttribute(
                    JSPStringConstants.USERNAME_PARAM, user);
            request.setAttribute(
                    JSPStringConstants.PASSWORD_PARAM, pass);
            request.setAttribute(
                    JSPStringConstants.CONFIRM_PASSWORD_PARAM,
                    conformationPass);
            goToFile(request, response, JSPStringConstants.REGISTER_JSP);
        } else if (!pass.equals(conformationPass)) {
            request.setAttribute(
                    JSPStringConstants.PASSWORDS_NOT_SAME_ERROR, 1);
            request.setAttribute(
                    JSPStringConstants.USERNAME_PARAM, user);
            request.setAttribute(
                    JSPStringConstants.PASSWORD_PARAM, pass);
            goToFile(request, response, JSPStringConstants.REGISTER_JSP);
        } else {
            Database.createAccount(user, pass);
            addAccountToSession(request, Database.getAccount(user));
            goToFile(request, response, JSPStringConstants.MAIN_JSP);
        }
    }
}
