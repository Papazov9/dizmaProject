package com.dizma.dizmademo.service;

import com.dizma.dizmademo.exceptions.ProductNotFoundException;
import com.dizma.dizmademo.model.binding.ProductBindingModel;
import com.dizma.dizmademo.model.entity.Product;
import com.dizma.dizmademo.model.viewModels.ProductViewModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

public interface ProductService {
    Page<ProductViewModel> findAll(Pageable pageable);

    Optional<Product> findByName(String name);

    Product saveProduct(ProductBindingModel productBindingModel, MultipartFile file) throws IOException;

    ProductViewModel findById(Long id);

    void editProduct(Long id, ProductBindingModel productEdit, MultipartFile file) throws ProductNotFoundException, IOException;

    void deleteOfferById(Long id) throws ProductNotFoundException;

}
