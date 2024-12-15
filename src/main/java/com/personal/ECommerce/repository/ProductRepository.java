package com.personal.ECommerce.repository;

import com.personal.ECommerce.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product,Long> {
    List<Product> findAllByCategoryId(int id);
}
