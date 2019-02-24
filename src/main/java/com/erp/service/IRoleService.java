package com.erp.service;

import com.erp.entity.PageEntity;
import com.erp.entity.Role;
import com.erp.entity.extraEntity.Role_Permission;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
@Mapper
public interface IRoleService {
    List<Role> getRoleById(int roleId);
    List<Role> queryAllRoles (PageEntity pageEntity);
    int countAllRoles(PageEntity page);
    Role getOnlyRoleById(int id);
    int editRole(Role role);
    int saveRole(List<Role_Permission> role_permissions);
    List<Role_Permission> getAllRole_Permission();
    int deleteRole_Permission(Role_Permission role_permission);
}
