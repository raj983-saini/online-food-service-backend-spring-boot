package com.example.roleBased.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class IngerdientCatagaries {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  Long id;

    private String  name;

    @ManyToOne
    @JsonIgnore
    private Restaurant restaurant;

    @OneToMany(cascade = CascadeType.ALL , mappedBy = "catagaries" , orphanRemoval = true ,fetch = FetchType.EAGER)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private List<Ingerident> ingeridents = new ArrayList<>();


}
