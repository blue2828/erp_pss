package com.erp.dao;

import com.erp.entity.*;
import org.apache.ibatis.annotations.*;

import java.util.List;
@Mapper
public interface IGoodsDao {
    @Select("<script>" +
                "SELECT p_o.*, g.*, r.`repoName` FROM tb_purchase_order p_o left JOIN tb_goods g ON p_o.goodsId = g.g_id left JOIN" +
                " tb_repo r ON r.`id` = p_o.`repoId` left join tb_employee e on p_o.employeeId = e.id left join tb_user u on p_o.userId = u.id " +
                " left join tb_purchase_supplier p_s on p_o.supplierId = p_s.id where 1 = 1" +
                "<if test=\"isState4\"> and p_o.checkState = 3</if>" +
                "<if test=\"goods.goodsName != null and goods.goodsName != \'\'\"> and g.goodsName like concat('%', #{goods.goodsName}, '%')</if>" +
                "<if test=\"goods.size != null and goods.size != \'\'\"> and g.size = #{goods.size}</if>" +
                "<if test=\"goods.buyPrice != -1.0 \"> and g.buyPrice = #{goods.buyPrice}</if>" +
                "<if test=\"goods.salePrice != -1.0 \"> and g.salePrice = #{goods.salePrice}</if>" +
                "<if test=\"goods.g_type != null and goods.g_type != \'\' \"> and g.g_type like concat('%', #{goods.g_type}, '%')</if>" +
                "<if test=\"employee.empName != null and employee.empName != \'\' \"> and e.empName like concat('%', #{employee.empName}, '%')</if>" +
                "<if test=\"repo.id != -1 \"> and r.id = #{repo.id}</if>" +
                "<if test=\"p_order.orderNumber != null and p_order.orderNumber != \'\' \"> and p_o.orderNumber like concat('%', #{p_order.orderNumber}, '%')</if>" +
                "<if test=\"goods.goodOrder != null and goods.goodOrder != \'\' \"> and g.goodOrder like concat('%', #{goods.goodOrder}, '%')</if>" +
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
                "SELECT count(*) FROM tb_purchase_order p_o left JOIN tb_goods g ON p_o.goodsId = g.g_id left JOIN" +
                " tb_repo r ON r.`id` = p_o.`repoId` left join tb_employee e on p_o.employeeId = e.id left join tb_user u on p_o.userId = u.id " +
                " left join tb_purchase_supplier p_s on p_o.supplierId = p_s.id where 1 = 1" +
                "<if test=\"isState4\"> and p_o.checkState = 3</if>" +
                "<if test=\"goods.goodsName != null and goods.goodsName != \'\'\"> and g.goodsName like concat('%', #{goods.goodsName}, '%')</if>" +
                "<if test=\"goods.size != null and goods.size != \'\'\"> and g.size = #{goods.size}</if>" +
                "<if test=\"goods.buyPrice != -1.0 \"> and g.buyPrice = #{goods.buyPrice}</if>" +
                "<if test=\"goods.salePrice != -1.0 \"> and g.salePrice = #{goods.salePrice}</if>" +
                "<if test=\"goods.g_type != null and goods.g_type != \'\' \"> and g.g_type like concat('%', #{goods.g_type}, '%')</if>" +
                "<if test=\"employee.empName != null and employee.empName != \'\' \"> and e.empName like concat('%', #{employee.empName}, '%')</if>" +
                "<if test=\"repo.id != -1 \"> and r.id = #{repo.id}</if>" +
                "<if test=\"p_order.orderNumber != null and p_order.orderNumber != \'\' \"> and p_o.orderNumber like concat('%', #{p_order.orderNumber}, '%')</if>" +
                "<if test=\"goods.goodOrder != null and goods.goodOrder != \'\' \"> and g.goodOrder like concat('%', #{goods.goodOrder}, '%')</if>" +
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
    @Update("update tb_goods set picture = #{goods.picture} where g_id = #{goods.g_id}")
    int freshPicture(@Param("goods") Goods goods);
    @Insert("insert into tb_goods (picture, goodsName, size, goodOrder, g_type, unit, buyPrice, salePrice, mark)values (#{goods.picture}, #{goods.goodsName}, " +
            "#{goods.size}, #{goods.goodOrder}, #{goods.g_type}, #{goods.unit}, #{goods.buyPrice}, #{goods.salePrice}, #{goods.mark})")
    int saveGoods (@Param("goods") Goods goods);
    @Select("SELECT MAX(goodOrder + 0) + 1 FROM tb_goods ")
    String getMaxOrder ();
    @Select("select * from tb_goods where goodOrder = #{order}")
    Goods getGoodsByOrder (@Param("order") String order);
    @Update("update tb_goods set goodsName = #{goods.goodsName}, size = #{goods.size}, g_type = #{goods.g_type}, unit = #{goods.unit}, buyPrice = #{goods.buyPrice}, salePrice = #{goods.salePrice}, mark = #{goods.mark} where g_id = #{goods.g_id}")
    int editGoods (@Param("goods") Goods goods);
    @Update("update tb_goods set goodsName = #{goods.goodsName}, size = #{goods.size}, g_type = #{goods.g_type}, unit = #{goods.unit}, mark = #{goods.mark} where g_id = #{goods.g_id}")
    int editSomeInfoOfGoods (@Param("goods") Goods goods);
}
