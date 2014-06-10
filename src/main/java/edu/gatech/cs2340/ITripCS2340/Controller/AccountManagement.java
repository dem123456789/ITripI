package edu.gatech.cs2340.ITripCS2340.Controller;

import java.io.*;
import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import edu.gatech.cs2340.ITripCS2340.Model.*;

@WebServlet("/AccountManagement/*")

public class AccountManagement extends HttpServlet {
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
        doGet(request, response);
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Username user = new Username(
                request.getParameter("Username") == null ? "" :
                        request.getParameter("username"));
    }
}