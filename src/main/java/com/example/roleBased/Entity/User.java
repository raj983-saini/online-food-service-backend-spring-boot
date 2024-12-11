package com.example.roleBased.Entity;


import com.example.roleBased.Dto.RestaurantDto;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "user3")
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String fullname;

    private String email;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;

    private String status;
    @OneToMany(cascade = CascadeType.ALL , mappedBy = "customer" ,fetch = FetchType.EAGER)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private List<Order> orders = new ArrayList<>();


    @OneToMany(cascade = CascadeType.ALL , orphanRemoval = true ,fetch = FetchType.EAGER)
    private List<Adressing> adress = new ArrayList<>();
    //
    //    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
       @ElementCollection(fetch = FetchType.EAGER)
    private  List <RestaurantDto> favurate = new ArrayList<>();
    @Override
    public  String getUsername(){
        return  email;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_" + role.name()));  // Add prefix
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }


}