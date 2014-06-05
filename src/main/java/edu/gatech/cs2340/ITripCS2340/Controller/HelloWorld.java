package edu.gatech.cs2340.ITripCS2340.Controller;

import java.io.*;
import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

@WebServlet(urlPatterns={
        "/Hello", // GET
})

public class HelloWorld extends HttpServlet {
    public void doPost(HttpServletRequest request,
                       HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request,response);
    }
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        RequestDispatcher dispatcher =
                getServletContext().getRequestDispatcher("/Hello.jsp");
        dispatcher.forward(request,response);
    }

}