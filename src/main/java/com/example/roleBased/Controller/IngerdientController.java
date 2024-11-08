package com.example.roleBased.Controller;

import com.example.roleBased.Dto.IengerdientDto;
import com.example.roleBased.Dto.IngerdientCategoryDto;
import com.example.roleBased.Entity.IngerdientCatagaries;
import com.example.roleBased.Entity.Ingerident;
import com.example.roleBased.Entity.User;
import com.example.roleBased.Services.IngerdientService;
import com.example.roleBased.Services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/iengerdient")
public class IngerdientController {
    @Autowired
    public IngerdientService ingerdientService;
    @Autowired
    public UserService userService;
    @PostMapping("/create")
    public ResponseEntity<?> createiengerdient(@RequestBody IengerdientDto dto,
                                               @RequestHeader("Authorization") String jwt
    ) throws Exception {
        User user = userService.finduserbyjwt(jwt);
        if (user == null){
            throw  new Exception("jwt is not correct");
        }
        Ingerident ingerident = ingerdientService.createIngerdientItem(dto);
        return  new ResponseEntity<>(ingerident , HttpStatus.CREATED);
    }


    @PostMapping("/create-category")
    public ResponseEntity<?> createiengerdientCategory(@RequestBody IngerdientCategoryDto dto,
                                                                                 @RequestHeader("Authorization") String jwt
                                               ) throws Exception {
        User user = userService.finduserbyjwt(jwt);
        if (user == null){
            throw  new Exception("jwt is not correct");
        }
        IngerdientCatagaries ingerident = ingerdientService.createIngerdientCategory(dto);
        return  new ResponseEntity<>(ingerident , HttpStatus.CREATED);
    }
    @GetMapping("/category/{id}")
    public ResponseEntity<?> getIngerdientCategory(@PathVariable Long id,
                                                                                 @RequestHeader("Authorization") String jwt
                                               ) throws Exception {
        User user = userService.finduserbyjwt(jwt);
        if (user == null){
            throw  new Exception("jwt is not correct");
        }
        IngerdientCatagaries ingerident = ingerdientService.findIngerdientCatagariesById(id);
        return  new ResponseEntity<>(ingerident , HttpStatus.OK);
    }
    @GetMapping("/restaurant/category/{id}")
    public ResponseEntity<?> getbyrestaurantId(@PathVariable Long id,
                                                                                 @RequestHeader("Authorization") String jwt
                                               ) throws Exception {
        User user = userService.finduserbyjwt(jwt);
        if (user == null){
            throw  new Exception("jwt is not correct");
        }
        List<IngerdientCatagaries> ingerident = ingerdientService.findIngerdientCatagariesByRestaurantId(id);
        return  new ResponseEntity<>(ingerident , HttpStatus.OK);
    }
    @GetMapping("/{id}")
    public ResponseEntity<?> getIngerdient(@PathVariable Long id,
                                                   @RequestHeader("Authorization") String jwt
    ) throws Exception {
        User user = userService.finduserbyjwt(jwt);
        if (user == null){
            throw  new Exception("jwt is not correct");
        }
        Ingerident ingerident = ingerdientService.updateStock(id);
        return  new ResponseEntity<>(ingerident , HttpStatus.OK);
    }
    @GetMapping("/restaurant/{id}")
    public ResponseEntity<?> getIngerdientrestaurant(@PathVariable Long id,
                                                   @RequestHeader("Authorization") String jwt
    ) throws Exception {
        User user = userService.finduserbyjwt(jwt);
        if (user == null){
            throw  new Exception("jwt is not correct");
        }
        List<Ingerident> ingerident = ingerdientService.findRestaurantIngerident(id);
        return  new ResponseEntity<>(ingerident , HttpStatus.OK);
    }

}
