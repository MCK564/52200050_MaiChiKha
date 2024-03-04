package com.java._52200050.ex5;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("")
public class HomeServlet extends HttpServlet {
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String page = request.getParameter("page");
        PrintWriter out = response.getWriter();

        if (page == null || page.isEmpty()) {

            request.getRequestDispatcher("/index.jsp").forward(request, response);
            return;
        }
        out.println(page);

        String destination = "";
        switch (page) {
            case "about":
                destination = "/WEB-INF/about.jsp";
                break;
            case "contact":
                destination = "/WEB-INF/contact.jsp";
                break;
            case "help":
                destination = "/WEB-INF/help.jsp";
                break;
            default:
                response.getWriter().println("Invalid page request");
                return;
        }

        request.getRequestDispatcher(destination).forward(request, response);
    }
}
