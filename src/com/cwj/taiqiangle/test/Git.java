package com.cwj.taiqiangle.test;

import com.cwj.taiqiangle.service.UserService;

import java.sql.SQLException;

public class Git {
    public static void main(String[] args){
        UserService us = new UserService();
        try {
            us.addUser("jason1234","jason1234","1123781124@qq.com",null,null,0);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
