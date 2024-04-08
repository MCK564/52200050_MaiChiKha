package com.java.lab08.response;

import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Data
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ListEmployeeResponse {
    private List<EmployeeResponse> employeeResponses = new ArrayList<>();
    private int totalPages;
    private String message;
}
