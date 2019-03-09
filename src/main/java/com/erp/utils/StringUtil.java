package com.erp.utils;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.erp.entity.User;
import org.springframework.stereotype.Component;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.text.SimpleDateFormat;
import java.util.*;
@Component
public class StringUtil {

    public static boolean isEmpty(String str){
        if(str == null || "".equals(str))
            return true;
        else
            return false;
    }

    public Date formatStrTimeToDate (String date, String formatStr) {
        SimpleDateFormat spdf = new SimpleDateFormat(formatStr);
        Date result = null;
        try {
            if (formatStr != null && !StringUtil.isEmpty(date))
                result = spdf.parse(date);
        }catch (Exception e) {
            result = null;
            e.printStackTrace();
        }
        return result;
    }
    public String formatTime (Date date, String formatStr) {
        String str = "";
        try {
            SimpleDateFormat spdf = new SimpleDateFormat(formatStr);
            if (date != null)
                str = spdf.format(date);
            else
                str = "";
        }catch (Exception e) {
            e.printStackTrace();
            str = "";
        }
        return str;
    }

    public String[] getFieldName (Object o) {
        Field[] fields = o.getClass().getDeclaredFields();
        String[] fieldNames = new String[fields.length];
        for (int i = 0; i < fields.length; i ++) {
            fieldNames[i] = fields[i].getName();
        }
        return fieldNames;
    }

    public Object getFieldValueByName (String fieldName, Object o) {
        Object value = null;
        try {
            String firstLetter = fieldName.substring(0, 1);
            String getter = "get" + firstLetter.toUpperCase() + fieldName.substring(1);
            Method method = o.getClass().getMethod(getter, new Class[] {});
            value = method.invoke(o, new Object[] {});
        }catch (Exception e) {
            value = null;
            e.printStackTrace();
        }
        return value;
    }
    public String getCurrentTimeStr () {
        String str = null;
        Calendar calendar = Calendar.getInstance();
        str = "" + calendar.get(Calendar.YEAR) + "-" + (calendar.get(Calendar.MONTH) + 1) + "-" + calendar.get(Calendar.DATE) + " " + calendar.get(Calendar.HOUR_OF_DAY) + ":" + calendar.get(Calendar.MINUTE) + ":" + calendar.get(Calendar.SECOND);
        return str;
    }
    public String getImgResolution (File file) {
        String str = null;
        BufferedImage bufferedImage = null;
        try {
            bufferedImage = ImageIO.read(file);
            str = bufferedImage.getWidth() + "*" + bufferedImage.getHeight();
        }catch (Exception e) {
            e.printStackTrace();
        }
        return str;
    }
    /*public JSONArray formatListToJsonArray (List<Object> list) {
        JSONArray result = new JSONArray();
        for (Object o : list) {
            JSONObject tempJb = new JSONObject();
            for (String fieldName : this.getFieldName(o)) {
                if ("employee".equals(fieldName) && o.getClass() == User.class) {
                    //if (((User) o).getE)
                 //   for (String ep_attr : this.getFieldName())
                }
                tempJb.put(fieldName, this.getFieldValueByName(fieldName, o));
            }
        }
        return result;
    }*/

    public String trimRight (String arg) {
        String str = "";
        if (arg.matches("^[\\t\\n\\r\\s]")) {
            str = arg.substring(0, arg.indexOf(arg.trim().substring(0, 1)) + arg.trim().length());
        }else
            str = arg.trim();
        return str;
    }
    public String getRandomStr () {
        String result = "";
        String willSelected = "随意搞几个就行了，哈哈";
        char[] chars = willSelected.toCharArray();
        Random random = new Random();
        for (int i = 0; i < 4; i ++) {
            result += willSelected.charAt(random.nextInt(chars.length));
        }
        return result;
    }

    public JSONArray formatObToJson (ResultSet resultSet) {
        JSONArray array = new JSONArray();
        try {
            ResultSetMetaData metaData = resultSet.getMetaData();
            int count = metaData.getColumnCount();
            resultSet.beforeFirst();
            while (resultSet.next()) {
                JSONObject temp = new JSONObject();
                for (int i = 1; i <= count; i ++) {
                    Object obj = resultSet.getObject(i);
                    if (obj instanceof Date)
                        this.formatTime((Date) obj, "yyyy-MM-dd HH:mm:ss");
                    temp.put(metaData.getColumnLabel(i), resultSet.getObject(i) == null ? "" : obj);
                }
                array.add(temp);
            }
        }catch (Exception e) {
            e.printStackTrace();
        }
        return array;
    }
    public static String formatDecimalStrToIntStr (String requiredArg) {
        return requiredArg.substring(0, requiredArg.lastIndexOf(".") > -1 ? requiredArg.lastIndexOf(".") : requiredArg.length());
    }
    public static String mkSingleStrToDb (String singleStr) {
        return singleStr.length() > 1 ? singleStr : "0".concat(singleStr);
    }
}
