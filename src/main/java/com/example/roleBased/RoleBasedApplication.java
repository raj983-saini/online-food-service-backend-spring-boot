package com.example.roleBased;

import com.example.roleBased.Dto.Registerdto;
import com.example.roleBased.Entity.Role;
import com.example.roleBased.Entity.User;
import com.example.roleBased.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;


@SpringBootApplication
public class RoleBasedApplication implements CommandLineRunner {
@Autowired
public UserRepository userRepository;
	public static void main(String[] args) {
		SpringApplication.run(RoleBasedApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		User admin = userRepository.findByEmail("admin@gamil.com");
		if (admin == null) {
			User user = new User();
			user.setEmail("admin@gamil.com");
			user.setFullname("admin");
			user.setPassword(new BCryptPasswordEncoder().encode("1234"));

			user.setRole(Role.ADMIN);
			userRepository.save(user);

	}
}}
