/**
 * 
 */
/*
 * to get the action URL
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
/*
 * to check whether the element is null
 */
function checkIsNull(id) {
	if ($("#" + id).val() == "") {
		return true;
	}
	return false;
}
/*
 * to check whether the element is illegal
 */
function checkIsIllegal(reg, id) {
	if (!reg.test($("#" + id).val())) {
		return true;
	}
	return false;
}


