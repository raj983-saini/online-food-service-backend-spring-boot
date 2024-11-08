package com.example.roleBased.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Food {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String description;

    private long prize;

    @ManyToOne
    @JoinColumn(name = "catagaries_id") // Foreign key to Catagaries
    private Catagaries favurateCatagries;

    @ElementCollection(fetch = FetchType.EAGER)
    private List<String> image = new ArrayList<>();

    private boolean available; // Fixed spelling

    @ManyToOne
    @JoinColumn(name = "restaurant_id") // Foreign key to Restaurant
    private Restaurant restaurant;

    private boolean isVegetarian; // Fixed spelling

    private boolean isSeasonal; // Fixed spelling

    @ManyToMany(fetch = FetchType.EAGER)
    private List<Ingerident> ingredients = new ArrayList<>(); // Fixed spelling

    private LocalDateTime createDate;
}
