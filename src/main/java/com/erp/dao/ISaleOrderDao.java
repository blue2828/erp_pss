package com.erp.dao;

import com.erp.entity.*;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface ISaleOrderDao {
    @Select("<script>SELECT s_o.*, g.*, r.`repoName` FROM tb_sale_order s_o LEFT JOIN tb_goods g ON s_o.goodsId = g.g_id LEFT JOIN " +
                "tb_repo r ON r.`id` = s_o.`repoId` LEFT JOIN tb_employee e ON s_o.employeeId = e.id LEFT JOIN tb_user u ON" +
                " s_o.checkAccount = u.id LEFT JOIN tb_sale_customer s_c ON s_o.customerId = s_c.id" +
                "<if test=\"goods.goodsName != null and goods.goodsName != \'\'\"> and g.goodsName like concat('%', #{goods.goodsName}, '%')</if>" +
                "<if test=\"employee.empName != null and employee.empName != \'\' \"> and e.empName like concat('%', #{employee.empName}, '%')</if>" +
                "<if test=\"repo.repoName != null and repo.repoName != \'\' \"> and r.repoName like concat('%', #{repo.repoName}, '%')</if>" +
                "<if test=\"s_order.orderNumber != null and s_order.orderNumber != \'\' \"> and s_o.orderNumber like concat('%', #{s_order.orderNumber}, '%')</if>" +
                "<if test=\"s_order.s_o_type != -1\"> and s_o.s_o_type = #{s_order.s_o_type}</if>" +
                "<if test=\"customer.cusName != null and customer.cusName != \'\'\"> and s_c.cusName like concat('%', #{customer.cusName}, '%')</if>" +
                "<if test=\"s_order.checkState != -1\"> and s_o.checkState = #{s_order.checkState}</if>" +
            "</script>"
            )
    @Results({
            @Result(
                    column = "repoId",
                    property = "repository",
                    one = @One(select = "com.erp.dao.IRepositoryDao.getRepoById")
            ),
            @Result(
                    column = "goodsId",
                    property = "goods",
                    one = @One(select = "com.erp.dao.IGoodsDao.getGoodsById")
            ),
            @Result(
                    column = "customerId",
                    property = "customer",
                    one = @One(select = "com.erp.dao.ICustomerDao.getCustomerById")
            ),
            @Result(
                    column = "employeeId",
                    property = "employee",
                    one = @One(select = "com.erp.dao.IEmployeeDao.getEmployeeById")
            ),
            @Result(
                    column = "checkAccount",
                    property = "user",
                    many = @Many(select = "com.erp.dao.IUserDao.getUserById")
            )
    })
    List<SaleOrder> queryAllSaleOrder (@Param("goods") Goods goods, @Param("s_order") SaleOrder s_order, @Param("employee") Employee employee, @Param("repo") com.erp.entity.Repository repo, @Param("customer") Customer customer, @Param("pageEntity") PageEntity pageEntity);
    @Select("<script>SELECT count(*) FROM tb_sale_order s_o LEFT JOIN tb_goods g ON s_o.goodsId = g.g_id LEFT JOIN " +
                "tb_repo r ON r.`id` = s_o.`repoId` LEFT JOIN tb_employee e ON s_o.employeeId = e.id LEFT JOIN tb_user u ON" +
                " s_o.checkAccount = u.id LEFT JOIN tb_sale_customer s_c ON s_o.customerId = s_c.id" +
                "<if test=\"goods.goodsName != null and goods.goodsName != \'\'\"> and g.goodsName like concat('%', #{goods.goodsName}, '%')</if>" +
                "<if test=\"employee.empName != null and employee.empName != \'\' \"> and e.empName like concat('%', #{employee.empName}, '%')</if>" +
                "<if test=\"repo.repoName != null and repo.repoName != \'\' \"> and r.repoName like concat('%', #{repo.repoName}, '%')</if>" +
                "<if test=\"s_order.orderNumber != null and s_order.orderNumber != \'\' \"> and s_o.orderNumber like concat('%', #{s_order.orderNumber}, '%')</if>" +
                "<if test=\"s_order.s_o_type != -1\"> and s_o.s_o_type = #{s_order.s_o_type}</if>" +
                "<if test=\"customer.cusName != null and customer.cusName != \'\'\"> and s_c.cusName like concat('%', #{customer.cusName}, '%')</if>" +
                "<if test=\"s_order.checkState != -1\"> and s_o.checkState = #{s_order.checkState}</if>" +
            "</script>")
    int countAllSaleOrder (@Param("goods") Goods goods, @Param("s_order") SaleOrder s_order, @Param("employee") Employee employee, @Param("repo") com.erp.entity.Repository repo, @Param("customer") Customer customer, @Param("pageEntity") PageEntity pageEntity);
    @Select("select * from tb_sale_order where id = #{id}")
    SaleOrder getSaleOrderById (@Param("id") int id);

    @Select("<script>" +
            "select * from tb_sale_order where s_o_type = 0 and checkState = 4" +
            "<if test=\"queryTimeStr != null and queryTimeStr != \'\'\">" +
            " and creatime between #{queryTimeStr[0]} and #{queryTimeStr[1]}" +
            "</if>" +
            "</script>")
    List<SaleOrder> queryAllSOrderByCon (@Param("queryTimeStr") String[] queryTimeStr);
    @Select("<script>" +
            "select count(*) as num from tb_sale_order where s_o_type = 0 and checkState = 4" +
            "<if test=\"queryTimeStr != null and queryTimeStr != \'\'\">" +
            " and creatime between #{queryTimeStr[0]} and #{queryTimeStr[1]}" +
            "</if>" +
            "</script>")
    int countAllOrderByCon (@Param("queryTimeStr") String[] queryTimeStr);
}
