<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE>
<html>
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>Suckserver</title>
<link rel="stylesheet" href="<s:url value='/css/bootstrap.min.css' />" />
<link rel="stylesheet" href="<s:url value='/css/index.css' />" />
<script src="<s:url value="/js/jquery.min.js" />"></script>
<script src="<s:url value="/js/bootstrap.min.js" />"></script>
<script src="<s:url value="/js/bootstrap-treeview.js" />"></script>
<script src="<s:url value="/js/xml-tree.js" />"></script>
<script src="<s:url value="/js/jquery.timers-1.1.2.js" />"></script>
<script src="<s:url value="/js/common.js" />"></script>
<script src="<s:url value="/js/index.js" />"></script>
<script src="<s:url value="/js/link.js" />"></script>
<script>
	var URL_CREATE_TASK = "<s:url value="/CreateTask.action" />";
	var URL_GET_LOGS = "<s:url value="/GetLogs.action" />";
	var URL_STOP_TASK = "<s:url value="/StopTask.action" />";
</script>
</head>
<body id="s-body">
	<div id="container">
		<div id="nav">
			<a class="navbar-brand" href="#">Suckserver</a>
		</div>
		<div id="side-bar">
			<div class="padding-s">
				 <ul class="nav nav-sidebar">
			            <li class="active"><a href="#">添加爬虫 <span class="sr-only">(current)</span></a></li>
			     </ul>
			</div>
		</div>
		<div id="log-box">
			<div class="padding-s"></div>
		</div>
		<div id="tool-bar1">
			<div class="padding-s">
				<button type="button" class="btn btn-primary btn-group" id="btn_edit">修改</button>
				<button type="button" class="btn btn-primary" id="btn_del">删除</button>
				<button type="button" class="btn btn-primary" id="btn_up">上移</button>
				<button type="button" class="btn btn-primary" id="btn_down">下移</button>
				<button type="button" class="btn btn-info" id="btn_test">测试</button>
				<button type="submit" class="btn btn-success" id="btn_submit">执行</button>
				<button type="button" class="btn btn-danger" id="btn_quit"
					disabled="disabled">终止</button>
			</div>
		</div>
		<div id="tool-bar2">
			<div class="padding-s">
				<button type="button" class="btn btn-primary cmd-group"
					data-toggle="modal" id="btn_table">创建新表</button>
				<button type="button" class="btn btn-primary cmd-group"
					data-toggle="modal" id="btn_column">创建新列</button>
				<button type="button" class="btn btn-primary cmd-group"
					data-toggle="modal" id="btn_go">载入页面</button>
				<button type="button" class="btn btn-primary cmd-group"
					data-toggle="modal" id="btn_select">选择元素</button>
				<button type="button" class="btn btn-primary cmd-group"
					data-toggle="modal" id="btn_match">文字匹配</button>
				<button type="button" class="btn btn-primary cmd-group"
					data-toggle="modal" id="btn_group_save">保存记录</button>
				<button type="button" class="btn btn-primary cmd-group"
					data-toggle="modal" id="btn_print">输出数据</button>
				<button type="button" class="btn btn-primary cmd-group"
					data-toggle="modal" id="btn_var">临时存储</button>
			</div>
		</div>
		<div id="main">
			<div>
				<ul class="nav nav-tabs" role="tablist" id="nav_second">
					<li role="presentation" class="active"><a href="#home"
						aria-controls="home" role="tab" data-toggle="tab" id="home_tab">命令树</a></li>
					<li role="presentation"><a href="#xml" aria-controls="xml"
						role="tab" data-toggle="tab" id="xml_tab">源代码</a></li>
				</ul>
			</div>
			<div class="padding-m">
				<div class="tab-content">
					<div role="tabpanel" class="tab-pane active" id="home">
						<div id="div-border">
							<div id="tree"></div>
						</div>
					</div>
					<div role="tabpanel" class="tab-pane" id="xml">
						<textarea class="form-cintrol" rows="12" id="xml_text"></textarea>
					</div>
				</div>
			</div>
		</div>
	</div>
	<!-- Modal for Table-->
	<div class="modal fade" id="myModal_table" tabindex="-1" role="dialog"
		aria-labelledby="myModalLabel">
		<div class="modal-dialog" role="document">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"
						aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
					<h4 class="modal-title" id="myModalLabel">添加表</h4>
				</div>
				<div class="modal-body">
					<form>
						<div class="form-group">
							<label class="control-label">*表名称</label> <input type="text"
								class="warning_info" id="warning_table"> <input
								type="text" class="form-control" id="txt_table"
								placeholder="该部分不能为空">
						</div>
						<div class="form-group">
							<label class="control-label">是否覆盖</label> <label
								class="checkbox-inline"> <input type="radio"
								name="optionsRadiosinline" id="optionsRadios3" value="option1"
								checked> 是
							</label> <label class="checkbox-inline"> <input type="radio"
								name="optionsRadiosinline" id="optionsRadios4" value="option2">
								否
							</label>
						</div>
					</form>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
					<button type="button" class="btn btn-primary" id="btn_table_save">保存</button>
				</div>
			</div>
		</div>
	</div>
	<!-- End of Modal for Table -->

	<!-- Modal for Column -->
	<div class="modal fade" id="myModal_column" tabindex="-1" role="dialog"
		aria-labelledby="myModalLabel">
		<div class="modal-dialog" role="document">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"
						aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
					<h4 class="modal-title" id="myModalLabel">添加列</h4>
				</div>
				<div class="modal-body">
					<form>
						<div class="form-group">
							<label class="control-label">*名称</label><input type="text"
								class="warning_info" id="warning_column_name" readonly>
							<input type="text" class="form-control" id="txt_column_name"
								placeholder="该部分不能为空">
						</div>
						<div class="form-group">
							<label class="control-label">类型</label> <input type="text"
								class="warning_info" id="warning_column_attr" readonly>
							<input type="text" class="form-control" id="txt_column_attr"
								placeholder="text">
						</div>
					</form>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
					<button type="button" class="btn btn-primary" id="btn_column_save">保存</button>
				</div>
			</div>
		</div>
	</div>
	<!-- End of Modal for Column -->

	<!-- Modal for Go -->
	<div class="modal fade" id="myModal_go" tabindex="-1" role="dialog"
		aria-labelledby="myModalLabel">
		<div class="modal-dialog" role="document">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"
						aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
					<h4 class="modal-title" id="myModalLabel">载入页面</h4>
				</div>
				<div class="modal-body">
					<form>
						<div class="form-group">
							<label class="control-label">地址</label> <input type="text"
								class="warning_info" id="warning_go_url" readonly> <input
								type="text" class="form-control" id="txt_go_url"
								placeholder="此部分不能为空">
						</div>
						<div class="form-group">
							<label class="control-label">最大页数</label> <input type="text"
								class="warning_info" id="warning_go_maxpage" readonly> <input
								type="text" class="form-control" id="txt_go_maxpage"
								placeholder="1">
						</div>
					</form>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
					<button type="button" class="btn btn-primary" id="btn_go_save">保存</button>
				</div>
			</div>
		</div>
	</div>
	<!-- End of Modal for Go -->

	<!-- Modal for Select -->
	<div class="modal fade" id="myModal_select" tabindex="-1" role="dialog"
		aria-labelledby="myModalLabel">
		<div class="modal-dialog" role="document">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"
						aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
					<h4 class="modal-title" id="myModalLabel">选择元素</h4>
				</div>
				<div class="modal-body">
					<form>
						<div class="form-group">
							<label class="control-label">*路径</label> <input type="text"
								class="warning_info" id="warning_select_path" readonly>
							<input type="text" class="form-control" id="txt_select_path"
								placeholder="此部分不能为空">
						</div>
					</form>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
					<button type="button" class="btn btn-primary" id="btn_select_save">保存</button>
				</div>
			</div>
		</div>
	</div>
	<!-- End of Modal for select -->

	<!-- Modal for Match -->
	<div class="modal fade" id="myModal_match" tabindex="-1" role="dialog"
		aria-labelledby="myModalLabel">
		<div class="modal-dialog" role="document">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"
						aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
					<h4 class="modal-title" id="myModalLabel">文字匹配</h4>
				</div>
				<div class="modal-body">
					<form>
						<div class="form-group">
							<label class="control-label">*对象</label><input type="text"
								class="warning_info" id="warning_match_attr" readonly> <input
								type="text" class="form-control" id="txt_match_attr"
								placeholder="此部分不能为空">
						</div>
						<div class="form-group">
							<label class="control-label">路径</label> <input type="text"
								class="form-control" id="txt_match_path">
						</div>
						<div class="form-group">
							<label class="control-label">属性</label> <input type="text"
								class="form-control" id="txt_match_tab">
						</div>
					</form>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
					<button type="button" class="btn btn-primary" id="btn_match_save">保存</button>
				</div>
			</div>
		</div>
	</div>
	<!-- End of Modal for Match -->

	<!-- Modal for Save-->
	<div class="modal fade" id="myModal_save" tabindex="-1" role="dialog"
		aria-labelledby="myModalLabel">
		<div class="modal-dialog" role="document">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"
						aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
					<h4 class="modal-title" id="myModalLabel">保存表</h4>
				</div>
				<div class="modal-body">
					<form>
						<div class="form-group">
							<label class="control-label">*名称</label> <input type="text"
								class="warning_info" id="warning_save_table"> <input
								type="text" class="form-control" id="txt_save_table"
								placeholder="该部分不能为空">
						</div>
					</form>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
					<button type="button" class="btn btn-primary" id="btn_save_save">保存</button>
				</div>
			</div>
		</div>
	</div>
	<!-- End of Modal for Save -->

<!-- Modal for Var-->
	<div class="modal fade" id="myModal_var" tabindex="-1" role="dialog"
		aria-labelledby="myModalLabel">
		<div class="modal-dialog" role="document">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"
						aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
					<h4 class="modal-title" id="myModalLabel">临时存储</h4>
				</div>
				<div class="modal-body">
					<form>
						<div class="form-group">
							<label class="control-label">*名称</label> <input type="text"
								class="warning_info" id="warning_var_name"> <input
								type="text" class="form-control" id="txt_var_name"
								placeholder="该部分不能为空">
							<label class="control-label">值</label> <input
								type="text" class="form-control" id="txt_var_attr">
						</div>
					</form>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
					<button type="button" class="btn btn-primary" id="btn_var_save">保存</button>
				</div>
			</div>
		</div>
	</div>
	<!-- End of Modal for Var -->

	<!-- Modal for Print -->
	<div class="modal fade" id="myModal_print" tabindex="-1" role="dialog"
		aria-labelledby="myModalLabel">
		<div class="modal-dialog" role="document">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"
						aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
					<h4 class="modal-title" id="myModalLabel">输出</h4>
				</div>
				<div class="modal-body">
					<form>
						<div class="form-group">
							<label class="control-label">*内容</label> <input type="text"
								class="warning_info" id="warning_print_content"> <input
								type="text" class="form-control" id="txt_print_content"
								placeholder="该部分不能为空">
						</div>
					</form>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
					<button type="button" class="btn btn-primary" id="btn_print_save">保存</button>
				</div>
			</div>
		</div>
	</div>
</body>
</html>