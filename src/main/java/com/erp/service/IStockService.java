package com.erp.service;

import com.erp.entity.Goods;
import com.erp.entity.PageEntity;
import com.erp.entity.Repository;
import com.erp.entity.Stock;

import java.util.List;

public interface IStockService {
    List<Stock> queryAllStock(Goods goods, Repository repo, PageEntity page);
    int countAllStock(Goods goods, Repository repo, PageEntity page);
    int stockAdd(Stock stock);
    boolean isExistInfoWithPOId (String p_o_id);
    int deleteInfoByPOId(String p_o_id);
}
