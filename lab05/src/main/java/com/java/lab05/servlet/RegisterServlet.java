package com.java.lab05.servlet;

import com.java.lab05.DTO.RegisterDTO;
import com.java.lab05.service.userService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

@WebServlet(name = "RegisterServlet", value = "/register")
public class RegisterServlet extends HttpServlet {
    private userService userService = new userService();
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        RegisterDTO registerDTO = RegisterDTO.builder()
                .email(request.getParameter("email"))
                .fullName(request.getParameter("fullname"))
                .password(request.getParameter("password"))
                .ConfirmPassword(request.getParameter("confirm-password"))
                .build();
      String message = userService.Register(registerDTO);
    }
}
