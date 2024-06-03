package com.example.demo.AOPexception.Exception;

public class DeleteExistedExcepton extends RuntimeException{
    public DeleteExistedExcepton(String msg)
    {
        super(msg);
    }
}
