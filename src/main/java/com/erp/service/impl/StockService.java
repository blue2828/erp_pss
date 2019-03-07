package com.erp.service.impl;

import com.erp.dao.IStockDao;
import com.erp.entity.Goods;
import com.erp.entity.PageEntity;
import com.erp.entity.Repository;
import com.erp.entity.Stock;
import com.erp.service.IStockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
@Service
public class StockService implements IStockService {
    @Autowired
    private IStockDao stockDao;
    @Override
    public List<Stock> queryAllStock(Goods goods, Repository repo, PageEntity page) {
        List<Stock> list = new ArrayList<Stock>();
        try {
            list = stockDao.queryAllStock(goods, repo, page);
        }catch (Exception e) {
            list.clear();
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public int countAllStock(Goods goods, Repository repo, PageEntity page) {
        int count = 0;
        try {
            count = stockDao.countAllStock(goods, repo, page);
        }catch (Exception e) {
            count = 0;
            e.printStackTrace();
        }
        return count;
    }
}
