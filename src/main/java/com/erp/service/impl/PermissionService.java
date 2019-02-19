package com.erp.service.impl;

import com.erp.dao.IPermissionDao;
import com.erp.entity.permission;
import com.erp.service.IPermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class PermissionService implements IPermissionService {
    @Autowired
    private IPermissionDao permissionDao;
    @Override
    public Set<permission> queryAllPsByRoleId(int roleId) {
        Set<permission> list = new HashSet<>();
        try {
            list = permissionDao.queryAllPsByRoleId(roleId);
        }catch (Exception e) {
            list.clear();
            e.printStackTrace();
        }
        return list;
    }
}
