package com.erp.dao.impl;

import com.erp.dao.IRoleDao;
import com.erp.entity.PageEntity;
import com.erp.entity.Role;
import com.erp.entity.extraEntity.Role_Permission;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository("roleDao")
public class RoleDao implements IRoleDao {
    @Autowired
    private IRoleDao roleDao;
    @Override
    public List<Role> getRoleById(int roleId) {
        return roleDao.getRoleById(roleId);
    }

    @Override
    public List<Role> queryAllRoles(PageEntity page) {
        return roleDao.queryAllRoles(page);
    }

    @Override
    public int countAllRoles(PageEntity page) {
        return roleDao.countAllRoles(page);
    }

    @Override
    public Role getOnlyRoleById(int id) {
        return roleDao.getOnlyRoleById(id);
    }

    @Override
    public int editRole(Role role) {
        return roleDao.editRole(role);
    }

    @Override
    public int saveRole(List<Role_Permission> role_permissions) {
        return roleDao.saveRole(role_permissions);
    }

    @Override
    public List<Role_Permission> getAllRole_Permission() {
        return roleDao.getAllRole_Permission();
    }

    @Override
    public int deleteRole_Permission(Role_Permission role_permission) {
        return roleDao.deleteRole_Permission(role_permission);
    }
    public static void main(String[] args) {
        System.out.println(1000 == 1000.00);
    }
}
