package com.erp.contorller.SaleManage;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.erp.entity.*;
import com.erp.entity.extraEntity.SOrderInfoForExcel;
import com.erp.service.ICustomerService;
import com.erp.service.IPurchaseOrderService;
import com.erp.service.ISaleOrderService;
import com.erp.utils.ContextUtil;
import com.erp.utils.ExcelUtil;
import com.erp.utils.StringUtil;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import java.io.BufferedOutputStream;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.*;

@RequestMapping("/saleManage/saleOrder")
@CrossOrigin
@Scope("prototype")
@Controller
public class SaleOrderController {
    @Autowired
    private ISaleOrderService saleOrderService;
    @Autowired
    private ICustomerService customerService;
    @Autowired
    private StringUtil stringUtil;
    @Autowired
    private IPurchaseOrderService purchaseOrderService;
    @Autowired
    private ContextUtil contextUtil;
    @Autowired
    private ExcelUtil excelUtil;
    @RequestMapping("/queryAllSaleOrder")
    @ResponseBody
    @RequiresPermissions("saleOrder:select")
    public JSONObject queryAllSaleOrder (Goods goods, SaleOrder s_order, Employee employee,String repoId, Customer customer, PageEntity pageEntity) {
        JSONObject resultJb = new JSONObject();
        pageEntity = PageEntity.initPageEntity(pageEntity);
        List<SaleOrder> list = saleOrderService.queryAllSaleOrder(goods == null ? new Goods() : goods, s_order == null ? new SaleOrder() :
                s_order, employee == null ? new Employee() : employee, new Repository(StringUtil.isEmpty(repoId) ? -1 : Integer.parseInt(repoId)), customer == null ? new Customer() : customer, pageEntity);
        resultJb.put("list", list);
        resultJb.put("count", saleOrderService.countAllSaleOrder(goods == null ? new Goods() : goods, s_order == null ? new SaleOrder() :
                s_order, employee == null ? new Employee() : employee, new Repository(StringUtil.isEmpty(repoId) ? -1 : Integer.parseInt(repoId)), customer == null ? new Customer() : customer, pageEntity));
        return resultJb;
    }
    @RequestMapping("/saveSaleOrder")
    @ResponseBody
    @RequiresPermissions(value = { "saleOrder:edit", "saleOrder:add" }, logical = Logical.OR)
    public Map<String, Object> handleSaleOrder (String customerId, String repoId, String goodId, SaleOrder saleOrder) {
        Map<String, Object> resultMap = new HashMap<String, Object>();
        int cusId = StringUtil.isEmpty(customerId) ? -1 : Integer.parseInt(customerId);
        int saveToCus = 1;
        Customer customer = new Customer(cusId);
        if (saleOrder.getState() == 1) {
            String lastArrears = customerService.getLastArrears(customerId);
            customer.setArrears(Double.parseDouble(StringUtil.isEmpty(lastArrears) ? "0" : lastArrears) + saleOrder.getUnitPrice() * saleOrder.getCount());
            saveToCus = customerService.editCustomer(customer, true);
        }
        User handler = contextUtil.getCurrentUser();
        Employee saleMan = handler.getEmployee();
        saleOrder.setEmployee(saleMan == null ? new Employee() : saleMan);
        saleOrder.setTotalPrice(saleOrder.getUnitPrice() * saleOrder.getCount());
        saleOrder.setCustomer(customer);
        saleOrder.setRepository(new Repository(StringUtil.isEmpty(repoId) ? -1 : Integer.parseInt(repoId)));
        saleOrder.setGoods(new Goods(StringUtil.isEmpty(goodId) ? -1 : Integer.parseInt(goodId)));
        Calendar calendar = Calendar.getInstance();
        saleOrder.setOrderNumber("s".concat(String.valueOf(calendar.get(Calendar.YEAR))).concat(StringUtil.mkSingleStrToDb(String.valueOf(calendar.get(Calendar.MONTH)))).
                concat(StringUtil.mkSingleStrToDb(String.valueOf(calendar.get(Calendar.DATE)))).concat(StringUtil.mkSingleStrToDb(String.valueOf(calendar.get(Calendar.HOUR_OF_DAY)))).
                concat(StringUtil.mkSingleStrToDb(String.valueOf(calendar.get(Calendar.MINUTE)))).concat(StringUtil.mkSingleStrToDb(String.valueOf(calendar.get(Calendar.SECOND)))));
        saleOrder.setCreatime(new Date());
        int saveToSOrder = saleOrderService.saleOrderAdd(saleOrder);
        resultMap.put("success", saveToCus > 0 && saveToSOrder > 0);
        resultMap.put("errMsg", "销售失败");
        return resultMap;
    }
    @RequestMapping("/querySomeSaleOrder")
    @ResponseBody
    @RequiresPermissions("saleOrder:select")
    public JSONObject querySomeSaleOrder (Goods goods) {
        JSONObject resultJb = new JSONObject();
        List<SaleOrder> list = saleOrderService.queryAllSaleOrder(goods, new SaleOrder(), new Employee(), new Repository(), new Customer(), PageEntity.initPageEntity(null));
        resultJb.put("list", list);
        return resultJb;
    }
    @RequestMapping("/approveSaleOrder")
    @ResponseBody
    @RequiresPermissions("saleOrderWithApprove:approve")
    public Map<String, Object> approveSaleOrder (SaleOrder saleOrder) {
        Map<String, Object> map = new HashMap<String, Object>();
        List<User> list = new ArrayList<User>();
        User currentUser = contextUtil.getCurrentUser();
        list.add(currentUser);
        saleOrder.setUser(list);
        saleOrder.setCheckTime(new Date());
        int saveToOrder = saleOrderService.editState(saleOrder);
        map.put("success", saveToOrder > 0);
        return map;
    }
    @RequestMapping("/saleOrderCancel")
    @ResponseBody
    @RequiresPermissions("saleOrder:cancel")
    public Map<String, Object> saleOrderCancel (String id) {
        Map<String, Object> map = new HashMap<String, Object>();
        String[] idArr = id.split(",");
        int flag = 0;
        for (String tempId : idArr) {
            flag += saleOrderService.cancelOrder(tempId);
        }
        map.put("success", flag == idArr.length);
        map.put("errMsg", flag != idArr.length ? flag > 0 ? "部分订购单退单失败" : "退单失败" : "退单成功");
        return map;
    }

    /**
     * 导出选中的销售订单信息到excel表
     */
    @RequestMapping(path = "/exportSOrderInfoToExcel")
    @RequiresPermissions("saleOrder:export")
    @ResponseBody
    public void exportPOrderInfoToExcel (String data, HttpServletResponse response) {
        String[] cellTitleName = { "订单号", "订单类型", "货品编号", "货品名称", "规格", "货品类型", "计量单位", "单价", "总价", "数量", "客户",
                "订单创建时间", "付款", "仓库", "出库时间", "销售员", "审批状态", "审批时间", "审批人" };
        int[] cw = { 30, 20, 20, 20, 20, 20, 20, 20, 20, 20, 20, 30, 20, 30, 30, 20, 20, 30, 20 };
        BufferedOutputStream out = null;
        OutputStream outputStream = null;
        List<SOrderInfoForExcel> excelInfos = JSON.parseArray(data.substring(data.indexOf("=") + 1), SOrderInfoForExcel.class);
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
}
