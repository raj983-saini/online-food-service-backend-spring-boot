package com.example.roleBased.Dto;

import com.example.roleBased.Entity.Ingerident;
import lombok.Data;

import java.util.List;

@Data
public class IngerdientCategoryDto {

    String name;
    Long restaurantId;
    List<Ingerident> ingeridents;
}
