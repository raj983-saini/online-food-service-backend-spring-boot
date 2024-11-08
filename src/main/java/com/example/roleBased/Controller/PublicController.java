package com.example.roleBased.Controller;



import com.example.roleBased.Dto.Registerdto;
import com.example.roleBased.Dto.logindto;
import com.example.roleBased.Entity.Role;
import com.example.roleBased.Services.JwtService;
import com.example.roleBased.Services.UserDetailsServiceImpl;
import com.example.roleBased.Services.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<String> login(@RequestBody logindto user) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(user.getEmail(), user.getPassword()));
            UserDetails userDetails = userDetailsService.loadUserByUsername(user.getEmail());
            String jwt = jwtUtil.generateToken(userDetails);  // Pass userDetails, not just username
            return new ResponseEntity<>( "Jwt Token :        " + jwt, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Incorrect username or password", HttpStatus.BAD_REQUEST);
        }
    }

}