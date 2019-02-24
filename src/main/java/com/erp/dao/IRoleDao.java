package com.erp.dao;

import com.erp.entity.PageEntity;
import com.erp.entity.Role;
import com.erp.entity.extraEntity.Role_Permission;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.mapping.FetchType;

import java.util.List;
import java.util.Set;

@Mapper
public interface IRoleDao {
    @Select("SELECT r.*, r_p.p_id FROM tb_role r JOIN tb_role_permission r_p ON r.`id` = r_p.roleId " +
            " JOIN tb_permission p ON p.id = r_p.`p_id` WHERE r.id = #{id}")
    @Results({
            @Result(property = "permissionList",
                    javaType = Set.class,
                    column = "p_id",
                    many = @Many(select = "com.erp.dao.IPermissionDao.queryAllPsById")
            )
    })
    List<Role> getRoleById (@Param("id") int id);
    @Select("select * from tb_role limit #{page.start}, #{page.pageSize}")
    List<Role> queryAllRoles (@Param("page") PageEntity page);
    @Select("select count(*) from tb_role limit #{page.start}, #{page.pageSize}")
    int countAllRoles (@Param("page") PageEntity page);
    @Select("select * from tb_role where id = #{id}")
    Role getOnlyRoleById (@Param("id") int id);
    @Update("update tb_role set roleName = #{role.roleName}, updaterId = #{role.listUsers[0].id}, updateTime = #{role.updateTime} where id = #{role.id}")
    int editRole (@Param("role") Role role);
    @Insert("<script>" +
            "   insert into tb_role_permission(roleId,p_id) values" +
            "   <foreach collection=\"role_permissions\" index=\"index\" item=\"r_p\" separator=\",\">" +
            "       (#{r_p.roleId},#{r_p.p_id})" +
            "   </foreach>" +
            "</script>")
    int saveRole (@Param("role_permissions") List<Role_Permission> role_permissions);
    @Select("select * from tb_role_permission")
    List<Role_Permission> getAllRole_Permission();
    @Delete("DELETE FROM tb_role_permission WHERE roleId = #{role_permissions.roleId} and p_id = #{role_permissions.p_id}")
    int deleteRole_Permission (@Param("role_permissions") Role_Permission role_permission);
}
