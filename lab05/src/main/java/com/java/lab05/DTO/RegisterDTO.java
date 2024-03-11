package com.java.lab05.DTO;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotEmpty;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
@Builder
public class RegisterDTO {
    @NotEmpty(message = "fullName Of product must not be null")
    @JsonProperty("full_name")
    private String fullName;

    @NotEmpty(message = "email Of product must not be null")
    private String email;

    @NotEmpty(message = "password Of product must not be null")
    private String password;

    @NotEmpty(message = "ConfirmPassword Of product must not be null")
    @JsonProperty("confirm_password")
    private String ConfirmPassword;

}
