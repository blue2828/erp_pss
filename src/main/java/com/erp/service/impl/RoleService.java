package com.erp.service.impl;

import com.erp.dao.IRoleDao;
import com.erp.entity.PageEntity;
import com.erp.entity.Role;
import com.erp.service.IRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class RoleService implements IRoleService{
    @Autowired
    private IRoleDao roleDao;
    @Override
    public List<Role> getRoleById(int roleId) {
        List<Role> list = new ArrayList<Role>();
        try {
            list = roleDao.getRoleById(roleId);
        }catch (Exception e) {
            list.clear();
        }
        return list;
    }

    @Override
    public List<Role> queryAllRoles(PageEntity pageEntity) {
        List<Role> list = new ArrayList<Role>();
        try {
            list = roleDao.queryAllRoles(pageEntity);
        }catch (Exception e) {
            list.clear();
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public int countAllRoles(PageEntity page) {
        int count = 0;
        try {
            count = roleDao.countAllRoles(page);
        }catch (Exception e) {
            count = 0;
            e.printStackTrace();
        }
        return count;
    }

    @Override
    public Role getOnlyRoleById(int id) {
        Role role = null;
        try {
            role = roleDao.getOnlyRoleById(id);
        }catch (Exception e) {
            role = null;
            e.printStackTrace();
        }
        return role;
    }
}
