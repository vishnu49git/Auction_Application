package com.myauction.auction.exception;

public class AuctionNotFoundException extends RuntimeException{
    public AuctionNotFoundException(String message){
        super(message);
    }
}
