package com.java.midtermShopWeb.responses.carts;

import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ListCartResponse {
    private List<CartResponse> cartResponses = new ArrayList<>();
    private int totalPages;
    private int quantity;
}
