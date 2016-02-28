var isEdit = false;
var isTree = true;
var master;
$(document).ready(function() {
	var form;
	$("#add-task").addClass("ui-state-active");
	$("#task-list").removeClass("ui-state-active");
	var treeview = $(".ui-treeview").treeview({
		onNodeSelected : function(e, node) {
		}
	});
	master = treeview.treeview("addNode", "task(name:Untitled task)", {
		data : {
			name : "task",
			params : {name : "Untitled task"}
		}
	});
	master.treeviewnode("expand");
	master.treeviewnode("setSelect");
	$("#dialog-task").dialog({
		autoOpen : false,
		height : 200,
		width : 350,
		modal : true,
		show : {
			effect : "blind",
			duration : 200
		},
		buttons : {
			"OK" : function() {
				if(checkIsNull("task-name")) {
					alert("name cannot be null");
					return;
				}
				form = getForm("dialog-task", "task");
				var content = getContext(form);
				var node = master.treeviewnode("getSelectedNode");
				if (isEdit) {
					treeview.treeview("editTreeNode", node, form);
				} else {
					treeview.treeview("addNode", content, form, node);
				}
				$("#dialog-task").dialog("close");
			},
			"Cancel" : function() {
				$("#dialog-task").dialog("close");
			}
		},
		close : function() {
			isEdit = false;
			emptyForm();
		}
	}).keydown(function(e) {
		if (e.which == 13) {
			e.preventDefault();
			$(this).parent().find("button:eq(1)").click();
		}
	});
	$("#dialog-table").dialog({
		autoOpen : false,
		height : 250,
		width : 350,
		modal : true,
		show : {
			effect : "blind",
			duration : 200
		},
		buttons : {
			"OK" : function() {
				if(checkIsNull("table-name")) {
					alert("name cannot be null");
					return;
				}
				var reg = /^[a-zA-Z]/;
				if(checkIsIllegal(reg,"table-name")) {
					alert("table name is illegal");
					return;
				}
				form = getForm("dialog-table", "table");
				var content = getContext(form);
				var node = master.treeviewnode("getSelectedNode");
				if (isEdit) {
					treeview.treeview("editTreeNode", node, form);
				} else {
					treeview.treeview("addNode", content, form, node);
				}
				$("#dialog-table").dialog("close");
			},
			"Cancel" : function() {
				$("#dialog-table").dialog("close");
			}
		},
		close : function() {
			isEdit = false;
			emptyForm();
		}
	}).keydown(function(e) {
		if (e.which == 13) {
			e.preventDefault();
			$(this).parent().find("button:eq(1)").click();
		}
	});
	$("#dialog-column").dialog({
		autoOpen : false,
		height : 250,
		width : 350,
		modal : true,
		show : {
			effect : "blind",
			duration : 200
		},
		buttons : {
			"OK" : function() {
				if(checkIsNull("column-name")) {
					alert("name cannot be null");
					return;
				}
				var reg1 = /^[a-zA-Z]/;
				if(checkIsIllegal(reg1,"column-name")) {
					alert("column name is illegal");
					return;
				}
				if($("#column-type").val() != "") {
					var reg2 = /^string$|^text$|^int$|^float$|^double$|^bool$|^boolean$/;
					if(checkIsIllegal(reg2,"column-type")) {
						alert("column type is illegal");
						return;
					}
				}
				if ($("#column-type").val() == "") {
					$("#column-type").val("text");
				}
				form = getForm("dialog-column", "column");
				var content = getContext(form);
				var node = master.treeviewnode("getSelectedNode");
				if (isEdit) {
					treeview.treeview("editTreeNode", node, form);
				} else {
					treeview.treeview("addNode", content, form, node);
				}
				$("#dialog-column").dialog("close");
			},
			"Cancel" : function() {
				$("#dialog-column").dialog("close");
			}
		},
		close : function() {
			isEdit = false;
			emptyForm();
		}
	}).keydown(function(e) {
		if (e.which == 13) {
			e.preventDefault();
			$(this).parent().find("button:eq(1)").click();
		}
	});
	$("#dialog-load").dialog({
		autoOpen : false,
		height : 290,
		width : 350,
		modal : true,
		show : {
			effect : "blind",
			duration : 200
		},
		buttons : {
			"OK" : function() {
				if(checkIsNull("load-url")) {
					alert("url cannot be null");
					return;
				}
				if($("#load-maxPage").val() != "") {
					var reg = /^[1-9]\d*|0$/;
					if(checkIsIllegal(reg,"load-maxPage")) {
						alert("maxPage is illegal");
						return;
					}
				}
				form = getForm("dialog-load", "load");
				var content = getContext(form);
				var node = master.treeviewnode("getSelectedNode");
				if (isEdit) {
					treeview.treeview("editTreeNode", node, form);
				} else {
					treeview.treeview("addNode", content, form, node);
				}
				$("#dialog-load").dialog("close");
			},
			"Cancel" : function() {
				$("#dialog-load").dialog("close");
			}
		},
		close : function() {
			isEdit = false;
			emptyForm();
		}
	}).keydown(function(e) {
		if (e.which == 13) {
			e.preventDefault();
			$(this).parent().find("button:eq(1)").click();
		}
	});
	$("#dialog-select").dialog({
		autoOpen : false,
		height : 240,
		width : 350,
		modal : true,
		show : {
			effect : "blind",
			duration : 200
		},
		buttons : {
			"OK" : function() {
				if(checkIsNull("select-path")) {
					alert("path cannot be null");
					return;
				}
				form = getForm("dialog-select", "select");
				var content = getContext(form);
				var node = master.treeviewnode("getSelectedNode");
				if (isEdit) {
					treeview.treeview("editTreeNode", node, form);
				} else {
					treeview.treeview("addNode", content, form, node);
				}
				$("#dialog-select").dialog("close");
			},
			"Cancel" : function() {
				$("#dialog-select").dialog("close");
			}
		},
		close : function() {
			isEdit = false;
			emptyForm();
		}
	}).keydown(function(e) {
		if (e.which == 13) {
			e.preventDefault();
			$(this).parent().find("button:eq(1)").click();
		}
	});
	$("#dialog-match").dialog({
		autoOpen : false,
		height : 400,
		width : 350,
		modal : true,
		show : {
			effect : "blind",
			duration : 200
		},
		buttons : {
			"OK" : function() {
				if(checkIsNull("match-var")) {
					alert("var cannot be null");
					return;
				}
				form = getForm("dialog-match", "match");
				var content = getContext(form);
				var node = master.treeviewnode("getSelectedNode");
				if (isEdit) {
					treeview.treeview("editTreeNode", node, form);
				} else {
					treeview.treeview("addNode", content, form, node);
				}
				$("#dialog-match").dialog("close");
			},
			"Cancel" : function() {
				$("#dialog-match").dialog("close");
			}
		},
		close : function() {
			isEdit = false;
			emptyForm();
		}
	}).keydown(function(e) {
		if (e.which == 13) {
			e.preventDefault();
			$(this).parent().find("button:eq(1)").click();
		}
	});
	$("#dialog-save").dialog({
		autoOpen : false,
		height : 240,
		width : 350,
		modal : true,
		show : {
			effect : "blind",
			duration : 200
		},
		buttons : {
			"OK" : function() {
				if(checkIsNull("save-name")) {
					alert("name cannot be null");
					return;
				}
				var reg = /^[a-zA-Z]/;
				if(checkIsIllegal(reg,"save-name")) {
					alert("name is illegal");
					return;
				}
				form = getForm("dialog-save", "save");
				var content = getContext(form);
				var node = master.treeviewnode("getSelectedNode");
				if (isEdit) {
					treeview.treeview("editTreeNode", node, form);
				} else {
					treeview.treeview("addNode", content, form, node);
				}
				$("#dialog-save").dialog("close");
			},
			"Cancel" : function() {
				$("#dialog-save").dialog("close");
			}
		},
		close : function() {
			isEdit = false;
			emptyForm();
		}
	}).keydown(function(e) {
		if (e.which == 13) {
			e.preventDefault();
			$(this).parent().find("button:eq(1)").click();
		}
	});
	$("#dialog-var").dialog({
		autoOpen : false,
		height : 300,
		width : 350,
		modal : true,
		show : {
			effect : "blind",
			duration : 200
		},
		buttons : {
			"OK" : function() {
				if(checkIsNull("var-name")) {
					alert("name cannot be null");
					return;
				}
				var reg = /^[a-zA-Z]/;
				if(checkIsIllegal(reg,"var-name")) {
					alert("name is illegal");
					return;
				}
				form = getForm("dialog-var", "var");
				var content = getContext(form);
				var node = master.treeviewnode("getSelectedNode");
				if (isEdit) {
					treeview.treeview("editTreeNode", node, form);
				} else {
					treeview.treeview("addNode", content, form, node);
				}
				$("#dialog-var").dialog("close");
			},
			"Cancel" : function() {
				$("#dialog-var").dialog("close");
			}
		},
		close : function() {
			isEdit = false;
			emptyForm();
		}
	}).keydown(function(e) {
		if (e.which == 13) {
			e.preventDefault();
			$(this).parent().find("button:eq(1)").click();
		}
	});
	$("#dialog-print").dialog({
		autoOpen : false,
		height : 230,
		width : 350,
		modal : true,
		show : {
			effect : "blind",
			duration : 200
		},
		buttons : {
			"OK" : function() {
				if(checkIsNull("print-content")) {
					alert("name cannot be null");
					return;
				}
				form = getForm("dialog-print", "print");
				var content = getContext(form);
				var node = master.treeviewnode("getSelectedNode");
				if (isEdit) {
					treeview.treeview("editTreeNode", node, form);
				} else {
					treeview.treeview("addNode", content, form, node);
				}
				$("#dialog-print").dialog("close");
			},
			"Cancel" : function() {
				$("#dialog-print").dialog("close");
			}
		},
		close : function() {
			isEdit = false;
			emptyForm();
		}
	}).keydown(function(e) {
		if (e.which == 13) {
			e.preventDefault();
			$(this).parent().find("button:eq(1)").click();
		}
	});
	$("#xml_text").keydown(function(e) {
		if (e.ctrlKey == true && e.keyCode == 83) {
			e.preventDefault();
			var str = $("#xml_text").val();
			var tree = xmlToTree(str);
			console.log(tree);
		}
	});
	$("#btn-edit").button().click(function() {
		var node = master.treeviewnode("getSelectedNode");
		if (node) {
			var node1 = node.treeviewnode("getData");
			isEdit = true;
			var cmd = node.treeviewnode("getData").data.name;
			$("#dialog-" + cmd).dialog("open");
			bindForm("dialog-" + cmd, node1);
		}
	});
	$("#btn-delete").button().click(function() {
		var node = master.treeviewnode("getSelectedNode");
		if (node) {
			var name = node.treeviewnode("getData").data.name;
			if(name != "task")
			treeview.treeview("removeNode", node);
		}
		master.treeviewnode("setSelect");
	});
	$("#btn-up").button().click(function() {
		var node = master.treeviewnode("getSelectedNode");
		if (node) {
			treeview.treeview("upTreeNode", node);
		}
	});
	$("#btn-down").button().click(function() {
		var node = master.treeviewnode("getSelectedNode");
		if (node) {
			var name = node.treeviewnode("getData").data.name;
			if(name != "task")
			treeview.treeview("downTreeNode", node);
		}
	});
	$("#btn-test").button();
	$("#btn-submit").button();
	$("#btn-quit").button();
	$("#btn-table").button().click(function() {
		var node = master.treeviewnode("getSelectedNode");
		if (node) {
			$("#dialog-table").dialog("open");
		}
	});
	$("#btn-column").button().click(function() {
		var node = master.treeviewnode("getSelectedNode");
		if (node) {
			$("#dialog-column").dialog("open");
		}
	});
	$("#btn-select").button().click(function() {
		var node = master.treeviewnode("getSelectedNode");
		if (node) {
			$("#dialog-select").dialog("open");
		}
	});
	$("#btn-match").button().click(function() {
		var node = master.treeviewnode("getSelectedNode");
		if (node) {
			$("#dialog-match").dialog("open");
		}
	});
	$("#btn-load").button().click(function() {
		var node = master.treeviewnode("getSelectedNode");
		if (node) {
			$("#dialog-load").dialog("open");
		}
	});
	$("#btn-save").button().click(function() {
		var node = master.treeviewnode("getSelectedNode");
		if (node) {
			$("#dialog-save").dialog("open");
		}
	});
	$("#btn-print").button().click(function() {
		var node = master.treeviewnode("getSelectedNode");
		if (node) {
			$("#dialog-print").dialog("open");
		}
	});
	$("#btn-var").button().click(function() {
		var node = master.treeviewnode("getSelectedNode");
		if (node) {
			$("#dialog-var").dialog("open");
		}
	});
	$("#tabs").tabs();
	$("#tab-tree").click(function() {
		if (isTree) {
			return;
		} else {
			isTree = true;
			$(".cmd-group button:lt(4)").attr('disabled', false);
			$(".button-group button").attr('disabled', false);
			treeview.treeview("empty");
			var str = $("#xml_text").val().trim();
			var str1 = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>";
			if(str == "" || str == str1) {
				str = "<task name=\"Untitled task\" />";
			}
			$("#xml_text").val(str);
			var t = xmlToTree(str);
			master = treeview.treeview("createTree",t);
			master.treeviewnode("setSelect");
		}
	});
	$("#tab-xml").click(function() {
		if (!isTree) {
			return;
		} else {
			isTree = false;
			$(".cmd-group button:lt(4)").attr('disabled', true);
			$(".button-group button").attr('disabled', true);
			var level = 0;
			var str = treeToXML(master,level,str);
			$("#xml_text").val(str);
		}
	});
	centerBG();
});
//
// get a form
function getForm(dialogName, name) {
	var node = {};
	node.data = {};
	var params = {};
	$("#" + dialogName + " input").each(function() {
		var m = $(this);
		if (m.attr("type") == "text") {
			var key = m.attr("name");
			var value = m.val();
			if (value != "") {
				params[key] = value;
			}
		}
		if (m.attr("type") == "radio") {
			var key = m.attr("name");
			var value = $('#' + dialogName + ' input:radio:checked').val();
			params[key] = value;
		}
	});
	node.data.name = name;
	node.data.params = params;
	return node;
}
//
// empty a form
function emptyForm() {
	$("input").each(function() {
		if ($(this).attr('type') == "text") {
			$(this).val("");
		}
		if ($(this).attr('type') == "radio") {
			$("input:radio:eq(0)").click();
		}
	});
}
//
// bind data for Form when edit
function bindForm(dialogName, node) {
	var params = node.data.params;
	for ( var key in params) {
		if (key == "overlap") {
			$(
					"#" + dialogName + " input[name=" + key + "][value="
							+ params[key] + "]").click();
		} else {
			$("#" + dialogName + " input[name=" + key + "]").val(params[key]);
		}
	}
}
//
// make the key of "tab" useful for textArea
function tab(obj) {
	if (event.keyCode == 9) {
		obj.value = obj.value + "\t";
		event.returnValue = false;
	}
}
//
// make button-group be center of main-right
function centerBG() {
	var h1 = parseInt($("#main-right").css("height"));
	var h2 = parseInt($("#button-group").css("height"));
	var h3 = (h1 - h2) / 2.0 + "px";
	$("#button-group").css("margin-top", h3);
}
