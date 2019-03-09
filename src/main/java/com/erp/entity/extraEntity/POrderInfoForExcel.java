package com.erp.entity.extraEntity;

import java.util.Date;

public class POrderInfoForExcel implements java.io.Serializable {
    private String orderNumber, p_o_type, goodOrder, goodsName, size, g_type, unit, unitPrice, totalPrice, count, supName;
    private Date creatime;
    private String state;
    private String repoName;
    private Date inTime;
    private String empName;
    private String checkStateStr;
    private Date checkTime;
    private String userName;

    public POrderInfoForExcel() {
    }

    public POrderInfoForExcel(String orderNumber, String p_o_type, String goodOrder, String goodsName, String size, String g_type, String unit,
                              String unitPrice, String totalPrice, String count, String supName, Date creatime, String state, String repoName,
                              Date inTime, String empName, String checkStateStr, Date checkTime, String userName) {
        this.orderNumber = orderNumber;
        this.p_o_type = p_o_type;
        this.goodOrder = goodOrder;
        this.goodsName = goodsName;
        this.size = size;
        this.g_type = g_type;
        this.unit = unit;
        this.unitPrice = unitPrice;
        this.totalPrice = totalPrice;
        this.count = count;
        this.supName = supName;
        this.creatime = creatime;
        this.state = state;
        this.repoName = repoName;
        this.inTime = inTime;
        this.empName = empName;
        this.checkStateStr = checkStateStr;
        this.checkTime = checkTime;
        this.userName = userName;
    }

    public String getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
    }

    public String getP_o_type() {
        return p_o_type;
    }

    public void setP_o_type(String p_o_type) {
        this.p_o_type = p_o_type;
    }

    public String getGoodOrder() {
        return goodOrder;
    }

    public void setGoodOrder(String goodOrder) {
        this.goodOrder = goodOrder;
    }

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getG_type() {
        return g_type;
    }

    public void setG_type(String g_type) {
        this.g_type = g_type;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(String unitPrice) {
        this.unitPrice = unitPrice;
    }

    public String getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(String totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }

    public String getSupName() {
        return supName;
    }

    public void setSupName(String supName) {
        this.supName = supName;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getRepoName() {
        return repoName;
    }

    public void setRepoName(String repoName) {
        this.repoName = repoName;
    }

    public String getEmpName() {
        return empName;
    }

    public void setEmpName(String empName) {
        this.empName = empName;
    }

    public String getCheckStateStr() {
        return checkStateStr;
    }

    public void setCheckStateStr(String checkStateStr) {
        this.checkStateStr = checkStateStr;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Date getCreatime() {
        return creatime;
    }

    public void setCreatime(Date creatime) {
        this.creatime = creatime;
    }

    public Date getInTime() {
        return inTime;
    }

    public void setInTime(Date inTime) {
        this.inTime = inTime;
    }

    public Date getCheckTime() {
        return checkTime;
    }

    public void setCheckTime(Date checkTime) {
        this.checkTime = checkTime;
    }
}
