package com.erp.contorller.SaleManage;

import com.alibaba.fastjson.JSONObject;
import com.erp.entity.Customer;
import com.erp.entity.PageEntity;
import com.erp.service.ICustomerService;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@RequestMapping("/saleManage/customer")
@CrossOrigin
@Scope("prototype")
@Controller
public class CustomerController {
    @Autowired
    private ICustomerService customerService;
    @RequestMapping("/queryAllCustomer")
    @ResponseBody
    @RequiresPermissions(value = { "customer:select" }, logical = Logical.OR)
    public JSONObject queryAllCustomer (String cusName, PageEntity pageEntity) {
        JSONObject resultJb = new JSONObject();
        pageEntity = PageEntity.initPageEntity(pageEntity);
        List<Customer> list = customerService.queryAllCustomer(cusName, pageEntity);
        int count = customerService.countAllCustomer(cusName, pageEntity);
        resultJb.put("list", list);
        resultJb.put("count", count);
        return resultJb;
    }
}
