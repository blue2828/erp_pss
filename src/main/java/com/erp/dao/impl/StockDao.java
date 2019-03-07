package com.erp.dao.impl;

import com.erp.dao.IStockDao;
import com.erp.entity.Goods;
import com.erp.entity.PageEntity;
import com.erp.entity.Repository;
import com.erp.entity.Stock;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
@org.springframework.stereotype.Repository("stockDao")
public class StockDao implements IStockDao {
    @Autowired
    private IStockDao stockDao;
    @Override
    public List<Stock> queryAllStock(Goods goods, Repository repo, PageEntity page) {
        return stockDao.queryAllStock(goods, repo, page);
    }

    @Override
    public int countAllStock(Goods goods, Repository repo, PageEntity page) {
        return stockDao.countAllStock(goods, repo, page);
    }
}
