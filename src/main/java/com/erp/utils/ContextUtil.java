package com.erp.utils;

import com.erp.entity.Employee;
import com.erp.entity.PageEntity;
import com.erp.entity.User;
import com.erp.service.IUserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ContextUtil {
    @Autowired
    private IUserService userService;
    public static boolean isLogined () {
        return !StringUtil.isEmpty((String) SecurityUtils.getSubject().getPrincipal());
    }
    public User getCurrentUser () {
        User resultUser = null;
        Subject subject = SecurityUtils.getSubject();
        String idOrName = (String) subject.getPrincipal();
        List<User> list = userService.getUserByUserNameOrId(this.getUserByIdOrName(idOrName));
        return resultUser = list.size() > 0 ? list.get(0) : null;
    }
    public User getUserByIdOrName (String idOrName) {
        User result = null;
        boolean isNum = idOrName.matches("^\\d+$");
        return result = isNum ? new User(Integer.parseInt(idOrName)) : new User(idOrName);
    }
}
