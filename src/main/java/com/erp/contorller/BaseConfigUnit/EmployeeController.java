package com.erp.contorller.BaseConfigUnit;

import com.alibaba.fastjson.JSONObject;
import com.erp.ErpApplication;
import com.erp.entity.Employee;
import com.erp.entity.PageEntity;
import com.erp.service.IEmployeeService;
import com.erp.utils.StringUtil;
import org.apache.ibatis.annotations.Result;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import java.util.Date;
import java.util.List;

@RequestMapping("/baseConfig/employee")
@CrossOrigin
@Scope("prototype")
@Controller
public class EmployeeController {
    @Autowired
    @Qualifier("employeeService")
    private IEmployeeService employeeService;
    @Autowired
    private StringUtil stringUtil;
    @RequestMapping("/queryAllEmployee")
    @RequiresPermissions("employee:select")
    @ResponseBody
    public JSONObject queryAllEmployee (String s_birthday, String s_sex, Employee employee, @RequestParam(required = false) PageEntity pageEntity) {
        pageEntity = PageEntity.initPageEntity(pageEntity);
        employee = employee == null ? new Employee() : employee;
        if (!StringUtil.isEmpty(s_birthday))
            employee.setBirthday(stringUtil.formatStrTimeToDate(s_birthday, "yyyy-MM-dd"));
        employee.setSex(StringUtil.isEmpty(s_sex) ? -1 : Integer.parseInt(s_sex));
        JSONObject resultJb = new JSONObject();
        List<Employee> list = employeeService.queryAllEmployee(employee, pageEntity);
        int countEmployee = employeeService.countEmployee(employee, pageEntity);
        resultJb.put("employeeInfo", list);
        resultJb.put("count", countEmployee);
        return resultJb;
    }
}
