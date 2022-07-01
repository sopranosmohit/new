package com.programmingtechie.orderservice.repository;

import com.programmingtechie.orderservice.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order,Long> {
}
