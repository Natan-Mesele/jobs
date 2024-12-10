package com.example.jobs.response;

import com.example.jobs.model.USER_ROLE;
import lombok.Data;

@Data
public class AuthResponse {

    private String jwt;

    private String message;

    private USER_ROLE role;

    private Long userId;

    private String phoneNumber;  // Field to hold the user's phone number
    private String fullName;

}