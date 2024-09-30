package com.test.artere.controller;

import com.test.artere.model.Category;
import com.test.artere.model.Product;
import com.test.artere.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {
    private final ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public List<Product> getAllProducts(){
        return this.productService.getAllProducts();
    }

    @PostMapping
    public Product addProduct(@RequestBody Product product) {
        return productService.addProduct(product);
    }

    @PutMapping("/{id}")
    public Product updateCategory(@PathVariable Long id, @RequestBody Product product){
        return this.productService.updateProduct(id, product);
    }

    @DeleteMapping("/{id}")
    public void deleteProduct(@PathVariable Long id){
         this.productService.deleteProduct(id);
    }

    @PutMapping("/{productId}/link/{updatedCategoryId}")
    public Product linkProduct(@PathVariable Long productId, @PathVariable Long updatedCategoryId){
        return this.productService.linkProduct(productId, updatedCategoryId);
    }

}
