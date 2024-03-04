package com.java._52200050.ex3;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.Serializable;
import java.util.*;

@WebServlet(name="ProductServlet",value="/product")
public class ProductServlet extends HttpServlet implements Serializable {
    private static final long serialVersionUID = 1L;
    private static final String UPLOAD_DIRECTORY = "uploads";
    private ProductManager products;
    @Override
    public void init() throws ServletException {
        products = new ProductManager();
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        JSONArray array = new JSONArray();
        Map<String,Object> params = new HashMap<>();
        if(request.getParameter("id")!=null){
        params.put("id",Long.parseLong(request.getParameter("id")));
        }

        if(request.getParameter("name")!=null){
            params.put("name",request.getParameter("name"));
        }
        if(request.getParameter("price")!=null) {
            params.put("price",Integer.parseInt(request.getParameter("price")));
        }

        if(params.isEmpty()){
            products.getProducts().stream().map(product -> {
                try {
                    return Converter.getInstance().convertToJsonObject(product);
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
            }).forEach(array::put);
        }
        else{
            products.getProducts().stream().map(product->{
                if(isAccepted(product,params)) {
                    try {
                        return Converter.getInstance().convertToJsonObject(product);
                    } catch (JSONException e) {
                        throw new RuntimeException(e);
                    }
                }
                return null;
            }).filter(Objects::nonNull)
                    .forEach(array::put);
        }

        response.setContentType("application/json");
        if(!array.toString().equals("[]")){
            response.getWriter().print(array.toString());
        }
        else response.getWriter().println(Message.DATA_NOT_FOUND);

    }
    private boolean isAccepted(Product product,Map<String,Object> params){
        for (Map.Entry<String, Object> entry : params.entrySet()) {
            String key = entry.getKey();
            Object value = entry.getValue();
            if(key.equals("id")){
                if (!product.getId().equals(value)) {
                    return false;
                }
            }
            if(key.equals("name")){
                if(!product.getName().equals(value)){
                    return false;
                }
            }
            if(key.equals("price")){
                if(!product.getPrice().equals(value)){
                    return false;
                }
            }
        }return true;
    }
    private boolean isNumeric(String str){
        try{
            Integer db = Integer.parseInt(str);
            return true;
        }catch(Exception e){
            return false;
        }
    }
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//        hàm này tích hợp hàm update luôn
        StringBuilder requestBody = new StringBuilder();
        BufferedReader reader = request.getReader();
        String line;
        while ((line = reader.readLine()) != null) {
            requestBody.append(line);
        }
        response.setContentType("application/json");
        try{
            JSONObject object = new JSONObject(requestBody.toString());
            if(object.get("name").equals("null")||object.get("price").equals("null")){
                response.getWriter().println(Message.NAME_PRICE_NOT_NULL);
                return;
            }
            if(!isNumeric(object.get("price").toString())){
                response.getWriter().println(Message.PRICE_IS_NOT_NUMERIC);
                return;
            }
            String name = object.get("name").toString();
            Integer price =Integer.valueOf(object.get("price").toString());

            if(object.get("id").toString().equals("null")){
                //create
                products.getProducts().add(new Product((long) (products.getSize()+1),name,price));
                products.setSize(products.getSize()+1);
                response.getWriter().println(Message.CREATE_SUCCESSFULLY);
                return;
            }
            else{
                //update
                if(!isNumeric(object.get("id").toString())){
                    response.getWriter().println(Message.ID_IS_NOT_NUMERIC);
                    return;
                }

                Long id = Long.valueOf(object.get("id").toString());
                if(remove(id)){
                    products.getProducts().add(new Product(id,name,price));
                    response.getWriter().println(Message.UPDATE_SUCCESSFULLY);
                }
                else{
                    response.getWriter().println(Message.UPDATE_FAILED);
                }
                return;
            }

        }catch(Exception e){
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().println("Dữ liệu không hợp lệ: " + e.getMessage());
        }
    }

    public void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        try{
            response.setContentType("application/json");
            if(request.getParameter("id")==null){
                response.getWriter().println(Message.ID_NOT_NULL);
                return;
            }
            Long id = Long.parseLong(request.getParameter("id"));
            if(remove(id)){
                response.getWriter().println(Message.DELETE_SUCCESSFULLY);
                return;
            }
            response.getWriter().println(Message.DELETE_FAILED);

        }catch(Exception e){
            response.getWriter().println(e.getMessage());
        }
    }
    private boolean remove (Long id){
        if(id>products.getProducts().size()){
            return false;
        }
        for(Product product: products.getProducts()){
            if(product.getId().equals(id)){
                products.getProducts().remove(product);
                return true;
            }
        }
        return false;
    }
}
