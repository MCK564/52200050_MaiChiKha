package com.java.midtermShopWeb.responses.user;


import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class isAdminResponse {
    private boolean isAdmin;
    private int quantity;


}
