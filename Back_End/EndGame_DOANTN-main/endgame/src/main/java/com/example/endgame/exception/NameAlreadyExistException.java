package com.example.endgame.exception;

public class NameAlreadyExistException  extends RuntimeException{

    public String message;

    public NameAlreadyExistException(String message) {
        super(message);
        this.message = message;
    }
}
