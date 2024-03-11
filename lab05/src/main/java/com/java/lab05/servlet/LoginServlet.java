package com.java.lab05.servlet;

import com.java.lab05.DAO.UserDAO;
import com.java.lab05.model.User;
import com.java.lab05.service.userService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "LoginServlet", value = "/login")
public class LoginServlet extends HttpServlet {
    private final userService userService = new userService();
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        if(userService.IsLoginSuccess(username,password)){
            HttpSession session = request.getSession();
            String fullname = userService.getFullNameByUsername(username);
            session.setAttribute("username", username);
            response.sendRedirect(request.getContextPath() + "/products.jsp");
        }
        else{
            response.sendRedirect(request.getContextPath() + "/login.jsp");
        }
    }
}
