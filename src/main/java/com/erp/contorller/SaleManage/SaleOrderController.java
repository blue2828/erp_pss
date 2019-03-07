package com.erp.contorller.SaleManage;

import com.alibaba.fastjson.JSONObject;
import com.erp.entity.*;
import com.erp.service.ISaleOrderService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@RequestMapping("/saleManage/saleOrder")
@CrossOrigin
@Scope("prototype")
@Controller
public class SaleOrderController {
    @Autowired
    private ISaleOrderService saleOrderService;
    @RequestMapping("/queryAllSaleOrder")
    @ResponseBody
    @RequiresPermissions("saleOrder:select")
    public JSONObject queryAllSaleOrder (Goods goods, SaleOrder s_order, Employee employee, com.erp.entity.Repository repo, Customer customer, PageEntity pageEntity) {
        JSONObject resultJb = new JSONObject();
        pageEntity = PageEntity.initPageEntity(pageEntity);
        List<SaleOrder> list = saleOrderService.queryAllSaleOrder(goods == null ? new Goods() : goods, s_order == null ? new SaleOrder() :
                s_order, employee == null ? new Employee() : employee, repo == null ? new Repository() : repo, customer == null ? new Customer() : customer, pageEntity);
        resultJb.put("list", list);
        resultJb.put("count", saleOrderService.countAllSaleOrder(goods == null ? new Goods() : goods, s_order == null ? new SaleOrder() :
                s_order, employee == null ? new Employee() : employee, repo == null ? new Repository() : repo, customer == null ? new Customer() : customer, pageEntity));
        return resultJb;
    }
}
