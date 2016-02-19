/**
 * 
 */

var arr = [];
var isTree = true;
var isEdit = false;
$(document).ready(function() {
	var tree = [ {
		text : "任务",
		data : {
			name : "task"
		}
	}
	];
	$('#tree').treeview({
		color : "#428bca",
		expandIcon : 'glyphicon glyphicon-chevron-right',
		collapseIcon : 'glyphicon glyphicon-chevron-down',
		nodeIcon : 'glyphicon glyphicon-bookmark',
		data : tree
	});
	$("#btn_del").click(function() {
		var nodeId = $("#tree").treeview("getSelected")[0].nodeId;
		$('#tree').treeview('delTreeNode', [ nodeId ]);
		var treeHeight = $("#tree").css('height');
		var t = parseInt(treeHeight);
		if(t > 208) {
			$("#div-border").css('height',t+'px');
		} else {
			$("#div-border").css('height','208px');
		}
	});
	$("#btn_up").click(function() {
		var node = $("#tree").treeview("getSelected")[0];
		$('#tree').treeview('upTreeNode', [ node ]);
	});
	$("#btn_down").click(function() {
		var node = $("#tree").treeview("getSelected")[0];
		$('#tree').treeview('downTreeNode', [ node ]);
	});
	$("#btn_table").click(function() {
		if (checkIsSelected()) {
			$("#myModal_table").modal('show');
			setTimeout("$('#txt_table').focus()",500);
		}
	});
	$("#btn_column").click(function() {
		if (checkIsSelected()) {
			$("#myModal_column").modal('show');
			setTimeout("$('#txt_column_name').focus()",500);
		}
	});
	$("#btn_go").click(function() {
		if (checkIsSelected()) {
			$("#myModal_go").modal('show');
			setTimeout("$('#txt_go_url').focus()",500);
		}
	});
	$("#btn_select").click(function() {
		if (checkIsSelected()) {
			$("#myModal_select").modal('show');
			setTimeout("$('#txt_select_path').focus()",500);
		}
	});
	$("#btn_match").click(function() {
		if (checkIsSelected()) {
			$("#myModal_match").modal('show');
			setTimeout("$('#txt_match_attr').focus()",500);
		}
	});
	$("#btn_print").click(function() {
		if (checkIsSelected()) {
			$("#myModal_print").modal('show');
			setTimeout("$('#txt_print_content').focus()",500);
		}
	});
	$("#btn_group_save").click(function() {
		if (checkIsSelected()) {
			$("#myModal_save").modal('show');
			setTimeout("$('#txt_save_table').focus()",500);
		}
	});
	$("#myModal_table").on('hide.bs.modal', function() {
		document.getElementById("txt_table").value = "";
		document.getElementById("warning_table").value = "";
		isEdit = false;
	});
	$("#myModal_table").on('show.bs.modal', function() {
		document.getElementById("txt_table").focus();
		if (arr.length != 0) {
			var m = $(this);
			m.find('#txt_table').val(arr[0]);
			arr = [];
			isEdit = true;
		}
	});
	$("#btn_table_save").click(function() {
		if (checkIsNull("txt_table", "warning_table")) {
			return;
		}
		var reg = /^[a-zA-Z]/;
		if (checkIsIllegal(reg, "txt_table", "warning_table")) {
			return;
		}
		var ipt = document.getElementById("txt_table");
		var tableName = ipt.value;
		var parentId = $("#tree").treeview("getSelected")[0].nodeId;
		var s;
		if (document.getElementById("optionsRadios3").checked) {
			s = true;
		} else {
			s = false;
		}
		var a = "添加表  " + tableName + " (是否覆盖： " + s + ")";
		var node = {
			text : a,
			data : {
				name : "table",
				params : {
					name : tableName,
					overlap : s
				}
			}
		};
		if (!isEdit) {
			addNodeText(node);
			$('#tree').treeview('addTreeNode', [ parentId, node ]);
			setDivHeight();
		} else {
			addNodeText(node);
			$('#tree').treeview('editTreeNode', [ parentId, node ]);
		}
		$('#myModal_table').modal('hide');
	});
	$('#myModal_column').on('hide.bs.modal', function() {
		document.getElementById("txt_column_name").value = "";
		document.getElementById("txt_column_attr").value = "";
		document.getElementById("warning_column_attr").value = "";
		document.getElementById("warning_column_name").value = "";
		isEdit = false;
	});
	$("#myModal_column").on('show.bs.modal', function() {
		if (arr.length != 0) {
			var m = $(this);
			console.log("当前执行column的启动界面 ");
			m.find('#txt_column_name').val(arr[0]);
			m.find('#txt_column_attr').val(arr[1]);
			arr = [];
			isEdit = true;
		}
	});
	$("#btn_column_save").click(function() {
		var ipt1 = document.getElementById("txt_column_name");
		var columnName = ipt1.value;
		var ipt2 = document.getElementById("txt_column_attr");
		var columnAttr = ipt2.value;
		if (checkIsNull("txt_column_name", "warning_column_name")) {
			return;
		}
		if (document.getElementById("txt_column_attr").value == "") {
			columnAttr = "text";
			document.getElementById("txt_column_attr").value = "text";
		}
		var reg1 = /^[a-zA-Z]/;
		if (checkIsIllegal(reg1, "txt_column_name", "warning_column_name")) {
			return;
		}
		var reg2 = /^string$|^text$|^int$|^float$|^double$|^bool$|^boolean$/;
		if (checkIsIllegal(reg2, "txt_column_attr", "warning_column_attr")) {
			return;
		}
		var parentId = $("#tree").treeview("getSelected")[0].nodeId;
		var a = "添加列  " + columnName + "( " + columnAttr + " )";
		var node = {
			text : a,
			data : {
				name : "column",
				params : {
					name : columnName,
					type : columnAttr
				}
			}
		};
		if (!isEdit) {
			addNodeText(node);
			$('#tree').treeview('addTreeNode', [ parentId, node ]);
			setDivHeight();
		} else {
			addNodeText(node);
			$('#tree').treeview('editTreeNode', [ parentId, node ]);
		}
		$('#myModal_column').modal('hide');
	});
	$('#myModal_go').on('hide.bs.modal', function() {
		document.getElementById("txt_go_url").value = "";
		document.getElementById("txt_go_maxpage").value = "";
		document.getElementById("warning_go_url").value = "";
		document.getElementById("warning_go_maxpage").value = "";
		isEdit = false;
	});
	$("#myModal_go").on('show.bs.modal', function() {
		if (arr.length != 0) {
			var m = $(this);
			console.log("当前执行go的启动界面 ");
			m.find('#txt_go_url').val(arr[0]);
			m.find('#txt_go_maxpage').val(arr[1]);
			arr = [];
			isEdit = true;
		}
	});
	$("#btn_go_save").click(function() {
		var ipt1 = document.getElementById("txt_go_url");
		var url = ipt1.value;
		var ipt2 = document.getElementById("txt_go_maxpage");
		var maxPage = ipt2.value;
		if (checkIsNull("txt_go_url", "warning_go_url")) {
			return;
		}
		document.getElementById("warning_go_url").value = "";
		var reg1 = /^[a-zA-Z]/;
	//	if (checkIsIllegal(reg1, "txt_go_url", "warning_go_url")) {
	//		return;
	//	}
		if (document.getElementById("txt_go_maxpage").value != "") {
			var reg2 = /^[1-9]\d*|0$/;
			if (checkIsIllegal(reg2, "txt_go_maxpage", "warning_go_maxpage")) {
				return;
			}
		}
		var a = "前往目标地址 ...";
		var parentId = $("#tree").treeview("getSelected")[0].nodeId;
		var params;
		var node = {
			text : a,
			data : {
				name : "load",
				params : {
					url : url,
					"max-page" : maxPage
				}
			}
		};
		if (!isEdit) {
			node.data.params = delNone(node.data.params);
			addNodeText(node);
			$('#tree').treeview('addTreeNode', [ parentId, node ]);
			setDivHeight();
		} else {
			addNodeText(node);
			$('#tree').treeview('editTreeNode', [ parentId, node ]);
		}
		$('#myModal_go').modal('hide');
	})
	$('#myModal_match').on('hide.bs.modal', function() {
		document.getElementById("txt_match_attr").value = "";
		document.getElementById("txt_match_path").value = "";
		document.getElementById("txt_match_tab").value = "";
		document.getElementById("warning_match_attr").value = "";
		isEdit = false;
	});
	$("#myModal_match").on('show.bs.modal', function() {
		if (arr.length != 0) {
			var m = $(this);
			m.find('#txt_match_attr').val(arr[0]);
			m.find('#txt_match_path').val(arr[1]);
			m.find('#txt_match_tab').val(arr[2]);
			arr = [];
			isEdit = true;
		}
	});
	$("#btn_match_save").click(function() {
		var ipt1 = document.getElementById("txt_match_attr");
		var attr = ipt1.value;
		var ipt2 = document.getElementById("txt_match_path");
		var path = ipt2.value;
		var ipt3 = document.getElementById("txt_match_tab");
		var tab = ipt3.value;
		if (checkIsNull("txt_match_attr", "warning_match_attr")) {
			return;
		}
		var reg = /^[a-zA-Z]/;
		if (checkIsIllegal(reg, "txt_match_attr", "warning_match_attr")) {
			return;
		}
		var a = "匹配属性 " + attr;
		var parentId = $("#tree").treeview("getSelected")[0].nodeId;
		var node = {
			text : a,
			data : {
				name : "match",
				params : {
					"var" : attr,
					path : path,
					attr : tab
				}
			}
		};
		if (!isEdit) {
			node.data.params = delNone(node.data.params);
			addNodeText(node);
			$('#tree').treeview('addTreeNode', [ parentId, node ]);
			setDivHeight();
		} else {
			addNodeText(node);
			$('#tree').treeview('editTreeNode', [ parentId, node ])
		}
		$('#myModal_match').modal('hide');
	});
	$('#myModal_select').on('hide.bs.modal', function() {
		document.getElementById("txt_select_path").value = "";
		document.getElementById("warning_select_path").value = "";
		isEdit = false;
	});
	$("#myModal_select").on('show.bs.modal', function() {
		if (arr.length != 0) {
			var m = $(this);
			m.find('#txt_select_path').val(arr[0]);
			arr = [];
			isEdit = true;
		}
	});
	$("#btn_select_save").click(function() {
		var ipt = document.getElementById("txt_select_path");
		var path = ipt.value;
		var a = "选择路径 ...";
		var parentId = $("#tree").treeview("getSelected")[0].nodeId;
		var node = {
			text : a,
			data : {
				name : "select",
				params : {
					path : path
				}
			}
		};
		if (!isEdit) {
			node.data.params = delNone(node.data.params);
			addNodeText(node);
			$('#tree').treeview('addTreeNode', [ parentId, node ]);
			setDivHeight();
		} else {
			addNodeText(node);
			$('#tree').treeview('editTreeNode', [ parentId, node ])
		}
		$('#myModal_select').modal('hide');
	});
	$('#myModal_print').on('hide.bs.modal', function() {
		document.getElementById("txt_print_content").value = "";
		document.getElementById("warning_print_content").value = "";
		isEdit = false;
	});
	$("#myModal_print").on('show.bs.modal', function() {
		if (arr.length != 0) {
			var m = $(this);
			m.find('#txt_print_content').val(arr[0]);
			arr = [];
			isEdit = true;
		}
	});
	$("#btn_print_save").click(function() {
		var ipt = document.getElementById("txt_print_content");
		var content = ipt.value;
		if (checkIsNull("txt_print_content", "warning_print_content")) {
			return;
		}
		var a = "打印  " + content;
		var parentId = $("#tree").treeview("getSelected")[0].nodeId;
		var node = {
			text : a,
			data : {
				name : "print",
				params : {
					content : content
				}
			}
		};
		if (!isEdit) {
			node.data.params = delNone(node.data.params);
			addNodeText(node);
			$('#tree').treeview('addTreeNode', [ parentId, node ]);
			setDivHeight();
		} else {
			addNodeText(node);
			$('#tree').treeview('editTreeNode', [ parentId, node ])
		}
		$('#myModal_print').modal('hide');
	});
	$('#myModal_save').on('hide.bs.modal', function() {
		document.getElementById("txt_save_table").value = "";
		document.getElementById("warning_save_table").value = "";
		isEdit = false;
	});
	$("#myModal_save").on('show.bs.modal', function() {
		if (arr.length != 0) {
			var m = $(this);
			m.find('#txt_save_table').val(arr[0]);
			arr = [];
			isEdit = true;
		}
	});
	$("#btn_save_save").click(function() {
		if (checkIsNull("txt_save_table", "warning_save_table")) {
			return;
		}
		var reg = /^[a-zA-Z]/;
		if (checkIsIllegal(reg, "txt_save_table", "warning_save_table")) {
			return;
		}
		var ipt = document.getElementById("txt_save_table");
		var tableName = ipt.value;
		var parentId = $("#tree").treeview("getSelected")[0].nodeId;
		var a = "保存表 "+tableName;
		var node = {
			text : a,
			data : {
				name : "save",
				params : {
					table : tableName
				}
			}
		};
		if (!isEdit) {
			addNodeText(node);
			$('#tree').treeview('addTreeNode', [ parentId, node ]);
			setDivHeight();
		} else {
			addNodeText(node);
			$('#tree').treeview('editTreeNode', [ parentId, node ]);
		}
		$('#myModal_save').modal('hide');
	});
	$("#myModal_table").keydown(function(e) {
		if(e.which == 13) {
			$("#btn_table_save").click();
		}
	});
	$("#myModal_column").keydown(function(e) {
		if(e.which == 13) {
			$("#btn_column_save").click();
		}
	});
	$("#myModal_go").keydown(function(e) {
		if(e.which == 13) {
			$("#btn_go_save").click();
		}
	});
	$("#myModal_select").keydown(function(e) {
		if(e.which == 13) {
			$("#btn_select_save").click();
		}
	});
	$("#myModal_match").keydown(function(e) {
		if(e.which == 13) {
			$("#btn_match_save").click();
		}
	});
	$("#myModal_save").keydown(function(e) {
		if(e.which == 13) {
			$("#btn_save_save").click();
		}
	});
	$("#myModal_print").keydown(function(e) {
		if(e.which == 13) {
			$("#btn_print_save").click();
		}
	});
	$("#btn_edit").click(function() {
		if ($("#tree").treeview("getSelected").length != 0) {
			var node = $("#tree").treeview("getSelected")[0];
			console.log("now is editing ");
			var cmd = node.data.name;
			if(cmd == "load") {
				arr = getEditModal(node);
				console.log(arr);
				$("#myModal_go").modal('show');
			}
			console.log(cmd);
			
			if (cmd != "task") {
				arr = getEditModal(node);
				if (arr.length != 0) {
					$("#myModal_" + cmd).modal('show');
				}
			}
		} else {
			// $("#btn_submit").disabled = "disabled";
			console.log("there is no selected node!");
		}
	});
	$("#text_submit").click(function() {
		var str = $("#xml_text").val();
		var tree = xmlToTree(str);
		$('#tree').treeview('createTree', [ tree ]);
	});
	function checkIsSelected() {
		if ($("#tree").treeview("getSelected").length == 0) {
			console.log("there is no selected node!");
			return false;
		}
		return true;
	};
	$("#xml_tab").click(function(e) {
		var root = $("#tree").treeview("getRoot");
		var level = 0;
		var str = treeToXML(root, level, str);
		$("#xml_text").val(str);
		$("#btn_edit").attr('disabled', true);
		$("#btn_del").attr('disabled', true);
		$("#btn_up").attr('disabled', true);
		$("#btn_down").attr('disabled', true);
		isTree = false;
	});
	$("#home_tab").click(function(e) {
		var str = $("#xml_text").val();
		var tree = xmlToTree(str);
		$('#tree').treeview('createTree', [ tree ]);
		$('#tree').treeview('expandAll');
		$("#btn_edit").attr('disabled', false);
		$("#btn_del").attr('disabled', false);
		$("#btn_up").attr('disabled', false);
		$("#btn_down").attr('disabled', false);
		isTree = true;
	});
	$("#btn_var").click(function() {
		if (checkIsSelected()) {
			$("#myModal_var").modal('show');
			setTimeout("$('#txt_var_name').focus()",500);
		}
	});
	$("#myModal_var").keydown(function(e) {
		if(e.which == 13) {
			$("#btn_var_save").click();
		}
	});
	$('#myModal_var').on('hide.bs.modal', function() {
		document.getElementById("txt_var_name").value = "";
		document.getElementById("txt_var_attr").value = "";
		document.getElementById("warning_var_name").value = "";
		isEdit = false;
	});
	$("#myModal_var").on('show.bs.modal', function() {
		if (arr.length != 0) {
			var m = $(this);
			console.log("当前执行var的启动界面 ");
			m.find('#txt_var_name').val(arr[0]);
			m.find('#txt_var_attr').val(arr[1]);
			arr = [];
			isEdit = true;
		}
	});
	$("#btn_var_save").click(function() {
		if (checkIsNull("txt_var_name", "warning_var_name")) {
			return;
		}
		var ipt = document.getElementById("txt_var_name");
		var name = ipt.value;
		var ipt2 = document.getElementById("txt_var_attr");
		var attr = ipt2.value;
		var parentId = $("#tree").treeview("getSelected")[0].nodeId;
		var a = "临时存储 ";
		var node = {
			text : a,
			data : {
				name : "var",
				params : {
					name : name,
					value : attr
				}
			}
		};
		if (!isEdit) {
			delNone(node.data.params);
			addNodeText(node);
			$('#tree').treeview('addTreeNode', [ parentId, node ]);
			setDivHeight();
		} else {
			delNone(node.data.params);
			addNodeText(node);
			$('#tree').treeview('editTreeNode', [ parentId, node ]);
		}
		$('#myModal_var').modal('hide');
	});
	function addlogs(str,type) {
		var oDiv=document.getElementById("log_div");
		var o=document.createElement('div');
		if(type == "1") {
			o.innerHTML='<span style="color:red">'+str+'</span>'; 
		} else {
			o.innerHTML='<span>'+str+'</span>'; 
		}
		oDiv.appendChild(o);
		oDiv.scrollTop=oDiv.scrollHeight;
	
	};
	function autoAddLogs() {
		var i = 0;
        $('body').everyTime('1s', function() {
        	addlogs("hello world"+(i++),1);
    		addlogs("hello world"+(i++),0);
        });
    };
    function setDivHeight() {
    	var treeHeight = $("#tree").css('height');
    	var divHeight = $("#div-border").css('height');
      	var t = parseInt(treeHeight);
      	var d = parseInt(divHeight);
    	if(t > d) {
    		$("#div-border").css('height',treeHeight);
    	}
    }
    function delNone(params) {
    	var p = {};
    	for (var k in params) {
    		if(params[k] != "") {
    			p[k] = params[k];
    		}
    	}
    	return p;
     }
});
