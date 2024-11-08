package com.example.roleBased.Dto;

import lombok.Data;

import java.util.List;

@Data
public class AddCartItemDto {
    public Long id;
    public  Long foodId;
   public Long cart;
    private  int quantit;
    private List<String> Ingerdient;

    private long totalPrize;
}
