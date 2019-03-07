package com.erp.dao;

import com.erp.entity.Goods;
import com.erp.entity.PurchaseOrder;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface IPurchaseOrderDao { //script 标签表示动态sql if标签里面的条件 如果成立的话if标签内的sql才会生效
    @Select("select * from tb_purchase_order where p_o_id = #{id}")
    PurchaseOrder getPOrderById (@Param("id") int id);
    @Select("<script>" +
                "select * from tb_purchase_order where p_o_type = 0 and checkState = 3" +
                    "<if test=\"queryTimeStr != null and queryTimeStr != \'\'\">" +
                        " and creatime between #{queryTimeStr[0]} and #{queryTimeStr[1]}" +
                    "</if>" +
            "</script>")
    List<PurchaseOrder> queryAllPOrderByCon (@Param("queryTimeStr") String[] queryTimeStr);
    @Select("<script>" +
                "select count(*) as num from tb_purchase_order where p_o_type = 0 and checkState = 3" +
                    "<if test=\"queryTimeStr != null and queryTimeStr != \'\'\">" +
                        " and creatime between #{queryTimeStr[0]} and #{queryTimeStr[1]}" +
                    "</if>" +
            "</script>")
    int countAllOrderByCon (@Param("queryTimeStr") String[] queryTimeStr);
    @Insert("insert into tb_purchase_order values (null, #{goods.g_id}, #{supId}, #{repoId}, #{purchaseOrder.orderNumber}, #{count}, " +
            "#{purchaseOrder.unitPrice}, #{purchaseOrder.totalPrice}, #{purchaseOrder.employee.id}, 1, NULL, NULL, NULL, #{purchaseOrder.creatime}, " +
            "#{purchaseOrder.state}, 0)")
    int addPurchaseOrder(@Param("goods") Goods goods, @Param("purchaseOrder") PurchaseOrder purchaseOrder, @Param("supId") String supId, @Param("repoId") String repoId, @Param("count") String count);
    @Update("<script>" +
                "<if test=\"onlyEditCkState\">" +
                    "update tb_purchase_order set p_o_type = #{purchaseOrder.p_o_type} where p_o_id = #{purchaseOrder.p_o_id}" +
                "</if>" +
                "update tb_purchase_order set supplierId = #{supId}, repoId = #{repoId}, count = #{count}, unitPrice = #{purchaseOrder.unitPrice}, totalPrice = #{purchaseOrder.totalPrice}, " +
                "employeeId = #{purchaseOrder.employee.id}, state = #{purchaseOrder.state} where p_o_id = #{purchaseOrder.p_o_id}" +
            "</script>")
    int editPOrder (@Param("purchaseOrder") PurchaseOrder purchaseOrder, @Param("supId") String supId, @Param("repoId") String repoId, @Param("count") String count, @Param("onlyEditCkState") boolean onlyEditCkState);
}
