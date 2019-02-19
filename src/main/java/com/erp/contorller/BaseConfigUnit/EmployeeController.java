package com.erp.contorller.BaseConfigUnit;

import com.alibaba.fastjson.JSONObject;
import com.erp.entity.Employee;
import com.erp.entity.PageEntity;
import com.erp.service.IEmployeeService;
import org.apache.ibatis.annotations.Result;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@RequestMapping("/baseConfig/employee")
@CrossOrigin
@Scope("prototype")
@Controller
public class EmployeeController {
    @Autowired
    @Qualifier("employeeService")
    private IEmployeeService employeeService;
    @RequestMapping("/queryAllEmployee")
    @RequiresPermissions("employee:select")
    @ResponseBody
    public JSONObject queryAllEmployee (@RequestParam(required = false) PageEntity pageEntity) {
        if (null == pageEntity)
            pageEntity = new PageEntity(1, 10);
        JSONObject resultJb = new JSONObject();
        List<Employee> list = employeeService.queryAllEmployee(pageEntity);
        int countEmployee = employeeService.countEmployee(pageEntity);
        resultJb.put("employeeInfo", list);
        resultJb.put("count", countEmployee);
        return resultJb;
    }
}
