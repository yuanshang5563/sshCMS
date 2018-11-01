<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
String extPath = request.getContextPath();
String extBasePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+extPath;
%>
<c:set var="root" value="<%=extBasePath%>"/>
<link rel="stylesheet" type="text/css" href="${root}/commons/jslib/ext4/resources/css/ext-all.css"/>
<script type="text/javascript" src="${root}/commons/jslib/ext4/ext-all.js"></script>
<script type="text/javascript" src="${root}/commons/jslib/ext4/locale/ext-lang-zh_CN.js"></script>
