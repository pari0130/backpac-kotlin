package com.example.backpackotlin.biz.core.product.repo;

import com.example.backpackotlin.biz.core.product.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderJPARepository extends JpaRepository<Order, Long>{
}
