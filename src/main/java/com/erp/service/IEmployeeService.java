package com.erp.service;

import com.erp.entity.Employee;
import com.erp.entity.PageEntity;
import com.erp.entity.User;

import java.util.List;

public interface IEmployeeService {
    public Employee getEmployeeById(int id);
    List<Employee> queryAllEmployee(PageEntity page);
    int countEmployee(PageEntity page);

}
