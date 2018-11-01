<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/commons/header.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>首页</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<link rel="stylesheet" type="text/css" href="${root}/commons/resources/css/commonsIcons.css"/>
	<script type="text/javascript" src="${root}/commons/jslib/indexlib/index.js"></script> 
  </head>
  
  <body id="indexBody">
    <input type="hidden" value="${root}" id="basePathUrl">
    <input type="hidden" value='${allSubMenus}' id="allSubMenus">
  </body>
</html>
