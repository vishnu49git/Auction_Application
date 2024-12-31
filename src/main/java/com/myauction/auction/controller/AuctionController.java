package com.myauction.auction.controller;

import com.myauction.auction.dto.BidDto;
import com.myauction.auction.model.Auction;
import com.myauction.auction.model.Bid;
import com.myauction.auction.service.AuctionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/auction")
public class AuctionController {
    @Autowired
    private AuctionService auctionService;

    @GetMapping("/allauctions")
    public List<Auction> getAllAuctions(){
        return auctionService.getAllAuctions();
    }

    @GetMapping("{auctionId}")
    public ResponseEntity<?> getAuctionById(@PathVariable Long auctionId){
        Auction auction = auctionService.getAuctionById(auctionId);

        if (auction == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Auction not found");
        }

        return ResponseEntity.ok(auction);

    }

    @PostMapping("/placebid")
//    @PreAuthorize("hasRole('ROLE_USER')")
    public ResponseEntity<String> placeBid(@RequestBody BidDto bidDto){
        String newBid = auctionService.placeBid(bidDto);
        return ResponseEntity.ok(newBid);
    }
    @GetMapping("/AllBids/{auctionId}")
    public List<Bid> getAllBidsByAuctionId(@PathVariable Long auctionId){
        return auctionService.getAllBidsByAuctionId(auctionId);
    }


}
