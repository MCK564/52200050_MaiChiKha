package com.java._52200050.ex4;


import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

@WebServlet(name="UploadServlet",value="/upload")
public class UploadServlet extends HttpServlet implements Serializable {
    private static final long serialVersionUID = 1L;
    public void init(){

    }
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
       try{
           Map<String,Object> params = new HashMap<>();
           if(request.getParameter("filename") != null){
               params.put("filename", request.getParameter("filename"));
           }

           Part filePart = request.getPart("file");
           if(filePart != null){
               String fileType = getFileExtension(filePart.getSubmittedFileName());
               params.put("file", filePart);
               params.put("fileType", fileType);
           }

           if(request.getParameter("override") != null){
               params.put("override", request.getParameter("override"));
           }

           response.setContentType("text/html");
           PrintWriter out = response.getWriter();
           out.println(params);
       }catch(Exception e){
           PrintWriter out = response.getWriter();
           out.println(e.getMessage());
       }
    }
    private String getFileExtension(String fileName) {
        return fileName.substring(fileName.lastIndexOf(".") + 1);
    }
}
