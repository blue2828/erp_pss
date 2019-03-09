package com.erp.dao;

import com.erp.entity.Customer;
import com.erp.entity.PageEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

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
    @Select("select * from tb_sale_customer where id = #{id}")
    Customer getCustomerById (@Param("id") int id);
    @Update("<script>" +
                "<if test=\"onlyEditArrears\">" +
                    "update tb_sale_customer set arrears = #{cus.arrears} where id = #{cus.id}" +
                "</if>" +
                "<if test=\"!onlyEditArrears\">" +
                    "update tb_sale_customer set cusName = #{cus.cusName}, linkMan = #{cus.linkMan}, mobile = #{cus.mobile}, " +
                    "address = #{cus.address} arrears = #{cus.arrears} where id = #{cus.id}" +
                "</if>" +
            "</script>")
    int editCustomer(@Param("cus") Customer cus, @Param("onlyEditArrears") boolean onlyEditArrears);
    @Select("select arrears from tb_sale_customer where id = #{id}")
    String getLastArrears (String id);
}
