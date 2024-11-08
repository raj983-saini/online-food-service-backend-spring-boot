package com.example.roleBased.Controller;

import com.example.roleBased.Dto.FoodDto;
import com.example.roleBased.Entity.Catagaries;
import com.example.roleBased.Entity.Food;
import com.example.roleBased.Entity.Restaurant;
import com.example.roleBased.Entity.User;
import com.example.roleBased.Repository.FoodRepository;
import com.example.roleBased.Repository.UserRepository;
import com.example.roleBased.Response.Messagerespose;
import com.example.roleBased.Services.CategoryService;
import com.example.roleBased.Services.Foodservice;
import com.example.roleBased.Services.RestaurantService;
import com.example.roleBased.Services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/owner/api/foods")
@PreAuthorize("hasRole('RESTAURANT_OWNER')")
public class RestaurantFoodController {

    @Autowired
    private Foodservice foodservice;

    @Autowired
    private UserService userService;

    @Autowired
    private CategoryService categoryService;
    @Autowired
    private RestaurantService restaurantService;

    @GetMapping("/hi")
    public String hello(){
    return  "okk";
}
    @PostMapping("/food-createfood")
    @PreAuthorize("hasRole('RESTAURANT_OWNER')")
    public ResponseEntity<?> createFood(@RequestBody FoodDto foodDto,
                                        @RequestParam Long restaurantId,
                                        @RequestParam Long catagoriesId,
                                        @RequestHeader("Authorization") String jwt) throws Exception {
        User user;
        try {
            user = userService.finduserbyjwt(jwt);
        } catch (Exception e) {
            return generateResponse("Invalid or expired token", HttpStatus.UNAUTHORIZED);
        }

        Restaurant restaurant = restaurantService.findResturantById(restaurantId);
        if (restaurant == null) {
            return generateResponse("Restaurant not found", HttpStatus.NOT_FOUND);
        }
        Catagaries catagaries = categoryService.findcategoryById(catagoriesId);
  
        Food food = foodservice.createFood(foodDto,catagaries , user, restaurant);
        return new ResponseEntity<>(food, HttpStatus.CREATED);
    }

    private ResponseEntity<String> generateResponse(String message, HttpStatus status) {
        return new ResponseEntity<>(message, status);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('RESTAURANT_OWNER')")
    public ResponseEntity<Messagerespose> deleteFood(@PathVariable Long id,
                                                     @RequestHeader("Authorization") String jwt) throws Exception {
        // Get the user from the JWT token
        User user = userService.finduserbyjwt(jwt);
        System.out.println("User Role: " + user.getRole());
        System.out.println("User ID: " + user.getId());

        // Find the food item to delete
        Food food = foodservice.findByFoodID(id);


        // Log the restaurant owner's ID and the current user's ID
        Long ownerId = food.getRestaurant().getOwner().getId();
        System.out.println("Food Restaurant Owner ID: " + ownerId);
        System.out.println("Current User ID: " + user.getId());

        // Compare user ID with the restaurant owner's ID
        if (!ownerId.equals(user.getId())) {
            throw new AccessDeniedException("You are not authorized to delete this food.");
        }

        // Proceed with deletion
        foodservice.deleteFood(id);
        Messagerespose messagerespose = new Messagerespose();
        messagerespose.setMsg("Delete successful with id " + id);
        return new ResponseEntity<>(messagerespose, HttpStatus.OK);
    }


    @PutMapping("/{id}/status")
    @PreAuthorize("hasRole('RESTAURANT_OWNER')")
    public ResponseEntity<Food> updateFoodReseturantStatus(@PathVariable Long id,
                                                           @RequestHeader("Authorization") String jwt) throws Exception {
        User user = userService.finduserbyjwt(jwt);
        System.out.println("User Role: " + user.getRole());
        System.out.println("User ID: " + user.getId());
        Food food = foodservice.findByFoodID(id);


        // Log the restaurant owner's ID and the current user's ID
        Long ownerId = food.getRestaurant().getOwner().getId();
        System.out.println("Food Restaurant Owner ID: " + ownerId);
        System.out.println("Current User ID: " + user.getId());
        if (!ownerId.equals(user.getId())) {
            throw new AccessDeniedException("You are not authorized to delete this food.");
        }

        food = foodservice.updateFoodAvalibityStatus(id);
        return new ResponseEntity<>(food, HttpStatus.OK);
    }
}
