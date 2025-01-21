package com.myauction.auction.service;
import com.myauction.auction.dto.BidDto;
import com.myauction.auction.exception.*;
import com.myauction.auction.model.Auction;
import com.myauction.auction.model.Bid;
import com.myauction.auction.model.Users;
import com.myauction.auction.repository.AuctionRepository;
import com.myauction.auction.repository.BidRepository;
import com.myauction.auction.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class AuctionService {

    @Autowired
    private AuctionRepository auctionRepository;

    @Autowired
    private BidRepository bidRepository;

    @Autowired
    private UserRepository userRepository;

    public List<Auction> getAllAuctions() {
        return auctionRepository.findAll();
    }

    public Auction getAuctionById(Long auctionId){
        return auctionRepository.findById(auctionId).orElseThrow(()->new AuctionNotFoundException("Auction Not Found"));
    }
    @Transactional
    public void placeBid(BidDto bidDto) throws Exception {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            throw new AuthenticationException("User not authenticated");
        }
        String username = authentication.getName();

        Optional<Users> userOptional = Optional.ofNullable(userRepository.findByUsername(username));
        if (userOptional.isEmpty()) {
            throw new UserNotFoundException("User not found");
        }
        Users user = userOptional.get();

        Optional<Auction> auctionOptional = auctionRepository.findById(bidDto.getAuctionId());

        if (auctionOptional.isPresent()) {
            Auction auction = auctionOptional.get();
            if(LocalDateTime.now().isBefore(auction.getEndTime())) {
                if(bidDto.getBidAmount() > auction.getReservedPrice()) {
                    if (bidDto.getBidAmount() > auction.getHighestBid()) {

                        Bid bid = new Bid();
                        bid.setUsers(user);
                        bid.setAuction(auction);
                        bid.setBidAmount(bidDto.getBidAmount());
                        bidRepository.save(bid);

                        auction.setHighestBid(bidDto.getBidAmount());
                        auction.setHighestBidder(user.getUsername());
                        auction.setNumberOfBids(auction.getNumberOfBids() + 1);
                        auctionRepository.save(auction);


                    } else {
                        throw new InvalidBidException("Bid must be higher than the current Bid");
                    }
                }else{
                    throw new LowerBidException("Bid must be higher than the Reserved Price");
                }
            }else{
                throw new AuctionClosedException("Auction Time exceed");
            }
        }else {
            throw new AuctionNotFoundException("Auction not found");
        }
    }
    public List<Bid> getAllBidsByAuctionId(Long auctionId) {

        return bidRepository.findByAuctionAuctionId(auctionId);
    }
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
