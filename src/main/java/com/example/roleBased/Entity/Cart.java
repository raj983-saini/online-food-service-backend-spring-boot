package com.example.roleBased.Entity;

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
public class Cart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private  long total;

    @OneToOne
    private  User customer;
    @OneToMany(mappedBy = "cart" ,cascade = CascadeType.ALL ,orphanRemoval = true , fetch = FetchType.EAGER)
            private List<CartItem> items  =  new ArrayList<>();
}
