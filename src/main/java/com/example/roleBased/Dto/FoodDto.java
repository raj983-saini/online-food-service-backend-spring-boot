package com.example.roleBased.Dto;

import com.example.roleBased.Entity.Catagaries;
import com.example.roleBased.Entity.Ingerident;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FoodDto {
    private Long id;
    private String name;
    private String description;
    private long prize;
    private Long favurateCatagriesId; // Change to ID for better handling
    private List<String> image;
    private boolean available; // Fixed spelling
    private Long restaurantId; // Fixed spelling
    private boolean isVegetarian; // Fixed spelling
    private boolean isSeasonal; // Fixed spelling
    private List<Ingerident> ingredients; // Fixed spelling
}
