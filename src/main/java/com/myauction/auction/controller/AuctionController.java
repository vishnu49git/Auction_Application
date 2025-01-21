package com.myauction.auction.controller;

import com.myauction.auction.dto.AuctionDto;
import com.myauction.auction.dto.BidDto;
import com.myauction.auction.exception.AuctionNotFoundException;
import com.myauction.auction.model.Auction;
import com.myauction.auction.model.Bid;
import com.myauction.auction.service.AuctionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/auction")
public class AuctionController {
    @Autowired
    private AuctionService auctionService;

    @GetMapping("/allAuctions")
    public List<Auction> getAllAuctions(){
        return auctionService.getAllAuctions();
    }

    @GetMapping("/Auction/{auctionId}")
    public ResponseEntity<?> getAuctionById(@PathVariable Long auctionId){
        try {
            Auction auction = auctionService.getAuctionById(auctionId);
            return ResponseEntity.ok(auction);
        } catch (AuctionNotFoundException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
        }
//        Auction auction = auctionService.getAuctionById(auctionId);
//
//        if (auction == null) {
//            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Auction not found");
//        }
//
//        return ResponseEntity.ok(auction);

    }

    @PostMapping("/placeBid")
    @PreAuthorize("hasRole('ROLE_USER')")
    public ResponseEntity<String> placeBid(@RequestBody BidDto bidDto){
        try {
            auctionService.placeBid(bidDto);
            return ResponseEntity.ok("Bid Placed Successfully");
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
    @GetMapping("/allBids/{auctionId}")
    public List<Bid> getAllBidsByAuctionId(@PathVariable Long auctionId){
        return auctionService.getAllBidsByAuctionId(auctionId);
    }


}
