package com.example.roleBased.Services;

import com.example.roleBased.Dto.Orderdto1;
import com.example.roleBased.Entity.Order;
import com.example.roleBased.Entity.User;

import java.util.List;

public interface OrderService {
    Order addOrder(Orderdto1 dto , User user) throws  Exception;
    Order updateOrderStatus(Long orderId , String orderstaus )throws  Exception;
    void  canelOrder(Long oderId)throws  Exception;
    List<Order> getUserOrder(Long userId) throws  Exception;
    List<Order> getRestaurantOrder(Long restaurantId , String orderstaus)throws  Exception  ;
    Order findOrderById(Long Id) throws Exception;
}
