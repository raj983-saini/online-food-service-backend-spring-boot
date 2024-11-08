package com.example.roleBased.Services;

import com.example.roleBased.Dto.Orderdto1;
import com.example.roleBased.Entity.*;
import com.example.roleBased.Repository.AdressRepository;
import com.example.roleBased.Repository.OrderRepository;
import com.example.roleBased.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private AdressRepository adressRepository;
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RestaurantService restaurantService;
    @Autowired
    private CartService cartService;

    @Override
    public Order addOrder(Orderdto1 dto, User user) throws Exception {
        Adressing shipAddress = dto.getAdressing();
        Adressing savedAddress = adressRepository.save(shipAddress);

        if (!user.getAdress().contains(savedAddress)) {
            user.getAdress().add(savedAddress);
            userRepository.save(user);
        }

        Restaurant restaurant = restaurantService.findResturantById(dto.getRestaurantId());

        Order order = new Order();
        order.setCustomer(user);
        order.setRestaurant(restaurant);
        order.setDeliveryAdress(savedAddress);
        order.setCreateorderDate(new Date());
        order.setOrderStatus("Pending");

        Cart cart = cartService.findCartByUser(user.getId());

        List<OrderItem> orderItems = cart.getItems().stream()
                .map(cartItem -> {
                    OrderItem orderItem = new OrderItem();
                    orderItem.setOrder(order);  // Associate with the order
                    orderItem.setFood(cartItem.getFood());
                    orderItem.setQuantity(cartItem.getQuantit());
                    orderItem.setTotalPrice(cartItem.getTotalPrize());
                    return orderItem;
                })
                .collect(Collectors.toList());

        order.setItems(orderItems);

        return orderRepository.save(order);  // This saves the order and all items due to CascadeType.ALL
    }


    @Override
    @Transactional
    public Order updateOrderStatus(Long orderId, String orderStatus) {
        Order order = findOrderById(orderId);

        if (List.of("OUT_FOR_DELIVERY", "DELIVERED", "COMPLETE", "PENDING").contains(orderStatus)) {
            order.setOrderStatus(orderStatus);
            return orderRepository.save(order);
        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid order status: " + orderStatus);
        }
    }

    @Override
    public void canelOrder(Long oderId) throws Exception {
        Order order = findOrderById(oderId);
        orderRepository.delete(order);
    }

    @Override
    @Transactional
    public List<Order> getUserOrder(Long userId) {
        return orderRepository.findByCustomerId(userId);
    }

    @Override
    @Transactional
    public List<Order> getRestaurantOrder(Long restaurantId, String orderStatus) {
        List<Order> orders = orderRepository.findByRestaurantId(restaurantId);
        if (orderStatus != null) {
            orders = orders.stream()
                    .filter(order -> order.getOrderStatus().equals(orderStatus))
                    .collect(Collectors.toList());
        }
        return orders;
    }

    @Override
    @Transactional
    public Order findOrderById(Long id) {
        return orderRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Order not found with ID: " + id));
    }
}
