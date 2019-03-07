package com.erp.dao;

import com.erp.entity.Goods;
import com.erp.entity.PageEntity;
import com.erp.entity.Repository;
import com.erp.entity.Stock;
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
}
