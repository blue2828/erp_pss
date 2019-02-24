package com.erp.dao;

import com.erp.entity.Employee;
import com.erp.entity.PageEntity;
import com.erp.entity.permission;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import java.util.List;
import java.util.Set;

@Mapper
public interface IPermissionDao {
    @Select("SELECT * FROM tb_permission p JOIN tb_role_permission rp ON p.id = rp.`p_id` JOIN tb_role r ON rp.`roleId` = r.id " +
            "where p.id = #{id}")
    Set<permission> queryAllPsById (@Param("id") int id);
    @Select("select * from tb_permission")
    List<permission> getAllPermissionOnly ();
    @Select("SELECT p.* FROM tb_permission p JOIN tb_role_permission rp ON p.id = rp.`p_id` JOIN tb_role r ON rp.`roleId` = r.id " +
            "where r.id = #{roleId}")
    List<permission> queryPsByRoleId(@Param("roleId") int roleId);
}
