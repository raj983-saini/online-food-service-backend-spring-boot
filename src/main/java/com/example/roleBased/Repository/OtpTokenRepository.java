package com.example.roleBased.Repository;



import com.example.roleBased.Entity.Otp;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface OtpTokenRepository extends CrudRepository <Otp ,Long>{
    List<Otp> findByEmail(String email);
    @Modifying
    @Transactional
    @Query("DELETE FROM Otp o WHERE o.email = :email")
    void deleteByEmail(String email);
}
