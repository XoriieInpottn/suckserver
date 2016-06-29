<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Suckserver - Create Task</title>
<link rel="stylesheet" href="<s:url value='/css/jquery-ui.min.css' />" />
<link rel="stylesheet" href="<s:url value='/css/common.css' />" />
<link rel="stylesheet" href="<s:url value='/css/create-task.css' />" />
<link rel="stylesheet" href="<s:url value='/css/jquery-treeview.css' />" />
<script src="<s:url value="/js/jquery.min.js" />"></script>
<script src="<s:url value="/js/jquery-ui.min.js" />"></script>
<script src="<s:url value="/js/jquery-treeview.js" />"></script>
<script src="<s:url value="/js/jquery-formdialogs.js" />"></script>
<script src="<s:url value="/js/common.js" />"></script>
<script src="<s:url value="/js/create-task.js" />"></script>
<script src="<s:url value="/js/xml-tree.js" />"></script>
<script src="<s:url value="/js/request.js" />"></script>
<%@ include file="actions.jsp"%>
</head>
<body>
	<div class="ui-container">
		<%@ include file="nav.jsp"%>
		<%@ include file="sidebar.jsp"%>
		<div class="ui-main">
			<div id="main">
				<div id="main-top">
					<div class="cmd-group">
						<button id="btn-edit">Modify</button>
						<button id="btn-delete">Remove</button>
						<button id="btn-up">Up</button>
						<button id="btn-down">Down</button>
						<button id="btn-test">Test</button>
						<button id="btn-submit">Execute</button>
						<button id="btn-quit">Stop</button>
					</div>
				</div>
				<div id="main-mid">
					<div id="tabs">
						<ul>
							<li><a href="#tabs-1" id="tab-tree">Tree</a></li>
							<li><a href="#tabs-2" id="tab-xml">XML</a></li>
						</ul>
						<div id="tabs-1">
							<div class="ui-accordion ui-treeview" id="tree"></div>
						</div>
						<div id="tabs-2">
							<div id="xml">
								<textarea id="xml_text""></textarea>
							</div>
						</div>
					</div>
				</div>
				<div id="main-right">
					<div id="routine-buttons" class="button-group"></div>
				</div>
				<div id="main-bottom">
					<div></div>
				</div>
			</div>
			<div id="routine-dialogs"></div>
		</div>
	</div>
</body>
</html>
