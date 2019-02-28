package com.erp.dao;

import com.erp.entity.Goods;
import com.erp.entity.PageEntity;
import com.erp.entity.Repository;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface IRepositoryDao {
    @Select("<script>" +
                "SELECT * FROM tb_repo r LEFT JOIN tb_user u ON r.`adminId` = u.`id` where 1 = 1 " +
                "<if test=\"orderOrName != null and orderOrName != \'\'\"> and repoName like concat('%', #{orderOrName}, '%')" +
                "   or repoCode like concat('%', #{orderOrName}, '%')" +
                "</if>" +
                " limit #{pageEntity.start}, #{pageEntity.pageSize}" +
            "</script>")
    @Results({
            @Result(
                    property = "employee",
                    column = "adminId",
                    one = @One(select = "com.erp.dao.IEmployeeDao.getEmployeeById")
            )
    })
    List<Repository> queryAllRepo(@Param("orderOrName") String orderOrName, @Param("pageEntity") PageEntity pageEntity);
    @Select("<script>" +
            "SELECT * FROM tb_repo r LEFT JOIN tb_user u ON r.`adminId` = u.`id` where 1 = 1" +
            "<if test=\"orderOrName != null and orderOrName != \'\'\"> and repoName like concat('%', #{orderOrName}, '%')" +
            "   or repoCode like concat('%', #{orderOrName}, '%')" +
            "</if>" +
            " limit #{pageEntity.start}, #{pageEntity.pageSize}" +
            "</script>")
    int countAllRepo(@Param("orderOrName") String orderOrName, @Param("pageEntity") PageEntity pageEntity);
    @Select("select * from tb_repo where id = #{id}")
    Repository getRepoById(@Param("id") int id);
}
