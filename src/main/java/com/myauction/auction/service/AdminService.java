package com.myauction.auction.service;
import com.myauction.auction.dto.ProductDto;
import com.myauction.auction.dto.UserRegisterDto;
import com.myauction.auction.exception.AuctionAlreadyExistsException;
import com.myauction.auction.exception.AuctionAlreadyExistsInTimeRangeException;
import com.myauction.auction.exception.ProductNotFoundException;
import com.myauction.auction.exception.UserAlreadyExistsException;
import com.myauction.auction.model.Auction;
import com.myauction.auction.dto.AuctionDto;
import com.myauction.auction.model.Product;
import com.myauction.auction.model.Users;
import com.myauction.auction.repository.AuctionRepository;
import com.myauction.auction.repository.BidRepository;
import com.myauction.auction.repository.ProductRepository;
import com.myauction.auction.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
@Slf4j
@Service
public class AdminService {
    @Autowired
    private AuctionRepository auctionRepository;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private PasswordEncoder encoder;

    @Autowired
    private UserRepository userRepository;
    @Transactional
    public void createAuction(AuctionDto auction) throws Exception {
        Optional<Product> productOptional = productRepository.findById(auction.getProductId());
        if (productOptional.isEmpty()) {
            throw new ProductNotFoundException("Product not found");
        }
        Product product = productOptional.get();

//        Optional<Auction> auctionOp = auctionRepository.findByProduct_ProductId(auction.getProductId());
//        if(auctionOp.isPresent()){
//            throw new AuctionAlreadyExistsException("Auction already exists for this product");
//        }
        List<Auction> overlappingAuction = auctionRepository.findByStartTimeBeforeAndEndTimeAfter(auction.getStartTime(),auction.getEndTime());
        if(!overlappingAuction.isEmpty()){
            throw new AuctionAlreadyExistsInTimeRangeException("Auction already exists within the time range");
        }
        Auction newAuction = new Auction();
        newAuction.setStartTime(auction.getStartTime());
        newAuction.setEndTime(auction.getEndTime());
        newAuction.setReservedPrice(auction.getReservedPrice());
        newAuction.setProduct(product);
        auctionRepository.save(newAuction);
    }
    public void deleteAuction(Long auctionId){
         auctionRepository.deleteById(auctionId);
    }
    public void addProduct(ProductDto product) throws IOException {
        Product product1 = new Product();
        product1.setProductName(product.getProductName());
        product1.setDescription(product.getDescription());
        product1.setSku(product.getSku());
        productRepository.save(product1);

    }

    public void deleteProduct(Long productId){
        productRepository.deleteById(productId);
    }

    public void registerAdmin(UserRegisterDto user) throws Exception {
        log.info("Admin added");
        if (userRepository.findByUsername(user.getUsername()) != null) {
            throw new UserAlreadyExistsException("Username is already exists");
        }
        Users user1 = new Users();
        user1.setEmail(user.getEmail());
        user1.setUsername(user.getUsername());
        user1.setPassword(encoder.encode(user.getPassword()));
        user1.setRole("ADMIN");
        userRepository.save(user1);
    }
    public List<Users> getAllUsers() {
        return userRepository.findAll();
    }

    public Users getUserById(Long userId) {
        return userRepository.findById(userId).orElse(null);
    }

    public Users findByUsername(String username) {
        return userRepository.findByUsername(username);
    }
}













//    public Product createProduct(ProductDto productDto){
//        Product product = new Product();
//        product.setProductName(productDto.getProductName());
//        product.setDescription(productDto.getDescription());
//        product.setSku(productDto.getSku());
////        product.setImgUrl(product.getImgUrl());
//        return productRepository.save(product);
//    }


//    public List<Users> getAllUsers(){
//        return userRepository.findAll();
//    }