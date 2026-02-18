package com.example.zeraki.Exceptions;

public class NotFoundException extends RuntimeException{
    public NotFoundException (String message)  {
        super(message);
    }
}
