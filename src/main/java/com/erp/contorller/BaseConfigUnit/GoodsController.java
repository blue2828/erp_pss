package com.erp.contorller.BaseConfigUnit;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.erp.entity.*;
import com.erp.service.IGoodsService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.*;
import java.util.List;

@RequestMapping("/baseConfig/goods")
@CrossOrigin
@Scope("prototype")
@Controller
public class GoodsController {
    @Autowired
    private IGoodsService goodsService;
    @RequestMapping(value = "/queryAllGoods")
    @ResponseBody
    @RequiresPermissions("goods:select")
    public JSONObject queryAllGoods(Goods good, PurchaseOrder p_order, Employee emp, com.erp.entity.Repository repo, Supplier sup, PageEntity pageEntity, boolean isState4) {
        JSONObject resultJb = new JSONObject();
        pageEntity = PageEntity.initPageEntity(pageEntity);
        List<PurchaseOrder> list = goodsService.queryAllGoods(good == null ? new Goods() : good, p_order == null ? new PurchaseOrder() : p_order, emp == null ? new Employee() :
                emp, repo == null ? new Repository() : repo, sup == null ? new Supplier() : sup, pageEntity, isState4);
        resultJb.put("list", list);
        resultJb.put("count", goodsService.countAllGoods(good == null ? new Goods() : good, p_order, emp == null ? new Employee() :
                emp, repo == null ? new Repository() : repo, sup == null ? new Supplier() : sup, pageEntity, isState4));
        return resultJb;
    }
    @RequestMapping("/getGoodsImg")
    @ResponseBody
    public String getGoodsImg (@RequestParam(required = false) int id) {
        String imgBase64 = "";
        BufferedInputStream bufferedInputStream = null;
        ByteArrayOutputStream byteArrayOutputStream = null;
        InputStream inputStream = null;
        byte[] base64Bytes = null;
        try {
            String imgHeader = goodsService.getGoodsImg(id);
            File file = new File(imgHeader == null ? "" : imgHeader);
            int temp = 0;
            byte[] bytes = new byte[1024];
            byteArrayOutputStream = new ByteArrayOutputStream();
            if (file.exists() && null != file) {
                inputStream = new FileInputStream(file);
                bufferedInputStream = new BufferedInputStream(inputStream);
                while ((temp = bufferedInputStream.read(bytes)) != -1) {
                    byteArrayOutputStream.write(bytes, 0, temp);
                    byteArrayOutputStream.flush();
                }
                base64Bytes = byteArrayOutputStream.toByteArray();
            }else
                base64Bytes = new byte[0];
        }catch (Exception e) {
            e.printStackTrace();
        }finally {
            try {
                if (null != byteArrayOutputStream)
                    byteArrayOutputStream.close();
                if (null != bufferedInputStream)
                    bufferedInputStream.close();
                if (null != inputStream)
                    inputStream.close();

            }catch (Exception e) {
                e.printStackTrace();
            }
        }
        imgBase64 = JSON.toJSONString(base64Bytes);
        return imgBase64;
    }
}
