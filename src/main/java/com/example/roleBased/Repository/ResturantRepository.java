package com.example.roleBased.Repository;

import com.example.roleBased.Entity.Restaurant;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ResturantRepository  extends JpaRepository<Restaurant , Long> {
    @Query("SELECT r FROM Restaurant r WHERE LOWER(r.name) = LOWER(:name)")
    Optional<Restaurant> findByName(@Param("name") String name);
    @Query("SELECT r FROM Restaurant r WHERE " +
            "LOWER(r.name) LIKE LOWER(CONCAT('%', :query, '%')) " +
            "OR LOWER(r.cuisineType) LIKE LOWER(CONCAT('%', :query, '%'))")
    List<Restaurant> findBySerachQuery(@Param("query") String query);

    Restaurant findResturantById(Long id);

    Restaurant findByOwnerId(Long userId);

}
