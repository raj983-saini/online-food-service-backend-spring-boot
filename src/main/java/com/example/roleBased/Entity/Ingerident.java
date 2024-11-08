package com.example.roleBased.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Ingerident {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private  String name;

    @ManyToOne
    private  IngerdientCatagaries catagaries;

    @ManyToOne
    @JsonIgnore
    private  Restaurant restaurant;

   private  boolean inStoke;

}
