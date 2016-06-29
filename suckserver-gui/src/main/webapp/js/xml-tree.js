/*
 * convert XML string to tree nodes
 */
function xmlToTree(xml) {
	var root = $($.parseXML(xml)).find("task");
	if (root.length == 0) {
		console.error("The root tag must be <task>.");
	}
	return _createTree(root.get(0));
}

/*
 * create tree from DOM
 */
function _createTree(elem) {
	//
	// Create node data.
	var node = {
		data : {
			name : toCamelCase(elem.tagName),
			params : {}
		}
	};
	//
	// Set params.
	var params = node.data.params;
	for ( var i in elem.attributes) {
		var attr = elem.attributes[i];
		if (!attr.specified) {
			continue;
		}
		var value = attr.value;
		value = escape(value);
		params[attr.name] = value;
	}
	//
	// Create child nodes.
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
 * get content of a node
 */
function getContext(node) {
	if(node.data.name == undefined) {
		return;
	}
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
	var name = toDomCase(data.name);
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
//	var reg1 = new RegExp("<","g");
//	var reg2 = new RegExp(">","g");
//	var reg3 = new RegExp("\"","g");
//	var reg4 = new RegExp("'","g");
	str = str.replace(reg0,"&amp;");
//    str = str.replace(reg1,"&lt;");
//	str = str.replace(reg2,"&gt;");
//	str = str.replace(reg3,"&quot;");
//	str = str.replace(reg4,"&apos;");
	return str;
}

function toCamelCase(str) {
	var name = "";
	var words = str.split("-");
	for (var i = 0; i < words.length; i++) {
		var word = words[i];
		word = word.substring(0, 1).toUpperCase() + word.substring(1);
		name += word;
	}
	return name;
}

function toDomCase(str) {
	var name = str.replace(/[A-Z]/, function(m) {
		return "-" + m.toLowerCase();
	});
	if (name.length != 0 && name.charAt(0) == '-') {
		name = name.substring(1);
	}
	return name;
}

