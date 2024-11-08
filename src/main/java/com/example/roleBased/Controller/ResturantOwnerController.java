package com.example.roleBased.Controller;

import com.example.roleBased.Dto.ResturantDetailDto;
import com.example.roleBased.Entity.Restaurant;
import com.example.roleBased.Entity.User;
import com.example.roleBased.Repository.IngerdientRepository;
import com.example.roleBased.Repository.ResturantRepository;
import com.example.roleBased.Response.Messagerespose;
import com.example.roleBased.Services.RestaurantService;
import com.example.roleBased.Services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import static com.example.roleBased.Entity.Role.RESTAURANT_OWNER;

@RestController
@RequestMapping("/owner")
public class ResturantOwnerController {
    @Autowired
    public RestaurantService restaurantService;

    @Autowired
    private IngerdientRepository ingredientRepository;
    @Autowired
    public ResturantRepository resturantRepository;
@Autowired
    public UserService userService  ;


@PostMapping("/create-restaurant")
    public ResponseEntity<?> createResturant(@RequestBody ResturantDetailDto detailDto , @RequestHeader("Authorization") String  jwt) throws Exception {
    User user = userService.finduserbyjwt(jwt);
 Restaurant restaurant =      restaurantService.createRestaurant(detailDto,user);
 return  new ResponseEntity<>(restaurant, HttpStatus.CREATED);
}
    @PutMapping("/{id}")
    public ResponseEntity<?> updateResturant(@RequestBody ResturantDetailDto detailDto ,
                                                      @RequestHeader("Authorization") String  jwt ,
                                                      @PathVariable Long id) throws Exception {
        User user = userService.finduserbyjwt(jwt);
        Restaurant restaurant = resturantRepository.findResturantById(id);
        if ( user.getRole() == RESTAURANT_OWNER &&user.getId() != restaurant.getOwner().getId()){
            Messagerespose mss = new Messagerespose();
            mss.setMsg("You are not own this restaurant");
            return  new ResponseEntity<>(mss, HttpStatus.OK);

        }
        Restaurant restaurant1 =      restaurantService.updateRestaurant(id,detailDto);

        return  new ResponseEntity<>(restaurant1, HttpStatus.OK);
    }
    @Transactional
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Messagerespose> deleteResturant(
                                                      @RequestHeader("Authorization") String  jwt ,
                                                      @PathVariable Long id) throws Exception {
        User user = userService.finduserbyjwt(jwt);
        Messagerespose mss = new Messagerespose();
        Restaurant restaurant = resturantRepository.findResturantById(id);
        if ( user.getRole() == RESTAURANT_OWNER &&user.getId() != restaurant.getOwner().getId()){

            mss.setMsg("You are not own this restaurant");
            return  new ResponseEntity<>(mss, HttpStatus.OK);

        }
        ingredientRepository.deleteByRestaurantId(id);
              restaurantService.deleteResturant(id);
        mss.setMsg("delete sucess");
        return  new ResponseEntity<>(mss, HttpStatus.OK);
    }
    @PutMapping("/{id}/status")
    public ResponseEntity<?> updateResturantStatus(
                                                      @RequestHeader("Authorization") String  jwt ,
                                                      @PathVariable Long id) throws Exception {
        User user = userService.finduserbyjwt(jwt);
        Restaurant restaurant = resturantRepository.findResturantById(id);
        if ( user.getRole() == RESTAURANT_OWNER &&user.getId() != restaurant.getOwner().getId()){
            Messagerespose mss = new Messagerespose();
            mss.setMsg("You are not own this restaurant");
            return  new ResponseEntity<>(mss, HttpStatus.OK);

        }

        Restaurant restaurant1 =      restaurantService.updateResturantStatus(id,user);
        return  new ResponseEntity<>(restaurant1, HttpStatus.OK);
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


