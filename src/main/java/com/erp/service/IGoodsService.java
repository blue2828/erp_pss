package com.erp.service;

import com.erp.entity.*;

import java.util.List;

public interface IGoodsService {
    List<PurchaseOrder> queryAllGoods(Goods goods, PurchaseOrder p_order, Employee employee, com.erp.entity.Repository repo, Supplier supplier, PageEntity pageEntity, boolean isState4);
    int countAllGoods(Goods goods, PurchaseOrder p_order, Employee employee, com.erp.entity.Repository repo, Supplier supplier, PageEntity pageEntity, boolean isState4);
    String getGoodsImg(int id);
    Goods getGoodsById(int id);
    public int freshPicture(Goods goods);
    int saveGoods(Goods goods);
    String getMaxOrder ();
    Goods getGoodsByOrder(String order);
    int editGoods(Goods goods);
    int editSomeInfoOfGoods(Goods goods);
}
