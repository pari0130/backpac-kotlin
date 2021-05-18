package com.example.backpackotlin.biz.core.product.repo;

import com.example.backpackotlin.biz.core.product.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductJPARepository extends JpaRepository<Product, Long>{
}
