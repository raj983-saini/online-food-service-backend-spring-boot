package com.example.roleBased.Services;

import com.example.roleBased.Dto.IengerdientDto;
import com.example.roleBased.Dto.IngerdientCategoryDto;
import com.example.roleBased.Entity.IngerdientCatagaries;
import com.example.roleBased.Entity.Ingerident;
import com.example.roleBased.Entity.User;

import java.util.List;


public interface IngerdientService {

    public IngerdientCatagaries createIngerdientCategory(IngerdientCategoryDto dto) throws  Exception;
    public  IngerdientCatagaries findIngerdientCatagariesById(Long id) throws  Exception;
    public List<IngerdientCatagaries> findIngerdientCatagariesByRestaurantId(Long restaurantId )throws  Exception;
    public Ingerident createIngerdientItem(IengerdientDto dto )throws  Exception;
    public List<Ingerident> findRestaurantIngerident(Long restaurantId) throws Exception;
    public Ingerident updateStock(Long id)throws  Exception;

}
