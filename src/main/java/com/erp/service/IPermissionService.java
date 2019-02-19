package com.erp.service;

import com.erp.entity.permission;

import java.util.List;
import java.util.Set;

public interface IPermissionService {
    Set<permission> queryAllPsByRoleId(int roleId);
}
