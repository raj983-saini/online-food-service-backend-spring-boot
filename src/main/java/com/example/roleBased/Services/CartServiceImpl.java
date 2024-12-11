package com.example.roleBased.Services;

import com.example.roleBased.Dto.AddCartItemDto;
import com.example.roleBased.Entity.Cart;
import com.example.roleBased.Entity.CartItem;
import com.example.roleBased.Entity.Food;
import com.example.roleBased.Entity.User;
import com.example.roleBased.Repository.CartItemrepository;
import com.example.roleBased.Repository.CartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class CartServiceImpl implements CartService {

    @Autowired
    public UserService userService;

    @Autowired
    public Foodservice foodservice;

    @Autowired
    public CartRepository cartRepository;

    @Autowired
    public CartItemrepository cartItemrepository;

    @Override
    public CartItem addCartIem(AddCartItemDto dto, String jwt) throws Exception {
        User user = userService.finduserbyjwt(jwt);
        Food food = foodservice.findByFoodID(dto.getFoodId());
        Cart cart = cartRepository.findByCustomerId(user.getId());

        // Check if item already exists in the cart
        for (CartItem cartItem : cart.getItems()) {
            if (cartItem.getFood().equals(food)) {
                int newQuantity = cartItem.getQuantit() + dto.getQuantit();
                CartItem updatedItem = updateCartItemQuantity(cartItem.getId(), newQuantity);

                // Recalculate cart total after updating an existing item
                cart.setTotal(calculateCartTotal(cart));
                cartRepository.save(cart);

                return updatedItem;
            }
        }

        // If the item is not already in the cart, create a new CartItem
        CartItem cartItem = new CartItem();
        cartItem.setCart(cart);
        cartItem.setFood(food);
        cartItem.setQuantit(dto.getQuantit());
        cartItem.setIngerdient(dto.getIngerdient());
        cartItem.setTotalPrize(food.getPrize() * dto.getQuantit()); // Correct item total

        // Save the new CartItem and add it to the cart
        CartItem cartItem1 = cartItemrepository.save(cartItem);
        cart.getItems().add(cartItem1);

        // Recalculate cart total after adding a new item
        cart.setTotal(calculateCartTotal(cart));
        cartRepository.save(cart);

        return cartItem1;
    }

    @Override
    public CartItem updateCartItemQuantity(Long cartitemId, int quantity) throws Exception {
        Optional<CartItem> cartItemOptional = cartItemrepository.findById(cartitemId);

        if (cartItemOptional.isEmpty()) {
            throw new Exception("Cart item not found for ID: " + cartitemId);
        }

        CartItem cartItem = cartItemOptional.get();
        cartItem.setQuantit(quantity);

        // Update total price for the cart item
        cartItem.setTotalPrize(cartItem.getFood().getPrize() * quantity);

        return cartItemrepository.save(cartItem);
    }

    @Transactional
    @Override
    public Cart removealItemFromCart(long cartitemID, String jwt) throws Exception {
        User user = userService.finduserbyjwt(jwt);
        Cart cart = cartRepository.findByCustomerId(user.getId());

        if (cart == null) {
            throw new Exception("Cart not found for user ID: " + user.getId());
        }

        Optional<CartItem> cartItemOptional = cartItemrepository.findById(cartitemID);
        if (cartItemOptional.isEmpty()) {
            throw new Exception("Cart item not found for ID: " + cartitemID);
        }

        CartItem item = cartItemOptional.get();
        boolean removed = cart.getItems().remove(item);

        if (removed) {
            cartItemrepository.delete(item); // Delete item from the database

            // Recalculate cart total
            Long newTotal = calculateCartTotal(cart);
            cart.setTotal(newTotal);
            cartRepository.save(cart); // Persist the updated total
        } else {
            throw new Exception("Failed to remove item from the cart.");
        }

        return cart;
    }

    @Override
    public Long calculateCartTotal(Cart cart) throws Exception {
        Long total = 0L;
        for (CartItem cartItem : cart.getItems()) {
            total += cartItem.getFood().getPrize() * cartItem.getQuantit(); // Correct calculation
        }
        return total;
    }

    @Override
    public Cart findCartById(Long id) throws Exception {
        Optional<Cart> cartOptional = cartRepository.findById(id);
        if (cartOptional.isEmpty()) {
            throw new Exception("Cart not found for ID: " + id);
        }
        return cartOptional.get();
    }

    @Override
    public Cart findCartByUser(Long id) throws Exception {
        Cart cart = cartRepository.findByCustomerId(id);
        if (cart == null) {
            throw new Exception("Cart not found for user ID: " + id);
        }

        // Update and return the cart total
        cart.setTotal(calculateCartTotal(cart));
        return cart;
    }

    @Override
    public Cart clearCart(String jwt) throws Exception {
        User user = userService.finduserbyjwt(jwt);
        Cart cart = findCartByUser(user.getId());

        // Clear all items from the cart
        cart.getItems().clear();
        cart.setTotal(0L); // Reset the total to 0
        return cartRepository.save(cart);
    }
}
