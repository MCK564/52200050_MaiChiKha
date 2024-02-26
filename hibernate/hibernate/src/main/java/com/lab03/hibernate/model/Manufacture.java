package com.lab03.hibernate.model;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@jakarta.persistence.Entity
@Table(name="Manufacture")
public class Manufacture {
    @jakarta.persistence.Id
    private String id;
    private String name;
    private String location;
    private Integer employee;

    @jakarta.persistence.OneToMany(mappedBy = "manufacture",cascade = jakarta.persistence.CascadeType.ALL)
    private List<Phone> phones = new ArrayList<>();

    public String toString(){
        return "Manufacture: "+id+"-"+name+"-"+location+"-"+employee;
    }
}
