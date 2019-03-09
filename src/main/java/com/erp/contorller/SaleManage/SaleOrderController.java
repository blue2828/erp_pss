package com.erp.contorller.SaleManage;

import com.alibaba.fastjson.JSONObject;
import com.erp.entity.*;
import com.erp.service.ICustomerService;
import com.erp.service.IPurchaseOrderService;
import com.erp.service.ISaleOrderService;
import com.erp.utils.ContextUtil;
import com.erp.utils.StringUtil;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.*;

@RequestMapping("/saleManage/saleOrder")
@CrossOrigin
@Scope("prototype")
@Controller
public class SaleOrderController {
    @Autowired
    private ISaleOrderService saleOrderService;
    @Autowired
    private ICustomerService customerService;
    @Autowired
    private StringUtil stringUtil;
    @Autowired
    private IPurchaseOrderService purchaseOrderService;
    @Autowired
    private ContextUtil contextUtil;
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
    @RequestMapping("/saveSaleOrder")
    @ResponseBody
    @RequiresPermissions(value = { "saleOrder:edit", "saleOrder:add" }, logical = Logical.OR)
    public Map<String, Object> handleSaleOrder (String customerId, String repoId, String goodId, SaleOrder saleOrder) {
        Map<String, Object> resultMap = new HashMap<String, Object>();
        int cusId = StringUtil.isEmpty(customerId) ? -1 : Integer.parseInt(customerId);
        int saveToCus = 1;
        Customer customer = new Customer(cusId);
        if (saleOrder.getState() == 1) {
            String lastArrears = customerService.getLastArrears(customerId);
            customer.setArrears(Double.parseDouble(StringUtil.isEmpty(lastArrears) ? "0" : lastArrears) + saleOrder.getUnitPrice() * saleOrder.getCount());
            saveToCus = customerService.editCustomer(customer, true);
        }
        User handler = contextUtil.getCurrentUser();
        Employee saleMan = handler.getEmployee();
        saleOrder.setEmployee(saleMan == null ? new Employee() : saleMan);
        saleOrder.setTotalPrice(saleOrder.getUnitPrice() * saleOrder.getCount());
        saleOrder.setCustomer(customer);
        saleOrder.setRepository(new Repository(StringUtil.isEmpty(repoId) ? -1 : Integer.parseInt(repoId)));
        saleOrder.setGoods(new Goods(StringUtil.isEmpty(goodId) ? -1 : Integer.parseInt(goodId)));
        Calendar calendar = Calendar.getInstance();
        saleOrder.setOrderNumber("s".concat(String.valueOf(calendar.get(Calendar.YEAR))).concat(StringUtil.mkSingleStrToDb(String.valueOf(calendar.get(Calendar.MONTH)))).
                concat(StringUtil.mkSingleStrToDb(String.valueOf(calendar.get(Calendar.DATE)))).concat(StringUtil.mkSingleStrToDb(String.valueOf(calendar.get(Calendar.HOUR_OF_DAY)))).
                concat(StringUtil.mkSingleStrToDb(String.valueOf(calendar.get(Calendar.MINUTE)))).concat(StringUtil.mkSingleStrToDb(String.valueOf(calendar.get(Calendar.SECOND)))));
        saleOrder.setCreatime(new Date());
        int saveToSOrder = saleOrderService.saleOrderAdd(saleOrder);
        resultMap.put("success", saveToCus > 0 && saveToSOrder > 0);
        resultMap.put("errMsg", "销售失败");
        return resultMap;
    }
    @RequestMapping("/querySomeSaleOrder")
    @ResponseBody
    @RequiresPermissions("saleOrder:select")
    public JSONObject querySomeSaleOrder (Goods goods) {
        JSONObject resultJb = new JSONObject();
        List<SaleOrder> list = saleOrderService.queryAllSaleOrder(goods, new SaleOrder(), new Employee(), new Repository(), new Customer(), PageEntity.initPageEntity(null));
        resultJb.put("list", list);
        return resultJb;
    }
    @RequestMapping("/approveSaleOrder")
    @ResponseBody
    @RequiresPermissions("saleOrderWithApprove:approve")
    public Map<String, Object> approveSaleOrder (SaleOrder saleOrder) {
        Map<String, Object> map = new HashMap<String, Object>();
        List<User> list = new ArrayList<User>();
        User currentUser = contextUtil.getCurrentUser();
        list.add(currentUser);
        saleOrder.setUser(list);
        int saveToOrder = saleOrderService.editState(saleOrder);
        map.put("success", saveToOrder > 0);
        return map;
    }
}
