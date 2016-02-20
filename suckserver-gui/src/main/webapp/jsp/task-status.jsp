<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>suckserver</title>
<link rel="stylesheet" href="<s:url value='/css/jquery-ui.min.css' />" />
<link rel="stylesheet" href="<s:url value='/css/common.css' />" />
<link rel="stylesheet" href="<s:url value='/css/task-status.css' />" />
<script src="<s:url value="/js/jquery.min.js" />"></script>
<script src="<s:url value="/js/jquery-ui.min.js" />"></script>
<script src="<s:url value="/js/jquery.timers-1.1.2.js" />"></script>
<script src="<s:url value="/js/common.js" />"></script>
<script src="<s:url value="/js/task-status.js" />"></script>
<%@ include file="actions.jsp"%>
</head>
<body>
	<div class="ui-container">
		<%@ include file="nav.jsp"%>
		<%@ include file="sidebar.jsp"%>
		<div class="ui-basic-info">
			<div>
				Task name: <span class="name"></span>
			</div>
			<div>
				Type: <span class="type"></span>
			</div>
			<div>
				Status: <span class="status"></span>
				<button class="stop-task" title="Stop the task.">&nbsp;</button>
			</div>
			<div>
				Start time: <span class="start-time"></span>
			</div>
			<div>
				End time: <span class="end-time"></span>
			</div>
			<div>
				<span class="stat"></span>
			</div>
		</div>
		<div class="ui-main log-box">
			<div class="ui-padding-m"></div>
		</div>
	</div>
</body>
</html>
