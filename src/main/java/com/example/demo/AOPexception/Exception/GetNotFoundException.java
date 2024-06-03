package com.example.demo.AOPexception.Exception;

public class GetNotFoundException extends RuntimeException{
    public GetNotFoundException(String msg)
    {
        super(msg);
    }
}
