package com.demo.payment.service;

import com.demo.payment.mapper.UserMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class UserService {

    @Resource
    private UserMapper userMapper;


    public void getUser(int id) {
        System.out.println(userMapper.getUserById(id));
    }
}
