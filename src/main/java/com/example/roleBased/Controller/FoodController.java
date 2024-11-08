package com.example.roleBased.Controller;

import com.example.roleBased.Entity.Food;
import com.example.roleBased.Entity.User;
import com.example.roleBased.Services.Foodservice;
import com.example.roleBased.Services.RestaurantService;
import com.example.roleBased.Services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/food")
public class FoodController {

    @Autowired
    public Foodservice foodservice;
    @Autowired
    public UserService userService;

    @Autowired
    public RestaurantService restaurantService;

    @GetMapping("/search")
    public ResponseEntity<List<Food>> searchFood(@RequestParam String keyboard,
                                                     @RequestHeader("Authorization") String jwt) throws Exception {
        User user = userService.finduserbyjwt(jwt);
   List < Food >food =   foodservice.searchFood(keyboard);

        return  new ResponseEntity<>(food , HttpStatus.OK);
    }
    @GetMapping("/restaurant/{id}")
    public ResponseEntity<List<Food>> getResturantFood(@RequestParam boolean vegetarian,
                                                           @PathVariable Long id,
                                                           @RequestParam boolean seasonal,
                                                           @RequestParam boolean nonVeg,
                                                           @RequestParam (required = false )String food_category,
                                                           @RequestHeader("Authorization") String jwt) throws Exception {
        User user = userService.finduserbyjwt(jwt);
        List < Food >food =   foodservice.getResturantsFoods(id,vegetarian,nonVeg,seasonal,food_category);

        return  new ResponseEntity<>(food , HttpStatus.OK);
    }
}
