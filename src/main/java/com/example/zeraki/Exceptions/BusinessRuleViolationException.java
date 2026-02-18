package com.example.zeraki.Exceptions;

public class BusinessRuleViolationException extends RuntimeException{
    public BusinessRuleViolationException (String message)  {
        super(message);
    }
}
