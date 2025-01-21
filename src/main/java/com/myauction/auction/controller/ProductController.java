package com.myauction.auction.controller;

import com.myauction.auction.model.Product;
import com.myauction.auction.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequestMapping("/product")
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping("/allProducts")
    public List<Product> getAllProducts(){
        return productService.getAllProducts();
    }
    @GetMapping("/productById/{productId}")
    public Product getProduct(@PathVariable Long productId){
        return productService.getProductById(productId);
    }
}
