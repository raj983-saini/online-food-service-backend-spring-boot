package com.example.roleBased.Repository;

import com.example.roleBased.Entity.Adressing;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdressRepository  extends JpaRepository<Adressing , Long> {
}
