package com.example.roleBased.Controller;


import com.example.roleBased.Dto.Registerdto;
import com.example.roleBased.Entity.Role;
import com.example.roleBased.Services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private UserService userService;


//    @GetMapping("/all-users")
//    public ResponseEntity<?> getAllUsers() {
//        List<User> all = userService.getAll();
//        if (all != null && !all.isEmpty()) {
//            return new ResponseEntity<>(all, HttpStatus.OK);
//        }
//        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//    }

    @PostMapping("/create-admin-user")
    public void createUser(@RequestBody Registerdto user) {
        userService.saveNewUser(user, Role.ADMIN);
    }
@GetMapping("/hello")
public String healthCheck() {
    return "Ok";
}

}