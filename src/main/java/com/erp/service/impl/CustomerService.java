package com.erp.service.impl;

import com.erp.dao.ICustomerDao;
import com.erp.dao.ISupplierDao;
import com.erp.entity.Customer;
import com.erp.entity.PageEntity;
import com.erp.entity.Supplier;
import com.erp.service.ICustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class CustomerService implements ICustomerService {
    @Autowired
    private ICustomerDao customerDao;
    @Override
    public List<Customer> queryAllCustomer(String cusName, PageEntity pageEntity) {
        List<Customer> list = new ArrayList<Customer> ();
        try {
            list = customerDao.queryAllCustomer(cusName, pageEntity);
        }catch (Exception e) {
            list.clear();
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public int countAllCustomer(String cusName, PageEntity pageEntity) {
        int count = 0;
        try {
            count = customerDao.countAllCustomer(cusName, pageEntity);
        }catch (Exception e) {
            count = 0;
            e.printStackTrace();
        }
        return count;
    }

    @Override
    @Transactional
    public int editCustomer(Customer cus, boolean onlyEditArrears) {
        int flag = 0;
        try {
            flag = customerDao.editCustomer(cus, onlyEditArrears);
        }catch (Exception e) {
            flag = 0;
            e.printStackTrace();
        }
        return flag;
    }

    @Override
    public String getLastArrears(String id) {
        String num = null;
        try {
            num = customerDao.getLastArrears(id);
        }catch (Exception e) {
            num = "";
            e.printStackTrace();
        }
        return num;
    }
}
