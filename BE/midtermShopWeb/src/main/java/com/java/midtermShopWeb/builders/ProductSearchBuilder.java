package com.java.midtermShopWeb.builders;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
@Builder
public class ProductSearchBuilder {
    private String name;
    private String description;
    private String keyword;
    private Long categoryId;
    private Double priceFrom;
    private Double priceTo;
    private String order;
    private String sortBy;
    private Integer page;
    private Integer limit;
}
