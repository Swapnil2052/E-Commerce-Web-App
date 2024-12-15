package com.personal.ECommerce.controller;

import com.personal.ECommerce.configuration.CategoryNotFoundException;
import com.personal.ECommerce.configuration.ProductNotFoundException;
import com.personal.ECommerce.dto.ProductDTO;
import com.personal.ECommerce.entity.Category;
import com.personal.ECommerce.entity.Product;
import com.personal.ECommerce.service.CategoryService;
import com.personal.ECommerce.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@Controller
public class AdminController {

    private final CategoryService categoryService;
    private final ProductService productService;
    @Autowired
    public AdminController(CategoryService categoryService, ProductService productService) {
        this.categoryService = categoryService;
        this.productService = productService;
    }

    //Category Section--------------------------------------------------------------------------------------------------

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

    //Product Section---------------------------------------------------------------------------------------------------

    @GetMapping("/admin/products")
    public String products(Model model){
        List<Product>allProd=productService.showAllProd();
        model.addAttribute("products",allProd);
        return "products";
    }

    @GetMapping("/admin/products/add")
    public String addProducts(Model model){
        model.addAttribute("productDTO", new ProductDTO());
        model.addAttribute("categories",categoryService.showAllCat());    
        return "productsAdd";
    }

    @PostMapping("/admin/products/add")
    public String saveProduct(@ModelAttribute ProductDTO productDTO, @RequestParam("productImage")MultipartFile imgfile,
                              @RequestParam("imgName") String imgName) throws CategoryNotFoundException, IOException {
        Product product=new Product();
        product.setId(productDTO.getId());
        product.setName(productDTO.getName());
        product.setCategory(categoryService.showCat(productDTO.getCategoryId()));
        product.setPrice(productDTO.getPrice());
        product.setWeight(productDTO.getWeight());
        product.setDescription(productDTO.getDescription());
        String imageUUID;
        String uploadDir="src/main/resources/static/productImages";
        if(!imgfile.isEmpty()){
            imageUUID= StringUtils.cleanPath(imgfile.getOriginalFilename());
            Path filepath= Paths.get(uploadDir,imageUUID);
            Files.write(filepath,imgfile.getBytes());
        }else{
            imageUUID=imgName;
        }
        product.setImageName(imageUUID);
        productService.addProd(product);
        return "redirect:/admin/products";
    }

    @GetMapping("/admin/product/delete/{id}")
    public String updateProducts(@PathVariable long id)  {
        productService.deleteProd(id);
        return "redirect:/admin/products";
    }

    @GetMapping("/admin/product/update/{id}")
    public String updateProducts(@PathVariable long id,Model model) throws ProductNotFoundException {
        Product product=productService.showProd(id);
        ProductDTO productDTO=new ProductDTO();
        productDTO.setId(product.getId());
        productDTO.setName(product.getName());
        productDTO.setCategoryId(product.getCategory().getId());
        productDTO.setPrice(product.getPrice());
        productDTO.setWeight(product.getWeight());
        productDTO.setDescription(product.getDescription());
        productDTO.setImageName(product.getImageName());

        model.addAttribute("productDTO",productDTO);
        model.addAttribute("categories",categoryService.showAllCat());
        return "productsAdd";
    }
}
