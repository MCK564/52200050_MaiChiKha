package com.lab020.models;

import lombok.*;

@Getter
@Data
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class student {
    private Long id;
    private String sex;
    private int age;
    private double average;
}
