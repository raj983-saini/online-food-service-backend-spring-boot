package com.example.roleBased.Controller;

import com.example.roleBased.Dto.Categorydto;
import com.example.roleBased.Entity.Catagaries;
import com.example.roleBased.Entity.Restaurant;
import com.example.roleBased.Entity.User;
import com.example.roleBased.Services.CategoryService;
import com.example.roleBased.Services.RestaurantService;
import com.example.roleBased.Services.UserService;
import jdk.jfr.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/category")
public class CategoryController {

    @Autowired
    public RestaurantService restaurantService ;
    @Autowired
    public UserService userService;
    @Autowired
    public CategoryService categoryService;

    @PostMapping("/create")
    public ResponseEntity<?> createCategory(@RequestBody Categorydto catagaries,
                                            @RequestHeader("Authorization") String jwt) throws Exception {
        User user = userService.finduserbyjwt(jwt);
        Restaurant restaurant =      restaurantService.getResturantByUserId(user.getId());

        Catagaries catagaries1 = categoryService.createCategory(catagaries.getName() ,restaurant );
return  new ResponseEntity<>(catagaries1 , HttpStatus.CREATED);

    };
    @GetMapping("/restaurant")
    public  ResponseEntity<List> getResturantbyCategoryId(@RequestBody Catagaries catagaries,
                                                       @RequestHeader("Authorities") String jwt) throws Exception {
        User user =   userService.finduserbyjwt(jwt);

     List <Catagaries> catagaries1 = categoryService.findCategoryByreturantId(user.getId() );
        return  new ResponseEntity<>(catagaries1 , HttpStatus.OK);
    }
}
