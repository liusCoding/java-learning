package com.liushuai.web;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;

import com.liushuai.domain.Book;
import com.liushuai.domain.Page;
import com.liushuai.service.BookService;

public class BookServlet extends HttpServlet {
	private BookService service = new BookService();
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String action = request.getParameter("action");
		System.out.println(action);
		if("manage".equals(action)){//1.书籍管理页面
			toManageBook(request, response);
		}else if("login".equals(action)){//2.登录功能处理
			toLogin(request, response);
		}else if("add".equals(action)){//3.添加书籍处理
			toAddBook(request, response);
		}else if("del".equals(action)){//4.删除学籍
			toDelBook(request, response);
		}else if("updateInit".equals(action)){//5.修改前初始化
			toUpdateInit(request, response);
		}else if("update".equals(action)){//6.修改书籍信息
			//1.获取前端修改的数据
			Map<String, String[]> map = request.getParameterMap();
			//2.封装数据 传递到service层进行处理
			Book book = new Book();
			try {
				BeanUtils.populate(book, map);
				System.out.println(book.toString());
			} catch (IllegalAccessException | InvocationTargetException e) {
				e.printStackTrace();
			}
			service.toUpdateBook(book);
			//3.前端展示
			response.sendRedirect("BookServlet?action=manage");
		}
	}

	private void toUpdateInit(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		//1.获取前端要修改书籍的id
		String id = request.getParameter("id");
		//2.传递书籍到service层获取book信息
		Book book = service.toFindBook(id);
		if(book!=null){
			request.setAttribute("book", book);
			request.getRequestDispatcher("updateBook.jsp").forward(request, response);
		}
	}

	private void toDelBook(HttpServletRequest request, HttpServletResponse response) throws IOException {
		//1.获取前端要删除id
		String id = request.getParameter("id");
		//2.传递id到serivce层进行删除
		service.toDelBook(id);
		//3.前端展示
		response.sendRedirect("BookServlet?action=manage");
	}

	private void toAddBook(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		//1.获取前端数据
		Map<String, String[]> map = request.getParameterMap();
		System.out.println(map);
		//2.封装数据 并传递到service层进行处理
		Book book = new Book();
		try {
			BeanUtils.populate(book, map);
			System.out.println(book.toString());
			} catch (IllegalAccessException | InvocationTargetException e) {
				e.printStackTrace();
			}
		boolean isAdd = service.addBook(book);
		//3.处理数据，传递到前端进行展示
		if(isAdd){
			response.sendRedirect("BookServlet?action=manage");
		}else {
			request.setAttribute("msg", "添加失败，请稍后再试！！！");
			request.getRequestDispatcher("add.jsp").forward(request, response);
		}
	}

	private void toLogin(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		//1.获取前端数据
		String name = request.getParameter("name");
		String password= request.getParameter("password");
		//2.传递数据到service层进行登录处理
		boolean isSuccess=service.toLogin(name,password);
		//3.处理数据，进行前端展示
		if(isSuccess){
			//保存凭证
			request.setAttribute("name", name);
			response.sendRedirect("BookServlet?action=manage");
		}else {
			request.setAttribute("msg", "用户名或者密码错误");
			request.getRequestDispatcher("login.jsp").forward(request, response);
		}
	}

	private void toManageBook(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		//1.获取前端数据
		String current = request.getParameter("currentPage");
		if (current== null) {
			current="1";
		}
		int currentPage= Integer.parseInt(current);
		System.out.println(currentPage);
		int pageSize = 4;
		//2.传递数据到service层 得到Page对象
		Page page = service.findPage(currentPage,pageSize);
		System.out.println(page);
		//3.传递数据到前端进行展示
		request.setAttribute("page", page);
		request.getRequestDispatcher("bookManage.jsp").forward(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
}