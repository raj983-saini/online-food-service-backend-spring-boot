package com.example.roleBased.Repository;

import com.example.roleBased.Entity.Ingerident;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IngerdientRepository extends JpaRepository<Ingerident , Long > {
    List<Ingerident> findByRestaurantId(Long id);
    void deleteByRestaurantId(Long id);

}
