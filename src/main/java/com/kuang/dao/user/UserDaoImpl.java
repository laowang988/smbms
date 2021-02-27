package com.kuang.dao.user;

import com.kuang.dao.BaseDao;
import com.kuang.pojo.User;
import org.junit.Test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDaoImpl implements UserDao{

    public User getLoginUser(Connection connection, String userCode) throws SQLException{
        User user = new User();
        if (connection != null) {
            String sql = "select * from smbms_user where userCode=?";
            PreparedStatement pstm = connection.prepareStatement(sql);
            Object[] params = {userCode};

            ResultSet rs = BaseDao.execute(connection, pstm,params);
            if (rs.next()){
             user.setId(rs.getInt("id"));
             user.setUserCode(rs.getString("userCode"));
             user.setUserName(rs.getString("userName"));
             user.setUserPassword(rs.getString("userPassword"));
             user.setAddress(rs.getString("address"));
             user.setPhone(rs.getString("phone"));
             user.setGender(rs.getInt("gender"));
             user.setBirthday(rs.getDate("birthday"));
             user.setUserRole(rs.getLong("userRole"));
             user.setCreateBy(rs.getLong("createdBy"));
             user.setCreationDate(rs.getDate("creationDate"));
             user.setModifyBy(rs.getInt("modifyBy"));
             user.setModifyDate(rs.getDate("modifyDate"));
            }
            BaseDao.closeResource(connection, pstm, rs);
        }
        return user;
    }

    public int updatePassword(Connection connection, int id, String password) throws SQLException {
        PreparedStatement pstm = null;
        int updateRows = 0;
        if (connection != null){
            String sql = "update smbms_user set userPassword=? where id=? ";
            pstm = connection.prepareStatement(sql);
            Object[] params = {password, id};
            updateRows = BaseDao.update(connection, pstm, params);
        }
        return updateRows;
    }

//    @Test
//    public void test(){
//        Connection conn = BaseDao.getConnection();
//        String userCode = "admin";
//        try {
//            User user = getLoginUser(conn, userCode);
//            System.out.println(user.getAddress());
//        }catch(Exception e){
//            e.printStackTrace();
//        }
//    }
}
