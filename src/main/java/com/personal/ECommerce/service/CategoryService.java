package com.personal.ECommerce.service;

import com.personal.ECommerce.configuration.CategoryNotFoundException;
import com.personal.ECommerce.entity.Category;
import com.personal.ECommerce.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {
    private final CategoryRepository categoryRepository;
    @Autowired
    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public List<Category> showAllCat(){
        return categoryRepository.findAll();
    }
    public Category showCat(int id) throws CategoryNotFoundException {
        return categoryRepository.findById(id).orElseThrow(()->new CategoryNotFoundException("not found"));
    }
    public void addCat(Category category){
        categoryRepository.save(category);
    }
    public void deleteCat(int id){
        categoryRepository.deleteById(id);
    }
}
