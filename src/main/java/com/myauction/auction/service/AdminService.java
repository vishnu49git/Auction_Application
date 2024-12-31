package com.myauction.auction.service;

import com.myauction.auction.model.Auction;
import com.myauction.auction.dto.AuctionDto;
import com.myauction.auction.model.Product;
import com.myauction.auction.model.Users;
import com.myauction.auction.repository.AuctionRepository;
import com.myauction.auction.repository.ProductRepository;
import com.myauction.auction.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AdminService {
    @Autowired
    private AuctionRepository auctionRepository;
    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private UserRepository userRepository;

    public Auction createAuction(AuctionDto auction) throws Exception {
        Optional<Product> productOptional = productRepository.findById(auction.getProductId());
        if (productOptional.isEmpty()) {
            throw new Exception("Product not found");
        }

        Product product = productOptional.get();
        Auction newAuction = new Auction();

        Optional<Auction> auctionOp = auctionRepository.findByProduct_ProductId(auction.getProductId());
        if(auctionOp.isPresent()){
            throw new Exception("Auction already exists for this product");
        }
        List<Auction> overlappingAuction = auctionRepository.findByStartTimeBeforeAndEndTimeAfter(auction.getStartTime(),auction.getEndTime());
        if(!overlappingAuction.isEmpty()){
            throw new Exception("Auction already exists within the time range");
        }

        newAuction.setStartTime(auction.getStartTime());
        newAuction.setEndTime(auction.getEndTime());
        newAuction.setReservedPrice(auction.getReservedPrice());
        newAuction.setProduct(product);

        return auctionRepository.save(newAuction);

    }

    public void deleteAuction(Long auctionId){
         auctionRepository.deleteById(auctionId);
    }
//    public Product createProduct(ProductDto productDto){
//        Product product = new Product();
//        product.setProductName(productDto.getProductName());
//        product.setDescription(productDto.getDescription());
//        product.setSku(productDto.getSku());
////        product.setImgUrl(product.getImgUrl());
//        return productRepository.save(product);
//    }


    public List<Users> getAllUsers(){
        return userRepository.findAll();
    }

}