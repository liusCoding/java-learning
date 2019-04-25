<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
	<script type="text/javascript" src="js/jquery-1.8.3.js"></script>
	<script type="text/javascript">
	$(function(){
		var isBookName = false;
		var isAuthor=false;
		var isPrice= false;
		$("#bookName").blur(function(){
			var value = $(this).val().trim();
			if(value==""){
				$("#bookNameSpan").html("<font color='red'>书名不能为空</font>")
				isName=false;
			}else{
					$("#bookNameSpan").html("<font color='green'>ok</font>")
				isName=true;
			}
		})
		
		$("#author").blur(function(){
			var value = $(this).val().trim();
			if(value==""){
				isAuthor=false;
				$("#authorSpan").html("<font color='red'>作者名不能为空</font>")
			}else{
					$("#authorSpan").html("<font color='green'>ok</font>")
				isAuthor=true;
			}
		})
		
		$("#price").blur(function(){
			var value = $(this).val().trim();
			if(value==""){
				isPrice=false;
				$("#priceSpan").html("<font color='red'>价格不能为空</font>")
			}else{
				isPrice=true;
				$("#priceSpan").html("<font color='green'>ok</font>")
			}
		})
		
		$("#form").submit(function(){
			$("#bookName").trigger("blur");
			$("#author").trigger("blur");
			$("#price").trigger("blur");
			return isName&&isAuthor&&isPrice;
		})
	})
	</script>
</head>
<body>
	<font face="微软雅黑" color="aquamarine" style="font-weight: 100;" size="6">修改书籍信息</font>
	<form action="BookServlet?action=update" method="post" id="form">
    		书名：<input type="text" name="bookName" id="bookName"  value="${book.bookName}"/>
    		<input type="hidden" name="id" value="${book.id }">
    		<span id="bookNameSpan"></span><br>
    		作者：<input type="text" name="author" id="author" value="${book.author}"/><span id="authorSpan"></span><br>
    		价格：<input type="text" name="price" id="price" value="${book.price}"/><span id="priceSpan"></span><br>
    		<input type="submit" value="确认修改"/>
    </form>
</body>
</html>
