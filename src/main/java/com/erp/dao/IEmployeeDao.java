package com.erp.dao;

import com.erp.entity.Employee;
import com.erp.entity.PageEntity;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface IEmployeeDao {
    @Select("select * from tb_employee where id = #{id}")
    Employee getEmployeeById (@Param("id") int id);
    @Select("SELECT * FROM tb_employee e LEFT JOIN tb_user u ON e.`updaterId` = u.`id` LIMIT" +
            " #{page.start},#{page.pageSize}")
    @Results({
            @Result(
                    property = "user",
                    column = "updaterId",
                    many = @Many(select = "com.erp.dao.IUserDao.getUserById")
            )
    })
    List<Employee> queryAllEmployee (@Param("page")PageEntity page);
    @Select("select count(*) from tb_employee limit #{page.start},#{page.pageSize}")
    int countEmployee (@Param("page")PageEntity page);
}
