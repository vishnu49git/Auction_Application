package com.myauction.auction.exception;

public class InvalidBidException extends RuntimeException{
    public InvalidBidException(String message){
        super(message);
    }
}
