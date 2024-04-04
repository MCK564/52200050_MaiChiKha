package com.java.midtermShopWeb.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CartDTO {
    @NotNull(message = "product_id must not be null")
    @Min(value = 1, message = "product_id must be greater than 0")
    @JsonProperty("product_id")
    private Long productId;

    @NotNull(message = "quantity must not be null")
    @Min(value = 1, message = "quantity must be greater than 0")
    private Integer quantity;

}
