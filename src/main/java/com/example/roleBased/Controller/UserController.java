package com.example.roleBased.Controller;

import com.example.roleBased.Dto.Registerdto;
import com.example.roleBased.Entity.User;
import com.example.roleBased.Services.JwtService;
import com.example.roleBased.Services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/User")
public class UserController {


    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }
    @CrossOrigin(origins = "http://localhost:5173", allowCredentials = "true")
    @GetMapping("/profile")
    public ResponseEntity<?> getUserDetails(@RequestHeader("Authorization") String authHeader) {
        // Check if the Authorization header contains the Bearer token
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Missing or invalid Authorization header");
        }

        // Extract token from the Authorization header (remove "Bearer " prefix)
        String token = authHeader.substring(7);

        // Fetch user details using the token
        User user = userService.getUserDetailsByJwt(token);

        if (user == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
        }
        return ResponseEntity.ok().build();
    }




}
