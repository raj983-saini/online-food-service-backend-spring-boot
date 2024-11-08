package com.example.roleBased.Services;




import com.example.roleBased.Dto.Registerdto;
import com.example.roleBased.Entity.Cart;
import com.example.roleBased.Entity.Role;
import com.example.roleBased.Entity.User;
import com.example.roleBased.Repository.CartRepository;
import com.example.roleBased.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {
@Autowired
    private UserRepository userRepository;

    @Autowired
public AuthenticationManager authenticationManager;

    @Autowired
    public  JwtService jwtService;
    @Autowired
    public UserDetailsService userDetailsService;

    @Autowired
    public CartRepository cartRepository;

@Autowired
private  PasswordEncoder passwordEncoder;

    public void saveNewUser(Registerdto registerdto, Role role) {
        User existingUser = userRepository.findByEmail(registerdto.getEmail());
        if (existingUser != null) {
            throw new RuntimeException("User already exists.");
        }

        User newUser = new User();
        newUser.setEmail(registerdto.getEmail());
        newUser.setPassword(passwordEncoder.encode(registerdto.getPassword()));
        newUser.setFullname(registerdto.getFullname());
//        newUser.setContact(registerdto.getContact());

        // Set role based on input
        newUser.setRole(role);
        userRepository.save(newUser);

        Cart cart = new Cart();
        cart.setCustomer(newUser);
    cartRepository.save(cart);

    }

    public User getUserDetailsByJwt(String token) {
        // Extract email from JWT token
        String email = jwtService.extractEmail(token);

        // Fetch user details using the email
        User user = userRepository.findByEmail(email);

        // If user is found, print their details
        if (user != null) {
            System.out.println("User Details:");
            System.out.println("ID: " + user.getId());
            System.out.println("Username: " + user.getFullname());
            System.out.println("Email: " + user.getEmail());
            System.out.println("Role: " + user.getRole());
            // You can add more fields if your User class has additional details
        } else {
            System.out.println("User not found for the provided token.");
        }

        return user;  // return the user object (or null if not found)
    }

    public  User finduserbyjwt(String authHeader) throws Exception{
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            throw new Exception(("Missing or invalid Authorization header"));
        }

        // Extract token from the Authorization header (remove "Bearer " prefix)
        String token = authHeader.substring(7);
        String email = jwtService.extractEmail(token);
        User user = userRepository.findByEmail(email);

        if (user == null) {
            throw new Exception(("User not found"));
        }
        return user;
    }

//    public List<User> getAll() {
//    }

//    public void saveUser(User user) {
//        userRepository.save(user);
//    }

//    public List<User> getAll() {
//        return userRepository.findByEmail();
//    }

//    public Optional<User> findById(long id) {
//        return userRepository.findById((int) id);
//    }
//
//    public void deleteById(long id) {
//        userRepository.deleteById((int)id);
//    }
//
//    public User findByUserName(String userName) {
//        return userRepository.findByEmail(userName);
//    }
}