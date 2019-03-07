package com.erp.dao.impl;

import com.erp.dao.IPurchaseOrderDao;
import com.erp.entity.Goods;
import com.erp.entity.PurchaseOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("purchaseOrderDao")
public class PurchaseOrderDao implements IPurchaseOrderDao {
    @Autowired
    private IPurchaseOrderDao purchaseOrderDao;

    @Override
    public PurchaseOrder getPOrderById(int id) {
        return purchaseOrderDao.getPOrderById(id);
    }

    @Override
    public List<PurchaseOrder> queryAllPOrderByCon(String[] queryTimeStr) {
        return purchaseOrderDao.queryAllPOrderByCon(queryTimeStr);
    }

    @Override
    public int countAllOrderByCon(String[] queryTimeStr) {
        return purchaseOrderDao.countAllOrderByCon(queryTimeStr);
    }

    @Override
    public int addPurchaseOrder(Goods goods, PurchaseOrder purchaseOrder, String supId, String repoId, String count) {
        return purchaseOrderDao.addPurchaseOrder(goods, purchaseOrder, supId, repoId, count);
    }

    @Override
    public int editPOrder(PurchaseOrder purchaseOrder, String supId, String repoId, String count, boolean onlyEditCkState) {
        return purchaseOrderDao.editPOrder(purchaseOrder, supId, repoId, count, onlyEditCkState);
    }
}
