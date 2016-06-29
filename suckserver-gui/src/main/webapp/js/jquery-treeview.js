var _treeviewnode = {
	/*
	 * Options.
	 */
	options : {
		indentation : "2em",
		content : "Empty node.",
		isCollapsed : false,
		data : undefined,
		onCollapsed : undefined,
		onExpanded : undefined,
		onSelected : undefined,
		onClick : undefined,
		onDblclick : undefined
	},
	/*
	 * Create.
	 */
	_create : function() {
		var that = this;
		//
		// Fields.
		this.fields = {
			indentation : this.options.indentation,
			content : this.options.content,
			isCollapsed : this.options.isCollapsed,
			data : this.options.data,
			isSelected : false
		};
		//
		//
		this.element.addClass("ui-treeview-node");
		//
		// Header.
		var header = $("<h3></h3>");
		this.element.append(header);
		header.addClass("ui-treeview-header");
		// header.addClass("ui-accordion-header");
		// header.addClass("ui-accordion-icons");
		header.addClass("ui-corner-all");
		header.addClass("ui-state-default");
		header.css("user-select", "none");
		this._hoverable(header);
		this._focusable(header);
		header.draggable({
			axis : "y",
			start : function() {
				var childrenBox = header.css("z-index", "65535").next();
				if (!that.fields.isCollapsed) {
					childrenBox.hide();
				}
			},
			stop : function() {
				var childrenBox = header.removeAttr("style").css("position", "relative").next();
				if (!that.fields.isCollapsed) {
					childrenBox.show();
				}
			}
		});
		header.droppable({
			greedy : true,
			drop : function(e, ui) {
				var node = ui.draggable.parent();
				var crtParent = node.treeviewnode("getParent");
				if (that == crtParent.data("lioxa-treeviewnode")) {
					console.log(that);
					console.log(crtParent.data("lioxa-treeviewnode"));
					return;
				}
				node.treeviewnode("detach");
				that.addChild(node);
			}
		});
		header.click(function() {
			that.setSelect();
			that._trigger("onClick");
		});
		header.dblclick(function() {
			that.toggleCollapse();
			that._trigger("onDblclick");
		});
		//
		// Icon.
		var icon = $("<span></span>");
		header.append(icon);
		icon.addClass("ui-treeview-header-icon");
		icon.addClass("ui-icon");
		icon.addClass(this.fields.isCollapsed ? "ui-icon-triangle-1-e" : "ui-icon-triangle-1-s");
		//
		// Content.
		var content = $("<div>" + this.fields.content + "</div>");
		header.append(content);
		//
		// Children box.
		var childrenBox = $("<div></div>");
		this.element.append(childrenBox);
		childrenBox.addClass("ui-treeview-children-box");
		childrenBox.css("padding-left", this.fields.indentation);
		if (this.fields.isCollapsed) {
			childrenBox.hide();
		}
		//
		// Refresh.
		this.refresh();
	},
	/*
	 * If this node is collapsed.
	 */
	isCollapsed : function() {
		return this.fields.isCollapsed;
	},
	/*
	 * Collapse the children box.
	 */
	collapse : function() {
		if (this.fields.isCollapsed) {
			return;
		}
		this._getIcon().removeClass("ui-icon-triangle-1-s").addClass("ui-icon-triangle-1-e");
		this.getChildrenBox().hide(50);
		this.fields.isCollapsed = true;
		this._trigger("onCollapsed");
	},
	/*
	 * Expand the children box.
	 */
	expand : function() {
		if (!this.fields.isCollapsed) {
			return;
		}
		this._getIcon().removeClass("ui-icon-triangle-1-e").addClass("ui-icon-triangle-1-s");
		this.getChildrenBox().show(50);
		this.fields.isCollapsed = false;
		this._trigger("onExpanded");
	},
	/*
	 * Toggle collapse the children box.
	 */
	toggleCollapse : function() {
		if (this.fields.isCollapsed) {
			this.expand();
		} else {
			this.collapse();
		}
	},
	/*
	 * edit a node
	 */
	editNode : function(form) {
		this.setData(form);
		var content = getContext(form);
		this.setContent(content);
	},
	/*
	 * Refresh this node based on the status.
	 */
	refresh : function() {
		var icon = this._getIcon();
		var childrenBox = this.getChildrenBox();
		if (this.fields.isCollapsed) {
			icon.removeClass("ui-icon-triangle-1-s").addClass("ui-icon-triangle-1-e");
			childrenBox.hide();
		} else {
			icon.removeClass("ui-icon-triangle-1-e").addClass("ui-icon-triangle-1-s");
			childrenBox.show();
		}
		if (childrenBox.children().length == 0) {
			icon.hide();
		} else {
			icon.show();
		}
		if (this.fields.isSelected) {
			this._getHeader().addClass("ui-state-active");
			var root = this._getRoot();
			this._unselectOthers(root);
		} else {
			this._getHeader().removeClass("ui-state-active");
		}
	},
	/*
	 * Add a child node to this node.
	 */
	addChild : function(node) {
		node.detach();
		this.getChildrenBox().append(node);
		this.refresh();
	},
	/*
	 * Detach this node from the tree.
	 */
	detach : function() {
		var parent = this.getParent();
		this.element.detach();
		if (parent) {
			parent.treeviewnode("refresh");
		}
	},
	/*
	 * Empty (remove all) children of this node.
	 */
	emptyChildren : function() {
		this.getChildrenBox().empty();
		this.refresh();
	},
	/*
	 * Get the parent node.
	 */
	getParent : function() {
		var parent = this.element.parent().parent();
		return parent.length == 1 ? parent : undefined;
	},
	/*
	 * Get the children box.
	 */
	getChildrenBox : function() {
		return this.element.children(".ui-treeview-children-box");
	},
	/*
	 * Get the child nodes.
	 */
	getChildren : function() {
		var childrenBox = this.getChildrenBox();
		return childrenBox.children();
	},
	/*
	 * Get data.
	 */
	getData : function() {
		return this.fields.data;
	},
	/*
	 * Set data.
	 */
	setData : function(data) {
		this.fields.data = data;
	},
	/*
	 * Get content.
	 */
	getContent : function() {
		return this.fields.content;
	},
	/*
	 * Set content.
	 */
	setContent : function(content) {
		var box = this._getIcon().siblings();
		box.empty();
		box.html(content);
	},
	/*
	 * If this node is selected.
	 */
	isSelected : function() {
		return this.fields.isSelected;
	},
	/*
	 * Select this node.
	 */
	setSelect : function(isSelected) {
		if (isSelected == undefined || isSelected) {
			isSelected = true;
		}
		if (!(isSelected ^ this.fields.isSelected)) {
			return;
		}
		this.fields.isSelected = isSelected;
		this.refresh();
		if (isSelected) {
			this._trigger("onSelected");
		}
	},
	/*
	 * Toggle this node.
	 */
	toggleSelect : function() {
		this.fields.isSelected = !this.fields.isSelected;
		this.refresh();
	},
	/*
	 * Get selected node of this tree.
	 */
	getSelectedNode : function() {
		if (this.fields.isSelected) {
			return this.element;
		}
		var children = this.getChildren();
		for (var i = 0; i < children.length; i++) {
			var child = children.get(i);
			var selectedNode = $(child).treeviewnode("getSelectedNode");
			if (selectedNode) {
				return selectedNode;
			}
		}
		return null;
	},
	/*
	 * Get the icon.
	 */
	_getIcon : function() {
		return this._getHeader().children(".ui-treeview-header-icon");
	},
	/*
	 * Get the header.
	 */
	_getHeader : function() {
		return this.element.children(".ui-treeview-header");
	},
	/*
	 * Get the root node.
	 */
	_getRoot : function() {
		var root = this.element;
		var parent = root.parent().parent();
		while (parent.length != 0 && parent.data("lioxa-treeviewnode")) {
			root = parent;
			parent = root.parent().parent();
		}
		return root;
	},
	/*
	 * Unselect other nodes in the same tree.
	 */
	_unselectOthers : function(root) {
		var nodeObj = root.data("lioxa-treeviewnode");
		if (nodeObj != this) {
			nodeObj.setSelect(false);
		}
		var children = nodeObj.getChildren();
		for (var i = 0; i < children.length; i++) {
			var child = children.get(i);
			this._unselectOthers($(child));
		}
	}
};

$.widget("lioxa.treeviewnode", _treeviewnode);

/*
 * 
 */

var _treeview = {
	/*
	 * Options.
	 */
	options : {
		indentation : "2em",
		onNodeCollapsed : undefined,
		onNodeExpanded : undefined,
		onNodeSelected : undefined,
		onNodeCilck : undefined,
		onNodeDblclick : undefined
	},
	/*
	 * Create.
	 */
	_create : function() {
		//
		// Fields.
		this.fields = {
			indentation : this.options.indentation
		};
		//
		//
		this.element.addClass("ui-treeview");
	},
	/*
	 * Add new node to the tree.
	 */
	addNode : function(content, data, parent) {
		var that = this;
		var node = $("<div></div>");
		node.treeviewnode({
			indentation : this.fields.indentation,
			content : content,
			data : data,
			onCollapsed : function(e) {
				that._trigger("onNodeCollapsed", e, [ node ]);
			},
			onExpanded : function(e) {
				that._trigger("onNodeExpanded", e, [ node ]);
			},
			onSelected : function(e) {
				that._trigger("onNodeSelected", e, [ node ]);
			},
			onClick : function(e) {
				that._trigger("onNodeClick", e, [ node ]);
			},
			onDblclick : function(e) {
				that._trigger("onNodeDblclick", e, [ node ]);
			}
		});
		if (!parent || parent.length <= 0) {
			this.element.append(node);
		} else {
			parent.treeviewnode("addChild", node);
		}
		return node;
	},
	/*
	 * Remove node from tree view.
	 */
	removeNode : function(node) {
		node.treeviewnode("detach");
	},
	/*
	 * Remove all nodes of this tree view.
	 */
	empty : function() {
		this.element.empty();
	},
	/*
	 * Get the current selected node.
	 */
	getSelectedNode : function() {
		var roots = this.element.children();
		for (var i = 0; i < roots.length; i++) {
			var root = roots.get(i);
			var selectedNode = $(root).treeviewnode("getSelectedNode");
			if (selectedNode) {
				return selectedNode;
			}
		}
		return null;
	},
	/*
	 * Move up a node
	 */
	upTreeNode : function(node) {
		if (node == null) {
			return;
		}
		if (node.index() > 0) {
			var prev = node.prev();
			node.detach();
			node.insertBefore(prev);
		}
	},
	/*
	 * Move down a node
	 */
	downTreeNode : function(node) {
		if (node == null) {
			return;
		}
		var parent = node.treeviewnode("getParent");
		var size = parent.treeviewnode("getChildren").length;
		if (node.index() < size - 1) {
			var next = node.next();
			node.detach();
			node.insertAfter(next);
		}
	},
	/*
	 * Edit a tree node
	 */
	editTreeNode : function(node, form) {
		if (node == null) {
			return;
		}
		node.treeviewnode("editNode", form);
	},
	/*
	 * Create a tree
	 */
	createTree : function(tree) {
		var root = this.addNode(getContext(tree), tree);
		var master = root;
		this.f(tree, root);
		return master;
	},

	f : function(node, parent) {
		if (node.nodes != undefined) {
			for (var i = 0; i < node.nodes.length; i++) {
				var child = node.nodes[i];
				var p = this.addNode(getContext(child), child, parent);
				this.f(child, p);
			}
		}
	}
};

$.widget("lioxa.treeview", _treeview);
