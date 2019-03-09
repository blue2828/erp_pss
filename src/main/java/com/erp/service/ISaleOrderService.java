package com.erp.service;

import com.erp.entity.*;

import java.util.List;

public interface ISaleOrderService {
    List<SaleOrder> queryAllSaleOrder(Goods goods, SaleOrder s_order, Employee employee, Repository repo, Customer customer, PageEntity pageEntity);
    int countAllSaleOrder(Goods goods, SaleOrder s_order, Employee employee, Repository repo, Customer customer, PageEntity pageEntity);
    List<SaleOrder> queryAllSOrderByCon(String[] queryTimeStr);
    int countAllOrderByCon(String[] queryTimeStr);
    boolean isExistSaleOrderWherPOId(int p_o_id);
    int saleOrderAdd(SaleOrder saleOrder);
    int editState(SaleOrder saleOrder);
    int cancelOrder(String id);
}
