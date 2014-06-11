package edu.gatech.cs2340.ITripCS2340.Controller;

import java.io.*;
import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import edu.gatech.cs2340.ITripCS2340.Model.*;

@WebServlet(urlPatterns = {"/Login/","/Register/"})

public class LoginServlet extends HttpServlet {
    private Hash table;

    public void init() throws ServletException {
        try {
            table = new Hash(getServletContext().getRealPath("/"));
        } catch (IOException e) {
            System.out.println("Path Error");
        }
    }

    public void doPost(HttpServletRequest request,
                       HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        if (request.getParameter("ManageAccount") != null) {
            ManageAccount(request, response);
        } else {
            Username user = new Username(
                    getParameterSafely(request, "username"));
            Password pass = new Password(
                    getParameterSafely(request, "password"));
            Password conformationPass = new Password(
                    getParameterSafely(request, "ConformationPass"));
            synchronized (this) {
                if (request.getParameter("LogIn") != null) {
                    LogIn(request, response, user, pass);
                } else {
                    register(request, response, user, pass, conformationPass);
                }
            }
        }
    }

    private String getParameterSafely(
            HttpServletRequest request, String paramName) {
        return request.getParameter(paramName) == null ? "" :
                request.getParameter(paramName);
    }

    private void LogIn(HttpServletRequest request,
                       HttpServletResponse response,
                       Username user, Password pass)
            throws ServletException, IOException {
        if (!table.containsUsername(user)) {
            request.setAttribute("UserError", 1);
            goToFileWithData(request, response, user, pass, "Login.jsp");
        } else if (!table.checkCorrectPassword(user, pass)) {
            request.setAttribute("PassError", 1);
            goToFileWithData(request, response, user, pass, "Login.jsp");
        } else {
            goToMain(request, response, user);
        }
    }

    private void register(HttpServletRequest request,
                          HttpServletResponse response,
                          Username user, Password pass,
                          Password conformationPass)
            throws ServletException, IOException {
        if (table.containsUsername(user)) {
            request.setAttribute("UserIncorrect", 1);
            goToFileWithData(request, response, user, pass, "Register.jsp");
        } else if (!pass.equals(conformationPass)) {
            request.setAttribute("PassDifferent", 1);
            goToFileWithData(request, response, user, pass, "Register.jsp");
        } else {
            table.addAccount(user, pass);
            goToMain(request, response, user);
        }
    }

    private void goToFileWithData(HttpServletRequest request,
                                  HttpServletResponse response,
                                  Username user, Password pass,
                                  String fileName)
            throws ServletException, IOException {
        request.setAttribute("NameString", user.getUsername());
        request.setAttribute("PassString", pass.getPassword());
        RequestDispatcher dispatcher =
                getServletContext().getRequestDispatcher("/" + fileName);
        dispatcher.forward(request, response);
    }

    private void goToFileWithUser(HttpServletRequest request,
                                  HttpServletResponse response,
                                  Username user, String fileName)
            throws ServletException, IOException {
        request.setAttribute("Username", user);
        RequestDispatcher dispatcher =
                getServletContext().getRequestDispatcher("/" + fileName);
        dispatcher.forward(request, response);
    }

    private void goToMain(HttpServletRequest request,
                          HttpServletResponse response,
                          Username user)
            throws ServletException, IOException {
        request.setAttribute("Username", user);
        RequestDispatcher dispatcher =
                getServletContext().getRequestDispatcher("/Main.jsp");
        dispatcher.forward(request, response);
    }

    //code below belongs in AccountManagement servlet here until table can be passed to it
    public void ManageAccount(HttpServletRequest request,
                              HttpServletResponse response)
            throws ServletException, IOException {
        Username user = new Username(getParameterSafely(request, "Username"));
        if (request.getParameter("changePass") != null) {
            goToFileWithUser(request, response, user, "Password.jsp");
        } else if (request.getParameter("changedPass") != null) {
            ChangePassword(request, response, user);
        } else if (request.getParameter("ChangeUser") != null) {
            goToFileWithUser(request, response, user, "Username.jsp");
        } else if (request.getParameter("ChangedUser") != null) {
            ChangeUserName(request, response, user);
        } else if (request.getParameter("returnToMain") == null) {
            goToFileWithUser(request, response, user, "Manager.jsp");
        } else {
            goToMain(request, response, user);
        }
    }

    private void ChangePassword(HttpServletRequest request,
                                HttpServletResponse response,
                                Username user)
            throws ServletException, IOException {
        Password oldPass = new Password(
                getParameterSafely(request, "oldPass"));
        Password newPass = new Password(
                getParameterSafely(request, "newPass"));
        if (!table.checkCorrectPassword(user, oldPass)) {
            request.setAttribute("PassError", 1);
            goToFileWithUser(request, response, user, "Password.jsp");
        } else {
            table.removeAccount(user);
            table.addAccount(user, newPass);
            goToFileWithUser(request, response, user, "Manager.jsp");
        }
    }

    private void ChangeUserName(HttpServletRequest request,
                                HttpServletResponse response,
                                Username user)
            throws ServletException, IOException {
        Password pass = new Password(getParameterSafely(request, "pass"));
        Username newUser = new Username(
                getParameterSafely(request, "newUser"));
        if (!table.checkCorrectPassword(user, pass)) {
            request.setAttribute("PassError", 1);
            goToFileWithUser(request, response, user, "Username.jsp");
        } else if (table.containsUsername(newUser)) {
            request.setAttribute("userError", 1);
            goToFileWithUser(request, response, user, "Username.jsp");
        } else {
            table.removeAccount(user);
            table.addAccount(newUser, pass);
            goToFileWithUser(request, response, newUser, "Manager.jsp");
        }
    }
}
