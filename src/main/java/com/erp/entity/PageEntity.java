package com.erp.entity;

public class PageEntity implements java.io.Serializable {
    private int currentPage = 1, pageSize = 10, start; //currentPage: 当前页, pageSize: 一页多少条, start从第几条开始

    public PageEntity() {
    }

    public PageEntity(int currentPage, int pageSize) {
        this.currentPage = currentPage;
        this.pageSize = pageSize;
    }
    public static PageEntity initPageEntity (PageEntity pageEntity) {
        return pageEntity = pageEntity == null ? new PageEntity(1, 10) : pageEntity;
    }
    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getStart() {
        return (this.currentPage - 1) * this.pageSize;
    }
}
