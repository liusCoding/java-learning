package com.liushuai.dao;

import java.sql.SQLException;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import com.liushuai.domain.Book;
import com.liushuai.utils.MyDataSource;

public class BookDao {
	//查询书籍信息的总条数
	public int getToalCount() {
		QueryRunner runner = new QueryRunner(MyDataSource.getDataSource());
		String sql = "select count(1) from book where flag = ? ";
		try {
			Long totalCount = (Long)runner.query(sql,new ScalarHandler(),1);
			return totalCount.intValue();
		} catch (SQLException e) {
			return 0;
		}
			
	}
	//查询分页的书籍数据
	public List<Book> getPageData(int startIndex, int pageSize) {	
		try {
			QueryRunner runner = new QueryRunner(MyDataSource.getDataSource());
			String sql = "select * from book where flag = ? limit ?,? ";
			return runner.query(sql,new BeanListHandler<Book>(Book.class),1,startIndex,pageSize);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	//插入书籍信息
	public int addBook(Book book) {
		try {
			QueryRunner runner = new QueryRunner(MyDataSource.getDataSource());
			String sql = "insert into book values(?,?,?,?,?)";
			return  runner.update(sql, null,book.getBookName(),book.getPrice(),book.getAuthor(),1);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}
	//删除指定id的书籍信息
	public void delBook(int id) {
		try {
			QueryRunner runner = new QueryRunner(MyDataSource.getDataSource());
			String sql = "update book set flag =? where id= ?";
			runner.update(sql,0,id);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	//获取指定id的书籍信息
	public Book toFindBook(int id) {
		try {
			QueryRunner runner  = new QueryRunner(MyDataSource.getDataSource());
			String sql = "select * from book where id =?";
			return runner.query(sql, new BeanHandler<Book>(Book.class),id);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
		
	}
	//修改指定id的书籍信息
	public void toUpdateBook(Book book) {
		try {
			QueryRunner runner = new QueryRunner(MyDataSource.getDataSource());
			String sql = "update book set bookName=?,price=?,author=? where id = ?";
			runner.update(sql,book.getBookName(),book.getPrice(),book.getAuthor(),book.getId());
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
