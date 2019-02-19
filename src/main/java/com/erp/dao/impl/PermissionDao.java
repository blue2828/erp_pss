package com.erp.dao.impl;

import com.erp.dao.IPermissionDao;
import com.erp.entity.Employee;
import com.erp.entity.PageEntity;
import com.erp.entity.permission;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Repository
public class PermissionDao implements IPermissionDao {
    @Autowired
    private IPermissionDao permissionDao;
    @Override
    public Set<permission> queryAllPsByRoleId(int roleId) {
        return permissionDao.queryAllPsByRoleId(roleId);
    }
}
