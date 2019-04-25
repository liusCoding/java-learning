package com.liushuai.service;

import java.util.List;

import com.liushuai.dao.BookDao;
import com.liushuai.domain.Book;
import com.liushuai.domain.Page;

public class BookService {
	private BookDao dao  = new BookDao();
	
	//分页处理
	public Page findPage(int currentPage, int pageSize) {
		Page page  =new Page();
		//1.封装当前页
		page.setCurrentPage(currentPage);
		//2.封装每页显示的条数
		page.setPageSize(pageSize);
		//3.封装总条数
		int totalCount  =dao.getToalCount();
		System.out.println("总条数：" + totalCount);
		page.setTotalCount(totalCount);
		//4.封装总页数
		int totalPage = (int)Math.ceil(1.0*totalCount/pageSize);
		System.out.println("总页数："+totalPage);
		page.setTotalPage(totalPage);
		//5.封装每页显示的数据
		//开始索引
		int startIndex = (currentPage -1)*pageSize;
		List<Book> bookList = dao.getPageData(startIndex,pageSize);
		System.out.println("分页数据:"+bookList);
		page.setPageData(bookList);
		return page ;
	}
	
	//用户登录处理
	public boolean toLogin(String name, String password) {
		if("admin".equals(name)&&"admin".equals(password)){
			return true;
		}
		return false;
	}
	//添加书籍
	public boolean addBook(Book book) {
		//没有业务进行处理 传递到dao层
		int rows = dao.addBook(book);
		return rows>0?true:false;
	}
	//删除书籍
	public void toDelBook(String id) {
		//没有负责业务  传递数据到dao层处理
		int id1= Integer.parseInt(id);
		dao.delBook(id1);
		
	}
	//获取id对应的Book
	public Book toFindBook(String id) {
		//传递数据到dao层 获取book
		int id1= Integer.parseInt(id);
		return dao.toFindBook(id1);
	}

	public void toUpdateBook(Book book) {
		//没有负责业务 将请求传到dao层
		dao.toUpdateBook(book);
	}

}
