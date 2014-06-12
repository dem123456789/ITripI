package edu.gatech.cs2340.ITripCS2340.Controller;

import edu.gatech.cs2340.ITripCS2340.Model.Username;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by Jonathan on 6/11/2014.
 */
public abstract class SharedServletMethods extends HttpServlet {

    public abstract void doPost(HttpServletRequest request,
                                HttpServletResponse response)
            throws ServletException, IOException;

    public abstract void doGet(HttpServletRequest request,
                               HttpServletResponse response)
            throws ServletException, IOException;

    protected String getStringParameterSafely(
            HttpServletRequest request, String paramName) {
        if (request.getParameter(paramName) == null) {
            return "";
        }
        return request.getParameter(paramName);
    }

    protected void goToFileWithUser(HttpServletRequest request,
                                    HttpServletResponse response,
                                    Username user, String fileName)
            throws ServletException, IOException {
        request.setAttribute(JSPStringConstants.USERNAME_PARAM,
                user.getUsername());
        RequestDispatcher dispatcher =
                getServletContext().getRequestDispatcher("/" + fileName);
        dispatcher.forward(request, response);
    }
}
