package com.erp.contorller.SystemUnit;

import com.alibaba.fastjson.JSONObject;
import com.erp.entity.PageEntity;
import com.erp.entity.Role;
import com.erp.entity.User;
import com.erp.service.IRoleService;
import com.erp.service.IUserService;
import com.erp.utils.StringUtil;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.*;

@RequestMapping("/sys/role")
@Controller
@CrossOrigin
@Scope("prototype")
public class RoleController {
    @Autowired
    private IRoleService roleService;
    @Autowired
    private IUserService userService;
    @Autowired
    private StringUtil stringUtil;
    @RequestMapping("/queryAllRoles")
    @RequiresPermissions(value = {"user:select", "user:add", "user:edit", "role:select"}, logical = Logical.OR)
    @ResponseBody
    public JSONObject queryAllRoles (@RequestParam(required = false) PageEntity pageEntity) {
        JSONObject jsonObject = new JSONObject();
        pageEntity = PageEntity.initPageEntity(pageEntity);
        List<Role> list = roleService.queryAllRoles(pageEntity);
        jsonObject.put("roleList", list);
        jsonObject.put("count", roleService.countAllRoles(pageEntity));
        return jsonObject;
    }
    @RequestMapping("/getRolesByUserId")
    @RequiresPermissions(value = {"user:select", "user:add", "user:edit", "role:select"}, logical = Logical.OR)
    @ResponseBody
    public JSONObject getRolesByUserId(String userId) {
        JSONObject jsonObject = new JSONObject();
        List<User> list = userService.getUserById((StringUtil.isEmpty(userId) ? -1 : Integer.parseInt(userId)));
        Set<Integer> roleIdsSet = new HashSet<Integer>();
        for (User user : list) {
            List<Role> tempRoleList = user.getRoleList();
            for (Role role : tempRoleList) {
                roleIdsSet.add(role.getId());
            }
        }
        List<Role> roleList = new ArrayList<Role>();
        for (int id : roleIdsSet) {
            roleList.add(roleService.getOnlyRoleById(id));
        }
        jsonObject.put("roleList", roleList);
        return jsonObject;
    }
}
