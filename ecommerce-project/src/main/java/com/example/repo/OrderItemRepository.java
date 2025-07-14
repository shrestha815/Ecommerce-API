package com.example.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.entity.OrderItem;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {

}
