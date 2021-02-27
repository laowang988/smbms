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
        if(user==null || !user.getUserPassword().equals("password")) {
            return null;
        }else {
            return user;
        }
    }
    public boolean updatePassword(int id, String password){
        Connection conn = null;
        boolean success = false;
        try{
            conn=BaseDao.getConnection();
            if (userDao.updatePassword(conn,id,password)>0)
                success = true;
            }catch (SQLException e){
                e.printStackTrace();
            }finally{
                BaseDao.closeResource(conn,null,null);
            }

        return success;
    }
    @Test
    public void test(){
        UserServiceImpl userService = new UserServiceImpl();
        User user = userService.login("admin", "1234567");
        if (user == null) {
            System.out.println("用户名或者密码错误");
        }else{
            System.out.println(user.getUserPassword());
        }
    }
}
