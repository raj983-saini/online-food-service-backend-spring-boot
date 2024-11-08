package com.example.roleBased.Controller;

import com.example.roleBased.Dto.Orderdto1;
import com.example.roleBased.Entity.Order;
import com.example.roleBased.Entity.User;
import com.example.roleBased.Services.OrderService;
import com.example.roleBased.Services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/order")
public class OrderController {

    @Autowired
    UserService userService;
    @Autowired
    OrderService orderService;

    @PostMapping("/create")
    public ResponseEntity<?> creaeteorder(@RequestBody  Orderdto1 orderdto1,
                                          @RequestHeader("Authorization") String jwt) throws Exception {
        User user = userService.finduserbyjwt(jwt);
        Order  order =orderService.addOrder(orderdto1,user);
        return  new ResponseEntity<>(order , HttpStatus.CREATED);
    }
     @GetMapping("/details")
    public ResponseEntity<?> orderHistory(
                                          @RequestHeader("Authorization") String jwt) throws Exception {
        User user = userService.finduserbyjwt(jwt);
        List<Order>  order =orderService.getUserOrder(user.getId());
        return  new ResponseEntity<>(order , HttpStatus.OK);
    }
    @GetMapping("/restaurant/{id}")
    public ResponseEntity<?> getOrderHistory(
            @PathVariable Long id,
            @RequestParam(required = false) String orderstatus,
                                          @RequestHeader("Authorization") String jwt) throws Exception {
        User user = userService.finduserbyjwt(jwt);
        List<Order>  order =orderService.getRestaurantOrder(id,orderstatus);
        return  new ResponseEntity<>(order , HttpStatus.OK);
    }
        @GetMapping("/restaurant/{id}/{orderstatus}")
    public ResponseEntity<?> updateOrderstatus(
            @PathVariable Long id,
            @PathVariable String orderstatus,
                                          @RequestHeader("Authorization") String jwt) throws Exception {
        User user = userService.finduserbyjwt(jwt);
        Order  order =orderService.updateOrderStatus(id,orderstatus);
        return  new ResponseEntity<>(order , HttpStatus.OK);
    }


}
