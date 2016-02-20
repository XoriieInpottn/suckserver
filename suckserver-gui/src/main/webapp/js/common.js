/**
 * 
 */

function getUrlVars() {
	var vars = {};
	var href = window.location.href;
	var start = href.indexOf('?');
	var end = href.indexOf('#');
	if (start < 0) {
		return vars;
	}
	start++;
	var hashes = end < 0 ? href.slice(start) : href.slice(start, end);
	hashes = hashes.split('&');
	for (var i = 0; i < hashes.length; i++) {
		hash = hashes[i].split('=');
		vars[hash[0]] = hash[1];
	}
	return vars;
}

function checkIsNull(id, warning_id) {
	if (document.getElementById(id).value == "") {
		document.getElementById(warning_id).value = "(此列表不能为空!)";
		return true;
	}
	return false;
}
function checkIsIllegal(reg, id, warning_id) {
	if (!reg.test(document.getElementById(id).value)) {
		document.getElementById(warning_id).value = "(此列表内容不合法 !)";
		return true;
	}
	return false;
}
function getEditModal(node) {
	if (node == undefined) {
		cosole.log("undefined node ");
		return;
	}
	if (node.data == undefined) {
		return {};
	}
	if (node.data.name == "table") {
		arr[0] = node.data.params.name;
		arr[1] = node.data.params.overlap;
	}
	if (node.data.name == "save") {
		arr[0] = node.data.params.table;
	}
	if (node.data.name == "column") {
		arr[0] = node.data.params.name;
		arr[1] = node.data.params.type;
	}
	if (node.data.name == "go" || node.data.name == "load") {
		arr[0] = node.data.params.url;
		arr[1] = node.data.params["max-page"];
	}
	if (node.data.name == "match") {
		arr[0] = node.data.params["var"];
		arr[1] = node.data.params.path;
		arr[2] = node.data.params.attr;
	}
	if (node.data.name == "print") {
		arr[0] = node.data.params.content;
	}
	if (node.data.name == "select") {
		arr[0] = node.data.params.path;
	}
	if (node.data.name == "var") {
		arr[0] = node.data.params.name;
		arr[1] = node.data.params.value;
	}
	return arr;
}