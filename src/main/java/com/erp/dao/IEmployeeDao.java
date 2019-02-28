package com.erp.dao;

import com.erp.entity.Employee;
import com.erp.entity.PageEntity;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface IEmployeeDao {
    @Select("select * from tb_employee where id = #{id}")
    Employee getEmployeeById (@Param("id") int id);
    @Select("<script>" +
                "SELECT * FROM tb_employee e LEFT JOIN tb_user u ON e.`updaterId` = u.`id` where 1 = 1" +
                "<if test=\"employee.empName != null and employee.empName != \'\'\">" +
                    "and empName like concat('%', #{employee.empName}, '%')" +
                "</if>" +
                "<if test=\"employee.code != null and employee.code != \'\'\">" +
                    "and code like concat('%', #{employee.code}, '%')" +
                "</if>" +
                "<if test=\"employee.sex != -1\">" +
                    "and sex = #{employee.sex}" +
                "</if>" +
                "<if test=\"employee.birthday != null\">" +
                    "and birthday = #{employee.birthday}" +
                "</if>" +
                " LIMIT #{page.start},#{page.pageSize}" +
            "</script>"
    )
    @Results({
            @Result(
                    property = "user",
                    column = "updaterId",
                    many = @Many(select = "com.erp.dao.IUserDao.getUserById")
            ),
            @Result(
                    property = "selfUser",
                    column = "userId",
                    many = @Many(select = "com.erp.dao.IUserDao.getUserById")
            )
    })
    List<Employee> queryAllEmployee (@Param("employee") Employee employee, @Param("page")PageEntity page);
    @Select("<script>" +
                "SELECT count(*) as num FROM tb_employee e where 1 = 1" +
                "<if test=\"employee.empName != null and employee.empName != \'\'\">" +
                    "and empName like concat('%', #{employee.empName}, '%')" +
                "</if>" +
                "<if test=\"employee.code != null and employee.code != \'\'\">" +
                    "and code like concat('%', #{employee.code}, '%')" +
                "</if>" +
                "<if test=\"employee.sex != -1\">" +
                    "and sex = #{employee.sex}" +
                "</if>" +
                "<if test=\"employee.birthday != null\">" +
                    "and birthday = #{employee.birthday}" +
                "</if>" +
                " LIMIT #{page.start},#{page.pageSize}" +
            "</script>")
    int countEmployee (@Param("employee") Employee employee, @Param("page")PageEntity page);
}
