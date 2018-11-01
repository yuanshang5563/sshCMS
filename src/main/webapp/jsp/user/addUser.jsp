<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/commons/header.jsp"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>addUser</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<script type="text/javascript" src="${root}/commons/jslib/userlib/user/addUser.js"></script> 
  </head>
  <body>
  <input type="hidden" value="${root}" id="basePathUrl">
  <input type="hidden" value="<%=request.getParameter("userId") %>" id="userId">
  <input type="hidden" value="<%=request.getParameter("viewFlag") %>" id="viewFlag">
  </body>
</html>
