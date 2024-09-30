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
public class CategoryService {

    private final CategoryRepository categoryRepository;
    private final ProductRepository productRepository;

    @Autowired
    public CategoryService(CategoryRepository categoryRepository, ProductRepository productRepository) {
        this.categoryRepository = categoryRepository;
        this.productRepository = productRepository;
    }

    public List<Category> getAllCategories() {
        return this.categoryRepository.findAll();
    }

    public Category addCategory(Category category) {
        return categoryRepository.save(category);
    }

    public Category updateCategory(Long id, Category updatedCategory){
        return this.categoryRepository.findById(id).map(category -> {
                    category.setName(updatedCategory.getName());
                    category.setDescription(updatedCategory.getDescription());
                    return this.categoryRepository.save(category);
                }
                ).orElse(null);
    }

    public void deleteCategory(Long id){
        this.categoryRepository.deleteById(id);
    }

    public Category linkSubCategory(Long parentId, Long subCategoryId){
        var parentCategory = this.categoryRepository.findById(parentId)
                .orElseThrow(()->new RuntimeException("parent category not found"));
        var subCategory = this.categoryRepository.findById(subCategoryId)
                .orElseThrow(()->new RuntimeException("sub category not found"));

        subCategory.setParentCategory(parentCategory);
        return this.categoryRepository.save(subCategory);
    }

    public Category unlinkSubCategory(Long subCategoryId){
        var subCategory = this.categoryRepository.findById(subCategoryId)
                .orElseThrow(()->new RuntimeException("sub category not found"));

        subCategory.setParentCategory(null);
        return this.categoryRepository.save(subCategory);
    }

}
