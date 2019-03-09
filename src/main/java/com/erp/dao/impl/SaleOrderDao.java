package com.erp.dao.impl;

import com.erp.dao.ISaleOrderDao;
import com.erp.entity.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
@org.springframework.stereotype.Repository("saleOrderDao")
public class SaleOrderDao implements ISaleOrderDao {
    @Autowired
    private ISaleOrderDao saleOrderDao;
    @Override
    public List<SaleOrder> queryAllSaleOrder(Goods goods, SaleOrder s_order, Employee employee, Repository repo, Customer customer, PageEntity pageEntity) {
        return saleOrderDao.queryAllSaleOrder(goods, s_order, employee, repo, customer, pageEntity);
    }

    @Override
    public int countAllSaleOrder(Goods goods, SaleOrder s_order, Employee employee, Repository repo, Customer customer, PageEntity pageEntity) {
        return saleOrderDao.countAllSaleOrder(goods, s_order, employee, repo, customer, pageEntity);
    }

    @Override
    public SaleOrder getSaleOrderById(int id) {
        return saleOrderDao.getSaleOrderById(id);
    }

    @Override
    public List<SaleOrder> queryAllSOrderByCon(String[] queryTimeStr) {
        return saleOrderDao.queryAllSOrderByCon(queryTimeStr);
    }

    @Override
    public int countAllOrderByCon(String[] queryTimeStr) {
        return saleOrderDao.countAllOrderByCon(queryTimeStr);
    }

    @Override
    public int isExistSaleOrderWherPOId(int p_o_id) {
        return saleOrderDao.isExistSaleOrderWherPOId(p_o_id);
    }

    @Override
    public int saleOrderAdd(SaleOrder saleOrder) {
        return saleOrderDao.saleOrderAdd(saleOrder);
    }

    @Override
    public int editState(SaleOrder saleOrder) {
        return saleOrderDao.editState(saleOrder);
    }

    @Override
    public int cancelOrder(String id) {
        return saleOrderDao.cancelOrder(id);
    }
}
