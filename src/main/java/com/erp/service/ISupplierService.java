package com.erp.service;

import com.erp.entity.PageEntity;
import com.erp.entity.Supplier;

import java.util.List;

public interface ISupplierService {
    List<Supplier> queryAllSupplier(String supName, PageEntity pageEntity);
    int countAllSupplier(String supName, PageEntity pageEntity);
    Supplier getSupplierById(int id);
}
