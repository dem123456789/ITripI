package edu.gatech.cs2340.ITripCS2340.Controller;

import edu.gatech.cs2340.ITripCS2340.Model.Username;
import java.io.IOException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.scribe.builder.ServiceBuilder;
import org.scribe.model.OAuthRequest;
import org.scribe.model.Response;
import org.scribe.model.Token;
import org.scribe.oauth.OAuthService;

/**
 * Holds methods common to all the servlets in this project
 * Created by Jonathan on 6/11/2014.
 * @author Jonathan
 * @version 1.0
 */

public abstract class SharedServletMethods extends HttpServlet {
    /**
     * Only calls doGet
     *
     * @param request  HTTP request
     * @param response HTTP response
     * @throws javax.servlet.ServletException
     * @throws java.io.IOException
     */
    public abstract void doPost(HttpServletRequest request,
                                HttpServletResponse response)
            throws ServletException, IOException;

    /**
     * @param request  HTTP request
     * @param response HTTP response
     * @throws javax.servlet.ServletException
     * @throws java.io.IOException
     */
    public abstract void doGet(HttpServletRequest request,
                               HttpServletResponse response)
            throws ServletException, IOException;

    /**
     * Checks for null then returns the string
     *
     * @param request   HTTP request
     * @param paramName Parameter you want
     * @throws javax.servlet.ServletException
     * @throws java.io.IOException
     */
    protected String getStringParameterSafely(
            HttpServletRequest request, String paramName) {
        if (request.getParameter(paramName) == null) {
            return "";
        }
        return request.getParameter(paramName);
    }

    /**
     * Redirects to the given file and passes the given username to it
     *
     * @param request  HTTP request
     * @param response HTTP response
     * @throws javax.servlet.ServletException
     * @throws java.io.IOException
     */
    protected void goToFileWithUser(HttpServletRequest request,
                                    HttpServletResponse response,
                                    Username user, String fileName)
            throws ServletException, IOException {
        if (user != null) {
            request.setAttribute(JSPStringConstants.USERNAME_PARAM,
                    user.getUsername());
        }
        RequestDispatcher dispatcher =
                getServletContext().getRequestDispatcher("/" + fileName);
        dispatcher.forward(request, response);
    }
    
    protected String QueryYelp(OAuthRequest request)
    {
        OAuthService service =
        new ServiceBuilder().provider(YelpOAuth.class)
                .apiKey(JSPStringConstants.YELP_API_KEY)
                .apiSecret(JSPStringConstants.YELP_API_SECRET)
                .build();
        Token accessToken = new Token(JSPStringConstants.YELP_TOKEN, 
            JSPStringConstants.YELP_TOKEN_SECRET);
        service.signRequest(accessToken, request);
        Response response = request.send();
        return response.getBody();
    }
}
