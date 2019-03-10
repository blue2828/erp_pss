package com.erp.dao.impl;

import com.erp.dao.ISupplierDao;
import com.erp.entity.PageEntity;
import com.erp.entity.Supplier;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository("supplierDao")
public class SupplierDao implements ISupplierDao {
    @Autowired
    private ISupplierDao supplierDao;
    @Override
    public List<Supplier> queryAllSupplier(String supName, PageEntity pageEntity) {
        return supplierDao.queryAllSupplier(supName, pageEntity);
    }

    @Override
    public int countAllSupplier(String supName, PageEntity pageEntity) {
        return supplierDao.countAllSupplier(supName, pageEntity);
    }

    @Override
    public Supplier getSupplierById(int id) {
        return supplierDao.getSupplierById(id);
    }

    @Override
    public int supAdd(Supplier supplier) {
        return supplierDao.supAdd(supplier);
    }

    @Override
    public int editSup(Supplier supplier) {
        return supplierDao.editSup(supplier);
    }
}
