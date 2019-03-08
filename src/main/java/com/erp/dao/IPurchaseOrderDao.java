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
                "<if test=\"onlyEditPOType\">" +
                    "update tb_purchase_order set p_o_type = 1, checkState = 1 where p_o_id = #{purchaseOrder.p_o_id}" +
                "</if>" +
                "<if test=\"!onlyEditPOType\">" +
                    "update tb_purchase_order po set po.supplierId = #{supId}, po.repoId = #{repoId}, po.count = #{count}, po.unitPrice = #{purchaseOrder.unitPrice}, po.totalPrice = #{purchaseOrder.totalPrice}, " +
                    "po.employeeId = #{purchaseOrder.employee.id}, po.state = #{purchaseOrder.state}, po.inTime = #{purchaseOrder.inTime} where po.p_o_id = #{purchaseOrder.p_o_id}" +
                "</if>" +
            "</script>")
    int editPOrder (@Param("purchaseOrder") PurchaseOrder purchaseOrder, @Param("supId") String supId, @Param("repoId") String repoId, @Param("count") String count, @Param("onlyEditPOType") boolean onlyEditPOType);
    @Update("update tb_purchase_order set checkState = #{purchaseOrder.checkState}, userId = #{purchaseOrder.user[0].id}, checkTime = #{purchaseOrder.checkTime} where p_o_id = #{purchaseOrder.p_o_id}")
    int approveOrder (@Param("purchaseOrder") PurchaseOrder purchaseOrder);
    @Select("select * from tb_purchase_order where orderNumber = #{order}")
    PurchaseOrder getPOrderByOrder (@Param("order") String order);
}
