package com.java.lab05.DTO;

import jakarta.validation.constraints.NotEmpty;
import jdk.jfr.DataAmount;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
@Builder
public class LoginDTO {
    @NotEmpty(message = "Username must not be null")
    private String username;

    @NotEmpty(message = "password must not be null")
    private String password;
}
