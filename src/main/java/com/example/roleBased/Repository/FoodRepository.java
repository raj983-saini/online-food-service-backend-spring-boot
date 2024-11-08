package com.example.roleBased.Repository;

import com.example.roleBased.Entity.Food;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FoodRepository  extends JpaRepository<Food ,Long> {
 List<Food> findByRestaurantId(Long id);

 @Query("SELECT f FROM Food f WHERE f.name LIKE CONCAT('%', :keyword, '%') OR f.favurateCatagries.name LIKE CONCAT('%', :keyword, '%')")
 List<Food> searchFood(@Param("keyword") String keyword);



}
