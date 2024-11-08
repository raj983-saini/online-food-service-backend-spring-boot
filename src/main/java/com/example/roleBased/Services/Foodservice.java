package com.example.roleBased.Services;

import com.example.roleBased.Dto.FoodDto;
import com.example.roleBased.Entity.Catagaries;
import com.example.roleBased.Entity.Food;
import com.example.roleBased.Entity.Restaurant;
import com.example.roleBased.Entity.User;

import java.util.List;

public interface Foodservice {

Food createFood(FoodDto dto , Catagaries catagaries , User user, Restaurant restaurant);
void deleteFood(Long id) throws Exception;
List<Food> getResturantsFoods(Long Resturantid,
                              boolean isVegiterian,
                              boolean isNonVeg,
                              boolean isSeasonal,
                              String foodCatagrie);

List<Food> searchFood(String keyword);
Food findByFoodID(Long foodId) throws Exception;
Food updateFoodAvalibityStatus(Long FoodId) throws Exception;


}
