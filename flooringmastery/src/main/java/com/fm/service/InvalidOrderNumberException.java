package com.fm.service;

public class InvalidOrderNumberException extends Exception {

    InvalidOrderNumberException(String message) {
        super(message);
    }

    InvalidOrderNumberException(String message, Throwable cause) {
        super(message, cause);
    }

}