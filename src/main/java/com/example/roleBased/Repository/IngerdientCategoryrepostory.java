package com.example.roleBased.Repository;

import com.example.roleBased.Entity.IngerdientCatagaries;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IngerdientCategoryrepostory  extends JpaRepository<IngerdientCatagaries , Long> {
 List <IngerdientCatagaries >findByRestaurantId(Long id);

}
