package com.java.lab05.DTO;

import jakarta.validation.constraints.NotEmpty;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
@Builder
public class ProductDTO {
    private Long id;

    @NotEmpty(message = "name Of product must not be null")
    private String name;

    @NotEmpty(message = "product's price must not be null")
    private Integer price;
}
