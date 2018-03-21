package com.avp.iscs.controller;

import com.avp.iscs.entity.User;
import com.avp.iscs.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by guo on 2018/3/10.
 */
@RestController
public class TestController {
    @Autowired
    UserMapper userMapper;

    @RequestMapping(value = "/test",method = RequestMethod.GET)
    public String test(){
        System.out.println("test...");

        List<User> list = userMapper.getUsers();
        System.out.println("len--"+list.size());

        return "test";
    }
}
