<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Suckserver - Create Task</title>
<link rel="stylesheet" href="<s:url value='/css/jquery-ui.min.css' />" />
<link rel="stylesheet" href="<s:url value='/css/common.css' />" />
<link rel="stylesheet" href="<s:url value='/css/createTask.css' />" />
<link rel="stylesheet" href="<s:url value='/css/jquery-treeview.css' />" />
<script src="<s:url value="/js/jquery.min.js" />"></script>
<script src="<s:url value="/js/jquery-ui.min.js" />"></script>
<script src="<s:url value="/js/common.js" />"></script>
<script src="<s:url value="/js/createTask.js" />"></script>
<script src="<s:url value="/js/xml-tree.js" />"></script>
<script src="<s:url value="/js/request.js" />"></script>
<script src="<s:url value="/js/jquery-treeview.js" />"></script>
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
								<textarea id="xml_text" onkeydown="tab(this)"></textarea>
							</div>
						</div>
					</div>
				</div>
				<div id="main-right">
					<div class="button-group" id="button-group">
						<button id="btn-table">Table</button>
						<button id="btn-column" class="margin-s">Column</button>
						<button id="btn-load" class="margin-s">Load</button>
						<button id="btn-select" class="margin-s">Select</button>
						<button id="btn-match" class="margin-s">Match</button>
						<button id="btn-save" class="margin-s">Save</button>
						<button id="btn-print" class="margin-s">Print</button>
						<button id="btn-var" class="margin-s">Var</button>
						<button id="btn-scan" class="margin-s">Scan</button>
						<button id="btn-subtask" class="margin-s">Subtask</button>
						<button id="btn-click" class="margin-s">Click</button>
						<button id="btn-type" class="margin-s">Type</button>
						<button id="btn-validate" class="margin-s">Validate</button>
						<button id="btn-drop" class="margin-s">Drop</button>
						<button id="btn-while" class="margin-s">While</button>
					</div>
				</div>
				<div id="main-bottom">
					<div></div>
				</div>
			</div>
			<%@ include file="dialogs/task.jsp"%>
			<%@ include file="dialogs/table.jsp"%>
			<%@ include file="dialogs/column.jsp"%>
			<%@ include file="dialogs/load.jsp"%>
			<%@ include file="dialogs/select.jsp"%>
			<%@ include file="dialogs/match.jsp"%>
			<%@ include file="dialogs/save.jsp"%>
			<%@ include file="dialogs/var.jsp"%>
			<%@ include file="dialogs/print.jsp"%>
			<%@ include file="dialogs/scan.jsp"%>
			<%@ include file="dialogs/subtask.jsp"%>
			<%@ include file="dialogs/click.jsp"%>
			<%@ include file="dialogs/type.jsp"%>
			<%@ include file="dialogs/drop.jsp"%>
			<%@ include file="dialogs/ic.jsp"%>
			<%@ include file="dialogs/validate.jsp"%>
			<%@ include file="dialogs/while.jsp"%>
		</div>
	</div>
</body>
</html>
