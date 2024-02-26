package com.lab03.hibernate.model;


import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
@jakarta.persistence.Entity
@Table(name="Phone")
public class Phone {
    @jakarta.persistence.Id
    private String id;
    private String name;
    private Integer price;
    private String color;
    private String country;
    private Integer quantity;

    @jakarta.persistence.ManyToOne
    @JoinColumn(name = "manufacture_id", referencedColumnName = "id", nullable = false)
    private Manufacture manufacture;

}