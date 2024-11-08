package com.example.roleBased.Services;

import com.example.roleBased.Dto.FoodDto;
import com.example.roleBased.Entity.Catagaries;
import com.example.roleBased.Entity.Food;
import com.example.roleBased.Entity.Restaurant;
import com.example.roleBased.Entity.User;
import com.example.roleBased.Repository.FoodRepository;
import com.example.roleBased.Repository.ResturantRepository;
import com.example.roleBased.Repository.UserRepository;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class FoodServiceImpl implements Foodservice {

    @Autowired
    private FoodRepository foodRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ResturantRepository resturantRepository;

    public class FoodNotFoundException extends RuntimeException {
        public FoodNotFoundException(String message) {
            super(message);
        }
    }

    @Override
    @Transactional
    public Food createFood(FoodDto dto, Catagaries catagaries, User user, Restaurant restaurant) {
        if (dto == null || restaurant == null) {
            throw new IllegalArgumentException("FoodDto or Restaurant cannot be null");
        }
        Hibernate.initialize(user.getOrders());
        Hibernate.initialize(restaurant.getOrders());
        Hibernate.initialize(restaurant.getImage());
        Food food = new Food();
        food.setRestaurant(restaurant);
        food.setFavurateCatagries(catagaries);
        food.setName(dto.getName());
        food.setDescription(dto.getDescription());
        food.setCreateDate(LocalDateTime.now());
        food.setImage(dto.getImage());
        food.setIngredients(dto.getIngredients()); // Fixed spelling
        food.setPrize(dto.getPrize());
        food.setSeasonal(dto.isSeasonal()); // Fixed spelling
        food.setVegetarian(dto.isVegetarian()); // Fixed spelling
        food.setAvailable(true); // Default availability can be set here

        return foodRepository.save(food);
    }

    @Override
    public void deleteFood(Long id) throws Exception {
        Food food = findByFoodID(id);
        food.setRestaurant(null);
        foodRepository.save(food);
    }

    @Override
    public List<Food> getResturantsFoods(Long restaurantId, boolean isVegetarian, boolean isNonVeg, boolean isSeasonal, String foodCatagrie) {
        List<Food> foods = foodRepository.findByRestaurantId(restaurantId);

        if (isVegetarian) {
            foods = filterByVegetarian(foods, isVegetarian);
        }
        if (isNonVeg) {
            foods = filterByNonVegetarian(foods, isNonVeg);
        }
        if (isSeasonal) {
            foods = filterBySeasonal(foods, isSeasonal);
        }
        if (foodCatagrie != null && !foodCatagrie.isEmpty()) {
            foods = filterByCategories(foods, foodCatagrie);
        }
        return foods;
    }

    private List<Food> filterByCategories(List<Food> foods, String foodCatagrie) {
        return foods.stream().filter(food -> {
            if (food.getFavurateCatagries() != null) {
                return food.getFavurateCatagries().getName().equals(foodCatagrie);
            }
            return false;
        }).collect(Collectors.toList());
    }

    private List<Food> filterBySeasonal(List<Food> foods, boolean isSeasonal) {
        return foods.stream().filter(food -> food.isSeasonal() == isSeasonal).collect(Collectors.toList());
    }

    private List<Food> filterByNonVegetarian(List<Food> foods, boolean isNonVeg) {
        return foods.stream().filter(food -> !food.isVegetarian()).collect(Collectors.toList());
    }

    private List<Food> filterByVegetarian(List<Food> foods, boolean isVegetarian) {
        return foods.stream().filter(food -> food.isVegetarian() == isVegetarian).collect(Collectors.toList());
    }

    @Override
    public List<Food> searchFood(String keyword) {
        return foodRepository.searchFood(keyword);
    }

    @Override
    public Food findByFoodID(Long foodId) throws Exception {
        Optional<Food> opt = foodRepository.findById(foodId);

        if (opt.isEmpty()) {
            throw new Exception("Food does not exist");
        }
        return opt.get();
    }

    @Override
    @Transactional
    public Food updateFoodAvalibityStatus(Long foodId) throws Exception {

        Food food = findByFoodID(foodId);
        Hibernate.initialize(food.getImage());
        food.setAvailable(!food.isAvailable());
        return foodRepository.save(food);
    }


}
