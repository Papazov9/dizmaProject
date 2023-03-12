package com.dizma.dizmademo.service.impl;

import com.dizma.dizmademo.model.entity.Product;
import com.dizma.dizmademo.repository.ProductRepository;
import com.dizma.dizmademo.service.ProductService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;

    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public List<Product> findAll() {
        return this.productRepository.findAll();
    }
}
