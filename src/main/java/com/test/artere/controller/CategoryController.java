package com.test.artere.controller;

import com.test.artere.model.Category;
import com.test.artere.model.Product;
import com.test.artere.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/categories")
public class CategoryController {
    private final CategoryService categoryService;

    @Autowired
    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping
    public List<Category> getAllCategories(){
        return this.categoryService.getAllCategories();
    }

    @PostMapping
    public Category addCategory(@RequestBody Category category) {
        return categoryService.addCategory(category);
    }

    @PutMapping("/{id}")
    public Category updateCategory(@PathVariable Long id, @RequestBody Category category){
        return this.categoryService.updateCategory(id, category);
    }

    @DeleteMapping("/{id}")
    public void deleteCategory(@PathVariable Long id){
         this.categoryService.deleteCategory(id);
    }

    @PostMapping("/{parentId}/subcategories/{subCategoryId}")
    public Category linkSubCategory(@PathVariable Long parentId, @PathVariable Long subCategoryId){
        return this.categoryService.linkSubCategory(parentId, subCategoryId);
    }

    @DeleteMapping("/{subCategoryId}/unlink")
    public Category unlink(@PathVariable Long subCategoryId){
        return this.categoryService.unlinkSubCategory(subCategoryId);
    }

}
