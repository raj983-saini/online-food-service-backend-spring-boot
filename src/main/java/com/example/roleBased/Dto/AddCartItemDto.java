package com.example.roleBased.Dto;

import lombok.Data;

import java.util.List;

@Data
public class AddCartItemDto {
    public Long id;
    public  Long foodId;
    private  int quantity;
    private List<String> Ingerdient;

}
