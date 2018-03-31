package com.orders.persistence.model.mysql.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Component;

import com.orders.persistence.model.mysql.entity.Order;

@Component
public interface OrderRepository extends JpaRepository<Order, Long> {
	
	List<Order> findAll();
	 @Query("SELECT o FROM Order o WHERE o.technician.id= :techId")
	List<Order> findAllByTechnicianId(@Param("techId")Long techId);
}