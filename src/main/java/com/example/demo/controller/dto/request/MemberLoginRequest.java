package com.example.demo.controller.dto.request;

public record MemberLoginRequest(
        String email,
        String password
)
{
}
