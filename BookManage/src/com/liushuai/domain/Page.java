package com.liushuai.domain;

import java.util.List;

public class Page {
	//当前页面
	private int currentPage;
	//当前页面显示的条数
	private int pageSize;
	//总条数
	private int totalCount;
	//总页数
	private int totalPage;
	//每页的显示的数据
	private List<?> pageData;
	private String url;
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
	public int getTotalCount() {
		return totalCount;
	}
	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
	}
	public int getTotalPage() {
		return totalPage;
	}
	public void setTotalPage(int totalPage) {
		this.totalPage = totalPage;
	}
	public List<?> getPageData() {
		return pageData;
	}
	public void setPageData(List<?> pageData) {
		this.pageData = pageData;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	@Override
	public String toString() {
		return "Page [currentPage=" + currentPage + ", pageSize=" + pageSize + ", totalCount=" + totalCount
				+ ", totalPage=" + totalPage + ", pageData=" + pageData + ", url=" + url + "]";
	}
	public Page(int currentPage, int pageSize, int totalCount, int totalPage, List<?> pageData, String url) {
		super();
		this.currentPage = currentPage;
		this.pageSize = pageSize;
		this.totalCount = totalCount;
		this.totalPage = totalPage;
		this.pageData = pageData;
		this.url = url;
	}
	public Page() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
	
	
}
