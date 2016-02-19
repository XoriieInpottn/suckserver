/*
 * map from name to text
 */
var NODE_NAME_MAP = {
	task : "新任务",
	table : "创建表",
	column : "创建列",
	load : "载入页面",
	select : "选择元素",
	match : "文字匹配",
	save : "保存表",
	print : "输出",
    "var" : "临时存储"
};

var ATTR_NAME_MAP = {
	name : "名称",
	type : "类型",
	overlap : "覆盖",
	content : "内容",
	attr : "属性",
	path : "路径",
	"var" : "对象",
	url : "地址",
	"max-page" : "最大页数",
	table : "表名",
	value : "值"
};

/*
 * convert XML string to tree nodes
 */
function xmlToTree(xml) {
	var root = $($.parseXML(xml)).find("task");
	if (root == undefined) {
		console.err("The root tag must be <task>.");
	}
	return _createTree(root.get(0));
}

/*
 * create tree from DOM
 */
function _createTree(elem) {
	/*
	 * create basic node
	 */
	var node = {
		data : {
			name : elem.tagName,
			params : {}
		}
	};
	/*
	 * set params
	 */
	var params = node.data.params;
	for ( var i in elem.attributes) {
		var attr = elem.attributes[i];
		if (!attr.specified) {
			continue;
		}
		params[attr.name] = attr.value;
	}
	/*
	 * create child nodes
	 */
	var childNodes = [];
	$(elem).children().each(function(i, childElem) {
		var childNode = _createTree(childElem);
		childNodes[childNodes.length] = childNode;
	});
	if (childNodes.length > 0) {
		node.nodes = childNodes;
	}
	/*
	 * add text
	 */
	addNodeText(node);
	return node;
}

/*
 * add text to node
 */
function addNodeText(node) {
	var text = nodeNameToText(node.data.name);
	var params = node.data.params;
	var arr = [];
	var count = 0;
	for ( var i in params) {
		if (count >= 2) {
			break;
		}
		var name = attrNameToText(i);
		var value = params[i];
		arr[arr.length] = name + ": " + value;
	}
	if (arr.length > 0) {
		text += " (" + arr.join(", ") + ")";
	}
	node.text = text;
}

/*
 * get text from the map
 */
function nodeNameToText(name) {
	var text = NODE_NAME_MAP[name];
	if (text == undefined) {
		console.log("Node name " + name + " is not defined.");
		text = name;
	}
	return text;
}

function attrNameToText(name) {
	var text = ATTR_NAME_MAP[name];
	if (text == undefined) {
		console.log("Attribute name " + name + " is not defined.");
		text = name;
	}
	return text;
}

/**
 * convert tree node data to XML string
 */
function treeToXML(root, level, str) {
	//
	// for root
	if (!level) {
		level = 0;
	}
	if (!str) {
		str = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n";
		// str = "";
	}
	//
	// get name and params
	var data = root.data;
	if (!data) {
		console.error("Invalid tree node.");
		return;
	}
	var name = data.name;
	var params = data.params;
	//
	// indent
	for (var i = 0; i < level; i++) {
		str += "    ";
	}
	//
	// tag and its attributes
	str += "<" + name;
	if (params) {
		for (key in params) {
			str += " " + key + "=\"" + params[key] + "\"";
		}
	}
	//
	// nodes
	if (root.nodes) {
		str += ">\n"
		for (i in root.nodes) {
			str = treeToXML(root.nodes[i], level + 1, str);
		}
		for (var i = 0; i < level; i++) {
			str += "    ";
		}
		str += "</" + name + ">\n";
	} else {
		str += " />\n";
	}
	return str;
}
