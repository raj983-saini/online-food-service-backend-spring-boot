package com.example.roleBased.Repository;

import com.example.roleBased.Entity.Catagaries;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryRepository extends JpaRepository<Catagaries , Long> {


List <Catagaries> findByRestaurantId(Long id);


}
