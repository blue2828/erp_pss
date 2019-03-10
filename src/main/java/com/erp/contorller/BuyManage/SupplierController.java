package com.erp.contorller.BuyManage;

import com.alibaba.fastjson.JSONObject;
import com.erp.entity.PageEntity;
import com.erp.entity.Supplier;
import com.erp.service.ISupplierService;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequestMapping("/buyManage/supplier")
@CrossOrigin
@Scope("prototype")
@Controller
public class SupplierController {
    @Autowired
    private ISupplierService supplierService;
    @RequestMapping("/queryAllSupplier")
    @ResponseBody
    @RequiresPermissions("supplier:select")
    public JSONObject queryAllSupplier (String supplierName, PageEntity pageEntity) {
        JSONObject resultJb = new JSONObject();
        pageEntity = PageEntity.initPageEntity(pageEntity);
        List<Supplier> list = supplierService.queryAllSupplier(supplierName, pageEntity);
        int count = supplierService.countAllSupplier(supplierName, pageEntity);
        resultJb.put("list", list);
        resultJb.put("count", count);
        return resultJb;
    }
    @RequestMapping("/saveSupplier")
    @ResponseBody
    @RequiresPermissions(value = { "supplier:add", "supplier:edit" }, logical = Logical.OR)
    public Map<String, Object> saveSupp (Supplier supplier, boolean isEdit) {
        Map<String, Object> map = new HashMap<String, Object>();
        int saveFlag = 0;
        if (isEdit)
            saveFlag = supplierService.editSup(supplier);
        else
            saveFlag = supplierService.supAdd(supplier);
        map.put("success", saveFlag > 0);
        map.put("errMsg", saveFlag > 0 ? "保存成功" : "保存失败");
        return map;
    }
}
