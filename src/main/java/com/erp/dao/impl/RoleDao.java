package com.erp.dao.impl;

import com.erp.dao.IRoleDao;
import com.erp.entity.PageEntity;
import com.erp.entity.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
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
}
