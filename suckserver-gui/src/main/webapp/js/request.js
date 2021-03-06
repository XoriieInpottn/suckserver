var tid = undefined;
var list = new Array();
var i = 0;
$(document).ready(function() {
	$("#btn-test").click(function() {
		createTask(true);
	});
	$("#btn-submit").click(function() {
		createTask(false);
	});
	$("#btn-quit").click(function() {
		stopTask();
	});
	clearLog();
	$("#icOK").click(function() {
		alert("helli");
		if ($("#ic-text").val() != "") {
			alert($("ic-text").val());
			sendIC($("ic-text").val());
		}
	});
});

function appendError(error) {
	var html = '<span style="color:red">' + error + "</span><br/>";
	$("#main-bottom>div").append(html);
	if ($("#main-bottom>div").children().length > 1000) {
		$("#main-bottom>div span:first").remove();
	}
}

function clearLog() {
	$("#main-bottom>div").empty();
	i = 0;
	list = null;
	list = new Array();
	appendLog("Waiting for a new task ~~~ :-)");
}

function createTask(test) {
	var template = editor.taskeditor("getTemplate");
	console.log(template);
	$.ajax({
		type : "POST",
		url : URL_CREATE_TASK,
		data : {
			template : template,
			test : test,
		},
		success : function(result) {
			switch (result.status) {
			case "success":
				tid = result.tid;
				if (tid != undefined) {
					$("#btn-test").attr('disabled', true);
					$("#btn-quit").attr('disabled', false);
					$("#btn-submit").attr('disabled', true);
					$("#main-bottom>div").empty();
					i = 0;
					list = new Array();
					appendLog("开始爬取数据，任务ID：" + tid);
					showLogs();
				}
				break;
			default:
				console.log("出现错误");
			}
		}
	});
}

var after = undefined;

function showLogs() {
	if (tid == undefined) {
		return;
	}
	var data = {
		tid : tid
	};
	if (after != undefined) {
		data.after = after;
	}

	$.ajax({
		type : "POST",
		url : URL_GET_LOGS,
		data : data,
		success : function(result) {
			switch (result.status) {
			case "success":
				if (result.image != undefined) {
					if (result.image.length > 0) {
						console.log("image = " + result.image);
						$("#ic-image").attr("src",
								"data:image/png;base64," + result.image);
						$("#dialog-ic").dialog("open");
					}
				}
				for ( var i in result.data) {
					var log = result.data[i];
					after = log.time;
					if (log.type == 0) {
						appendLog(log.content, {
							time : after,
							id : log.id
						});
					} else {
						appendLog(log.content, {
							time : after,
							color : "red",
							id : log.id
						});
					}
				}
				$('#main-bottom').scrollTop($('#main-bottom')[0].scrollHeight);
				switch (result.task.status) {
				case 0:
					setTimeout(showLogs, 2000);
					break;
				case 1:
					appendLog("Task complete.", {
						time : after,
						color : "green"
					});
					break;
				case 2:
					appendLog("Task terminated.", {
						time : after,
						color : "blue"
					});
					break;
				case -1:
					appendLog("Task error.", {
						time : after,
						color : "red"
					});
					break;
				default:
					appendLog("Unknown status.", {
						time : after,
						color : "red"
					});
				}
				if (result.task.status != 0) {
					$("#btn-test").attr('disabled', false);
					$("#btn-submit").attr('disabled', false);
					$("#btn-quit").attr('disabled', true);
				}
				break;
			}
		}
	});
}

function stopTask() {
	if (tid == undefined)
		return;
	$.ajax({
		type : "POST",
		url : URL_STOP_TASK,
		data : {
			tid : tid
		},
		success : function(result) {
			switch (result.status) {
			case "success":
				break;
			}
		}
	});
}

function sendIC(icValue) {
	if (tid == undefined) {
		return;
	}
	$.ajax({
		type : "post",
		url : URL_SEND_IC,
		data : {
			tid : tid,
			icValue : icValue
		},
		success : function(result) {
			switch (result.status) {
			case "success":
				break;
			}
		}
	});
}

function appendLog(log, options) {
	if (options != undefined) {
		var time = options.time;
		var color = options.color;
		var id = options.id;
	}
	var html = "<p>";
	if (id != undefined) {
		html = "<p id = " + id + ">";
	}
	if (time != undefined) {
		html += "<span  style=\"color:#777\">" + time + "</span>";
		html += "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;";
	}
	if (color == undefined) {
		html += "<span>" + log + "</span>";
	} else {
		html += "<span  style=\"color:" + color + "\">" + log + "</span>";
	}
	html += "</p>";
	if (id != undefined) {
		for (x in list) {
			if (id == list[x]) {
				return;
			}
		}
		list[i] = id;
		i = (i + 1) % 100;
	}
	$("#main-bottom>div").append(html);
	if ($("#main-bottom>div").children().length > 1000) {
		$("#main-bottom>div span:first").remove();
	}
}
