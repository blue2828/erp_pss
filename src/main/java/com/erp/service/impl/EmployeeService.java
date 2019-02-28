package com.erp.service.impl;

import com.erp.dao.IEmployeeDao;
import com.erp.entity.Employee;
import com.erp.entity.PageEntity;
import com.erp.entity.User;
import com.erp.service.IEmployeeService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service("employeeService")
public class EmployeeService implements IEmployeeService {
    @Autowired
    private IEmployeeDao employeeDao;
    @Override
    public Employee getEmployeeById(int id) {
        Employee employee = null;
        try {
            employee = employeeDao.getEmployeeById(id);
        }catch (Exception e) {
            employee = null;
            e.printStackTrace();
        }
        return employee;
    }

    @Override
    public List<Employee> queryAllEmployee(Employee employee, PageEntity page) {
        List<Employee> list = new ArrayList<Employee>();
        try {
            list = employeeDao.queryAllEmployee(employee, page);
        }catch (Exception e) {
            list.clear();
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public int countEmployee(Employee employee, PageEntity page) {
        int count = 0;
        try {
            count = employeeDao.countEmployee(employee, page);
        }catch (Exception e) {
            count = 0;
            e.printStackTrace();
        }
        return count;
    }

}
