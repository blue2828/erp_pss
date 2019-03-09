package com.erp.contorller.BuyManage;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.erp.contorller.SystemUnit.UserController;
import com.erp.entity.*;
import com.erp.entity.extraEntity.POrderInfoForExcel;
import com.erp.entity.extraEntity.g_po_col;
import com.erp.service.*;
import com.erp.utils.ContextUtil;
import com.erp.utils.ExcelUtil;
import com.erp.utils.FileUtil;
import com.erp.utils.StringUtil;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.subject.Subject;
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

import javax.servlet.http.HttpServletResponse;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;
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
    @Autowired
    private IStockService stockService;
    @Autowired
    private ISaleOrderService saleOrderService;
    @Autowired
    private ContextUtil contextUtil;
    @RequestMapping("/savePurchaseOrder")
    @ResponseBody
    @RequiresPermissions(value = { "purchaseOrder:edit", "purchaseOrder:add" }, logical = Logical.OR)
    public Map<String, Object> saveUserInfo (@RequestParam(value = "file", required = false) MultipartFile multipartFile, PurchaseOrder purchaseOrder, Goods goods,
                                             @RequestParam(required = false) String handleUserId, boolean isEdit, String supId, String repoId, String state, String count, String selectedEp, String inTimeStr) {
        Map<String, Object> resultMap = new HashMap<String, Object>();
        boolean saveImg = false;
        boolean isTransferToDisked = false;
        StringBuffer errMsg = new StringBuffer();
        int saveToPurchaseOrder = 0;
        purchaseOrder.setUnitPrice(goods.getBuyPrice());
        purchaseOrder.setTotalPrice(goods.getBuyPrice() * Integer.parseInt(count));
        int saveToGoods = 0;
        String maxOrder = goodsService.getMaxOrder();
        purchaseOrder.setInTime(stringUtil.formatStrTimeToDate(inTimeStr, "yyyy-MM-dd HH:mm:ss"));
        if (!isEdit) {
            String currentTime = stringUtil.getCurrentTimeStr();
            Calendar calendar = Calendar.getInstance();
            String orderNumber = "p".concat(String.valueOf(calendar.get(Calendar.YEAR))).concat(StringUtil.mkSingleStrToDb(String.valueOf(calendar.get(Calendar.MONTH)))).
                    concat(StringUtil.mkSingleStrToDb(String.valueOf(calendar.get(Calendar.DATE)))).concat(StringUtil.mkSingleStrToDb(String.valueOf(calendar.get(Calendar.HOUR_OF_DAY)))).
                    concat(StringUtil.mkSingleStrToDb(String.valueOf(calendar.get(Calendar.MINUTE)))).concat(StringUtil.mkSingleStrToDb(String.valueOf(calendar.get(Calendar.SECOND))));
            purchaseOrder.setOrderNumber(orderNumber);
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
    @RequiresPermissions("purchaseOrder:cancel")
    public Map<String, Object> cancelPOrder (String p_o_id) {
        Map<String, Object> map = new HashMap<String, Object>();
        String[] idsArr = p_o_id.split(",");
        int count = 0;
        String whereExsitSaleOrderErrMsg = "";
        String whereExsitStockErrMsg = "";
        for (String ids : idsArr) {
            int i = !StringUtil.isEmpty(ids) ? Integer.parseInt(ids) : -1;
            if (saleOrderService.isExistSaleOrderWherPOId(StringUtil.isEmpty(p_o_id) ? -1 : Integer.parseInt(p_o_id))) {
                whereExsitSaleOrderErrMsg = "该订单的商品已经被销售出，不可退单";
                continue;
            } else {
                boolean isExistStockWithPOId = stockService.isExistInfoWithPOId(p_o_id);
                if (isExistStockWithPOId) {
                    int flag = stockService.deleteInfoByPOId(p_o_id);
                    whereExsitStockErrMsg = flag > 0 ? "" : "库存更新出错";
                }
            }
            count += purchaseOrderService.editPOrder(new PurchaseOrder(!StringUtil.isEmpty(ids) ? Integer.parseInt(ids) : -1), null, null, null, true);
        }
        logger.error(whereExsitStockErrMsg);
        map.put("success", count == idsArr.length && StringUtil.isEmpty(whereExsitSaleOrderErrMsg) ? true : false);
        map.put("errMsg", count != idsArr.length ? count > 0 ? "部分订单退单失败" : "退单失败" : "退单成功");
        map.put("extraErrMsg", StringUtil.isEmpty(whereExsitSaleOrderErrMsg) ? null : whereExsitSaleOrderErrMsg);
        return map;
    }

    @RequestMapping("/purchaseOrderApprove")
    @ResponseBody
    @RequiresPermissions("purchaseOrderWithApprove:approve")
    public Map<String, Object> purchaseOrderApprove (String g_po_col, int checkState) {
        Map<String, Object> map = new HashMap<String, Object>();
        List<g_po_col> list = JSONObject.parseArray(g_po_col, g_po_col.class);
        Subject subject = SecurityUtils.getSubject();
        User currentUser = contextUtil.getUserByIdOrName((String) subject.getPrincipal());
        List<User> currentUserList = new ArrayList<User>();
        currentUserList.add(currentUser);
        int saveToPOrder = 0;
        int saveToStock = 0;
        for (g_po_col temp : list) {
            PurchaseOrder tempPO = new PurchaseOrder(temp.getP_o_id(), checkState, currentUserList, new Date());
            saveToPOrder += purchaseOrderService.approveOrder(tempPO);
            Goods tempG = new Goods(temp.getG_id());
            List<SaleOrder> saleOrders = saleOrderService.queryAllSaleOrder(tempG, new SaleOrder(), new Employee(), new Repository(), new Customer(), new PageEntity(1, 10) );
            switch (checkState) {
                case 3 :
                    saveToStock += stockService.stockAdd(new Stock(tempG, new Repository(temp.getRepoId()), saleOrders.size() > 0 ? saleOrders.get(0) : new SaleOrder(), tempPO ));
                    break;
                case 2 :
                    saveToStock += stockService.deleteInfoByPOId(String.valueOf(temp.getP_o_id()));
                    break;
            }
        }
        map.put("success", saveToPOrder == list.size() && saveToStock == list.size() ? true : false);
        map.put("errMsg", !(saveToPOrder == list.size() && saveToStock == list.size()) ? saveToPOrder > 0 ? "部分订单".concat(checkState == 3 ? "审批" : "取消审批").concat("失败") :
                (checkState == 3 ? "审批" : "取消审批").concat("失败") : (checkState == 3 ? "审批" : "取消审批").concat("成功"));
        return map;
    }
    /**
     * 导出选中的采购订单信息到excel表
     */
    @RequestMapping(path = "/exportPOrderInfoToExcel")
    @RequiresPermissions("purchaseOrder:export")
    @ResponseBody
    public void exportPOrderInfoToExcel (String data, HttpServletResponse response) {
        String[] cellTitleName = { "订单号", "订单类型", "货品编号", "货品名称", "规格", "货品类型", "计量单位", "单价", "总价", "数量", "供应商",
                "订单创建时间", "付款", "仓库", "入库时间", "采购员", "审批状态", "审批时间", "审批人" };
        int[] cw = { 30, 20, 20, 20, 20, 20, 20, 20, 20, 20, 20, 30, 20, 30, 30, 20, 20, 30, 20 };
        BufferedOutputStream out = null;
        OutputStream outputStream = null;
        List<POrderInfoForExcel> excelInfos = JSON.parseArray(data.substring(data.indexOf("=") + 1), POrderInfoForExcel.class);
        HSSFWorkbook wb = excelUtil.getExcelWb("采购订单信息", "采购订单信息", cw, cellTitleName, excelInfos);
        try {
            response.setHeader("content-disposition", "attachment;filename=".concat(URLEncoder.encode("采购订单信息表_", "utf-8").concat(stringUtil.getCurrentTimeStr()).concat(".xls")));
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
}
