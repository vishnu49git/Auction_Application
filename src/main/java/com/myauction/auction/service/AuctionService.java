package com.myauction.auction.service;

import com.myauction.auction.dto.BidDto;
import com.myauction.auction.model.Auction;
import com.myauction.auction.model.Bid;
import com.myauction.auction.model.Users;
import com.myauction.auction.repository.AuctionRepository;
import com.myauction.auction.repository.BidRepository;
import com.myauction.auction.repository.ProductRepository;
import com.myauction.auction.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service

public class AuctionService {

    @Autowired
    private AuctionRepository auctionRepository;

    @Autowired
    private BidRepository bidRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private UserRepository userRepository;

    public List<Auction> getAllAuctions() {
        return auctionRepository.findAll();
    }



    public Auction getAuctionById(Long auctionId){
        return auctionRepository.findById(auctionId).orElse(null);
    }


//    public String placeBid(BidDto bidDto) {
//
//
//        Optional<Auction> auctionOptional = auctionRepository.findById(bidDto.getAuctionId());
//
//        if (auctionOptional.isPresent()) {
//            Auction auction = auctionOptional.get();
//
//            Optional<Users> userOptional = userRepository.findById(bidDto.getUserId());
//            if (userOptional.isPresent()) {
//                Users user = userOptional.get();
//                if (bidDto.getBidAmount() > auction.getHighestBid()) {
//                    Bid bid = new Bid();
//                    bid.setUsers(user);
//                    bid.setAuction(auction);
//                    bid.setBidAmount(bidDto.getBidAmount());
//                    bidRepository.save(bid);
//
//                    auction.setHighestBid(bidDto.getBidAmount());
//                    auctionRepository.save(auction);
//
//
//                    auction.setHighestBidder(bidDto.getUserId());
//                    auctionRepository.save(auction);
//                    auction.setNumberOfBids(auction.getNumberOfBids() + 1);
//                    auctionRepository.save(auction);
//                    return "Bid placed successfully!";
//                } else {
//                    return "Bid must be higher than the current highest bid.";
//                }
//            }else{
//                return "User not found";
//            }
//        }
//        return "Auction not found.";
//    }
public String placeBid(BidDto bidDto) {

    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    String username = authentication.getName(); // This assumes username is used as the unique identifier


    Optional<Users> userOptional = Optional.ofNullable(userRepository.findByUsername(username));
    if (userOptional.isEmpty()) {
        return "User not found.";
    }
    Users user = userOptional.get();


    Optional<Auction> auctionOptional = auctionRepository.findById(bidDto.getAuctionId());

    if (auctionOptional.isPresent()) {
        Auction auction = auctionOptional.get();

        if (bidDto.getBidAmount() > auction.getHighestBid()) {

            Bid bid = new Bid();
            bid.setUsers(user);
            bid.setAuction(auction);
            bid.setBidAmount(bidDto.getBidAmount());
            bidRepository.save(bid);


            auction.setHighestBid(bidDto.getBidAmount());
            auction.setHighestBidder(user.getId());
            auction.setNumberOfBids(auction.getNumberOfBids() + 1);
            auctionRepository.save(auction);

            return "Bid placed successfully!";
        } else {
            return "Bid must be higher than the current highest bid.";
        }
    }
    return "Auction not found.";
}
    public List<Bid> getAllBidsByAuctionId(Long auctionId) {

        return bidRepository.findByAuctionAuctionId(auctionId);
    }
}
