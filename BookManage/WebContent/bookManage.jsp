<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
			<table border="1" cellspacing="1" cellpadding="1" width="500" height="300">
			<tr>
				<th colspan="4">书籍管理信息</th>
			</tr>
			<tr>
				<td align="center">书名</td>
				<td align="center">价格</td>
				<td align="center">作者</td>
				<td align="center">操作</td>
			</tr>
			
			<c:forEach items="${page.pageData}"  var="book">
			<tr>
				<td align="center">${book.bookName }</td>
				<td align="center">${book.price }</td>
				<td align="center">${book.author }</td>
				<td align="center">
					<a href="BookServlet?action=updateInit&id=${book.id }">修改</a>|<a href="BookServlet?action=del&id=${book.id }">删除</a>
				</td>
			</tr>
			</c:forEach>
			<tr>
				<td align="center" colspan="4"><a href="addBook.jsp">添加书籍</a></td>
				
			</tr>
		
			<tr>
			<td colspan="4" align="center">
			<a href="BookServlet?action=manage&currentPage=1">首页</a>
			<c:if test="${page.currentPage != 1 }">
				<a href="BookServlet?action=manage&currentPage=${page.currentPage-1 }">上一页</a>
			</c:if>
			<c:if test="${page.currentPage != page.totalPage }">
			<a href="BookServlet?action=manage&currentPage=${page.currentPage+1 }">下一页</a>
			</c:if>
				<a href="BookServlet?action=manage&currentPage=${page.totalPage }">尾页</a>
			
			</td>
		
		</tr>
		</table>
</body>
</html>