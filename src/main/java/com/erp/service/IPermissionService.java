package com.erp.service;

import com.erp.entity.permission;

import java.util.List;
import java.util.Set;

public interface IPermissionService {
    Set<permission> queryAllPsById(int roleId);
    List<permission> getAllPermissionOnly();
    List<permission> queryPsByRoleId(int roleId);
}
