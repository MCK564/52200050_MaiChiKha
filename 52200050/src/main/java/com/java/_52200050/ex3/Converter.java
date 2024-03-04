package com.java._52200050.ex3;

import org.json.JSONException;
import org.json.JSONObject;

public class Converter {
    public static Converter getInstance(){
        return new Converter();
    }

    public JSONObject convertToJsonObject(Product product) throws JSONException {
        JSONObject jsonProduct = new JSONObject();
        jsonProduct.put("id", product.getId());
        jsonProduct.put("name", product.getName());
        jsonProduct.put("price", product.getPrice());
        return jsonProduct;
    }
}
