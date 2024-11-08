package com.example.roleBased.Controller;

import com.example.roleBased.Dto.ResturantDetailDto;
import com.example.roleBased.Entity.Restaurant;
import com.example.roleBased.Entity.User;
import com.example.roleBased.Response.Messagerespose;
import com.example.roleBased.Services.RestaurantService;
import com.example.roleBased.Services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/owner")
public class ResturantOwnerController {
    @Autowired
    public RestaurantService restaurantService;
@Autowired
    public UserService userService  ;


@PostMapping("/create-restaurant")
    public ResponseEntity<Restaurant> createResturant(@RequestBody ResturantDetailDto detailDto , @RequestHeader("Authorization") String  jwt) throws Exception {
    User user = userService.finduserbyjwt(jwt);
 Restaurant restaurant =      restaurantService.createRestaurant(detailDto,user);
 return  new ResponseEntity<>(restaurant, HttpStatus.CREATED);
}
    @PutMapping("/{id}")
    public ResponseEntity<Restaurant> updateResturant(@RequestBody ResturantDetailDto detailDto ,
                                                      @RequestHeader("Authorization") String  jwt ,
                                                      @PathVariable Long id) throws Exception {
        User user = userService.finduserbyjwt(jwt);
        Restaurant restaurant =      restaurantService.updateRestaurant(id,detailDto);

        return  new ResponseEntity<>(restaurant, HttpStatus.OK);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Messagerespose> deleteResturant(
                                                      @RequestHeader("Authorization") String  jwt ,
                                                      @PathVariable Long id) throws Exception {
        User user = userService.finduserbyjwt(jwt);
              restaurantService.deleteResturant(id);
        Messagerespose mss = new Messagerespose();
        mss.setMsg("delete sucess");
        return  new ResponseEntity<>(mss, HttpStatus.OK);
    }
    @PutMapping("/{id}/status")
    public ResponseEntity<Restaurant> updateResturantStatus(
                                                      @RequestHeader("Authorization") String  jwt ,
                                                      @PathVariable Long id) throws Exception {
        User user = userService.finduserbyjwt(jwt);
        Restaurant restaurant =      restaurantService.updateResturantStatus(id);
        return  new ResponseEntity<>(restaurant, HttpStatus.OK);
    }
    @GetMapping("/user")
    public ResponseEntity<Restaurant> findResturantByuserId(
                                                            @RequestHeader("Authorization") String  jwt
                                                       ) throws Exception {
        User user = userService.finduserbyjwt(jwt);
        Restaurant restaurant =      restaurantService.getResturantByUserId(user.getId());
        return  new ResponseEntity<>(restaurant, HttpStatus.OK);
    }
}


