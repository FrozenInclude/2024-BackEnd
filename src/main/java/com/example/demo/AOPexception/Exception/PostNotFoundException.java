package com.example.demo.AOPexception.Exception;

public class PostNotFoundException extends RuntimeException{
    public PostNotFoundException(String msg)
    {
        super(msg);
    }
}
