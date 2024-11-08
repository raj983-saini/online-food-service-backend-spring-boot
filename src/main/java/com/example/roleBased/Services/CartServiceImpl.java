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

import java.util.Optional;
@Service
public class CartServiceImpl implements  CartService{

    @Autowired
    public  UserService userService;

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

        for (CartItem cartItem : cart.getItems()) {
            if (cartItem.getFood().equals(food)) {
                int newQuantity = cartItem.getQuantit() + dto.getQuantit();
                return updateCartItemQuantity(cartItem.getId(), newQuantity);
            }
        }
        CartItem cartItem = new CartItem();
        cartItem.setCart(cart);
        cartItem.setFood(food);
        cartItem.setQuantit(dto.getQuantit());
        cartItem.setIngerdient(dto.getIngerdient());
        cartItem.setTotalPrize(dto.getQuantit() * food.getPrize());
        CartItem cartItem1 = cartItemrepository.save(cartItem);
        cart.getItems().add(cartItem1);

        return cartItem1;
    }
    @Override
    public CartItem updateCartItemQuantity(Long cartitemId, int quantity) throws Exception {
        Optional<CartItem> cartItem = cartItemrepository.findById(cartitemId);
       if (cartItem.isEmpty())
       {
           throw  new Exception("not item");
       }
       CartItem item = cartItem.get();
       item.setQuantit(quantity);

        item.setTotalPrize(item.getFood().getPrize()*quantity);
        return cartItemrepository.save(item);
    }

    @Override
    public Cart removealItemFromCart(long cartitemID, String jwt) throws Exception {
        User user = userService.finduserbyjwt(jwt);
        Cart cart = cartRepository.findByCustomerId(user.getId());
        Optional<CartItem> cartItem = cartItemrepository.findById(cartitemID);
        if (cartItem.isEmpty())
        {
            throw  new Exception("not item");
        }
        CartItem item = cartItem.get();
        cart.getItems().remove(item);
        return cartRepository.save(cart);
    }

    @Override
    public Long calculateCartTotal(Cart cart) throws Exception {
Long total = 0L;
for (CartItem  cartItem : cart.getItems()){
    total += cartItem.getFood().getPrize()+cartItem.getQuantit();
}
        return total;
    }

    @Override
    public Cart findCartById(Long id) throws Exception {
        Optional<Cart> cartItem = cartRepository.findById(id);
        if (cartItem.isEmpty())
        {
            throw  new Exception("not cart present");
        }

        return cartItem.get();
    }

    @Override
    public Cart findCartByUser(Long id) throws Exception {
        return cartRepository.findByCustomerId(id);
    }

    @Override
    public Cart clearCart(String jwt) throws Exception {
        User user = userService.finduserbyjwt(jwt);
        Cart cart = findCartByUser(user.getId());
        cart.getItems().clear();
        return cartRepository.save(cart);
    }
}
