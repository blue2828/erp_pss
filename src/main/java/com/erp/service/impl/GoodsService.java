package com.erp.service.impl;

import com.erp.dao.IGoodsDao;
import com.erp.entity.*;
import com.erp.service.IGoodsService;
import com.erp.utils.StringUtil;
import com.sun.org.apache.regexp.internal.RE;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class GoodsService implements IGoodsService{
    @Autowired
    private IGoodsDao goodsDao;

    @Override
    public List<PurchaseOrder> queryAllGoods(Goods goods, PurchaseOrder p_order, Employee employee, com.erp.entity.Repository repo, Supplier supplier, PageEntity pageEntity, boolean isState4) {
        List<PurchaseOrder> list = new ArrayList<PurchaseOrder>();
        try {
            list = goodsDao.queryAllGoods(goods, p_order, employee, repo, supplier, pageEntity, isState4);
        }catch (Exception e) {
            list.clear();
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public int countAllGoods(Goods goods, PurchaseOrder p_order, Employee employee, com.erp.entity.Repository repo, Supplier supplier, PageEntity pageEntity, boolean isState4) {
        int count = 0;
        try {
            count = goodsDao.countAllGoods(goods, p_order, employee, repo, supplier, pageEntity, isState4);
        }catch (Exception e) {
            count = 0;
            e.printStackTrace();
        }
        return count;
    }

    @Override
    public String getGoodsImg(int id) {
        String img = "";
        try {
            img = goodsDao.getGoodsImg(id);
        }catch (Exception e) {
            img = "";
            e.printStackTrace();
        }
        return img;
    }
    public Goods getGoodsById(int id) {
        Goods goods = new Goods();
        try {
            goods = goodsDao.getGoodsById(id);
        }catch (Exception e) {
            goods = new Goods();
            e.printStackTrace();
        }
        return goods;
    }

    @Override
    @Transactional
    public int freshPicture(Goods goods) {
        int flag = 0;
        try {
            flag = goodsDao.freshPicture(goods);
        } catch (Exception e) {
            flag = 0;
            e.printStackTrace();
        }
        return flag;
    }

    @Override
    @Transactional
    public int saveGoods(Goods goods) {
        int flag = 0;
        try {
            flag = goodsDao.saveGoods(goods);
        } catch (RuntimeException e) {
            flag = 0;
            e.printStackTrace();
        }
        return flag;
    }

    @Override
    public String getMaxOrder() {
        String maxOrder = "";
        try {
            maxOrder = StringUtil.isEmpty(goodsDao.getMaxOrder()) ? "0" : goodsDao.getMaxOrder();
        }catch (Exception e) {
            maxOrder = "";
            e.printStackTrace();
        }
        return maxOrder;
    }

    @Override
    public Goods getGoodsByOrder(String order) {
        Goods goods = null;
        try {
            goods = goodsDao.getGoodsByOrder(order);
        } catch (Exception e) {
            goods = null;
            e.printStackTrace();
        }
        return goods;
    }

    @Override
    @Transactional
    public int editGoods(Goods goods) {
        int flag = 0;
        try {
            flag = goodsDao.editGoods(goods);
        } catch (RuntimeException e) {
            flag = 0;
            e.printStackTrace();
        }
        return flag;
    }

    @Override
    @Transactional
    public int editSomeInfoOfGoods(Goods goods) {
        int flag = 0;
        try {
            flag = goodsDao.editSomeInfoOfGoods(goods);
        } catch (RuntimeException e) {
            flag = 0;
            e.printStackTrace();
        }
        return flag;
    }
}
