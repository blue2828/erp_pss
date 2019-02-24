package com.erp.entity.extraEntity;

public class Role_Permission implements java.io.Serializable{
    private int roleId, p_id;

    public Role_Permission() {
    }

    public Role_Permission(int roleId, int p_id) {
        this.roleId = roleId;
        this.p_id = p_id;
    }

    public int getRoleId() {
        return roleId;
    }

    public void setRoleId(int roleId) {
        this.roleId = roleId;
    }

    public int getP_id() {
        return p_id;
    }

    public void setP_id(int p_id) {
        this.p_id = p_id;
    }
}
