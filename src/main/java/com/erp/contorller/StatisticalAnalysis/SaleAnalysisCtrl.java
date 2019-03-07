package com.erp.contorller.StatisticalAnalysis;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.erp.entity.PurchaseOrder;
import com.erp.entity.SaleOrder;
import com.erp.service.IPurchaseOrderService;
import com.erp.service.ISaleOrderService;
import com.erp.utils.StringUtil;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@RequestMapping("/statisticalAnalysis/saleAnalysis")
@CrossOrigin
@Scope("prototype")
@Controller
public class SaleAnalysisCtrl {
    @Autowired
    private ISaleOrderService saleOrderService;
    @Autowired
    private StringUtil stringUtil;
    @RequestMapping(value = "/queryAllSaleOrderByCon")
    @ResponseBody
    @RequiresPermissions("saleOrder:select")
    public JSONObject queryAllSaleOrderByCon (String queryTime) {
        JSONObject resultJb = new JSONObject();
        JSONObject temp = JSONObject.parseObject(queryTime);
        JSONArray array = temp.getJSONArray("queryTime");
        String[] dates = new String[2];
        dates[0] = (String) array.get(0);
        dates[1] = (String) array.get(1);
        List<SaleOrder> list = saleOrderService.queryAllSOrderByCon(dates);
        int count = saleOrderService.countAllOrderByCon(dates);
        resultJb.put("list", list);
        resultJb.put("count", count);
        return resultJb;
    }
}
