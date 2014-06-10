package edu.gatech.cs2340.ITripCS2340.Controller;

import java.io.*;
import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import edu.gatech.cs2340.ITripCS2340.Model.*;

@WebServlet("/Login/*")

public class LoginServlet extends HttpServlet
{
    private Hash table;

    public void init() throws ServletException
    {
        try {
            table = new Hash(getServletContext().getRealPath("/"));
        }catch (IOException e){
        }
    }

    public void doPost(HttpServletRequest request,
                       HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request,response);
    }
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException
    {
        if(request.getParameter("ManageAccount")==null) {
            Username user = new Username(
                    request.getParameter("username") == null ? "" :
                            request.getParameter("username"));
            Password pass = new Password(
                    request.getParameter("password") == null ? "" :
                            request.getParameter("password"));
            synchronized (this) {
                if (Boolean.parseBoolean(request.getParameter("SignIn"))) {
                    signIn(request, response, user, pass);
                } else {
                    register(request, response, user, pass);
                }
            }
        }
        else
        {
            Username user = new Username(
                    request.getParameter("Username") == null ? "" :
                            request.getParameter("Username"));
            if(request.getParameter("changePass")!=null)
            {
                request.setAttribute("Username", user);
                RequestDispatcher dispatcher =
                        getServletContext().getRequestDispatcher("/Password.jsp");
                dispatcher.forward(request,response);
            }
            else if(request.getParameter("changedPass")!=null)
            {
                Password oldPass = new Password(
                        request.getParameter("oldPass") == null ? "" :
                                request.getParameter("oldPass"));
                Password newPass = new Password(
                        request.getParameter("newPass") == null ? "" :
                                request.getParameter("newPass"));
                if(!table.checkCorrectPassword(user,oldPass))
                {
                    request.setAttribute("Username", user);
                    request.setAttribute("PassError", 1);
                    RequestDispatcher dispatcher =
                            getServletContext().getRequestDispatcher("/Password.jsp");
                    dispatcher.forward(request,response);
                }
                else
                {
                    table.removeAccount(user);
                    table.addAccount(user,newPass);
                    request.setAttribute("Username", user);
                    RequestDispatcher dispatcher =
                            getServletContext().getRequestDispatcher("/Manager.jsp");
                    dispatcher.forward(request,response);
                }
            }
            else if(request.getParameter("ChangeUser")!=null)
            {
                request.setAttribute("Username", user);
                RequestDispatcher dispatcher =
                        getServletContext().getRequestDispatcher("/Username.jsp");
                dispatcher.forward(request,response);
            }
            else if(request.getParameter("ChangedUser")!=null)
            {
                Password pass = new Password(
                        request.getParameter("pass") == null ? "" :
                                request.getParameter("oldPass"));
                Username newUser = new Username(
                        request.getParameter("newUser") == null ? "" :
                                request.getParameter("newUser"));
                if(!table.checkCorrectPassword(user,pass))
                {
                    request.setAttribute("Username", user);
                    request.setAttribute("userError", 1);
                    RequestDispatcher dispatcher =
                            getServletContext().getRequestDispatcher("/Username.jsp");
                    dispatcher.forward(request,response);
                }
                else
                {
                    table.removeAccount(user);
                    table.addAccount(newUser,pass);
                    request.setAttribute("Username", newUser);
                    RequestDispatcher dispatcher =
                            getServletContext().getRequestDispatcher("/Manager.jsp");
                    dispatcher.forward(request,response);
                }
            }
            else if(request.getParameter("returnToMain")==null){
                request.setAttribute("Username", user);
                RequestDispatcher dispatcher =
                        getServletContext().getRequestDispatcher("/Manager.jsp");
                dispatcher.forward(request, response);
            }
            else
            {
                request.setAttribute("Username", user);
                RequestDispatcher dispatcher =
                        getServletContext().getRequestDispatcher("/Main.jsp");
                dispatcher.forward(request,response);
            }
        }
    }

    private void signIn(HttpServletRequest request,
                        HttpServletResponse response,
                        Username user, Password pass)
            throws ServletException, IOException
    {
        if(!table.containsUsername(user))
        {
            request.setAttribute("UserError", 1);
            request.setAttribute("NameString", user.getUsername());
            request.setAttribute("PassString", pass.getPassword());
            RequestDispatcher dispatcher =
                    getServletContext().getRequestDispatcher("/index.jsp");
            dispatcher.forward(request,response);
        }
        else if(!table.checkCorrectPassword(user, pass))
        {
            request.setAttribute("PassError", 1);
            request.setAttribute("NameString", user.getUsername());
            request.setAttribute("PassString", pass.getPassword());
            RequestDispatcher dispatcher =
                    getServletContext().getRequestDispatcher("/index.jsp");
            dispatcher.forward(request,response);
        }
        else
        {
            request.setAttribute("Username", user);
            RequestDispatcher dispatcher =
                    getServletContext().getRequestDispatcher("/Main.jsp");
            dispatcher.forward(request,response);
        }
    }
    private void register(HttpServletRequest request,
                          HttpServletResponse response,
                          Username user, Password pass)
            throws ServletException, IOException
    {
        if(table.containsUsername(user))
        {
            request.setAttribute("UserIncorrect", 1);
            request.setAttribute("NameString", user.getUsername());
            request.setAttribute("PassString", pass.getPassword());
            RequestDispatcher dispatcher =
                    getServletContext().getRequestDispatcher("/index.jsp");
            dispatcher.forward(request,response);
        }
        else
        {
            table.addAccount(user, pass);
            request.setAttribute("Username", user);
            RequestDispatcher dispatcher =
                    getServletContext().getRequestDispatcher("/Main.jsp");
            dispatcher.forward(request,response);
        }
    }
}