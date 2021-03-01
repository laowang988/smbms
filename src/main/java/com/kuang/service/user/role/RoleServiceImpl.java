package com.kuang.service.user.role;

import com.kuang.dao.BaseDao;
import com.kuang.dao.role.RoleDao;
import com.kuang.dao.role.RoleDaoImpl;
import com.kuang.pojo.Role;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class RoleServiceImpl implements RoleService{
    private RoleDao roleDao;

    public RoleServiceImpl(){
        this.roleDao = new RoleDaoImpl();
    }
    public List<Role> getRoleList() {
        Connection conn = null;
        List<Role> roleList = null;

        try{
            conn = BaseDao.getConnection();
            roleList = roleDao.getRoleList(conn);
        }catch(SQLException e){
            e.printStackTrace();
        }finally{
            BaseDao.closeResource(conn,null,null);
        }
        return roleList;
    }
}
