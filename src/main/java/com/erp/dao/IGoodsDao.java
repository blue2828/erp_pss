package com.erp.dao;

import com.erp.entity.*;
import org.apache.ibatis.annotations.*;

import java.util.List;
@Mapper
public interface IGoodsDao {
    @Select("<script>" +
                "SELECT p_o.*, g.*, r.`repoName` FROM tb_purchase_order p_o left JOIN tb_goods g ON p_o.goodsId = g.g_id left JOIN" +
                " tb_repo r ON r.`id` = p_o.`repoId` left join tb_employee e on p_o.employeeId = e.id left join tb_user u on p_o.userId = u.id " +
                "left join tb_purchase_supplier p_s on p_o.supplierId = p_s.id where 1 = 1" +
                "<if test=\"isState4\"> and p_o.checkState = 4</if>" +
                "<if test=\"goods.goodsName != null and goods.goodsName != \'\'\"> and g.goodsName like concat('%', #{goods.goodsName}, '%')</if>" +
                "<if test=\"goods.size != null and goods.size != \'\'\"> and g.size = #{goods.size}</if>" +
                "<if test=\"goods.goodOrder != null and goods.goodOrder != \'\'\"> and g.goodOrder like concat('%', #{goods.goodsName}, '%')</if>" +
                "<if test=\"goods.buyPrice != 0 \"> and g.buyPrice = #{goods.buyPrice}</if>" +
                "<if test=\"goods.salePrice != 0 \"> and g.salePrice = #{goods.salePrice}</if>" +
                "<if test=\"goods.type != null and goods.type != \'\' \"> and g.type like concat('%', #{goods.type}, '%')</if>" +
                "<if test=\"employee.empName != null and employee.empName != \'\' \"> and e.empName like concat('%', #{employee.empName}, '%')</if>" +
                "<if test=\"repo.repoName != null and repo.repoName != \'\' \"> and r.repoName like concat('%', #{repo.repoName}, '%')</if>" +
                "<if test=\"p_order.orderNumber != null and p_order.orderNumber != \'\' \"> and p_o.orderNumber like concat('%', #{p_order.orderNumber}, '%')</if>" +
                "<if test=\"p_order.p_o_type != -1\"> and p_o.p_o_type = #{p_order.p_o_type}</if>" +
                "<if test=\"supplier.supName != null and supplier.supName != \'\'\"> and p_s.supName like concat('%', #{supplier.supName}, '%')</if>" +
                "<if test=\"p_order.checkState != -1\"> and p_o.checkState = #{p_order.checkState}</if>" +
                " limit #{pageEntity.start}, #{pageEntity.pageSize}" +
            "</script>")
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
                    column = "supplierId",
                    property = "supplier",
                    one = @One(select = "com.erp.dao.ISupplierDao.getSupplierById")
            ),
            @Result(
                    column = "employeeId",
                    property = "employee",
                    one = @One(select = "com.erp.dao.IEmployeeDao.getEmployeeById")
            ),
            @Result(
                    column = "userId",
                    property = "user",
                    many = @Many(select = "com.erp.dao.IUserDao.getUserById")
            )
    })
    List<PurchaseOrder> queryAllGoods (@Param("goods") Goods goods, @Param("p_order") PurchaseOrder p_order, @Param("employee") Employee employee, @Param("repo") Repository repo, @Param("supplier") Supplier supplier, @Param("pageEntity") PageEntity pageEntity, @Param("isState4") boolean isState4);
    @Select("<script>" +
            "SELECT p_o.*, g.*, r.`repoName` FROM tb_purchase_order p_o left JOIN tb_goods g ON p_o.goodsId = g.g_id left JOIN" +
                " tb_repo r ON r.`id` = p_o.`repoId` left join tb_employee e on p_o.employeeId = e.id left join tb_user u on p_o.userId = u.id " +
                "left join tb_purchase_supplier p_s on p_o.supplierId = p_s.id where 1 = 1" +
                "<if test=\"isState4\"> and p_o.checkState = 4</if>" +
                "<if test=\"goods.goodsName != null and goods.goodsName != \'\'\"> and g.goodsName like concat('%', #{goods.goodsName}, '%')</if>" +
                "<if test=\"goods.size != null and goods.size != \'\'\"> and g.size = #{goods.size}</if>" +
                "<if test=\"goods.goodOrder != null and goods.goodOrder != \'\'\"> and g.goodOrder like concat('%', #{goods.goodsName}, '%')</if>" +
                "<if test=\"goods.buyPrice != 0 \"> and g.buyPrice = #{goods.buyPrice}</if>" +
                "<if test=\"goods.salePrice != 0 \"> and g.salePrice = #{goods.salePrice}</if>" +
                "<if test=\"goods.type != null and goods.type != \'\' \"> and g.type like concat('%', #{goods.type}, '%')</if>" +
                "<if test=\"employee.empName != null and employee.empName != \'\' \"> and e.empName like concat('%', #{employee.empName}, '%')</if>" +
                "<if test=\"repo.repoName != null and repo.repoName != \'\' \"> and r.repoName like concat('%', #{repo.repoName}, '%')</if>" +
                "<if test=\"p_order.orderNumber != null and p_order.orderNumber != \'\' \"> and p_o.orderNumber like concat('%', #{p_order.orderNumber}, '%')</if>" +
                "<if test=\"p_order.p_o_type != -1\"> and p_o.p_o_type = #{p_order.p_o_type}</if>" +
                "<if test=\"supplier.supName != null and supplier.supName != \'\'\"> and p_s.supName like concat('%', #{supplier.supName}, '%')</if>" +
                "<if test=\"p_order.checkState != -1\"> and p_o.checkState = #{p_order.checkState}</if>" +
                " limit #{pageEntity.start}, #{pageEntity.pageSize}" +
            "</script>")
    int countAllGoods (@Param("goods") Goods goods, @Param("p_order") PurchaseOrder p_order, @Param("employee") Employee employee, @Param("repo") Repository repo, @Param("supplier") Supplier supplier, @Param("pageEntity") PageEntity pageEntity, @Param("isState4") boolean isState4);
    @Select("select picture from tb_goods where g_id = #{id}")
    String getGoodsImg (@Param("id") int id);
    @Select("select * from tb_goods where g_id = #{id}")
    Goods getGoodsById(@Param("id") int id);
}
