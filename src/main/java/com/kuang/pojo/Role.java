package com.kuang.pojo;

import java.util.*;

public class Role {
    private int id;
    private String roleCode;
    private String roleName;
    private long createdBy;
    private Date creationDate;
    private long modifyBy;
    private Date modifyDate;

    public int getId() {
        return id;
    }

    public void setId(int id) { this.id = id; }

    public String getRoleCode() { return roleCode; }

    public void setRoleCode(String roleCode) { this.roleCode = roleCode; }

    public String getRoleName() { return roleName; }

    public void setRoleName(String roleName) { this.roleName = roleName; }

    public long getCreatedBy() { return createdBy; }

    public void setCreatedBy(long createdBy) { this.createdBy = createdBy; }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public long getModifyBy() {
        return modifyBy;
    }

    public void setModifyBy(long modifyBy) {
        this.modifyBy = modifyBy;
    }

    public Date getModifyDate() {
        return modifyDate;
    }

    public void setModifyDate(Date modifyDate) {
        this.modifyDate = modifyDate;
    }
}
