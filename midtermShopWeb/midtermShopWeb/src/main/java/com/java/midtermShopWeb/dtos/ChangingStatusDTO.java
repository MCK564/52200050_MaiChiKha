package com.java.midtermShopWeb.dtos;

import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ChangingStatusDTO {
    private Long id;
    private String status;
}
