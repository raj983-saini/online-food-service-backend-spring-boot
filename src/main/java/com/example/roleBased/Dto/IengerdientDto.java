package com.example.roleBased.Dto;

import com.example.roleBased.Entity.IngerdientCatagaries;
import lombok.Data;

import java.util.List;

@Data
public class IengerdientDto {
    private  Long id;
    private  String name;
    private  Long restaurantId;
    private Long ingerdientCatagaries;
}
