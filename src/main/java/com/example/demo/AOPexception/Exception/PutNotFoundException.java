package com.example.demo.AOPexception.Exception;

public class PutNotFoundException extends RuntimeException{
    public PutNotFoundException(String msg)
    {
        super(msg);
    }
}
