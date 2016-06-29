/**
 * 
 */

$(document).ready(function() {
	var editor = $("body").taskeditor();
	// var form;
	// $("#add-task").addClass("ui-state-active");
	// $("#task-list").removeClass("ui-state-active");
	// window.onload = function() {
	// centerBG();
	// }
	// window.onresize = function() {
	// centerBG();
	// }
});

var _taskeditor = {
	rootNode : null,
	isTree : true,
	isEdit : false,
	_create : function() {
		this._initTree();
		this._initTabs();
		this._initRoutines();
		this._initToolbar();
	},
	_initTree : function() {
		var treeview = this._getTreeview().treeview();
		var data = this._makeNodeData("Task", {
			name : "Untitled task"
		});
		var content = getContext(data);
		this.rootNode = treeview.treeview("addNode", content, data);
		this.rootNode.treeviewnode("expand");
		this.rootNode.treeviewnode("setSelect");
	},
	_initTabs : function() {
		var that = this;
		$("#tabs").tabs();
		$("#tab-tree").click(function() {
			if (that.isTree) {
				return;
			}
			that.isTree = true;
			$(".cmd-group button:lt(4)").attr('disabled', false);
			$(".button-group button").attr('disabled', false);
			var treeview = that._getTreeview();
			treeview.treeview("empty");
			var str = $("#xml_text").val().trim();
			var str1 = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>";
			if (str == "" || str == str1) {
				str = "<task name=\"Untitled task\" />";
			}
			$("#xml_text").val(str);
			var t = xmlToTree(str);
			that.rootNode = treeview.treeview("createTree", t);
			that.rootNode.treeviewnode("setSelect");
		});
		$("#tab-xml").click(function() {
			if (!that.isTree) {
				return;
			}
			that.isTree = false;
			$(".cmd-group button:lt(4)").attr('disabled', true);
			$(".button-group button").attr('disabled', true);
			var level = 0;
			var str = treeToXML(that.rootNode, level, str);
			$("#xml_text").val(str);
		});
	},
	/**
	 * Initialize routines based on their info. <br />
	 * This operation include: <br />
	 * 1) Create routine buttons. <br />
	 * 2) Create routine dialogs. <br />
	 */
	_initRoutines : function() {
		var that = this;
		this._getRoutineDialogs().formdialogs({
			onSubmit : function(e, name, params) {
				var node = that.rootNode.treeviewnode("getSelectedNode");
				var data = that._makeNodeData(name, params);
				var content = getContext(data);
				if (that.isEdit) {
					node.treeviewnode("setContent", content);
					node.treeviewnode("setData", data);
					that.isEdit = false;
				} else {
					that._getTreeview().treeview("addNode", content, data, node);
				}
			}
		});
		$.ajax({
			type : "POST",
			url : URL_GET_ROUTINES,
			success : function(result) {
				switch (result.status) {
				case "success":
					var btns = that._getRoutineButtons();
					var dialogs = that._getRoutineDialogs();
					var routines = eval("(" + result.routines + ")");
					for (var i = 0; i < routines.length; i++) {
						var routine = routines[i];
						var name = routine.name;
						var params = routine.params;
						that._createButtons(btns, name);
						dialogs.formdialogs("addDialog", name, name + " routine.", params);
					}
					break;
				default:
					console.error("出现错误");
				}
			}
		});
	},
	/**
	 * 
	 */
	_initToolbar : function() {
		var that = this;
		$("#btn-edit").button().click(function() {
			var node = that.rootNode.treeviewnode("getSelectedNode");
			if (!node) {
				return;
			}
			var data = node.treeviewnode("getData");
			console.log(data);
			that._getRoutineDialogs().formdialogs("openDialog", data.data.name, data.data.params);
			that.isEdit = true;
		});
		$("#btn-delete").button().click(function() {
			var node = that.rootNode.treeviewnode("getSelectedNode");
			if (!node) {
				return;
			}
			var data = node.treeviewnode("getData");
			if (data.data.name != "Task") {
				that._getTreeview().treeview("removeNode", node);
			}
			that.rootNode.treeviewnode("setSelect");
		});
		$("#btn-up").button().click(function() {
			var node = that.rootNode.treeviewnode("getSelectedNode");
			if (!node) {
				return;
			}
			that._getTreeview().treeview("upTreeNode", node);
		});
		$("#btn-down").button().click(function() {
			var node = that.rootNode.treeviewnode("getSelectedNode");
			if (!node) {
				return;
			}
			var name = node.treeviewnode("getData").data.name;
			if (name != "task") {
				that._getTreeview().treeview("downTreeNode", node);
			}
		});
		$("#btn-test").button();
		$("#btn-submit").button();
		$("#btn-quit").button();
	},
	/**
	 * 
	 * @param name
	 * @param params
	 * @returns
	 */
	_makeNodeData : function(name, params) {
		var filtered = {};
		for ( var key in params) {
			var value = params[key];
			value = value.trim();
			if (value.length == 0) {
				continue;
			}
			var reg0 = new RegExp("&", "g");
			value = value.replace(reg0, "&amp;");
			filtered[key] = value;
		}
		return {
			data : {
				name : name,
				params : filtered
			}
		};
	},
	/**
	 * 
	 * @param parent
	 * @param name
	 */
	_createButtons : function(parent, name) {
		var that = this;
		var btn = $("<button id=\"btn-" + name + "\">" + name + "</button>");
		parent.append(btn);
		btn.data("routine-name", name);
		btn.button().click(function() {
			var node = that.rootNode.treeviewnode("getSelectedNode");
			if (node) {
				that._getRoutineDialogs().formdialogs("openDialog", $(this).data("routine-name"));
			}
		});
	},
	_getTreeview : function() {
		return $(".ui-treeview");
	},
	_getRoutineButtons : function() {
		return $("#routine-buttons");
	},
	_getRoutineDialogs : function() {
		return $("#routine-dialogs");
	}
};

$.widget("lioxa.taskeditor", _taskeditor);
