package com.example.roleBased.Controller;

import com.example.roleBased.Dto.AddCartItemDto;
import com.example.roleBased.Dto.UpadteCartItem;
import com.example.roleBased.Entity.Cart;
import com.example.roleBased.Entity.CartItem;
import com.example.roleBased.Entity.User;
import com.example.roleBased.Services.CartService;
import com.example.roleBased.Services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/cart")
public class CartController {
    @Autowired
    CartService cartService;
    @Autowired
    public UserService userService;

    @PutMapping("/add")
    public ResponseEntity<?> additemToCart(@RequestBody AddCartItemDto dto,
                                           @RequestHeader("Authorization") String jwt) throws Exception {
        CartItem cartItem = cartService.addCartIem(dto, jwt);
        return new ResponseEntity<>(cartItem, HttpStatus.CREATED);
    }
    @PutMapping("/item-update")
    public ResponseEntity<?> updateCartQuantity(@RequestBody UpadteCartItem dto,
                                           @RequestHeader("Authorization") String jwt) throws Exception {
        CartItem cartItem = cartService.updateCartItemQuantity(dto.getCartitems(),dto.getQuantity());
        return new ResponseEntity<>(cartItem, HttpStatus.OK);
    }
    @PutMapping("/item-delete")
    public ResponseEntity<?> clearitem(
                                           @RequestHeader("Authorization") String jwt) throws Exception {

        Cart cart= cartService.clearCart(jwt);
        return new ResponseEntity<>(cart, HttpStatus.OK);
    }
    @GetMapping("/items")
    public ResponseEntity<?> findUSerCart(
                                           @RequestHeader("Authorization") String jwt) throws Exception {
   User user = userService.finduserbyjwt(jwt);
        Cart cart= cartService.findCartByUser(user.getId());
        return new ResponseEntity<>(cart, HttpStatus.OK);
    }



}
