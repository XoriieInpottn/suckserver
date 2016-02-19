<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<html>
<head>
<link rel="stylesheet" href="<s:url value='/css/bootstrap.min.css' />" />
<script src="<s:url value="/js/jquery.min.js" />"></script>
<script src="<s:url value="/js/bootstrap.min.js" />"></script>
<script src="<s:url value="/js/link.js" />"></script>
<style type="text/css">
#tpl {
	width: 600px;
	height: 400px;
}
#logs {
	width: 600px;
	height: 200px;
	border: 1px solid #aaa;
	overflow: auto;
}
</style>
</head>
<div id="tid"></div>
<div id="logs"></div>
<textarea id="tpl"></textarea>
<button id="do">开始执行</button>
<button id="showLogs">查看日志</button>
<button id="stop">停止执行</button>
<body>
</body>
</html>
