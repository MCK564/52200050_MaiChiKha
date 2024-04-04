package com.java.midtermShopWeb.dtos;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Data//toString
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CategoryDTO {

    @NotBlank(message = "Category's name cannot be empty")
    private String name;
}
