package com.example.brightClean.service;

import java.util.List;
import java.util.Optional;
import com.example.brightClean.domain.Product;

public interface ProductService {
    Product create(Product product);

    Optional<Product> getProduct(Integer id);

    List<Product> getAll();

    void update(Product product) throws Exception;

}
