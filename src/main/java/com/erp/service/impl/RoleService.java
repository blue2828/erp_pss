package com.erp.service.impl;

import com.erp.dao.IRoleDao;
import com.erp.entity.PageEntity;
import com.erp.entity.Role;
import com.erp.entity.extraEntity.Role_Permission;
import com.erp.service.IRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    @Override
    @Transactional
    public int editRole(Role role) {
        int flag = 0;
        try {
            flag = roleDao.editRole(role);
        }catch (Exception e) {
            flag = 0;
            e.printStackTrace();
        }
        return flag;
    }

    @Override
    @Transactional
    public int saveRole(List<Role_Permission> role_permissions) {
        List<Role_Permission> allRP = roleDao.getAllRole_Permission();
        for(Role_Permission rp : allRP) {
            for (int i = 0; i < role_permissions.size(); i ++) {
                if (String.valueOf(role_permissions.get(i).getRoleId()).equals(String.valueOf(rp.getRoleId())) &&
                        String.valueOf(role_permissions.get(i).getP_id()).equals(String.valueOf(rp.getP_id()))) {
                    role_permissions.remove(i);
                }
            }
        }
        int flag = 0;
        try {
            switch (role_permissions.size()) {
                case 0 :
                    flag = 1;
                    break;
                default :
                flag = roleDao.saveRole(role_permissions);
            }
        }catch (Exception e) {
            flag = 0;
            e.printStackTrace();
        }
        return flag;
    }

    @Override
    public List<Role_Permission> getAllRole_Permission() {
        List<Role_Permission> list = new ArrayList<Role_Permission>();
        try {
            list = roleDao.getAllRole_Permission();
        }catch (Exception e) {
            list.clear();
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public int deleteRole_Permission(Role_Permission role_permission) {
        int flag = 0;
        try {
            flag = roleDao.deleteRole_Permission(role_permission);
        }catch (Exception e) {
            flag = 0;
            e.printStackTrace();
        }
        return flag;
    }
}
