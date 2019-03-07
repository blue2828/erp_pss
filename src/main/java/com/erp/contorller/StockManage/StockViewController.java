package com.erp.contorller.StockManage;

import com.alibaba.fastjson.JSONObject;
import com.erp.entity.Goods;
import com.erp.entity.PageEntity;
import com.erp.entity.Repository;
import com.erp.entity.Stock;
import com.erp.service.IStockService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@RequestMapping("/stockMange/stockView")
@CrossOrigin
@Scope("prototype")
@Controller
public class StockViewController {
    @Autowired
    private IStockService stockService;
    @RequestMapping("/queryAllStock")
    @ResponseBody
    @RequiresPermissions("stock:select")
    public JSONObject queryAllStock (String repoName, String type, String goodsName, PageEntity pageEntity) {
        pageEntity = PageEntity.initPageEntity(pageEntity);
        JSONObject resultJb = new JSONObject();
        Goods goods = new Goods(goodsName, type);
        Repository repository = new Repository(repoName);
        List<Stock> list = stockService.queryAllStock(goods, repository, pageEntity);
        int count = stockService.countAllStock(goods, repository, pageEntity);
        resultJb.put("list", list);
        resultJb.put("count", count);
        return resultJb;
    }
}
