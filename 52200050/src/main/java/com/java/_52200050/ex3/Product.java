package com.java._52200050.ex3;


import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.json.JSONException;
import org.json.JSONObject;

@Getter
@Setter
@Data
@Builder
public class Product {
    private Long id;
    private String name;
    private Integer price;
}
