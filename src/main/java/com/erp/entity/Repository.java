package com.erp.entity;

public class Repository implements java.io.Serializable{
    private int id;
    private String repoName, repoCode, address;
    private Employee employee; // 仓库管理员对应实体类
    private String mark;

    public Repository() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getRepoName() {
        return repoName;
    }

    public void setRepoName(String repoName) {
        this.repoName = repoName;
    }

    public String getRepoCode() {
        return repoCode;
    }

    public void setRepoCode(String repoCode) {
        this.repoCode = repoCode;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public String getMark() {
        return mark;
    }

    public void setMark(String mark) {
        this.mark = mark;
    }
}
