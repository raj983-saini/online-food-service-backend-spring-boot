package com.example.roleBased.Controller;

import com.example.roleBased.Dto.RestaurantDto;
import com.example.roleBased.Dto.ResturantDetailDto;
import com.example.roleBased.Entity.Restaurant;
import com.example.roleBased.Entity.User;
import com.example.roleBased.Services.RestaurantService;
import com.example.roleBased.Services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/restaurant")
public class ResturantController {
    @Autowired
    public RestaurantService restaurantService;
    @Autowired
    public UserService userService  ;

    @GetMapping("/search")
    public ResponseEntity<List> findResturantByuserId(
                                                            @RequestHeader("Authorization") String  jwt ,
                                                            @RequestParam String keyword) throws Exception {
        User user = userService.finduserbyjwt(jwt);
       List< Restaurant> restaurant =      restaurantService.searchByKeyword(keyword);
        return  new ResponseEntity<>(restaurant  , HttpStatus.OK);
    }
    @GetMapping()
    public ResponseEntity<List> getAllResturant(
                                                      @RequestHeader("Authorization") String  jwt
                                                     ) throws Exception {
        User user = userService.finduserbyjwt(jwt);
        List< Restaurant> restaurant =      restaurantService.getAllResturant();
        return  new ResponseEntity<>(restaurant, HttpStatus.OK);
    }
    @GetMapping("/{id}")
    public ResponseEntity<Restaurant> findresturantById(
                                                      @RequestHeader("Authorization") String  jwt ,
                                                      @PathVariable Long id) throws Exception {
        User user = userService.finduserbyjwt(jwt);
        Restaurant restaurant =      restaurantService.findResturantById(id);
        return  new ResponseEntity<>(restaurant, HttpStatus.OK);
    }

    @PutMapping("/{id}/add-favurate")
    public ResponseEntity<RestaurantDto> addFavurate(
                                                      @RequestHeader("Authorization") String  jwt ,
                                                      @PathVariable Long id) throws Exception {
        User user = userService.finduserbyjwt(jwt);
        RestaurantDto restaurant =      restaurantService.addFavurate(id,user);
        return  new ResponseEntity<>(restaurant, HttpStatus.OK);
    }
}
