<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>suckserver</title>
<link rel="stylesheet" href="<s:url value='/css/jquery-ui.min.css' />" />
<link rel="stylesheet" href="<s:url value='/css/jquery-datalist.css' />" />
<link rel="stylesheet" href="<s:url value='/css/common.css' />" />
<link rel="stylesheet" href="<s:url value='/css/task-list.css' />" />
<script src="<s:url value="/js/jquery.min.js" />"></script>
<script src="<s:url value="/js/jquery-ui.min.js" />"></script>
<script src="<s:url value="/js/jquery-datalist.js" />"></script>
<script src="<s:url value="/js/common.js" />"></script>
<script src="<s:url value="/js/task-list.js" />"></script>
<%@ include file="actions.jsp"%>
</head>
<body>
	<div style="display: inline-block; width: 100px; height: 100px; background: red; text-align: center;">a
		<div style="display: inline-block; width: 10px; height: 10px; background: green; vertical-align: middle;"></div>
		<div style="display: inline-block; width: 10px; height: 40px; background: blue; vertical-align: middle;"></div>
	</div>
</body>
</html>
