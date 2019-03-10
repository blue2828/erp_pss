package com.erp.service.impl;

import com.erp.dao.ISupplierDao;
import com.erp.entity.PageEntity;
import com.erp.entity.Supplier;
import com.erp.service.ISupplierService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class SupplierService implements ISupplierService {
    @Autowired
    private ISupplierDao supplierDao;
    @Override
    public List<Supplier> queryAllSupplier(String supName, PageEntity pageEntity) {
        List<Supplier> list = new ArrayList<Supplier> ();
        try {
            list = supplierDao.queryAllSupplier(supName, pageEntity);
        }catch (Exception e) {
            list.clear();
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public int countAllSupplier(String supName, PageEntity pageEntity) {
        int count = 0;
        try {
            count = supplierDao.countAllSupplier(supName, pageEntity);
        }catch (Exception e) {
            count = 0;
            e.printStackTrace();
        }
        return count;
    }

    @Override
    public Supplier getSupplierById(int id) {
        Supplier supplier = new Supplier();
        try {
            supplier = supplierDao.getSupplierById(id);
        }catch (Exception e) {
            supplier = new Supplier();
            e.printStackTrace();
        }
        return supplier;
    }

    @Override
    @Transactional
    public int supAdd(Supplier supplier) {
        int flag = 0;
        try {
            flag = supplierDao.supAdd(supplier);
        }catch (Exception e) {
            flag = 0;
            e.printStackTrace();
        }
        return flag;
    }

    @Override
    public int editSup(Supplier supplier) {
        int flag = 0;
        try {
            flag = supplierDao.editSup(supplier);
        }catch (Exception e) {
            flag = 0;
            e.printStackTrace();
        }
        return flag;
    }
}
