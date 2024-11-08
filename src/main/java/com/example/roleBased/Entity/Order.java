package com.example.roleBased.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User customer;

    @ManyToOne
    private Restaurant restaurant;
//
    @ManyToMany( cascade = CascadeType.ALL , fetch = FetchType.EAGER)
    private List<OrderItem> items = new ArrayList<>();


    @ManyToOne
private  Adressing deliveryAdress;

    private Date createorderDate;
    private  long tatalprize;
    private  long totalAmount;
    private int tatalItem;

//  private Payment payment;
    private String OrderStatus;



}
