package com.example.brightClean.rest;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.example.brightClean.domain.Product;
import com.example.brightClean.repository.ProductRepository;
import com.example.brightClean.service.ProductService;

@RestController
@RequestMapping("/product")
@CrossOrigin("http://localhost:8080")
public class ProductController {

    @Autowired
    private ProductService productService;

    @Autowired
    private ProductRepository productRepository;

    @GetMapping("/get")
    public Product getMethodName(@RequestParam("id") int id) {
        return productService.getProduct(id).get();
    }

    @GetMapping("/getAll")
    public List<Product> getUsers() {
        return productService.getAll();
    }

    @PostMapping("/add")
    public ResponseEntity<Product> postMethodName(@RequestBody Product product) {
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().toString());
        return ResponseEntity.created(uri).body(productService.create(product));
    }

    @GetMapping("/{page}/{rows}")
    public List<Product> getAllBySales(@PathVariable("page") int page, @PathVariable("rows") int rows) {

        Pageable pageable = PageRequest.of(page, rows, Sort.by("productId"));
        return productRepository.findAll(pageable).getContent();
    }
}
