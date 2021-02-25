package com.kuang.dao.user;

import com.kuang.dao.BaseDao;
import com.kuang.pojo.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDaoImpl implements UserDao{
    public User getLoginUser(Connection connection, String userCode) throws SQLException{
        PreparedStatement pstm = null;
        ResultSet rs = null;
        User user = null;

        if (connection != null) {
            String sql = "select * from smbms_user where userCode=?";
            Object[] params = {userCode};

            rs = BaseDao.execute(connection, sql, params, rs, pstm);
            if (rs.next()){
             user = new User();
             user.setId(rs.getInt("id"));
             user.setUserCode(rs.getString("userCode"));
             user.setUserName(rs.getString("userName"));
             user.setUserPassword(rs.getString("userPassword"));
            }
            BaseDao.closeResource(null, pstm, rs);

        }
        return user;
    }
}
