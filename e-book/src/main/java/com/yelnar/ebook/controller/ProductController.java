package com.yelnar.ebook.controller;

import com.yelnar.ebook.models.MyFormObject;
import com.yelnar.ebook.models.Product;
import com.yelnar.ebook.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping("/product")
public class ProductController {
    private final ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping()
    public String products(Model model, @ModelAttribute("title") MyFormObject title) {
        List<Product> products = productService.productTitle(title.getTitle());
        model.addAttribute("products", products);
        model.addAttribute("prod", new Product());

        return "navbar";
    }

    @GetMapping("/{id}")
    public String productInfo(@PathVariable Long id, Model model) {

        Product product = productService.showProduct(id);
        
        model.addAttribute("productInfo", productService.showProduct(id));
        model.addAttribute("image", product.getImages());

        return "product-info";
    }


    @PostMapping("/create")
    public String createProduct(@RequestParam(value = "file1") MultipartFile file1,
                                @RequestParam(value = "file2") MultipartFile file2,
                                @RequestParam(value = "file3") MultipartFile file3, Product product) throws IOException {


        productService.saveProduct(product, file1, file2, file3);
        return "redirect:/product";
    }

    @DeleteMapping("product/{id}")
    public String deleteProduct(@PathVariable("id") Long id) {
        productService.deleteProduct(id);
        return "redirect:/product";
    }


}
