package com.java.lab07;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name="student")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String email;
    private Integer age;
    private Double ielts;

}
