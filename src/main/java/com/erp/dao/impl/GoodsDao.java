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

    @Override
    public int freshPicture(Goods goods) {
        return goodsDao.freshPicture(goods);
    }

    @Override
    public int saveGoods(Goods goods) {
        return goodsDao.saveGoods(goods);
    }

    @Override
    public String getMaxOrder() {
        return goodsDao.getMaxOrder();
    }

    @Override
    public Goods getGoodsByOrder(String order) {
        return goodsDao.getGoodsByOrder(order);
    }

    @Override
    public int editGoods(Goods goods) {
        return goodsDao.editGoods(goods);
    }

    @Override
    public int editSomeInfoOfGoods(Goods goods) {
        return goodsDao.editSomeInfoOfGoods(goods);
    }
}
