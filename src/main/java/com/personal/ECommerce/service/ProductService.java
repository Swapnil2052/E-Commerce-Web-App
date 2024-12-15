package com.personal.ECommerce.service;

import com.personal.ECommerce.configuration.ProductNotFoundException;
import com.personal.ECommerce.entity.Product;
import com.personal.ECommerce.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {
    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public List<Product> showAllProd(){
        return productRepository.findAll();
    }

    public Product showProd(Long id) throws ProductNotFoundException {
        return productRepository.findById(id).orElseThrow(()-> new ProductNotFoundException("no product found"));
    }

    public void addProd(Product product){
        productRepository.save(product);
    }
    public void deleteProd(Long id){
        productRepository.deleteById(id);
    }

    public List<Product>showAllProdByCategoryId(int id){
        return productRepository.findAllByCategoryId(id);
    }
}
