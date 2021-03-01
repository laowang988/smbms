package com.kuang.dao.role;

import com.kuang.dao.BaseDao;
import com.kuang.pojo.Role;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class RoleDaoImpl implements RoleDao{
    public List<Role> getRoleList(Connection conn) throws SQLException {
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        ArrayList<Role> list = new ArrayList<Role>();
        if(conn != null){
            String sql = "select * from smbms_role";
            Object[] params = {};
            pstmt = conn.prepareStatement(sql);
            rs = BaseDao.execute(conn,pstmt,params);
            while(rs.next()){
                Role role = new Role();
                role.setRoleName(rs.getString("roleName"));
                role.setId(rs.getInt("id")) ;
                role.setRoleCode("roleCode");
                list.add(role);
            }
            BaseDao.closeResource(null,pstmt,rs);
        }
        return null;
    }
}
