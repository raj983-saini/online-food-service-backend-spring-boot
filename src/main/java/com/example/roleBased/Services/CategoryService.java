package com.example.roleBased.Services;

import com.example.roleBased.Entity.Catagaries;
import com.example.roleBased.Entity.Restaurant;

import java.util.List;

public interface CategoryService {
    Catagaries createCategory(String name, Restaurant userId) throws Exception;
    List <Catagaries> findCategoryByreturantId(Long id)throws  Exception ;
    Catagaries findcategoryById(Long id)throws  Exception;

}
