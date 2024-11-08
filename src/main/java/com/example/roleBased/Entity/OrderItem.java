package com.example.roleBased.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "order_items")
public class OrderItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Food food;

    private int quantity;
    private String description;
    private long totalPrice;

    @ElementCollection ( fetch = FetchType.EAGER)
    @CollectionTable(name = "order_item_ingredients", joinColumns = @JoinColumn(name = "order_item_id"))
    @Column(name = "ingredient")
    private List<String> ingredient = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "order_id")
    private Order order;  // Add reference to Order


}
