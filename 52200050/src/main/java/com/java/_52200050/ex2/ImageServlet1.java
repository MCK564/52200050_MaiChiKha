package com.java._52200050.ex2;

import jakarta.servlet.ServletException;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

@WebServlet("/image1")
public class ImageServlet1 extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("image/jpeg");
        File imageFile = new File("C:\\Users\\KHA\\Pictures\\Screenshots\\Ảnh chụp màn hình 2024-03-01 161813.png");

        response.setContentLength((int) imageFile.length());
        response.setHeader("Content-Disposition", "inline; filename=\"" + imageFile.getName() + "\"");

        try (InputStream inputStream = new FileInputStream(imageFile);
             ServletOutputStream outputStream = response.getOutputStream()) {
            byte[] buffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, bytesRead);
            }
        }
    }
}