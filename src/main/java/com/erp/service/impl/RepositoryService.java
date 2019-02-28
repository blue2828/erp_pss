package com.erp.service.impl;

import com.erp.dao.IRepositoryDao;
import com.erp.entity.Goods;
import com.erp.entity.PageEntity;
import com.erp.entity.Repository;
import com.erp.service.IRepositoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
@Service
public class RepositoryService implements IRepositoryService {
    @Autowired
    private IRepositoryDao repositoryDao;
    @Override
    public  List<Repository> queryAllRepo(String orderOrName, PageEntity pageEntity) {
        List<Repository> list = new ArrayList<Repository>();
        try {
            list = repositoryDao.queryAllRepo(orderOrName, pageEntity);
        }catch (Exception e) {
            list.clear();
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public int countAllRepo(String orderOrName, PageEntity pageEntity) {
        int count = 0;
        try {
            count = repositoryDao.countAllRepo(orderOrName, pageEntity);
        }catch (Exception e) {
            count = 0;
            e.printStackTrace();
        }
        return count;
    }

    @Override
    public Repository getRepoById(int id) {
        Repository repository = new Repository();
        try {
            repository = repositoryDao.getRepoById(id);
        }catch (Exception e) {
            repository = new Repository();
            e.printStackTrace();
        }
        return repository;
    }
}
