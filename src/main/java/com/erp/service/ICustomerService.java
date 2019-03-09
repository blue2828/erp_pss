package com.erp.service;

import com.erp.entity.Customer;
import com.erp.entity.PageEntity;
import com.erp.entity.Supplier;

import java.util.List;

public interface ICustomerService {
    List<Customer> queryAllCustomer(String cusName, PageEntity pageEntity);
    int countAllCustomer(String cusName, PageEntity pageEntity);
    int editCustomer(Customer cus, boolean onlyEditArrears);
    String getLastArrears(String id);
}
