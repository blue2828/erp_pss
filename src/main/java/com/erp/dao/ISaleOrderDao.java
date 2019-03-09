package com.erp.dao;

import com.erp.entity.*;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface ISaleOrderDao {
    @Select("<script>SELECT s_o.*, g.*, r.`repoName` FROM tb_sale_order s_o LEFT JOIN tb_goods g ON s_o.goodsId = g.g_id LEFT JOIN " +
                "tb_repo r ON r.`id` = s_o.`repoId` LEFT JOIN tb_employee e ON s_o.employeeId = e.id LEFT JOIN tb_user u ON" +
                " s_o.checkAccount = u.id LEFT JOIN tb_sale_customer s_c ON s_o.customerId = s_c.id where 1 = 1 " +
                "<if test=\"goods.goodsName != null and goods.goodsName != \'\'\"> and g.goodsName like concat('%', #{goods.goodsName}, '%')</if>" +
                "<if test=\"employee.empName != null and employee.empName != \'\' \"> and e.empName like concat('%', #{employee.empName}, '%')</if>" +
                "<if test=\"repo.id != -1 \"> and r.id = #{repo.id}</if>" +
                "<if test=\"s_order.orderNumber != null and s_order.orderNumber != \'\' \"> and s_o.orderNumber like concat('%', #{s_order.orderNumber}, '%')</if>" +
                "<if test=\"s_order.s_o_type != -1\"> and s_o.s_o_type = #{s_order.s_o_type}</if>" +
                "<if test=\"customer.cusName != null and customer.cusName != \'\'\"> and s_c.cusName like concat('%', #{customer.cusName}, '%')</if>" +
                "<if test=\"s_order.checkState != -1\"> and s_o.checkState = #{s_order.checkState}</if>" +
                "<if test=\"goods.g_id != -1\"> and s_o.goodsId = #{goods.g_id}</if>" +
                "limit #{pageEntity.start}, #{pageEntity.pageSize}" +
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
                " s_o.checkAccount = u.id LEFT JOIN tb_sale_customer s_c ON s_o.customerId = s_c.id where 1 = 1 " +
                "<if test=\"goods.goodsName != null and goods.goodsName != \'\'\"> and g.goodsName like concat('%', #{goods.goodsName}, '%')</if>" +
                "<if test=\"employee.empName != null and employee.empName != \'\' \"> and e.empName like concat('%', #{employee.empName}, '%')</if>" +
                "<if test=\"repo.repoName != null and repo.repoName != \'\' \"> and r.repoName like concat('%', #{repo.repoName}, '%')</if>" +
                "<if test=\"s_order.orderNumber != null and s_order.orderNumber != \'\' \"> and s_o.orderNumber like concat('%', #{s_order.orderNumber}, '%')</if>" +
                "<if test=\"s_order.s_o_type != -1\"> and s_o.s_o_type = #{s_order.s_o_type}</if>" +
                "<if test=\"customer.cusName != null and customer.cusName != \'\'\"> and s_c.cusName like concat('%', #{customer.cusName}, '%')</if>" +
                "<if test=\"s_order.checkState != -1\"> and s_o.checkState = #{s_order.checkState}</if>" +
                "<if test=\"goods.g_id != -1\"> and s_o.goodsId = #{goods.g_id}</if>" +
                "limit #{pageEntity.start}, #{pageEntity.pageSize}" +
            "</script>")
    int countAllSaleOrder (@Param("goods") Goods goods, @Param("s_order") SaleOrder s_order, @Param("employee") Employee employee, @Param("repo") com.erp.entity.Repository repo, @Param("customer") Customer customer, @Param("pageEntity") PageEntity pageEntity);
    @Select("select * from tb_sale_order where id = #{id}")
    SaleOrder getSaleOrderById (@Param("id") int id);

    @Select("<script>" +
                "select * from tb_sale_order where s_o_type = 0 and checkState = 3" +
                "<if test=\"queryTimeStr != null and queryTimeStr != \'\'\">" +
                    " and creatime between #{queryTimeStr[0]} and #{queryTimeStr[1]}" +
                "</if>" +
            "</script>")
    List<SaleOrder> queryAllSOrderByCon (@Param("queryTimeStr") String[] queryTimeStr);
    @Select("<script>" +
                "select count(*) as num from tb_sale_order where s_o_type = 0 and checkState = 3" +
                "<if test=\"queryTimeStr != null and queryTimeStr != \'\'\">" +
                    " and creatime between #{queryTimeStr[0]} and #{queryTimeStr[1]}" +
                "</if>" +
            "</script>")
    int countAllOrderByCon (@Param("queryTimeStr") String[] queryTimeStr);
    @Select("SELECT COUNT(*) FROM tb_sale_order s_o JOIN tb_purchase_order p_o ON s_o.`goodsId` = p_o.`goodsId`  WHERE s_o.`s_o_type` = 0 AND s_o.checkState = 3" +
            " AND p_o.`p_o_id` = #{p_o_id}")
    int isExistSaleOrderWherPOId (@Param("p_o_id") int p_o_id);
    @Insert("insert into tb_sale_order values (null, #{saleOrder.goods.g_id}, #{saleOrder.customer.id}, #{saleOrder.repository.id}, " +
            "#{saleOrder.orderNumber}, #{saleOrder.count}, #{saleOrder.unitPrice}, #{saleOrder.totalPrice}, #{saleOrder.employee.id}," +
            " #{saleOrder.descs}, 1, NULL, NULL, NULL, #{saleOrder.creatime}, #{saleOrder.state}, 0)")
    int saleOrderAdd (@Param("saleOrder") SaleOrder saleOrder);
    @Update("update tb_sale_order set checkState = #{saleOrder.checkState}, checkAccount = #{saleOrder.user[0].id}," +
            " checkTime = #{saleOrder.checkTime} where id = #{saleOrder.id}")
    int editState (@Param("saleOrder") SaleOrder saleOrder);
    @Update("update tb_sale_order set checkState = 1, s_o_type = 1 where id = #{id}")
    int cancelOrder (@Param("id") String id);
}
