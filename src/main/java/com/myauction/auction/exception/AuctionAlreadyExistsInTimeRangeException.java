package com.myauction.auction.exception;

public class AuctionAlreadyExistsInTimeRangeException extends RuntimeException{
    public AuctionAlreadyExistsInTimeRangeException(String message){
        super(message);
    }
}
