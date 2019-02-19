package com.erp.utils;

import com.erp.entity.PageEntity;
import org.apache.shiro.SecurityUtils;

public class ContextUtil {
    public static boolean isLogined () {
        return !StringUtil.isEmpty((String) SecurityUtils.getSubject().getPrincipal());
    }
}
