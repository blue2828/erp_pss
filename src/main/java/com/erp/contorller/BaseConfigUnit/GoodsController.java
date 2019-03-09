package com.erp.contorller.BaseConfigUnit;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.erp.entity.*;
import com.erp.entity.extraEntity.GoodInfoForExcel;
import com.erp.service.IGoodsService;
import com.erp.utils.ExcelUtil;
import com.erp.utils.FileUtil;
import com.erp.utils.StringUtil;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequestMapping("/baseConfig/goods")
@CrossOrigin
@Scope("prototype")
@Controller
public class GoodsController {
    @Autowired
    private ExcelUtil excelUtil;
    @Autowired
    private StringUtil stringUtil;
    @Autowired
    private IGoodsService goodsService;
    private static Logger logger = LoggerFactory.getLogger(GoodsController.class);
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

    /**
     * 导出选中的商品信息到excel表
     */
    @RequestMapping(path = "/exportGoodInfoToExcel")
    @RequiresPermissions("goods:export")
    @ResponseBody
    public void exportPOrderInfoToExcel (String data, HttpServletResponse response) {
        String[] cellTitleName = { "货品编号", "货品名称", "规格", "货品类型", "计量单位", "采购价", "零售价", "数量", "供应商", "存放仓库", "描述" };
        int[] cw = { 20, 20, 20, 20, 20, 20, 20, 20, 30, 20, 30 };
        BufferedOutputStream out = null;
        OutputStream outputStream = null;
        List<GoodInfoForExcel> excelInfos = JSON.parseArray(data.substring(data.indexOf("=") + 1), GoodInfoForExcel.class);
        HSSFWorkbook wb = excelUtil.getExcelWb("销售订单信息", "销售订单信息", cw, cellTitleName, excelInfos);
        try {
            response.setHeader("content-disposition", "attachment;filename=".concat(URLEncoder.encode("销售订单信息表_", "utf-8").concat(stringUtil.getCurrentTimeStr()).concat(".xls")));
            response.setContentType("application/msexcel");
            outputStream = response.getOutputStream();
            out = new BufferedOutputStream(outputStream);
            wb.write(outputStream);
        }catch (Exception e) {
            e.printStackTrace();
        }finally {
            try {
                if (null != outputStream)
                    outputStream.close();
                if (null != out)
                    out.close();
            }catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    @RequestMapping(path = "/editGoods")
    @RequiresPermissions("goods:edit")
    @ResponseBody
    public Map<String, Object> editGoods (@RequestParam(value = "file", required = false) MultipartFile multipartFile, Goods goods) {
        Map<String, Object> resultMap = new HashMap<String, Object>();
        StringBuffer errMsg = new StringBuffer();
        boolean isTransferToDisked = false;
        boolean saveImg = false;
        int saveToGoods = goodsService.editSomeInfoOfGoods(goods);
        if (null != multipartFile) { //如果有上传的图片
            String storePath = "d:/fileUpload";
            FileUtil.checkDirOrMk(storePath);
            String fileName = multipartFile.getOriginalFilename();
            fileName = storePath.concat(File.separator).concat(FileUtil.mkFileName(fileName));
            File targetFile = new File(fileName);
            try {
                multipartFile.transferTo(targetFile);
                isTransferToDisked = true;
            }catch (IOException e) {
                logger.info("图片保存到磁盘失败");
                errMsg.append("图片上传失败");
                isTransferToDisked = false;
                e.printStackTrace();
            }finally {
                int freshRsNum = 0;
                goods.setPicture(fileName);
                freshRsNum = goodsService.freshPicture(goods);
                switch (freshRsNum) {
                    case 0 :
                        errMsg.append("图片上传失败");
                        logger.info("图片地址保存到数据库失败");
                        saveImg = false;
                        if (isTransferToDisked) {
                            if (null == targetFile)
                                new File (fileName).delete();
                            else
                                targetFile.delete();
                        }
                        break;
                    default:
                        saveImg = true;
                }
            }
        }
        resultMap.put("success", saveImg && isTransferToDisked && saveToGoods > 0);
        resultMap.put("errMsg", (saveImg && isTransferToDisked && saveToGoods > 0) ? "编辑成功" : StringUtil.isEmpty(errMsg.toString()) ? "编辑失败" : errMsg.toString());
        return resultMap;
    }

}
