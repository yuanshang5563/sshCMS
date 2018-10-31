<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>helloExtJS4.jsp</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
	
	<%@include file="/jsp/commons/extImport.jsp"%>
	<link rel="stylesheet" type="text/css" href="<%=basePath%>resources/commons/css/commonsIcons.css"/>
	<script type="text/javascript" src="<%=basePath%>jslib/system/menu/menuForm.js"></script> 
  </head>
  <body>
	<input type="hidden" value="<%=basePath %>" id="basePathUrl">
	<input type="hidden" value="<%=request.getParameter("menuId") %>" id="menuId">
	<input type="hidden" value="<%=request.getParameter("viewFlag") %>" id="viewFlag">
  </body>
</html>
