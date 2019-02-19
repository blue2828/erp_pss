package com.erp.entity;

import java.util.*;

public class Role implements java.io.Serializable {
    private int id;
    private String roleName, descs, roleCode;
    private Date updateTime;
    private List<User> listUsers;
    private Set<permission> permissionList = new HashSet<permission>();

    public Role() {
    }

    public List<User> getListUsers() {
        return listUsers;
    }

    public void setListUsers(List<User> listUsers) {
        this.listUsers = listUsers;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public String getDescs() {
        return descs;
    }

    public void setDescs(String descs) {
        this.descs = descs;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getRoleCode() {
        return roleCode;
    }

    public void setRoleCode(String roleCode) {
        this.roleCode = roleCode;
    }

    public Set<permission> getPermissionList() {
        return permissionList;
    }

    public void setPermissionList(Set<permission> permissionList) {
        this.permissionList = permissionList;
    }
}
