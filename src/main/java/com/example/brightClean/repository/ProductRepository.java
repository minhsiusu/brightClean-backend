package com.example.brightClean.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

import com.example.brightClean.domain.Product;

@Repository

public interface ProductRepository extends JpaRepository<Product, Integer> {

    public List<Product> findAllBySales(int sales, Pageable agable);
}
