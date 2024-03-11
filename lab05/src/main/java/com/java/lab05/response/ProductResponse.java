package com.java.lab05.response;

import com.java.lab05.model.Product;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Data
@Getter
@Setter
@Builder
public class ProductResponse {
    private List<Product> products = new ArrayList<>();
    private Long totalItem;
}
