package com.liushuai.utils;


import java.sql.Connection;
import java.sql.SQLException;

import javax.sql.DataSource;

import com.mchange.v2.c3p0.ComboPooledDataSource;

public class MyDataSource {
	private static DataSource myDataSource = new ComboPooledDataSource();
	
	//直接获取一个连接池
	public static DataSource getDataSource(){
		return myDataSource;
	}
	//从连接池获取一个连接
	public static Connection getConnection() throws SQLException{
		return myDataSource.getConnection();
	}
	
}
