package com.java._52200050.ex6;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.json.JSONArray;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@WebServlet("/register")
public class RegisterServlet extends HttpServlet {
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        JSONArray array = new JSONArray();
        Map<String, Object> params = new HashMap<>();
        String[] favoriteIdeValues = request.getParameterValues("favorite_ide");
        List<String> favoriteIdeValueList = Arrays.asList(favoriteIdeValues);

        Map<String, String[]> parameterMap = request.getParameterMap();
        for (Map.Entry<String, String[]> entry : parameterMap.entrySet()) {
            String paramName = entry.getKey();
            String[] paramValues = entry.getValue();
            if (paramValues.length > 0) {
                params.put(paramName, paramValues[0]);
            }
        }

        response.setContentType("text/html");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().println("<html><body>");
        response.getWriter().println("<h1>Registration Details:</h1>");
        response.getWriter().println("<table style='border:1px solid black'>");
        for (Map.Entry<String, Object> entry : params.entrySet()) {

            response.getWriter().println("<tr style='border:1px solid black'>");
            if(entry.getKey().equals("favorite_ide")){
                response.getWriter().println("<td style='border:1px solid black; color:green'>"
                        + entry.getKey() + "</td> <td style='border:1px solid black'>"
                        + favoriteIdeValueList.toString() + "</td>");
            }
            else {
                response.getWriter().println("<td style='border:1px solid black; color:green'>" + entry.getKey() + "</td> <td style='border:1px solid black'>" + entry.getValue() + "</td>");
            }response.getWriter().println("</tr>");
        }
        response.getWriter().println("</table></body></html>");
    }
}
