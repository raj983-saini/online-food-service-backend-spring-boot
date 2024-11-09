package com.example.roleBased.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.context.annotation.Lazy;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Restaurant {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String name;

   private String cuisineType;

    private String description;

    private String profilePicture;
    @OneToOne(fetch = FetchType.EAGER)
    private Adressing adressing;

    @Embedded
    private  ContactInfo contactInfo;

    private  String openingHour;

    @OneToMany(mappedBy = "restaurant", cascade = CascadeType.ALL,orphanRemoval = true ,fetch = FetchType.EAGER)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private List<Order> orders = new ArrayList<>();


    @Column(length = 1000)
    @ElementCollection(fetch = FetchType.EAGER )
    private List<String> image = new ArrayList<>();

    @OneToOne
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private User owner;

private LocalDateTime registerdate;

    @OneToMany(mappedBy = "restaurant", cascade = CascadeType.ALL, orphanRemoval = true , fetch = FetchType.EAGER)
    private List<Ingerident> ingredients;  // Assuming "Ingredient" is related to "ingerident"

private boolean open;

@OneToMany( mappedBy = "restaurant" , cascade = CascadeType.ALL,fetch = FetchType.EAGER )
@JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
private  List<Food>  foods = new ArrayList<>();

}
