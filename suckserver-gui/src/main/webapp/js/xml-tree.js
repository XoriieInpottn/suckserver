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
		var value = attr.value;
		value = escape(value);
		params[attr.name] = value;
//		params[attr.name] = attr.value;
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
 * get content of a node
 */
function getContext(node) {
	if(node.data.name == undefined) {
		return;
	}
//	var text = nodeNameToText(node.data.name);
	var text = node.data.name;
	var params = node.data.params;
	var arr = [];
	var count = 0;
	for ( var i in params) {
		if (count >= 2) {
			break;
		}
//		var name = attrNameToText(i);
		var name = i;
		var value = params[i];
		arr[arr.length] = name + ": " + value;
	}
	if (arr.length > 0) {
		text += " (" + arr.join(", ") + ")";
	}
	return text;
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
	var data = root.treeviewnode("getData").data;
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
	var nodes = root.treeviewnode("getChildren");
	if (nodes.length > 0) {
		str += ">\n";
		for (var i = 0; i < nodes.length; i++) {
			str = treeToXML($(nodes.get(i)), level + 1, str);
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

/*
 *to escape the special character of xml. eg. '&'->'&amp;'  '<'->'&lt;' '>'->'&gt;' '"'->"&quot;" "'"->"&apos;" 
 */
function escape(str) {
	var reg0 = new RegExp("&","g");
	var reg1 = new RegExp("<","g");
	var reg2 = new RegExp(">","g");
	var reg3 = new RegExp("\"","g");
	var reg4 = new RegExp("'","g");
	str = str.replace(reg0,"&amp;");
    str = str.replace(reg1,"&lt;");
	str = str.replace(reg2,"&gt");
	str = str.replace(reg3,"&quot;");
	str = str.replace(reg4,"&apos;");
	return str;
}

