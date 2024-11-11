package com.example.roleBased.Controller;



import com.example.roleBased.Dto.Registerdto;
import com.example.roleBased.Dto.logindto;
import com.example.roleBased.Entity.Role;
import com.example.roleBased.Entity.User;
import com.example.roleBased.Services.JwtService;
import com.example.roleBased.Services.UserDetailsServiceImpl;
import com.example.roleBased.Services.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/public")
@Slf4j
public class PublicController {

    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private UserDetailsServiceImpl userDetailsService;
    @Autowired
    private UserService userService;

    @Autowired
    private JwtService jwtUtil;


    @GetMapping("/health-check")    // FOR  text Api
    public String healthCheck() {
        return "Ok";
    }

    @PostMapping("/signup")
    public String signup(@RequestBody Registerdto user) {
        if (user.getRole() == Role.USER) {
            userService.saveNewUser(user, Role.USER);
        }
        else {
            userService.saveNewUser(user, Role.RESTAURANT_OWNER);
        }
        return  "Succsfully Singup";
    }

    @PostMapping("/login")
    public ResponseEntity<Map<String, String>> login(@RequestBody logindto user) {
        try {
            // Authenticate the user with username and password
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(user.getEmail(), user.getPassword()));

            // Load user details by email
            UserDetails userDetails = userDetailsService.loadUserByUsername(user.getEmail());

            // Generate the JWT token
            String jwt = jwtUtil.generateToken(userDetails);

            // Prepare the response with JWT and user details
            Map<String, String> response = new HashMap<>();
            response.put("Jwt", jwt);  // Add the JWT token to the response
            response.put("email", userDetails.getUsername());  // Add user email to the response
            response.put("fullName", ((User) userDetails).getFullname()); // Assuming fullName is available

            // Return the response with the JWT and user details
            return new ResponseEntity<>(response, HttpStatus.OK);

        } catch (BadCredentialsException e) {
            // Handle bad credentials explicitly and return a Map
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("error", "Incorrect username or password");
            return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            // Handle other exceptions and return a Map
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("error", "Error occurred during login");
            return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }



}


