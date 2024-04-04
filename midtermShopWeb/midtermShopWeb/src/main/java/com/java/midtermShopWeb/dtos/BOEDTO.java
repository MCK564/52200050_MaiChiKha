package com.java.midtermShopWeb.dtos;


import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@NotBlank
public class BOEDTO {
    private Long id;
    private Boolean active;
}
