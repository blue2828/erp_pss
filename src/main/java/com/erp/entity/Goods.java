package com.erp.entity;

import java.util.Date;

public class Goods implements java.io.Serializable {
    private int g_id = -1;
    private String picture, goodsName, size, goodOrder, g_type, unit, mark;
    private double buyPrice = -1, salePrice = -1;
    public Goods() {
    }

    public Goods(String goodsName, String g_type) {
        this.goodsName = goodsName;
        this.g_type = g_type;
    }

    public int getG_id() {
        return g_id;
    }

    public void setG_id(int g_id) {
        this.g_id = g_id;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
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

    public String getGoodOrder() {
        return goodOrder;
    }

    public void setGoodOrder(String goodOrder) {
        this.goodOrder = goodOrder;
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

    public String getMark() {
        return mark;
    }

    public void setMark(String mark) {
        this.mark = mark;
    }

    public double getBuyPrice() {
        return buyPrice;
    }

    public void setBuyPrice(double buyPrice) {
        this.buyPrice = buyPrice;
    }

    public double getSalePrice() {
        return salePrice;
    }

    public void setSalePrice(double salePrice) {
        this.salePrice = salePrice;
    }

}
