package com.erp.dao;

import com.erp.entity.PageEntity;
import com.erp.entity.User;
import org.apache.ibatis.annotations.*;
import java.util.List;

@Mapper
public interface IUserDao {
    @Select("<script>" +
                "SELECT u.*, u_r.roleId FROM tb_user u JOIN tb_user_role u_r ON u.id = u_r.`uid` JOIN tb_role r ON" +
                " r.id = u_r.`roleId` where 1 = 1 " +
                "<if test=\"user.userOrder != null\"> and u.userOrder like concat('%', #{user.userOrder}, '%')</if>" +
                "<if test=\"user.userName != null\"> and u.userName like concat('%', #{user.userName}, '%')</if>" +
                " limit #{page.start}, #{page.pageSize}" +
            "</script>" )
    @Results({
            @Result(property = "roleList",
                    column = "roleId",
                    many = @Many(select = "com.erp.dao.IRoleDao.getRoleById")
            ),
            @Result(property = "user",
                    column = "updaterId",
                    one = @One(select = "com.erp.dao.IUserDao.getUserById")
            ),
            @Result(property = "employee",
                    column = "employeeId",
                    one = @One(select = "com.erp.dao.IEmployeeDao.getEmployeeById")
            )
    })
    public List<User> getAllUser (@Param("page") PageEntity page, @Param("user") User user);

    @Select("<script>" +
                "SELECT COUNT(*) AS total FROM tb_user where 1 = 1" +
                "<if test=\"user.userOrder != null\"> and userOrder like concat('%', #{user.userOrder}, '%')</if>" +
                "<if test=\"user.userName != null\"> and userName like concat('%', #{user.userName}, '%')</if>" +
                " LIMIT #{page.start}, #{page.pageSize}" +
            "</script>")
    public int countAllUser (@Param("page") PageEntity pageEntity, @Param("user") User user);

    @Select("<script>SELECT u.*, u_r.roleId FROM tb_user u JOIN tb_user_role u_r ON u.id = u_r.`uid` JOIN tb_role r ON" +
            " r.id = u_r.`roleId` where 1 = 1 " +
            "   <if test=\"user.id != -1\">and u.id = #{user.id}</if>" +
            "   <if test=\"user.userName != null\"> and u.userName = #{user.userName}</if>" +
            "</script>")
    @Results({
            @Result(property = "roleList",
                    column = "roleId",
                    many = @Many(select = "com.erp.dao.IRoleDao.getRoleById")
            ),
            @Result(property = "user",
                    column = "updaterId",
                    many = @Many(select = "com.erp.dao.IUserDao.getUserById")
            ),
            @Result(property = "employee",
                    column = "employeeId",
                    one = @One(select = "com.erp.dao.IEmployeeDao.getEmployeeById")
            )
    })
    List<User> getUserByUserNameOrId (@Param("user") User user);
    @Select("SELECT u.*, u_r.roleId FROM tb_user u JOIN tb_user_role u_r ON u.`id` = u_r.`uid` JOIN tb_role r ON r.`id` = u_r.`roleId` " +
            "WHERE u.id = #{id}")
    @Results({
            @Result(property = "roleList",
                    column = "roleId",
                    many = @Many(select = "com.erp.dao.IRoleDao.getRoleById")
            )
    })
    List<User> getUserById (@Param("id") int id);
    @Select("select imgHeader from tb_user where id = #{idOrName} or userName = #{idOrName}")
    String getUserImg (@Param("idOrName") String idOrName);

    @Delete("<script>delete from tb_user where id in " +
                "<foreach collection=\"ids\" index=\"index\" item=\"id\" open=\"(\" close=\")\" separator=\",\">" +
                    "#{id}" +
                "</foreach>" +
            "</script>")
    int deleteUser(@Param("ids") String[] ids);
    @Update("update tb_user set imgHeader = #{user.imgHeader} where id = #{user.id}")
    int freshImgHeader (@Param("user") User user);
    @Update("<script>" +
            "   <if test=\"onlyUpdatePwd\">" +
            "       update tb_user set password = #{user.password}" +
            "   </if>" +
            "   <if test=\"!onlyUpdatePwd\">" +
            "       update tb_user set userOrder = #{user.userOrder}, userName = #{user.userName}, " +
            "       employeeId = #{user.employee.id}, updaterId = #{user.user[0].id}, updateTime = #{user.updateTime}" +
            "   </if>" +
            "   where id = #{user.id}" +
            "</script>")
    int updateUserInfo (@Param("user") User user, @Param("onlyUpdatePwd") boolean onlyUpdatePwd);
    @Select("SELECT id FROM tb_user WHERE employeeId = #{empId}")
    int queryIdByEmpId (@Param("empId") int empId);
    @Select("select * from tb_user where userOrder = #{userOrder}")
    User isExistUser (@Param("userOrder") String userOrder);
    @Delete("<script>" +
            "   delete from tb_user_role where uid in " +
            "<foreach collection=\"uids\" index=\"index\" item=\"uid\" open=\"(\" close=\")\" separator=\",\">" +
            "   #{uid}" +
            "</foreach>" +
            "</script>")
    int deleteUser_Role (@Param("uids") String[] uids);
    @Insert("insert into tb_user(userOrder, userName, password, employeeId, updaterId, updateTime, imgHeader) " +
            "values(#{user.userOrder}, #{user.userName}, #{user.password}, #{user.employee.id}, #{user.user[0].id}, #{user.updateTime}, #{user.imgHeader})")
    int addUser (@Param("user") User user);
    @Insert("insert into tb_user_role values(#{userId}, #{roleId})")
    int insertIntoUser_Role(@Param("userId") int userId, @Param("roleId") int roleId);
    @Delete("delete from tb_user_role where uid = #{userId} and roleId = #{roleId}")
    int deleteUser_RoleByEachOther (@Param("userId") int userId, @Param("roleId") int roleId);
    @Select("select max(userOrder) + 1 from tb_user")
    public String getMaxUserOrder ();
    @Select("select * from tb_user where userOrder = #{order}")
    List<User> getUserByOrder (@Param("order") String order);
}
