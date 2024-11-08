package com.example.roleBased.Repository;

import com.example.roleBased.Entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
public List <Order> findByCustomerId( Long userID);
List<Order> findByRestaurantId(Long restaurantId);

}
