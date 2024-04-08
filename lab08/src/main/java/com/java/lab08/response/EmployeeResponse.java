package com.java.lab08.response;

import com.java.lab08.models.Employee;
import lombok.*;

@Data
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeResponse {
    private Long id;
    private String name;
    private String email;
    private String address;
    private String phone;
    private String message;

    public static EmployeeResponse fromEmployee(Employee e){
        return EmployeeResponse.builder()
                .id(e.getId())
                .address(e.getAddress())
                .name(e.getName())
                .phone(e.getPhone())
                .email(e.getEmail())
                .build();
    }

}
