package com.example.jobs.service;

import com.example.jobs.model.User;

public interface UserService {

    public User findUserByJwtToken(String jwt) throws Exception;

    public User findUserByEmail(String email)throws Exception;
}
