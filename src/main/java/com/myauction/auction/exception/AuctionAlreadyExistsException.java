package com.myauction.auction.exception;

public class AuctionAlreadyExistsException extends RuntimeException{
    public AuctionAlreadyExistsException(String message){
        super(message);
    }
}
