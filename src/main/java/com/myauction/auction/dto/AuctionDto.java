package com.myauction.auction.dto;


import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class AuctionDto {

    private Long productId;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private double reservedPrice;
}
