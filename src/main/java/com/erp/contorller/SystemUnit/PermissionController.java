package com.erp.contorller.SystemUnit;

import com.alibaba.fastjson.JSONObject;
import com.erp.entity.permission;
import com.erp.service.IPermissionService;
import com.erp.utils.StringUtil;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@RequestMapping("/sys/permission")
@Controller
@CrossOrigin
@Scope("prototype")
public class PermissionController {
    @Autowired
    private IPermissionService permissionService;
    @RequestMapping("/getAllPermissionOnly")
    @ResponseBody
    public JSONObject getAllPermissionOnly () {
        JSONObject resultJb = new JSONObject();
        resultJb.put("list", permissionService.getAllPermissionOnly());
        return resultJb;
    }
    @RequestMapping("/getPsByRoleId")
    @ResponseBody
    public JSONObject getPsByRoleId (String roleId) {
        JSONObject resultJb = new JSONObject();
        List<permission> list = permissionService.queryPsByRoleId(StringUtil.isEmpty(roleId) ? -1 : Integer.parseInt(roleId));
        resultJb.put("list", list);
        return resultJb;
    }
}
