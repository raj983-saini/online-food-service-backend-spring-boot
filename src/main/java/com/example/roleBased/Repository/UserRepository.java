package com.example.roleBased.Repository;


import com.example.roleBased.Entity.Role;
import com.example.roleBased.Entity.User;
import jakarta.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User,Integer> {

   User findByEmail(String email);
   boolean existsByEmail(String email);
   User  findByRole(Role role);
   @Modifying
   @Transactional
   @Query("UPDATE User u SET u.password = :newPassword WHERE u.email = :email")
   void updatePasswordByEmail(@Param("newPassword") String newPassword, @Param("email") String email);


}
