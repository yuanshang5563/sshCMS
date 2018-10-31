<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String extPath = request.getContextPath();
String extBasePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+extPath+"/";
%>
<link rel="stylesheet" type="text/css" href="<%=extBasePath%>jslib/ext4/resources/css/ext-all.css"/>
<script type="text/javascript" src="<%=extBasePath%>jslib/ext4/ext-all.js"></script>
<script type="text/javascript" src="<%=extBasePath%>jslib/ext4/locale/ext-lang-zh_CN.js"></script>
