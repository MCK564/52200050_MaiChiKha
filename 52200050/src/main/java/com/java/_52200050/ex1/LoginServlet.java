package com.java._52200050.ex1;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Serializable;
import java.util.HashMap;

@WebServlet(name = "loginServlet", value = "/login-servlet")
public class LoginServlet extends HttpServlet implements Serializable  {
    private static final long serialVersionUID = 1L;
    private HashMap<String, String> accounts;

    public void init() throws ServletException {
        accounts = new HashMap<>();
        accounts.put("admin", "123456");
        accounts.put("user1", "123456");
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.sendRedirect("index.jsp");
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");


        String username = request.getParameter("username");
        String password = request.getParameter("password");
        PrintWriter out = response.getWriter();

        // Check if the account exists
        if (accounts.containsKey(username) && accounts.get(username).equals(password)) {
            out.println("<html><body>");
            out.println("<h3>Name/Password match</h3>");
            out.println("</body></html>");
        } else {
            out.println("<html><body>");
            out.println("<h3>Name/Password does not match</h3>");
            out.println("</body></html>");
        }
    }
}
