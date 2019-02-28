package com.erp.dao;

import com.erp.entity.Customer;
import com.erp.entity.PageEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface ICustomerDao {
    @Select("<script>" +
                "SELECT * FROM tb_sale_customer where 1 = 1 " +
                "<if test=\"cusName != null and cusName != \'\'\">" +
                "   and cusName like concat('%', #{cusName}, '%')" +
                "</if>" +
                " limit #{pageEntity.start}, #{pageEntity.pageSize}" +
            "</script>")
    List<Customer> queryAllCustomer(@Param("cusName") String cusName, @Param("pageEntity") PageEntity pageEntity);
    @Select("<script>" +
                "SELECT count(*) as num FROM tb_sale_customer where 1 = 1 " +
                "<if test=\"cusName != null and cusName != \'\'\">" +
                "   and cusName like concat('%', #{cusName}, '%')" +
                "</if>" +
                " limit #{pageEntity.start}, #{pageEntity.pageSize}" +
            "</script>")
    int countAllCustomer(@Param("cusName") String cusName,  @Param("pageEntity") PageEntity pageEntity);
}
