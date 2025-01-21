package com.myauction.auction.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class BidDto {
    private Long auctionId;
    private double bidAmount;
}
