package com.erp.contorller.BaseConfigUnit;

import com.alibaba.fastjson.JSONObject;
import com.erp.entity.PageEntity;
import com.erp.entity.Repository;
import com.erp.service.IRepositoryService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@RequestMapping("/baseConfig/repo")
@CrossOrigin
@Scope("prototype")
@Controller
public class RepositoryController {
    @Autowired
    private IRepositoryService repositoryService;
    @RequestMapping("/queryAllRepo")
    @ResponseBody
    @RequiresPermissions("repository:select")
    public JSONObject queryAllRepo (String orderOrName, PageEntity pageEntity) {
        pageEntity = PageEntity.initPageEntity(pageEntity);
                JSONObject resultJb = new JSONObject();
        List<Repository> list = repositoryService.queryAllRepo(orderOrName, pageEntity);
        int count = repositoryService.countAllRepo(orderOrName, pageEntity);
        resultJb.put("list", list);
        resultJb.put("count", count);
        return resultJb;
    }
}
