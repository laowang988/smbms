package com.kuang.dao.user;

import com.kuang.dao.BaseDao;
import com.kuang.pojo.User;

import com.mysql.jdbc.StringUtils;
import org.junit.Test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserDaoImpl implements UserDao{

    public int getUserCount(Connection connection, String userName, int userRole) throws SQLException {
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        int count = 0;
        if (connection!=null){
           StringBuffer sql = new StringBuffer();
           sql.append("select count(1) as count from smbms_user u,smbms_role r where u.userRole=r.id");
           ArrayList<Object> list = new ArrayList<Object>();
            if (!StringUtils.isNullOrEmpty(userName)) {
                sql.append(" and u.userName like ?");
                list.add("%"+userName+"%");
            }
            if(userRole>0){
                sql.append(" and r.id=?");
                list.add(userRole);
            }
            Object[] params = list.toArray();
            System.out.println("UserDaoImpl-->getUserCount:"+sql.toString());
            pstmt = connection.prepareStatement(sql.toString());
            rs = BaseDao.execute(connection,pstmt,params);

            if(rs.next()){
                count = rs.getInt("count");
            }
            BaseDao.closeResource(null,pstmt,rs);
        }
        return count;
    }

    public List<User> getUserList(Connection connection, String userName, int userRole, int currentPageNo, int pageSize) {
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        ArrayList<Object> list = new ArrayList<Object>();
        List<User> userList = new ArrayList<User>();
        try {
            if (connection != null) {
                StringBuffer sql = new StringBuffer();
                sql.append("select u.*,r.roleName as userRoleName from smbms_user u,smbms_role r where u.userRole=r.id ");
                if (!StringUtils.isNullOrEmpty(userName)) {
                    sql.append(" and u.userName like ?");
                    list.add("%" + userName + "%");
                }
                if (userRole > 0) {
                    sql.append(" and r.id=?");
                    list.add(userRole);
                }
                // 排序和分页
                // 分页：limit 开始下标, 页面大小
                // 假如每页3个数据，我想从第2页开始: 第一页 0 1 2, 第二页 3 4 5
                // 开始下标为 2 - 1 = 1, 1 * 3 = 3
                // limit 3, 3
                sql.append(" order by creationDate DESC limit ?,?");
                currentPageNo = (currentPageNo-1)*pageSize;
                list.add(currentPageNo);
                list.add(pageSize);
                Object[] params = list.toArray();
                pstmt = connection.prepareStatement(sql.toString());
                rs = BaseDao.execute(connection, pstmt, params);
                while (rs.next()){
                    User user = new User();
                    user.setId(rs.getInt("id"));
                    user.setUserName(rs.getString("userName"));
                    user.setUserCode(rs.getString("userCode"));
                    user.setPhone(rs.getString("phone"));
                    user.setBirthday(rs.getDate("birthday"));
                    user.setGender(rs.getInt("gender"));
                    userList.add(user);
                }
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
        return userList;
    }

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
//        int userRole = 3;
//        List<User> list = new ArrayList<User>();
//        try {
//            list = getUserList(conn,null,userRole,1,5);
//
//            for(User u:list){
//                System.out.println("Name:" + u.getUserName());
//            }
//        }catch(Exception e){
//            e.printStackTrace();
//        }
//    }
}
