package com.myauction.auction.exception;

public class LowerBidException extends RuntimeException{
    public LowerBidException(String message){
        super(message);
    }
}
