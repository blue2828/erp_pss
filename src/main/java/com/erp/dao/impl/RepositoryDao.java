package com.erp.dao.impl;

import com.erp.dao.IRepositoryDao;
import com.erp.entity.Goods;
import com.erp.entity.PageEntity;
import com.erp.entity.Repository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
@org.springframework.stereotype.Repository("repositoryDao")
public class RepositoryDao implements IRepositoryDao{
    @Autowired
    private IRepositoryDao repositoryDao;
    @Override
    public  List<Repository> queryAllRepo(String orderOrName, PageEntity pageEntity) {
        return repositoryDao.queryAllRepo(orderOrName, pageEntity);
    }

    @Override
    public int countAllRepo(String orderOrName, PageEntity pageEntity) {
        return repositoryDao.countAllRepo(orderOrName, pageEntity);
    }

    @Override
    public Repository getRepoById(int id) {
        return repositoryDao.getRepoById(id);
    }

}
