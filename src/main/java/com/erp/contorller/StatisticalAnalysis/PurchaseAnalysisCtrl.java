package com.erp.contorller.StatisticalAnalysis;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.erp.entity.PurchaseOrder;
import com.erp.service.IPurchaseOrderService;
import com.erp.utils.StringUtil;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Map;

@RequestMapping("/statisticalAnalysis/purchaseAnalysis")
@CrossOrigin
@Scope("prototype")
@Controller
public class PurchaseAnalysisCtrl {
    @Autowired
    private IPurchaseOrderService purchaseOrderService;
    @Autowired
    private StringUtil stringUtil;
    @RequestMapping(value = "/queryAllPurchaseOrderByCon")
    @ResponseBody
    @RequiresPermissions("purchaseOrder:select")
    public JSONObject queryAllPurchaseOrderByCon (String queryTime) {
        JSONObject resultJb = new JSONObject();
        JSONObject temp = JSONObject.parseObject(queryTime);
        JSONArray array = temp.getJSONArray("queryTime");
        String[] dates = new String[2];
        dates[0] = (String) array.get(0);
        dates[1] = (String) array.get(1);
        List<PurchaseOrder> list = purchaseOrderService.queryAllPOrderByCon(dates);
        int count = purchaseOrderService.countAllOrderByCon(dates);
        resultJb.put("list", list);
        resultJb.put("count", count);
        return resultJb;
    }
}
