package com.myauction.auction.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Bid {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long bidId;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private Users users;
    @ManyToOne
    @JoinColumn(name = "auction_id")
    private Auction auction;

    private double bidAmount;


//    public Bid(Long bidId, Long userId, double bidAmount, Auction auction) {
//        this.bidId=bidId;
//        this.bidAmount=bidAmount;
//        this.auction=auction;
//    }
}



