package com.erp.contorller.SystemUnit;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.erp.entity.PageEntity;
import com.erp.entity.Role;
import com.erp.entity.User;
import com.erp.entity.extraEntity.Role_Permission;
import com.erp.entity.permission;
import com.erp.service.IPermissionService;
import com.erp.service.IRoleService;
import com.erp.service.IUserService;
import com.erp.utils.StringUtil;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
    @Autowired
    private IPermissionService permissionService;
    private static Logger logger = LoggerFactory.getLogger(RoleController.class);
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
    @RequestMapping("/editRole")
    @RequiresPermissions("role:edit")
    @ResponseBody
    public Map<String, Object> editRole (Role role, String updaterId) {
        Map<String, Object> resultMap = new HashMap<String, Object>();
        List<User> listUsers = new ArrayList<User>();
        User updaterUser = new User();
        updaterUser.setId(StringUtil.isEmpty(updaterId) ? -1 : Integer.parseInt(updaterId));
        listUsers.add(updaterUser);
        role.setListUsers(listUsers);
        role.setUpdateTime(new Date());
        int flag = roleService.editRole(role);
        resultMap.put("success", flag > 0 ? true : false);
        resultMap.put("errMsg", flag > 0 ? null : "更新失败");
        return resultMap;
    }

    @RequestMapping("/saveRole_Permission")
    @RequiresPermissions("role:edit")
    @ResponseBody
    public Map<String, Object> saveRole_Permission (String role_permission) {
        Map<String, Object> resultMap = new HashMap<String, Object> ();
        List<Role_Permission> list = JSON.parseArray(role_permission, Role_Permission.class);
        List<permission> roleIdWithPermission = permissionService.queryPsByRoleId(list.size() > 0 ? list.get(0).getRoleId() : -1);
        List<Integer> pIdList = new ArrayList<Integer>();
        for(int i = 0; i < list.size(); i ++) {
            pIdList.add(list.get(i).getP_id());
        }
        List<Role_Permission> requiredDelete = new ArrayList<Role_Permission>();
        for(permission p : roleIdWithPermission) {
            if (!pIdList.contains(p.getId()))
                requiredDelete.add(new Role_Permission(list.size() > 0 ? list.get(0).getRoleId() : -1, p.getId()));
        }
        int deleteFlag = 0;
        boolean deleteSuccess = false;
        for (Role_Permission r_p : requiredDelete)
            deleteFlag += roleService.deleteRole_Permission(r_p);
        if (deleteFlag == requiredDelete.size())
            deleteSuccess = true;
        else
            logger.error("删除多余角色-菜单表失败");
        int flag = roleService.saveRole(list);
        resultMap.put("success", flag > 0 && deleteSuccess ? true : false);
        resultMap.put("errMsg", flag > 0 && deleteSuccess ? "保存成功" : "保存失败");
        return resultMap;
    }
}
