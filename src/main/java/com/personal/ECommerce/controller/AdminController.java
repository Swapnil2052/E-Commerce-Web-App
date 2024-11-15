package com.personal.ECommerce.controller;

import com.personal.ECommerce.configuration.CategoryNotFoundException;
import com.personal.ECommerce.entity.Category;
import com.personal.ECommerce.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class AdminController {

    private final CategoryService categoryService;
    @Autowired
    public AdminController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping("/admin")
    public String adminHome(){
        return "adminHome";
    }

    @GetMapping("/admin/categories")
    public String categories(Model model){
        List<Category>list=categoryService.showAllCat();
        model.addAttribute("categories",list);
        return "categories";
    }

    @GetMapping("/admin/categories/add")
    public String addCategories(Model model){
        model.addAttribute("category",new Category());
        return "categoriesAdd";
    }

    @PostMapping("/admin/categories/add")
    public String postAddCategories(@ModelAttribute Category category){
        categoryService.addCat(category);
        return "redirect:/admin/categories";
    }

    @GetMapping("/admin/categories/delete/{id}")
    public String deleteCategories(@PathVariable int id){
        categoryService.deleteCat(id);
        return "redirect:/admin/categories";
    }

    @GetMapping("/admin/categories/update/{id}")
    public String updateCategories(@PathVariable int id,Model model) throws CategoryNotFoundException {
        Category category=categoryService.showCat(id);
        model.addAttribute("category",category);
        return "categoriesAdd";
    }

    @GetMapping("/admin/products")
    public String products(){
        return "products";
    }

}
