package com.example.roleBased.Services;

import com.example.roleBased.Dto.Orderdto1;
import com.example.roleBased.Entity.*;
import com.example.roleBased.Repository.AdressRepository;
import com.example.roleBased.Repository.OrderRepository;
import com.example.roleBased.Repository.UserRepository;
import org.aspectj.weaver.ast.Or;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl  implements  OrderService{
    @Autowired
    AdressRepository adressRepository;
    @Autowired
    OrderRepository orderRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    RestaurantService restaurantService;
    @Autowired
    CartService cartService;
    @Override
    public Order addOrder(Orderdto1 dto, User user) throws Exception {
        Adressing shipadress = dto.getAdressing();
        Adressing saveadress = adressRepository.save(shipadress);

        if (!user.getAdress().contains(saveadress)){
            user.getAdress().add(saveadress);
            userRepository.save(user);
        }
        Restaurant restaurant =   restaurantService.findResturantById(dto.getRestaurantId());
        Order order = new Order( );
        order.setCustomer(user);
        order.setRestaurant(restaurant);
        order.setDeliveryAdress(saveadress);
        order.setCreateorderDate(new Date());
        order.setOrderStatus("Pending");

        Cart cart = cartService.findCartByUser(user.getId());

        Order saveorder =orderRepository.save(order);
        restaurant.getOrders().add(saveorder);
        return order;


    }

    @Override
    public Order updateOrderStatus(Long orderId, String orderstaus) throws Exception {
        Order order = findOrderById(orderId);
        if (orderstaus.equals("OUT_FOR_DELIVERY")
                ||orderstaus.equals("DELIVERED")
                ||orderstaus.equals("COMPLETE")
                ||orderstaus.equals("PENDING")){
            order.setOrderStatus(orderstaus);
            return  orderRepository.save(order);
        }
       throw new Exception("please enter valid status");
    }

    @Override
    public void canelOrder(Long oderId) throws Exception {
Order order = findOrderById(oderId);
orderRepository.delete(order);
    }

    @Override
    public List<Order> getUserOrder(Long userId) throws Exception {
        return orderRepository.findByCustomerId(userId);
    }

    @Override
    public List<Order> getRestaurantOrder(Long restaurantId ,String orderStatus) throws Exception {
        List<Order> orders = orderRepository.findByRestaurantId(restaurantId);
        if (orderStatus != null){
            orders = orders.stream().filter(order ->
                    order.getOrderStatus().equals(orderStatus)).collect(Collectors.toList());
        }
        return orders;
    }

    @Override
    public Order findOrderById(Long Id) throws Exception {

        Optional <Order> optionalOrder = orderRepository.findById(Id);
        if (optionalOrder.isEmpty()){
            throw new Exception("Order not found");
        }

        return optionalOrder.get();
    }
}
