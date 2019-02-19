package com.erp.utils;

public class EnumUtil {
    public static String formatIntSexToStr (int sex) {
        String str = "";
        switch (sex) {
            case 0 : str = "女";
                break;
            case 1 : str = "男";
                break;
        }
        return str;
    }
    public static String formatIntRoleCodeToStr (int roleCode) {
        String str = "";
        switch (roleCode) {
            case 0 : str = "用户";
                break;
            case 1 : str = "管理员";
                break;
        }
        return str;
    }
}
