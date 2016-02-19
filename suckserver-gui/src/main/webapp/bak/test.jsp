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
  </head>
  <body id="s-body">
    <nav class="navbar navbar-inverse navbar-fixed-top">
		<div class="container">
			<!-- Brand and toggle get grouped for better mobile display -->
			<div class="navbar-header">
				<button type="button" class="navbar-toggle collapsed" data-toggle="collapse"
					data-target="#bs-example-navbar-collapse-1" aria-expanded="false">
					<span class="sr-only">Toggle navigation</span> <span class="icon-bar"></span> <span class="icon-bar"></span> <span
						class="icon-bar"></span>
				</button>
				<a class="navbar-brand" href="#">Suckserver</a>
			</div>
		</div>
		<!-- /.container-fluid -->
	</nav>
    <div class="container-fluid">
      <div class="row">
        <div class="col-sm-3 sidebar">     
          <ul class="nav nav-sidebar">
            <li class="active"><a href="#">添加爬虫 <span class="sr-only">(current)</span></a></li>
          </ul>
       </div>  
        <div class="col-sm-9 main">
			<div>
				<ul class="nav nav-tabs" role="tablist" id="nav_second">
				<li role="presentation" class="active"><a href="#home" aria-controls="home" role="tab" data-toggle="tab" id="home_tab">命令树</a></li>
				<li role="presentation"><a href="#xml" aria-controls="xml" role="tab" data-toggle="tab" id="xml_tab">源代码</a></li>
				</ul>
			</div>
        	<div class="tab-content">
        		<div role="tabpanel" class="tab-pane active" id="home">
        		<div class="container" id="main">
					<div class="row">
						<div class="col-md-9">
					    	<button type="button" class="btn btn-primary" id="btn_edit">修改元素</button>
							<button type="button" class="btn btn-primary" id="btn_del">删除元素</button>
							<button type="button" class="btn btn-primary" id="btn_up">向上移动</button>
							<button type="button" class="btn btn-primary" id="btn_down">向下移动</button>
							<button type="button" class="btn btn-info" id="btn_test" >&nbsp;&nbsp;&nbsp;&nbsp;测试&nbsp;&nbsp;&nbsp;&nbsp;</button>
							<button type="submit" class="btn btn-success" id="btn_submit">&nbsp;&nbsp;&nbsp;&nbsp;执行&nbsp;&nbsp;&nbsp;&nbsp;</button>
							<button type="button" class="btn btn-danger" id="btn_quit" disabled="disabled">&nbsp;&nbsp;&nbsp;&nbsp;终止&nbsp;&nbsp;&nbsp;&nbsp;</button>
							<div id="div-border">
								<div id="tree"></div>
							</div>
						</div>
						<div class="col-md-3">
							<div>
								<div class="btn-group-vertical">
									<button type="button" class="btn btn-primary cmd-group" data-toggle="modal" id= "btn_table">创建新表</button>
									<button type="button" class="btn btn-primary cmd-group" data-toggle="modal" id="btn_column">创建新列</button>
									<button type="button" class="btn btn-primary cmd-group" data-toggle="modal" id="btn_go">载入页面</button>
									<button type="button" class="btn btn-primary cmd-group" data-toggle="modal" id="btn_select">选择元素</button>
									<button type="button" class="btn btn-primary cmd-group" data-toggle="modal" id="btn_match">文字匹配</button>
									<button type="button" class="btn btn-primary cmd-group" data-toggle="modal" id="btn_group_save">保存记录</button>
									<button type="button" class="btn btn-primary cmd-group" data-toggle="modal" id="btn_print">输出数据</button>
								</div>
							</div>
						</div>
					</div>
				</div>
        		</div>
        		<div role="tabpanel" class="tab-pane" id="xml">
	        		<div class="container" id="main2">
		        		<div class="rows">
							<div class="col-md-1"></div>
							<div class="col-md-10">
								<textarea class="form-cintrol" rows="12" id="xml_text"></textarea>
								<button type="submit" class="btn btn-primary" id="text_submit">开始执行</button>
							</div>
							<div class="col-md-1"></div>
						</div>
	        		</div>
        		</div>
        	</div>
        </div>
      </div>
    </div>
     <div id="log_div">
			<div id="msg" style="overflow:hidden;width:480px;"></div>
			<div id="msg_end" style="height:0px; overflow:hidden"></div>
	</div>
    	<!-- Modal for Table-->
	<div class="modal fade" id="myModal_table" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
		<div class="modal-dialog" role="document">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal" aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
					<h4 class="modal-title" id="myModalLabel">设置Table属性</h4>
				</div>
				<div class="modal-body">
					<form>
						<div class="form-group">
							<label class="control-label">*表名称</label> <input type="text" class="warning_info" id="warning_table"> <input
								type="text" class="form-control" id="txt_table" placeholder="该部分不能为空">
						</div>
						<div class="form-group">
							<label class="control-label">是否覆盖</label> <label class="checkbox-inline"> <input type="radio"
								name="optionsRadiosinline" id="optionsRadios3" value="option1" checked> 是
							</label> <label class="checkbox-inline"> <input type="radio" name="optionsRadiosinline" id="optionsRadios4"
								value="option2"> 否
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
	<div class="modal fade" id="myModal_column" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
		<div class="modal-dialog" role="document">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal" aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
					<h4 class="modal-title" id="myModalLabel">设置Column属性</h4>
				</div>
				<div class="modal-body">
					<form>
						<div class="form-group">
							<label class="control-label">*列名称</label><input type="text" class="warning_info" id="warning_column_name"
								readonly> <input type="text" class="form-control" id="txt_column_name" placeholder="该部分不能为空">
						</div>
						<div class="form-group">
							<label class="control-label">列属性</label> <input type="text" class="warning_info" id="warning_column_attr"
								readonly> <input type="text" class="form-control" id="txt_column_attr" placeholder="text">
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
	<div class="modal fade" id="myModal_go" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
		<div class="modal-dialog" role="document">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal" aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
					<h4 class="modal-title" id="myModalLabel">设置Go属性</h4>
				</div>
				<div class="modal-body">
					<form>
						<div class="form-group">
							<label class="control-label">目标地址</label> <input type="text" class="warning_info" id="warning_go_url" readonly>
							<input type="text" class="form-control" id="txt_go_url" placeholder="此部分不能为空">
						</div>
						<div class="form-group">
							<label class="control-label">最大页数</label> <input type="text" class="warning_info" id="warning_go_maxpage"
								readonly> <input type="text" class="form-control" id="txt_go_maxpage" placeholder="1">
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
	<div class="modal fade" id="myModal_select" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
		<div class="modal-dialog" role="document">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal" aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
					<h4 class="modal-title" id="myModalLabel">设置Select属性</h4>
				</div>
				<div class="modal-body">
					<form>
						<div class="form-group">
							<label class="control-label">*目标路径</label> <input type="text" class="warning_info" id="warning_select_path"
								readonly> <input type="text" class="form-control" id="txt_select_path" placeholder="此部分不能为空">
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
	<div class="modal fade" id="myModal_match" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
		<div class="modal-dialog" role="document">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal" aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
					<h4 class="modal-title" id="myModalLabel">设置Match属性</h4>
				</div>
				<div class="modal-body">
					<form>
						<div class="form-group">
							<label class="control-label">*属性</label><input type="text" class="warning_info" id="warning_match_attr" readonly>
							<input type="text" class="form-control" id="txt_match_attr" placeholder="此部分不能为空">
						</div>
						<div class="form-group">
							<label class="control-label">路径</label> <input type="text" class="form-control" id="txt_match_path">
						</div>
						<div class="form-group">
							<label class="control-label">标签</label> <input type="text" class="form-control" id="txt_match_tab">
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
	<div class="modal fade" id="myModal_save" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
		<div class="modal-dialog" role="document">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal" aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
					<h4 class="modal-title" id="myModalLabel">设置Save属性</h4>
				</div>
				<div class="modal-body">
					<form>
						<div class="form-group">
							<label class="control-label">*表名称</label> <input type="text" class="warning_info" id="warning_save_table"> <input
								type="text" class="form-control" id="txt_save_table" placeholder="该部分不能为空">
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

	<!-- Modal for Print -->
	<div class="modal fade" id="myModal_print" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
		<div class="modal-dialog" role="document">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal" aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
					<h4 class="modal-title" id="myModalLabel">设置打印内容</h4>
				</div>
				<div class="modal-body">
					<form>
						<div class="form-group">
							<label class="control-label">*内容</label> <input type="text" class="warning_info" id="warning_print_content">
							<input type="text" class="form-control" id="txt_print_content" placeholder="该部分不能为空">
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