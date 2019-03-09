package com.erp.dao.impl;

import com.erp.dao.ICustomerDao;
import com.erp.entity.Customer;
import com.erp.entity.PageEntity;
import com.erp.entity.Supplier;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("customerDao")
public class CustomerDao implements ICustomerDao {
    @Autowired
    private ICustomerDao customerDao;
    @Override
    public List<Customer> queryAllCustomer(String cusName, PageEntity pageEntity) {
        return customerDao.queryAllCustomer(cusName, pageEntity);
    }

    @Override
    public int countAllCustomer(String cusName, PageEntity pageEntity) {
        return customerDao.countAllCustomer(cusName, pageEntity);
    }

    @Override
    public Customer getCustomerById(int id) {
        return customerDao.getCustomerById(id);
    }

    @Override
    public int editCustomer(Customer cus, boolean onlyEditArrears) {
        return customerDao.editCustomer(cus, onlyEditArrears);
    }

    @Override
    public String getLastArrears(String id) {
        return customerDao.getLastArrears(id);
    }
}
