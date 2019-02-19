package com.erp.service;

import com.erp.entity.PageEntity;
import com.erp.entity.Role;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
@Mapper
public interface IRoleService {
    List<Role> getRoleById(int roleId);
    List<Role> queryAllRoles (PageEntity pageEntity);
    int countAllRoles(PageEntity page);
    Role getOnlyRoleById(int id);
}
