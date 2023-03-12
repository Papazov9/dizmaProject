package com.dizma.dizmademo.service;

import com.dizma.dizmademo.model.binding.ProductBindingModel;
import com.dizma.dizmademo.model.entity.Product;
import com.dizma.dizmademo.model.viewModels.ProductViewModel;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

public interface ProductService {
    List<ProductViewModel> findAll();

    Optional<Product> findByName(String name);

    Product saveProduct(ProductBindingModel productBindingModel, MultipartFile file) throws IOException;
}
