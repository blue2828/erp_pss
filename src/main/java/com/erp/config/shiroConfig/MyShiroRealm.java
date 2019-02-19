package com.erp.config.shiroConfig;

import com.erp.entity.Role;
import com.erp.entity.User;
import com.erp.entity.permission;
import com.erp.service.IUserService;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Set;

public class MyShiroRealm extends AuthorizingRealm {
    private static Logger logger = LoggerFactory.getLogger(MyShiroRealm.class);
    @Autowired
    private IUserService userService;
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        logger.info("权限配置MyShiroRealm.doGetAuthorizationInfo()");
        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
        String userNameOrId = (String) principalCollection.getPrimaryPrincipal();
        boolean flag = userNameOrId.matches("^\\d+$");
        List<User> user = null;
        if (flag)
            user = userService.getUserByUserNameOrId(new User(Integer.parseInt(userNameOrId)));
        else
            user = userService.getUserByUserNameOrId(new User(userNameOrId));
        if (null != user && user.size() > 0) {
            for (User item : user) {
                List<Role> roleList = item.getRoleList();
                if (null != roleList && roleList.size() > 0) {
                    for (Role role : roleList) {
                        authorizationInfo.addRole(role.getRoleCode());
                        Set<permission> permissionList = role.getPermissionList();
                        for (permission p : permissionList) {
                            if (p != null)
                                authorizationInfo.addStringPermission(p.getPermission());
                        }
                    }

                }
            }
        }
        return authorizationInfo;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        logger.info("用户认证MyShiroRealm.doGetAuthenticationInfo()======");
        if (null == token.getPrincipal())
            return null;
        String userNameOrId = (String) token.getPrincipal();
        boolean isNum = userNameOrId.matches("^\\d+$");
        User user = null;
        if (isNum)
            user =  new User(Integer.parseInt(userNameOrId));
        else
            user = new User(userNameOrId);
        List<User> resultUser = userService.getUserByUserNameOrId(user);
        if (resultUser == null)
            return null;
        SimpleAuthenticationInfo simpleAuthenticationInfo = null;
        if (resultUser.size() > 0)
            simpleAuthenticationInfo = new SimpleAuthenticationInfo(userNameOrId, resultUser.get(0).getPassword(),
                    getName());
        else
            simpleAuthenticationInfo = new SimpleAuthenticationInfo();
        return simpleAuthenticationInfo;
    }
}
