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
import com.myauction.auction.model.Users;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.io.IOException;
import java.util.List;
@Slf4j
@RestController
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private AdminService adminService;

    @PostMapping("/create-admin")
    public ResponseEntity<?> createAdmin(@RequestBody UserRegisterDto user) throws Exception {
        try {
             adminService.registerAdmin(user);
             return ResponseEntity.ok("Admin registered successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PostMapping("/createAuction")
    public ResponseEntity<?> createAuction(@RequestBody AuctionDto auction) throws Exception {
        try {
            adminService.createAuction(auction);
            return ResponseEntity.ok("Auction Created");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @DeleteMapping("/deleteAuction/{auctionId}")
    public ResponseEntity<?> deleteAuction(@PathVariable Long auctionId){
        adminService.deleteAuction(auctionId);
        return ResponseEntity.ok("Auction deleted successfully");

    }

    @PostMapping("/addProduct")
    public ResponseEntity<?> createProduct(@RequestBody ProductDto product) throws IOException {
        adminService.addProduct(product);
        return ResponseEntity.ok("Product added Successfully");
    }

    @DeleteMapping("/deleteProduct/{productId}")
    public ResponseEntity<?> deleteProduct(@PathVariable Long productId){
        adminService.deleteProduct(productId);
        return ResponseEntity.ok("Product deleted Successfully");
    }

    @GetMapping("/getAllUsers")
    public ResponseEntity<?> getAllUsers() {
        List<Users> users = adminService.getAllUsers();

        if (users.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body("No users found");
        }
        return ResponseEntity.ok(users);
    }

    @GetMapping("userByUsername")
    public Users findByUsername(@PathVariable String username){
        return adminService.findByUsername(username);
    }

    @GetMapping("/userById/{userId}")
    public ResponseEntity<?> getUserById(@PathVariable Long userId) {
        Users user = adminService.getUserById(userId);

        if (user == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
        }
        return ResponseEntity.ok(user);
    }
}



























//@GetMapping("/auction/{auctionId}")
//public ResponseEntity<?> getAuctionById(@PathVariable Long auctionId){
//    Auction auction = auctionService.getAuctionById(auctionId);
//
//    if (auction == null) {
//        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Auction not found");
//    }
//    return ResponseEntity.ok(auction);
//
//}
