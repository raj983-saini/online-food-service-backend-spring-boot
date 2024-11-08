package com.example.roleBased.Services;

import com.example.roleBased.Dto.AddCartItemDto;
import com.example.roleBased.Entity.Cart;
import com.example.roleBased.Entity.CartItem;

public interface CartService {

    CartItem addCartIem(AddCartItemDto dto , String jwt)throws  Exception;
    CartItem updateCartItemQuantity(Long cartitem , int quantity) throws  Exception;
    Cart removealItemFromCart(long cartitem , String jwt) throws  Exception;
    Long calculateCartTotal(Cart cart) throws  Exception;
    Cart findCartById(Long id)throws  Exception;
    Cart findCartByUser(Long id) throws  Exception;
    Cart clearCart(String jwt)throws  Exception;
}
