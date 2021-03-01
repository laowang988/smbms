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
        Connection conn=null;
        User user=null;

        try {
            conn = BaseDao.getConnection();
            user = userDao.getLoginUser(conn,userCode);
        } catch (SQLException e) {
            e.printStackTrace();
        }finally{
            BaseDao.closeResource(conn,null,null);
        }
        if((user==null)|| !user.getUserPassword().equals(password)) {
            return null;
        }

        return user;

    }
    public boolean updatePassword(int UserId, String newPassword){
        Connection conn = null;
        boolean success = false;
        try{
            conn=BaseDao.getConnection();
            if (userDao.updatePassword(conn,UserId,newPassword)>0)
                success = true;
            }catch (SQLException e){
                e.printStackTrace();
            }finally{
                BaseDao.closeResource(conn,null,null);
            }

        return success;
    }

    public int getUserCount(String userName, int userRole) {
        Connection conn = null;
        int userCount = 0;
        try{
             conn=BaseDao.getConnection();
             if (conn!=null){
                userCount = userDao.getUserCount(conn,userName,userRole);
             }
        }catch (SQLException e){
            e.printStackTrace();
        }finally{
            BaseDao.closeResource(conn,null,null);
        }
        return userCount;
    }
//    @Test
//    public void test(){
//        UserService userService = new UserServiceImpl();
//        int userCount = userService.getUserCount("系统管理员",0);
//            System.out.println(userCount);
//    }


}
