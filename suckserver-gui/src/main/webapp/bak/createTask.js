var isEdit = false;
var isTree = true;
var master;
$(document)
		.ready(
				function() {
					var form;
					$("#add-task").addClass("ui-state-active");
					$("#task-list").removeClass("ui-state-active");
					var treeview = $(".ui-treeview").treeview({
						onNodeSelected : function(e, node) {
						}
					});
					master = treeview.treeview("addNode",
							"task(name:Untitled task)", {
								data : {
									name : "task",
									params : {
										name : "Untitled task"
									}
								}
							});
					master.treeviewnode("expand");
					master.treeviewnode("setSelect");
//					$("#dialog-task")
//							.dialog(
//									{
//										autoOpen : false,
//										height : 400,
//										width : 350,
//										modal : true,
//										show : {
//											effect : "blind",
//											duration : 200
//										},
//										buttons : {
//											"OK" : function() {
//												if (checkIsNull("task-name")) {
//													alert("name cannot be null");
//													return;
//												}
//												form = getForm("dialog-task",
//														"task");
//												var content = getContext(form);
//												var node = master
//														.treeviewnode("getSelectedNode");
//												if (isEdit) {
//													treeview.treeview(
//															"editTreeNode",
//															node, form);
//												} else {
//													treeview.treeview(
//															"addNode", content,
//															form, node);
//												}
//												$("#dialog-task").dialog(
//														"close");
//											},
//											"Cancel" : function() {
//												$("#dialog-task").dialog(
//														"close");
//											}
//										},
//										close : function() {
//											isEdit = false;
//											emptyForm("dialog-task");
//										}
//									}).keydown(
//									function(e) {
//										if (e.which == 13) {
//											e.preventDefault();
//											$(this).parent().find(
//													"button:eq(1)").click();
//										}
//									});
//					$("#dialog-subtask")
//							.dialog(
//									{
//										autoOpen : false,
//										height : 200,
//										width : 350,
//										modal : true,
//										show : {
//											effect : "blind",
//											duration : 200
//										},
//										buttons : {
//											"OK" : function() {
//												if (checkIsNull("subtask-name")) {
//													alert("name cannot be null");
//													return;
//												}
//												form = getForm(
//														"dialog-subtask",
//														"subtask");
//												var content = getContext(form);
//												var node = master
//														.treeviewnode("getSelectedNode");
//												if (isEdit) {
//													treeview.treeview(
//															"editTreeNode",
//															node, form);
//												} else {
//													treeview.treeview(
//															"addNode", content,
//															form, node);
//												}
//												$("#dialog-subtask").dialog(
//														"close");
//											},
//											"Cancel" : function() {
//												$("#dialog-subtask").dialog(
//														"close");
//											}
//										},
//										close : function() {
//											isEdit = false;
//											emptyForm("dialog-subtask");
//										}
//									}).keydown(
//									function(e) {
//										if (e.which == 13) {
//											e.preventDefault();
//											$(this).parent().find(
//													"button:eq(1)").click();
//										}
//									});
//					$("#dialog-table")
//							.dialog(
//									{
//										autoOpen : false,
//										height : 250,
//										width : 350,
//										modal : true,
//										show : {
//											effect : "blind",
//											duration : 200
//										},
//										buttons : {
//											"OK" : function() {
//												if (checkIsNull("table-name")) {
//													alert("name cannot be null");
//													return;
//												}
//												var reg = /^[a-zA-Z]/;
//												if (checkIsIllegal(reg,
//														"table-name")) {
//													alert("table name is illegal");
//													return;
//												}
//												form = getForm("dialog-table",
//														"table");
//												var content = getContext(form);
//												var node = master
//														.treeviewnode("getSelectedNode");
//												if (isEdit) {
//													treeview.treeview(
//															"editTreeNode",
//															node, form);
//												} else {
//													treeview.treeview(
//															"addNode", content,
//															form, node);
//												}
//												$("#dialog-table").dialog(
//														"close");
//											},
//											"Cancel" : function() {
//												$("#dialog-table").dialog(
//														"close");
//											}
//										},
//										close : function() {
//											isEdit = false;
//											emptyForm("dialog-table");
//										}
//									}).keydown(
//									function(e) {
//										if (e.which == 13) {
//											e.preventDefault();
//											$(this).parent().find(
//													"button:eq(1)").click();
//										}
//									});
//					$("#dialog-column")
//							.dialog(
//									{
//										autoOpen : false,
//										height : 300,
//										width : 350,
//										modal : true,
//										show : {
//											effect : "blind",
//											duration : 200
//										},
//										buttons : {
//											"OK" : function() {
//												if (checkIsNull("column-name")) {
//													alert("name cannot be null");
//													return;
//												}
//												var reg1 = /^[a-zA-Z]/;
//												if (checkIsIllegal(reg1,
//														"column-name")) {
//													alert("column name is illegal");
//													return;
//												}
//												if ($("#column-type").val() != "") {
//													var reg2 = /^string$|^text$|^int$|^float$|^double$|^bool$|^boolean$/;
//													if (checkIsIllegal(reg2,
//															"column-type")) {
//														alert("column type is illegal");
//														return;
//													}
//												}
//												if ($("#column-type").val() == "") {
//													$("#column-type").val(
//															"text");
//												}
//												form = getForm("dialog-column",
//														"column");
//												var content = getContext(form);
//												var node = master
//														.treeviewnode("getSelectedNode");
//												if (isEdit) {
//													treeview.treeview(
//															"editTreeNode",
//															node, form);
//												} else {
//													treeview.treeview(
//															"addNode", content,
//															form, node);
//												}
//												$("#dialog-column").dialog(
//														"close");
//											},
//											"Cancel" : function() {
//												$("#dialog-column").dialog(
//														"close");
//											}
//										},
//										close : function() {
//											isEdit = false;
//											emptyForm("dialog-column");
//										}
//									}).keydown(
//									function(e) {
//										if (e.which == 13) {
//											e.preventDefault();
//											$(this).parent().find(
//													"button:eq(1)").click();
//										}
//									});
//					$("#dialog-load")
//							.dialog(
//									{
//										autoOpen : false,
//										height : 400,
//										width : 350,
//										modal : true,
//										show : {
//											effect : "blind",
//											duration : 200
//										},
//										buttons : {
//											"OK" : function() {
//												if ($("#load-maxPage").val() != "") {
//													var reg = /^[1-9]\d*|0$/;
//													if (checkIsIllegal(reg,
//															"load-maxPage")) {
//														alert("MaxPage is illegal");
//														return;
//													}
//													if (checkIsNull("load-nextPath")) {
//														alert("NextPath cannot be null");
//														return;
//													}
//												}
//												form = getForm("dialog-load",
//														"load");
//												var content = getContext(form);
//												var node = master
//														.treeviewnode("getSelectedNode");
//												if (isEdit) {
//													treeview.treeview(
//															"editTreeNode",
//															node, form);
//												} else {
//													treeview.treeview(
//															"addNode", content,
//															form, node);
//												}
//												$("#dialog-load").dialog(
//														"close");
//											},
//											"Cancel" : function() {
//												$("#dialog-load").dialog(
//														"close");
//											}
//										},
//										close : function() {
//											isEdit = false;
//											emptyForm("dialog-load");
//										}
//									}).keydown(
//									function(e) {
//										if (e.which == 13) {
//											e.preventDefault();
//											$(this).parent().find(
//													"button:eq(1)").click();
//										}
//									});
//					$("#dialog-select")
//							.dialog(
//									{
//										autoOpen : false,
//										height : 250,
//										width : 350,
//										modal : true,
//										show : {
//											effect : "blind",
//											duration : 200
//										},
//										buttons : {
//											"OK" : function() {
//												if (checkIsNull("select-path")) {
//													alert("Path cannot be null");
//													return;
//												}
//												form = getForm("dialog-select",
//														"select");
//												var content = getContext(form);
//												var node = master
//														.treeviewnode("getSelectedNode");
//												if (isEdit) {
//													treeview.treeview(
//															"editTreeNode",
//															node, form);
//												} else {
//													treeview.treeview(
//															"addNode", content,
//															form, node);
//												}
//												$("#dialog-select").dialog(
//														"close");
//											},
//											"Cancel" : function() {
//												$("#dialog-select").dialog(
//														"close");
//											}
//										},
//										close : function() {
//											isEdit = false;
//											emptyForm("dialog-select");
//										}
//									}).keydown(
//									function(e) {
//										if (e.which == 13) {
//											e.preventDefault();
//											$(this).parent().find(
//													"button:eq(1)").click();
//										}
//									});
//					$("#dialog-match")
//							.dialog(
//									{
//										autoOpen : false,
//										height : 400,
//										width : 350,
//										modal : true,
//										show : {
//											effect : "blind",
//											duration : 200
//										},
//										buttons : {
//											"OK" : function() {
//												if (checkIsNull("match-var")) {
//													alert("var cannot be null");
//													return;
//												}
//												form = getForm("dialog-match",
//														"match");
//												var content = getContext(form);
//												var node = master
//														.treeviewnode("getSelectedNode");
//												if (isEdit) {
//													treeview.treeview(
//															"editTreeNode",
//															node, form);
//												} else {
//													treeview.treeview(
//															"addNode", content,
//															form, node);
//												}
//												$("#dialog-match").dialog(
//														"close");
//											},
//											"Cancel" : function() {
//												$("#dialog-match").dialog(
//														"close");
//											}
//										},
//										close : function() {
//											isEdit = false;
//											emptyForm("dialog-match");
//										}
//									}).keydown(
//									function(e) {
//										if (e.which == 13) {
//											e.preventDefault();
//											$(this).parent().find(
//													"button:eq(1)").click();
//										}
//									});
//					$("#dialog-save")
//							.dialog(
//									{
//										autoOpen : false,
//										height : 300,
//										width : 350,
//										modal : true,
//										show : {
//											effect : "blind",
//											duration : 200
//										},
//										buttons : {
//											"OK" : function() {
//												if (checkIsNull("save-name")) {
//													alert("name cannot be null");
//													return;
//												}
//												var reg = /^[a-zA-Z]/;
//												if (checkIsIllegal(reg,
//														"save-name")) {
//													alert("name is illegal");
//													return;
//												}
//												form = getForm("dialog-save",
//														"save");
//												var content = getContext(form);
//												var node = master
//														.treeviewnode("getSelectedNode");
//												if (isEdit) {
//													treeview.treeview(
//															"editTreeNode",
//															node, form);
//												} else {
//													treeview.treeview(
//															"addNode", content,
//															form, node);
//												}
//												$("#dialog-save").dialog(
//														"close");
//											},
//											"Cancel" : function() {
//												$("#dialog-save").dialog(
//														"close");
//											}
//										},
//										close : function() {
//											isEdit = false;
//											emptyForm("dialog-save");
//										}
//									}).keydown(
//									function(e) {
//										if (e.which == 13) {
//											e.preventDefault();
//											$(this).parent().find(
//													"button:eq(1)").click();
//										}
//									});
//					$("#dialog-var")
//							.dialog(
//									{
//										autoOpen : false,
//										height : 300,
//										width : 350,
//										modal : true,
//										show : {
//											effect : "blind",
//											duration : 200
//										},
//										buttons : {
//											"OK" : function() {
//												if (checkIsNull("var-name")) {
//													alert("name cannot be null");
//													return;
//												}
//												var reg = /^[a-zA-Z]/;
//												if (checkIsIllegal(reg,
//														"var-name")) {
//													alert("name is illegal");
//													return;
//												}
//												form = getForm("dialog-var",
//														"var");
//												var content = getContext(form);
//												var node = master
//														.treeviewnode("getSelectedNode");
//												if (isEdit) {
//													treeview.treeview(
//															"editTreeNode",
//															node, form);
//												} else {
//													treeview.treeview(
//															"addNode", content,
//															form, node);
//												}
//												$("#dialog-var")
//														.dialog("close");
//											},
//											"Cancel" : function() {
//												$("#dialog-var")
//														.dialog("close");
//											}
//										},
//										close : function() {
//											isEdit = false;
//											emptyForm("dialog-var");
//										}
//									}).keydown(
//									function(e) {
//										if (e.which == 13) {
//											e.preventDefault();
//											$(this).parent().find(
//													"button:eq(1)").click();
//										}
//									});
//					$("#dialog-print")
//							.dialog(
//									{
//										autoOpen : false,
//										height : 300,
//										width : 350,
//										modal : true,
//										show : {
//											effect : "blind",
//											duration : 200
//										},
//										buttons : {
//											"OK" : function() {
//												if (checkIsNull("print-content")) {
//													alert("name cannot be null");
//													return;
//												}
//												if ($("#print-html").val() != "") {
//													var reg = /^true$|^false$/;
//													if (checkIsIllegal(reg,
//															"print-html")) {
//														alert("Html is illegal");
//														return;
//													}
//												}
//												form = getForm("dialog-print",
//														"print");
//												var content = getContext(form);
//												var node = master
//														.treeviewnode("getSelectedNode");
//												if (isEdit) {
//													treeview.treeview(
//															"editTreeNode",
//															node, form);
//												} else {
//													treeview.treeview(
//															"addNode", content,
//															form, node);
//												}
//												$("#dialog-print").dialog(
//														"close");
//											},
//											"Cancel" : function() {
//												$("#dialog-print").dialog(
//														"close");
//											}
//										},
//										close : function() {
//											isEdit = false;
//											emptyForm("dialog-print");
//										}
//									}).keydown(
//									function(e) {
//										if (e.which == 13) {
//											e.preventDefault();
//											$(this).parent().find(
//													"button:eq(1)").click();
//										}
//									});
//					$("#dialog-scan")
//							.dialog(
//									{
//										autoOpen : false,
//										height : 440,
//										width : 350,
//										modal : true,
//										show : {
//											effect : "blind",
//											duration : 200
//										},
//										buttons : {
//											"OK" : function() {
//												form = getForm("dialog-scan",
//														"scan");
//												var content = getContext(form);
//												var node = master
//														.treeviewnode("getSelectedNode");
//												if (isEdit) {
//													treeview.treeview(
//															"editTreeNode",
//															node, form);
//												} else {
//													treeview.treeview(
//															"addNode", content,
//															form, node);
//												}
//												$("#dialog-scan").dialog(
//														"close");
//											},
//											"Cancel" : function() {
//												$("#dialog-scan").dialog(
//														"close");
//											}
//										},
//										close : function() {
//											isEdit = false;
//											emptyForm("dialog-scan");
//										}
//									}).keydown(
//									function(e) {
//										if (e.which == 13) {
//											e.preventDefault();
//											$(this).parent().find(
//													"button:eq(1)").click();
//										}
//									});
//					$("#dialog-click")
//							.dialog(
//									{
//										autoOpen : false,
//										height : 525,
//										width : 350,
//										modal : true,
//										show : {
//											effect : "blind",
//											duration : 200
//										},
//										buttons : {
//											"OK" : function() {
//												form = getForm("dialog-click",
//														"click");
//												var content = getContext(form);
//												var node = master
//														.treeviewnode("getSelectedNode");
//												if (isEdit) {
//													treeview.treeview(
//															"editTreeNode",
//															node, form);
//												} else {
//													treeview.treeview(
//															"addNode", content,
//															form, node);
//												}
//												$("#dialog-click").dialog(
//														"close");
//											},
//											"Cancel" : function() {
//												$("#dialog-click").dialog(
//														"close");
//											}
//										},
//										close : function() {
//											isEdit = false;
//											emptyForm("dialog-click");
//										}
//									}).keydown(
//									function(e) {
//										if (e.which == 13) {
//											e.preventDefault();
//											$(this).parent().find(
//													"button:eq(1)").click();
//										}
//									});
//					$("#dialog-drop")
//							.dialog(
//									{
//										autoOpen : false,
//										height : 325,
//										width : 350,
//										modal : true,
//										show : {
//											effect : "blind",
//											duration : 200
//										},
//										buttons : {
//											"OK" : function() {
//												form = getForm("dialog-drop",
//														"drop");
//												var content = getContext(form);
//												var node = master
//														.treeviewnode("getSelectedNode");
//												if (isEdit) {
//													treeview.treeview(
//															"editTreeNode",
//															node, form);
//												} else {
//													treeview.treeview(
//															"addNode", content,
//															form, node);
//												}
//												$("#dialog-drop").dialog(
//														"close");
//											},
//											"Cancel" : function() {
//												$("#dialog-drop").dialog(
//														"close");
//											}
//										},
//										close : function() {
//											isEdit = false;
//											emptyForm("dialog-drop");
//										}
//									}).keydown(
//									function(e) {
//										if (e.which == 13) {
//											e.preventDefault();
//											$(this).parent().find(
//													"button:eq(1)").click();
//										}
//									});
//					$("#dialog-type")
//							.dialog(
//									{
//										autoOpen : false,
//										height : 400,
//										width : 350,
//										modal : true,
//										show : {
//											effect : "blind",
//											duration : 200
//										},
//										buttons : {
//											"OK" : function() {
//												if (checkIsNull("type-path")) {
//													alert("the path of type cannot be null.");
//													return;
//												}
//												form = getForm("dialog-type",
//														"type");
//												var content = getContext(form);
//												var node = master
//														.treeviewnode("getSelectedNode");
//												if (isEdit) {
//													treeview.treeview(
//															"editTreeNode",
//															node, form);
//												} else {
//													treeview.treeview(
//															"addNode", content,
//															form, node);
//												}
//												$("#dialog-type").dialog(
//														"close");
//											},
//											"Cancel" : function() {
//												$("#dialog-type").dialog(
//														"close");
//											}
//										},
//										close : function() {
//											isEdit = false;
//											emptyForm("dialog-type");
//										}
//									}).keydown(
//									function(e) {
//										if (e.which == 13) {
//											e.preventDefault();
//											$(this).parent().find(
//													"button:eq(1)").click();
//										}
//									});
//					$("#dialog-validate")
//							.dialog(
//									{
//										autoOpen : false,
//										height : 400,
//										width : 350,
//										modal : true,
//										show : {
//											effect : "blind",
//											duration : 200
//										},
//										buttons : {
//											"OK" : function() {
//												if (checkIsNull("validate-valuePath")) {
//													alert("the valuePath of validate cannot be null.");
//													return;
//												}
//												form = getForm(
//														"dialog-validate",
//														"validate");
//												var content = getContext(form);
//												var node = master
//														.treeviewnode("getSelectedNode");
//												if (isEdit) {
//													treeview.treeview(
//															"editTreeNode",
//															node, form);
//												} else {
//													treeview.treeview(
//															"addNode", content,
//															form, node);
//												}
//												$("#dialog-validate").dialog(
//														"close");
//											},
//											"Cancel" : function() {
//												$("#dialog-validate").dialog(
//														"close");
//											}
//										},
//										close : function() {
//											isEdit = false;
//											emptyForm("dialog-validate");
//										}
//									}).keydown(
//									function(e) {
//										if (e.which == 13) {
//											e.preventDefault();
//											$(this).parent().find(
//													"button:eq(1)").click();
//										}
//									});
//					$("#dialog-ic").dialog({
//						autoOpen : false,
//						height : 200,
//						width : 250,
//						modal : true,
//						show : {
//							effect : "blind",
//							duration : 200
//						},
//						buttons : {
//							"OK" : function() {
//								if (checkIsNull("ic-text")) {
//									alert("the content cannot be null.");
//									return;
//								}
//								sendIC($("#ic-text").val());
//								$("#dialog-ic").dialog("close");
//							},
//							"Cancel" : function() {
//								$("#dialog-type").dialog("close");
//							}
//						},
//						close : function() {
//							emptyForm("dialog-ic");
//						}
//					}).keydown(function(e) {
//						if (e.which == 13) {
//							e.preventDefault();
//							$(this).parent().find("button:eq(1)").click();
//						}
//					});
//					$("#dialog-while")
//							.dialog(
//									{
//										autoOpen : false,
//										height : 425,
//										width : 350,
//										modal : true,
//										show : {
//											effect : "blind",
//											duration : 200
//										},
//										buttons : {
//											"OK" : function() {
//												form = getForm("dialog-while",
//														"while");
//												var content = getContext(form);
//												var node = master
//														.treeviewnode("getSelectedNode");
//												if (isEdit) {
//													treeview.treeview(
//															"editTreeNode",
//															node, form);
//												} else {
//													treeview.treeview(
//															"addNode", content,
//															form, node);
//												}
//												$("#dialog-while").dialog(
//														"close");
//											},
//											"Cancel" : function() {
//												$("#dialog-while").dialog(
//														"close");
//											}
//										},
//										close : function() {
//											isEdit = false;
//											emptyForm("dialog-while");
//										}
//									}).keydown(
//									function(e) {
//										if (e.which == 13) {
//											e.preventDefault();
//											$(this).parent().find(
//													"button:eq(1)").click();
//										}
//									});
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
							if (name != "task")
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
							if (name != "task")
								treeview.treeview("downTreeNode", node);
						}
					});
					$("#btn-test").button();
					$("#btn-submit").button();
					$("#btn-quit").button();
					$("#btn-subtask").button().click(function() {
						var node = master.treeviewnode("getSelectedNode");
						if (node) {
							$("#dialog-subtask").dialog("open");
						}
					});
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
					$("#btn-scan").button().click(function() {
						var node = master.treeviewnode("getSelectedNode");
						if (node) {
							$("#dialog-scan").dialog("open");
						}
					});
					$("#btn-click").button().click(function() {
						var node = master.treeviewnode("getSelectedNode");
						if (node) {
							$("#dialog-click").dialog("open");
						}
					});
					$("#btn-type").button().click(function() {
						var node = master.treeviewnode("getSelectedNode");
						if (node) {
							$("#dialog-type").dialog("open");
						}
					});
					$("#btn-validate").button().click(function() {
						var node = master.treeviewnode("getSelectedNode");
						if (node) {
							$("#dialog-validate").dialog("open");
						}
					});
					$("#btn-drop").button().click(function() {
						var node = master.treeviewnode("getSelectedNode");
						if (node) {
							$("#dialog-drop").dialog("open");
						}
					});
					$("#btn-while").button().click(function() {
						var node = master.treeviewnode("getSelectedNode");
						if (node) {
							$("#dialog-while").dialog("open");
						}
					});
					$("#tabs").tabs();
					$("#tab-tree")
							.click(
									function() {
										if (isTree) {
											return;
										} else {
											isTree = true;
											$(".cmd-group button:lt(4)").attr(
													'disabled', false);
											$(".button-group button").attr(
													'disabled', false);
											treeview.treeview("empty");
											var str = $("#xml_text").val()
													.trim();
											var str1 = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>";
											if (str == "" || str == str1) {
												str = "<task name=\"Untitled task\" />";
											}
											$("#xml_text").val(str);
											var t = xmlToTree(str);
											master = treeview.treeview(
													"createTree", t);
											master.treeviewnode("setSelect");
										}
									});
					$("#tab-xml").click(
							function() {
								if (!isTree) {
									return;
								} else {
									isTree = false;
									$(".cmd-group button:lt(4)").attr(
											'disabled', true);
									$(".button-group button").attr('disabled',
											true);
									var level = 0;
									var str = treeToXML(master, level, str);
									$("#xml_text").val(str);
								}
							});
					window.onload = function() {
						centerBG();
					}
					window.onresize = function() {
						centerBG();
					}
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
			value = escape(value);
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
// empty a form
function emptyForm(name) {
	$("#" + name + " input").each(function() {
		if ($(this).attr('type') == "text") {
			$(this).val("");
		}
		if ($(this).attr('type') == "radio") {
			$("#" + name + " input:radio:eq(0)").click();
		}
	});
}
//
// bind data for Form when edit
function bindForm(dialogName, node) {
	var params = node.data.params;
	for ( var key in params) {
		if (key == "overlap" || key == "isJSON") {
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
	var h2 = h1 * 0.8 + "px";
	var h3 = h1 * 0.1 + "px";
	$("#button-group").css("height", h2);
	$("#button-group").css("margin-top", h3);
}
//
// to escape the special character of xml. eg. '&'->'&amp;' '<'->'&lt;'
// '>'->'&gt;' '"'->"&quot;" "'"->"&apos;"
function escape(str) {
	var reg0 = new RegExp("&", "g");
	str = str.replace(reg0, "&amp;");
	return str;
}
