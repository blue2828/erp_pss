package com.erp.entity.extraEntity;

public class GoodInfoForExcel implements java.io.Serializable { //服务于导出商品信息到excel的实体类
    private String goodOrder, goodsName, size, g_type, unit, buyPrice, salePrice, count, supName, repoName, mark;

    public GoodInfoForExcel(String goodOrder, String goodsName, String size, String g_type, String unit, String buyPrice, String salePrice,
                            String count, String supName, String repoName, String mark) {
        this.goodOrder = goodOrder;
        this.goodsName = goodsName;
        this.size = size;
        this.g_type = g_type;
        this.unit = unit;
        this.buyPrice = buyPrice;
        this.salePrice = salePrice;
        this.count = count;
        this.supName = supName;
        this.repoName = repoName;
        this.mark = mark;
    }

    public GoodInfoForExcel() {
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

    public String getBuyPrice() {
        return buyPrice;
    }

    public void setBuyPrice(String buyPrice) {
        this.buyPrice = buyPrice;
    }

    public String getSalePrice() {
        return salePrice;
    }

    public void setSalePrice(String salePrice) {
        this.salePrice = salePrice;
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

    public String getRepoName() {
        return repoName;
    }

    public void setRepoName(String repoName) {
        this.repoName = repoName;
    }

    public String getMark() {
        return mark;
    }

    public void setMark(String mark) {
        this.mark = mark;
    }
}
