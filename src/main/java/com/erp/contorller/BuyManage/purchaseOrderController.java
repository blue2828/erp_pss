package com.erp.contorller.BuyManage;

import com.erp.contorller.SystemUnit.UserController;
import com.erp.entity.Employee;
import com.erp.entity.Goods;
import com.erp.entity.PurchaseOrder;
import com.erp.entity.User;
import com.erp.service.IEmployeeService;
import com.erp.service.IGoodsService;
import com.erp.service.IPurchaseOrderService;
import com.erp.service.IUserService;
import com.erp.utils.ExcelUtil;
import com.erp.utils.FileUtil;
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
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.*;

@RequestMapping("/buyManage/purchaseOrder")
@CrossOrigin
@Scope("prototype")
@Controller
public class purchaseOrderController {
    @Autowired
    private StringUtil stringUtil;
    @Autowired
    private ExcelUtil excelUtil;
    private static Logger logger = LoggerFactory.getLogger(UserController.class);
    @Autowired
    private IGoodsService goodsService;
    @Autowired
    private IPurchaseOrderService purchaseOrderService;
    @Autowired
    private IUserService userService;
    @Autowired
    private IEmployeeService employeeService;
    @RequestMapping("/savePurchaseOrder")
    @ResponseBody
    @RequiresPermissions(value = { "purchaseOrder:edit", "purchaseOrder:add" }, logical = Logical.OR)
    public Map<String, Object> saveUserInfo (@RequestParam(value = "file", required = false) MultipartFile multipartFile, PurchaseOrder purchaseOrder, Goods goods,
                                             @RequestParam(required = false) String handleUserId, boolean isEdit, String supId, String repoId, String state, String count, String selectedEp) {
        Map<String, Object> resultMap = new HashMap<String, Object>();
        boolean saveImg = false;
        boolean isTransferToDisked = false;
        StringBuffer errMsg = new StringBuffer();
        int saveToPurchaseOrder = 0;
        purchaseOrder.setUnitPrice(goods.getBuyPrice());
        purchaseOrder.setTotalPrice(goods.getBuyPrice() * Integer.parseInt(count));
        int saveToGoods = 0;
        String maxOrder = goodsService.getMaxOrder();
        if (!isEdit) {
            String currentTime = stringUtil.getCurrentTimeStr();
            Calendar calendar = Calendar.getInstance();
            purchaseOrder.setOrderNumber("p".concat(String.valueOf(calendar.get(Calendar.YEAR))).concat(StringUtil.mkSingleStrToDb(String.valueOf(calendar.get(Calendar.MONTH)))).
                    concat(StringUtil.mkSingleStrToDb(String.valueOf(calendar.get(Calendar.DATE)))).concat(StringUtil.mkSingleStrToDb(String.valueOf(calendar.get(Calendar.HOUR_OF_DAY)))).
                    concat(StringUtil.mkSingleStrToDb(String.valueOf(calendar.get(Calendar.MINUTE)))).concat(StringUtil.mkSingleStrToDb(String.valueOf(calendar.get(Calendar.SECOND)))));
            goods.setGoodOrder(maxOrder);
            purchaseOrder.setState(StringUtil.isEmpty(state) ? -1 : Integer.parseInt(state));
            purchaseOrder.setCreatime(new Date());
            List<User> listUser = userService.getUserById(StringUtil.isEmpty(handleUserId) ? -1 : Integer.parseInt(handleUserId));
            purchaseOrder.setEmployee(listUser.size() > 0 ? listUser.get(0).getEmployee() == null ? new Employee() : listUser.get(0).getEmployee() : new Employee());
            try {
                saveToGoods = goodsService.saveGoods(goods);
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                Goods tempG = goodsService.getGoodsByOrder(maxOrder);
                goods.setG_id(tempG.getG_id());
                saveToPurchaseOrder = purchaseOrderService.addPurchaseOrder(goods, purchaseOrder, supId, repoId, count);
            }
        } else {
            Employee employee = employeeService.getEmployeeById(StringUtil.isEmpty(selectedEp) ? -1 : Integer.parseInt(selectedEp));
            purchaseOrder.setEmployee(employee);
            saveToGoods = goodsService.editGoods(goods);
            saveToPurchaseOrder = purchaseOrderService.editPOrder(purchaseOrder, supId, repoId, count, false);
        }
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
                if (!isEdit) {
                    Goods temp = goodsService.getGoodsByOrder(maxOrder);
                    temp.setPicture(fileName);
                    freshRsNum = goodsService.freshPicture(temp);
                }else {
                    goods.setPicture(fileName);
                    freshRsNum = goodsService.freshPicture(goods);
                }
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
        if (multipartFile == null ? true : isTransferToDisked && multipartFile == null ? true : saveImg  && saveToGoods > 0 && saveToPurchaseOrder > 0)
            resultMap.put("success", true);
        else
            resultMap.put("success", false);
        return resultMap;
    }
    @RequestMapping("/purchaseOrderCancel")
    @ResponseBody
    @RequiresPermissions(value = { "purchaseOrder:edit", "purchaseOrder:add" }, logical = Logical.OR)
    public Map<String, Object> cancelPOrder (String p_o_id) {
        Map<String, Object> map = new HashMap<String, Object>();
        String[] idsArr = p_o_id.split(",");
        int count = 0;
        for (String ids : idsArr) {

        }
        purchaseOrderService.editPOrder(null, null, null, null, true);
        return map;
    }
}
