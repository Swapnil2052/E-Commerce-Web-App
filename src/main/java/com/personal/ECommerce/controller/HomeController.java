package com.personal.ECommerce.controller;

import com.personal.ECommerce.configuration.ProductNotFoundException;
import com.personal.ECommerce.service.CategoryService;
import com.personal.ECommerce.service.ProductService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class HomeController {
    private final CategoryService categoryService;
    private final ProductService productService;

    public HomeController(CategoryService categoryService, ProductService productService) {
        this.categoryService = categoryService;
        this.productService = productService;
    }

    @GetMapping({"/","/home"})
    public String home(){
        return "index";
    }

    @GetMapping("/shop")
    public String shop(Model model){
        model.addAttribute("categories",categoryService.showAllCat());
        model.addAttribute("products",productService.showAllProd());
        return "shop";
    }

    @GetMapping("/shop/category/{id}")
    public String shopByCat(Model model, @PathVariable int id){
        model.addAttribute("categories",categoryService.showAllCat());
        model.addAttribute("products",productService.showAllProdByCategoryId(id));
        return "shop";
    }

    @GetMapping("/shop/viewproduct/{id}")
    public String viewProd(Model model, @PathVariable long id) throws ProductNotFoundException {
        model.addAttribute("product",productService.showProd(id));
        return "viewProduct";
    }
}
