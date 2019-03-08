package com.erp.dao;

import com.erp.entity.*;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface IStockDao {
    @Select("<script>" +
                "SELECT * FROM tb_stock s LEFT JOIN tb_goods g ON s.`goodsId` = g.g_id LEFT JOIN tb_repo r ON s.`repoId` = r.id " +
                "LEFT JOIN tb_purchase_order p_o ON s.`purchaseOrderId` = p_o.`p_o_id` LEFT JOIN tb_sale_order s_o ON s.`saleOrderId` = " +
                "p_o.`p_o_id` where 1 = 1" +
                "<if test=\"goods != null \">" +
                    "<if test=\"goods.goodsName != null and goods.goodsName != \'\'\">" +
                        "and g.goodsName like concat('%', #{goods.goodsName}, '%')" +
                    "</if>" +
                "</if>" +
                "<if test=\"goods != null \">" +
                    "<if test=\"goods.g_type != null and goods.g_type != \'\'\">" +
                        "and g.type like concat('%', #{goods.g_type}, '%')" +
                    "</if>" +
                "</if>" +
                "<if test=\"repo != null \">" +
                    "<if test=\"repo.repoName != null and repo.repoName != \'\'\">" +
                        "and r.repoName like concat('%', #{repo.repoName}, '%')" +
                    "</if>" +
                "</if>" +
                "limit #{page.start}, #{page.pageSize}" +
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
                    column = "saleOrderId",
                    property = "saleOrder",
                    one = @One(select = "com.erp.dao.ISaleOrderDao.getSaleOrderById")
            ),
            @Result(
                    column = "purchaseOrderId",
                    property = "purchaseOrder",
                    one = @One(select = "com.erp.dao.IPurchaseOrderDao.getPOrderById")
            )
    })
    List<Stock> queryAllStock (@Param("goods") Goods goods, @Param("repo")Repository repo, @Param("page") PageEntity page);
    @Select("<script>" +
                "SELECT count(*) as num  FROM tb_stock s LEFT JOIN tb_goods g ON s.`goodsId` = g.g_id LEFT JOIN tb_repo r ON s.`repoId` = r.id " +
                "LEFT JOIN tb_purchase_order p_o ON s.`purchaseOrderId` = p_o.`p_o_id` LEFT JOIN tb_sale_order s_o ON s.`saleOrderId` = " +
                "p_o.`p_o_id` where 1 = 1" +
                "<if test=\"goods != null \">" +
                    "<if test=\"goods.goodsName != null and goods.goodsName != \'\'\">" +
                        "and g.goodsName like concat('%', #{goods.goodsName}, '%')" +
                    "</if>" +
                "</if>" +
                "<if test=\"goods != null \">" +
                    "<if test=\"goods.g_type != null and goods.g_type != \'\'\">" +
                        "and g.type like concat('%', #{goods.g_type}, '%')" +
                    "</if>" +
                "</if>" +
                "<if test=\"repo != null \">" +
                    "<if test=\"repo.repoName != null and repo.repoName != \'\'\">" +
                        "and r.repoName like concat('%', #{repo.repoName}, '%')" +
                    "</if>" +
                "</if>" +
                "limit #{page.start}, #{page.pageSize}" +
            "</script>"
    )
    int countAllStock (@Param("goods") Goods goods, @Param("repo")Repository repo, @Param("page") PageEntity page);
    @Insert("insert into tb_stock values(null, #{stock.goods.g_id}, #{stock.repository.id}, #{stock.saleOrder.id}, #{stock.purchaseOrder.p_o_id})")
    int stockAdd (@Param("stock") Stock stock);
    @Select("SELECT COUNT(*) FROM tb_stock WHERE purchaseOrderId = #{p_o_id}")
    int isExistInfoWithPOId (@Param("p_o_id") String p_o_id);
    @Delete("delete from tb_stock where purchaseOrderId = #{p_o_id}")
    int deleteInfoByPOId (@Param("p_o_id") String p_o_id);
}
