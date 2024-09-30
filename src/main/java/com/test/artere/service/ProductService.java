package com.test.artere.service;

import com.test.artere.model.Category;
import com.test.artere.model.Product;
import com.test.artere.repository.CategoryRepository;
import com.test.artere.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class ProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;

    @Autowired
    public ProductService(ProductRepository productRepository, CategoryRepository categoryRepository) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
    }

    public List<Product> getAllProducts() {
        return this.productRepository.findAll();
    }

    public Product addProduct(Product product) {
        return productRepository.save(product);
    }

    public Product updateProduct(Long id, Product updatedProduct){
        return this.productRepository.findById(id).map(category -> {
                    category.setName(updatedProduct.getName());
                    category.setPrice(updatedProduct.getPrice());
                    category.setStockQuantity(updatedProduct.getStockQuantity());
                    return this.productRepository.save(category);
                }
                ).orElse(null);
    }

    public void deleteProduct(Long id){
        this.productRepository.deleteById(id);
    }

    public Product linkProduct(Long productId, Long updatedCategoryId) {
        return productRepository.findById(productId).map(product -> {
            var category = categoryRepository.findById(updatedCategoryId)
                    .orElseThrow(()->new RuntimeException("category not found"));
            product.setCategory(category);
            return productRepository.save(product);
            }).orElseThrow(()->new RuntimeException("product not found"));
    }

}
