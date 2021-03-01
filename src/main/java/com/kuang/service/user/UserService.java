package com.kuang.service.user;

import com.kuang.pojo.User;

import java.sql.Connection;

public interface UserService {
    public User login(String userCode, String password);
    public boolean updatePassword(int UserId, String newPassword);
    public int getUserCount(String userName,int userRole);
}
