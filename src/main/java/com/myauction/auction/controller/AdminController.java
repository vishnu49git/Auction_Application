package com.myauction.auction.controller;

import com.myauction.auction.dto.AuctionDto;
import com.myauction.auction.dto.ProductDto;
import com.myauction.auction.dto.UserRegisterDto;
import com.myauction.auction.service.AdminService;
import com.myauction.auction.service.AuctionService;
import com.myauction.auction.service.ProductService;
import com.myauction.auction.service.UserService;
import lombok.extern.slf4j.Slf4j;
import com.myauction.auction.model.Auction;
import com.myauction.auction.model.Product;
import com.myauction.auction.model.Users;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
@Slf4j
@RestController
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private AdminService adminService;
    @Autowired
    private AuctionService auctionService;
    @Autowired
    private UserService userServ;
    @Autowired
    private ProductService productService;

    @GetMapping("/auctions")
    public List<Auction> getAllAuctions(){
        return auctionService.getAllAuctions();
    }

    @GetMapping("/auction/{auctionId}")
    public ResponseEntity<?> getAuctionById(@PathVariable Long auctionId){
        Auction auction = auctionService.getAuctionById(auctionId);

        if (auction == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Auction not found");
        }

        return ResponseEntity.ok(auction);

    }
    @GetMapping("/users")
    public ResponseEntity<?> getAllUsers() {
        List<Users> users = userServ.getAllUsers();

        if (users.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body("No users found");
        }

        return ResponseEntity.ok(users);
    }
    @GetMapping("/user/{userId}")
    public ResponseEntity<?> getUserById(@PathVariable Long userId) {
        Users user = userServ.getUserById(userId);

        if (user == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
        }

        return ResponseEntity.ok(user);
    }



    @PostMapping("/createauction")
    @PreAuthorize("hasAnyRole('ROLE_SUPER_ADMIN','ROLE_ADMIN'")
    public Auction createAuction(@RequestBody AuctionDto auction) throws Exception {
        return adminService.createAuction(auction);
    }
    @DeleteMapping("/deleteauction/{auctionId}")
    @PreAuthorize("hasAnyRole('ROLE_SUPER_ADMIN','ROLE_ADMIN'")
    public ResponseEntity<?> deleteAuction(@PathVariable Long auctionId){
        adminService.deleteAuction(auctionId);
        return ResponseEntity.ok("Auction deleted successfully");

    }
    @PostMapping("/addproduct")
    @PreAuthorize("hasAnyRole('ROLE_SUPER_ADMIN','ROLE_ADMIN'")
    public String createProduct(@RequestBody ProductDto product) throws IOException {
        return productService.createProduct(product);
    }

    @PostMapping("/create-admin")
    @PreAuthorize("hasAnyRole('ROLE_SUPER_ADMIN','ROLE_ADMIN'")
    public ResponseEntity<?> createAdmin(@RequestBody UserRegisterDto user) throws Exception {
        try {
            Users newAdmin = userServ.registerAdmin(user);
            return ResponseEntity.ok("Admin registered successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
    @GetMapping("/allProducts")
    public List<Product> getAllProducts(){
        return productService.getAllProducts();
    }
    @GetMapping("/productById/{productId}")
    public Product getProduct(@PathVariable Long productId){
        return productService.getProduct(productId);
    }



}

