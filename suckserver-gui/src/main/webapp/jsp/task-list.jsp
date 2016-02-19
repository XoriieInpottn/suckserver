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
	<div class="ui-container">
		<%@ include file="nav.jsp"%>
		<%@ include file="sidebar.jsp"%>
		<div class="ui-main">
			<div class="ui-padding-l">
				<div class="pager1">
					<button class="ui-pager-first">&nbsp;</button>
					<button class="ui-pager-prev">&nbsp;</button>
					<div class="ui-pager-range">
						<button>{{pageNum}}</button>
					</div>
					<button class="ui-pager-next">&nbsp;</button>
					<button class="ui-pager-last">&nbsp;</button>
				</div>
				<ul class="datalist">
					<li>
						<div class="ui-column-5pct">ID</div>
						<div class="ui-column-20pct">Name</div>
						<div class="ui-column-10pct">Type</div>
						<div class="ui-column-10pct">Status</div>
						<div class="ui-column-20pct">Start time</div>
						<div class="ui-column-20pct">End time</div>
					</li>
					<li>
						<div class="ui-column-5pct">{{0.id}}</div>
						<div class="ui-column-20pct">
							<a href="<s:url value="/" />?id={{0.id}}">{{0.name}}</a>
						</div>
						<div class="ui-column-10pct">{{0.type}}</div>
						<div class="ui-column-10pct">{{0.status}}</div>
						<div class="ui-column-20pct">{{0.startTime}}</div>
						<div class="ui-column-20pct">{{0.endTime}}</div>
						<div class="ui-column-10pct">
							<button class="btn-remove" style="display: inline;">&nbsp;</button>
						</div>
					</li>
				</ul>
				<div class="pager2">
					<button class="ui-pager-first">&nbsp;</button>
					<button class="ui-pager-prev">&nbsp;</button>
					<div class="ui-pager-range">
						<button>{{pageNum}}</button>
					</div>
					<button class="ui-pager-next">&nbsp;</button>
					<button class="ui-pager-last">&nbsp;</button>
				</div>
			</div>
		</div>
	</div>
</body>
</html>
