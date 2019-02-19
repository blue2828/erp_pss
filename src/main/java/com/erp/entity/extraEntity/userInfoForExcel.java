package com.erp.entity.extraEntity;

import java.util.Date;

public class userInfoForExcel implements java.io.Serializable{
    private String empNo, updaterName, userOrder, userName;
    private Date updateTime;

    public userInfoForExcel(String empNo, String updaterName, String userOrder, String userName, Date updateTime) {
        this.empNo = empNo;
        this.updaterName = updaterName;
        this.userOrder = userOrder;
        this.userName = userName;
        this.updateTime = updateTime;
    }

    public userInfoForExcel() {
    }

    public String getEmpNo() {
        return empNo;
    }

    public void setEmpNo(String empNo) {
        this.empNo = empNo;
    }

    public String getUpdaterName() {
        return updaterName;
    }

    public void setUpdaterName(String updaterName) {
        this.updaterName = updaterName;
    }

    public String getUserOrder() {
        return userOrder;
    }

    public void setUserOrder(String userOrder) {
        this.userOrder = userOrder;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}
