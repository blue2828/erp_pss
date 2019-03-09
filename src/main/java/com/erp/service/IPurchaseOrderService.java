package com.erp.service;

import com.erp.entity.Goods;
import com.erp.entity.PurchaseOrder;

import java.util.List;

public interface IPurchaseOrderService {
    List<PurchaseOrder> queryAllPOrderByCon(String[] queryTimeStr);
    int countAllOrderByCon(String[] queryTimeStr);
    int addPurchaseOrder (Goods goods, PurchaseOrder purchaseOrder, String supId, String repoId, String count);
    int editPOrder(PurchaseOrder purchaseOrder, String supId, String repoId, String count, boolean onlyEditPOType);
    int approveOrder(PurchaseOrder purchaseOrder);
    PurchaseOrder getPOrderByOrder (String order);
}
