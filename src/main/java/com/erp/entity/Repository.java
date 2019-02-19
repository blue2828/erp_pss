package com.erp.entity;

public class Repository implements java.io.Serializable{
    private int id, state;
    private String repoName, repoCode, address;
    private Employee employee; // 仓库管理员对应实体类
}
