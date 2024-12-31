package com.myauction.auction.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BidDto {
    private Long auctionId;
    private double bidAmount;
}
