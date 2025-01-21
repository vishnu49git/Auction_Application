package com.myauction.auction.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Auction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "auction_id")
    private Long auctionId;

    @OneToOne
    @JoinColumn(name = "product_id")
    private Product product;
    private int numberOfBids = 0;
    private double highestBid = 0;
    private String highestBidder;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private double reservedPrice;

}
