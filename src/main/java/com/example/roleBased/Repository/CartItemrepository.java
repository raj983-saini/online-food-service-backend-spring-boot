package com.example.roleBased.Repository;

import com.example.roleBased.Entity.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CartItemrepository extends JpaRepository<CartItem , Long> {

}
