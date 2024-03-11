package com.java.lab05.servlet;

import com.google.gson.Gson;
import com.java.lab05.DTO.ProductDTO;
import com.java.lab05.HelloServlet;
import com.java.lab05.exception.DataNotFoundException;
import com.java.lab05.exception.ObjectExisting;
import com.java.lab05.model.Product;
import com.java.lab05.response.ProductResponse;
import com.java.lab05.service.productService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@WebServlet(name = "ProductServlet", value = "/products")
public class ProductServlet extends HelloServlet {
    private productService productService = new productService();
    @Override
    public void init() {
        super.init();
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        HttpSession session = request.getSession(false);
        if(session==null || session.getAttribute("username") == null){
            response.sendRedirect(request.getContextPath()+ "/login.jsp");
        }
        else{
            String username = (String) session.getAttribute("username");
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
            response.setHeader("Pragma", "no-cache");
            response.setHeader("Expires", "0");
            PrintWriter out = response.getWriter();
            out.print("{\"username\": \"" + username + "\"}");
            out.flush();
        }
    }
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String action = request.getParameter("action");
        if (action != null) {
            if (action.equals("logout")) {
                logout(request, response);

            }else if(action.equals("getAllProducts")){
                getListProducts(request,response);
            }
            else if(action.equals("getOneProductById")){
                getOneProductById(request, response);
            }
        } else{

        }
    }
    private void getOneProductById(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        Long id = Long.valueOf(request.getParameter("id"));
        response.setContentType("application/json");
        PrintWriter out = response.getWriter(); response.setCharacterEncoding("UTF-8");
        Product newProduct = productService.getOneById(id);

        try {
            JSONObject responseJson = new JSONObject();
            responseJson.put("id", newProduct.getId());
            responseJson.put("name", newProduct.getName());
            responseJson.put("price", newProduct.getPrice());
            String jsonResponse = responseJson.toString();
            out.print(jsonResponse);
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }

    }
    private void logout(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.removeAttribute("username");
            session.invalidate();
        }
        response.sendRedirect(request.getContextPath() + "/login.jsp");
    }
    public void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException  {
        BufferedReader reader = request.getReader();
        StringBuilder stringBuilder = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            stringBuilder.append(line);
        }
        reader.close();
        String jsonPayload = stringBuilder.toString();
        JSONObject jsonObject = null;
        try {
            jsonObject = new JSONObject(jsonPayload);
            Long id = null;
            if (jsonObject.has("id")) {
                id = jsonObject.getLong("id");
                if(id==0)id=null;
            }
            String name = jsonObject.getString("name");
            Integer price = jsonObject.getInt("price");
            response.setContentType("application/json");
            PrintWriter out = response.getWriter();
            ProductDTO productDTO = ProductDTO.builder()
                    .id(id)
                    .name(name)
                    .price(price)
                    .build();
            try {
                Product newProduct = productService.addProduct(productDTO);
                JSONObject responseJson = new JSONObject();
                responseJson.put("id", newProduct.getId());
                responseJson.put("name", newProduct.getName());
                responseJson.put("price", newProduct.getPrice());
                String jsonResponse = responseJson.toString();
                out.print(jsonResponse);
            } catch (ObjectExisting e) {
                throw new RuntimeException(e);
            }

        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
    }


    private void getListProducts(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        response.setContentType("text/html");

        // Lấy danh sách sản phẩm từ productService
        List<Product> productList = productService.getAllProducts();

        // Tạo chuỗi HTML cho bảng sản phẩm
        StringBuilder htmlTable = new StringBuilder();
        htmlTable.append("<table class=\"table table-striped\">");
        htmlTable.append("<thead>");
        htmlTable.append("<tr class=\"align-item-center\">");
        htmlTable.append("<th>STT</th>");
        htmlTable.append("<th>Tên sản phẩm</th>");
        htmlTable.append("<th>Giá</th>");
        htmlTable.append("<th>Thao tác</th>");
        htmlTable.append("</tr>");
        htmlTable.append("</thead>");
        htmlTable.append("<tbody>");

        // Lặp qua danh sách sản phẩm và thêm từng hàng vào bảng
        for (int i = 0; i < productList.size(); i++) {
            Product product = productList.get(i);
            htmlTable.append("<tr>");
            htmlTable.append("<td>").append(i + 1).append("</td>");
            htmlTable.append("<td><a href=\"#\">").append(product.getName()).append("</a></td>");
            htmlTable.append("<td>").append(product.getPrice()).append("</td>");
            htmlTable.append("<td class=\"d-flex align-items-center\">");
            // Trong vòng lặp:
            htmlTable.append("<button type=\"button\" class=\"btn btn-warning px-5 mr-2\" onclick=\"updateProduct(").append(product.getId()).append(")\">Chỉnh sửa</button>");

            htmlTable.append("<button type=\"button\" class=\"btn btn-danger px-5\" onclick=\"deleteProduct(").append(product.getId()).append(")\">Xóa</button>");
            htmlTable.append("</td>");
            htmlTable.append("</tr>");
        }
        htmlTable.append("</tbody>");
        htmlTable.append("</table>");

        // Gửi chuỗi HTML chứa bảng sản phẩm về trình duyệt
        PrintWriter out = response.getWriter();
        out.print(htmlTable.toString());
    }

    public void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        Long id = Long.valueOf(request.getParameter("id"));
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        if(id==null){
            try {
                throw new DataNotFoundException("Can not find product with id = "+ id);
            } catch (DataNotFoundException e) {
                throw new RuntimeException(e);
            }
        }
        else{
            productService.removeProduct(id);
           out.print("already delete product with id = "+id);
           out.flush();
        }
    }
}
