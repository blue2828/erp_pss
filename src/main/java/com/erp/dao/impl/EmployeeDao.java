package com.erp.dao.impl;

import com.erp.dao.IEmployeeDao;
import com.erp.entity.Employee;
import com.erp.entity.PageEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class EmployeeDao implements IEmployeeDao {
    @Autowired
    private IEmployeeDao employeeDao;
    @Override
    public Employee getEmployeeById(int id) {
        return employeeDao.getEmployeeById(id);
    }

    @Override
    public List<Employee> queryAllEmployee(Employee employee, PageEntity page) {
        return employeeDao.queryAllEmployee(employee, page);
    }

    @Override
    public int countEmployee(Employee employee, PageEntity page) {
        return employeeDao.countEmployee(employee, page);
    }


}
