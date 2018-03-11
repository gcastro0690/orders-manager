package com.orders.persistence.model.mysql.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

import com.orders.persistence.model.mysql.entity.User;
import com.orders.persistence.model.mysql.entity.UserRole;

@Component
public interface UserRepository extends JpaRepository<User, Long> {
	User findByUsernameAndPass(String username, String pass);
	List<User> findAll();
	List<User> findByRole(UserRole role);
}