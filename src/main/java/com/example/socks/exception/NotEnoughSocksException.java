package com.example.socks.exception;

public class NotEnoughSocksException extends RuntimeException {
    public NotEnoughSocksException(String message) {
        super(message);
    }
}
