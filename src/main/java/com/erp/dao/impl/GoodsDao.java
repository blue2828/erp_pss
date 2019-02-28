package com.erp.dao.impl;

import com.erp.dao.IGoodsDao;
import com.erp.entity.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository("goodsDao")
public class GoodsDao implements IGoodsDao {
    @Autowired
    private IGoodsDao goodsDao;

    @Override
    public List<PurchaseOrder> queryAllGoods(Goods goods, PurchaseOrder p_order, Employee employee, com.erp.entity.Repository repo, Supplier supplier, PageEntity pageEntity, boolean isState4) {
        return goodsDao.queryAllGoods(goods, p_order, employee, repo, supplier, pageEntity, isState4);
    }

    @Override
    public int countAllGoods(Goods goods, PurchaseOrder p_order, Employee employee, com.erp.entity.Repository repo, Supplier supplier, PageEntity pageEntity, boolean isState4) {
        return goodsDao.countAllGoods(goods, p_order, employee, repo, supplier, pageEntity, isState4);
    }

    @Override
    public String getGoodsImg(int id) {
        return goodsDao.getGoodsImg(id);
    }

    @Override
    public Goods getGoodsById(int id) {
        return goodsDao.getGoodsById(id);
    }
}
