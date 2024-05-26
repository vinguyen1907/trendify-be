package com.project.uit.trendify.repository;

import com.project.uit.trendify.common.lib.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findAllByCodeIn(List<String> codes);
}