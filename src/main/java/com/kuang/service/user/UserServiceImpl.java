package com.kuang.service.user;

import com.kuang.dao.BaseDao;
import com.kuang.dao.user.UserDao;
import com.kuang.dao.user.UserDaoImpl;
import com.kuang.pojo.User;
import org.junit.Test;

import java.sql.Connection;
import java.sql.SQLException;

public class UserServiceImpl implements UserService {
    private UserDao userDao;
    public UserServiceImpl(){
        userDao = new UserDaoImpl();
    }

    public User login(String userCode, String password) {
        Connection conn = null;
        User user = null;

        try {
            conn = BaseDao.getConnection();
            user = userDao.getLoginUser(conn,userCode);
        } catch (SQLException e) {
            e.printStackTrace();
        }finally{
            BaseDao.closeResource(conn,null,null);
        }
        return user;
    }
    @Test
    public void test(){
        UserServiceImpl userService = new UserServiceImpl();
        User user = userService.login("admin", "123456");
        System.out.println(user.getUserPassword());
    }
}
