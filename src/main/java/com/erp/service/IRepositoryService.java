package com.erp.service;

import com.erp.entity.Goods;
import com.erp.entity.PageEntity;
import com.erp.entity.Repository;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface IRepositoryService {
    List<Repository> queryAllRepo(String orderOrName, PageEntity pageEntity);
    int countAllRepo(String orderOrName, PageEntity pageEntity);
    Repository getRepoById(int id);
}
