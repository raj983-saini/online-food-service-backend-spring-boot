package com.example.roleBased.Dto;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Data
@Embeddable
public class RestaurantDto {
    private Long id;
    private String name;
    private String description;
    private boolean open;
    private List<String> image;
}

