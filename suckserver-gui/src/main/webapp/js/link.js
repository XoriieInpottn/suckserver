var tid = undefined;

$(document).ready(function() {
	$("#btn_test").click(function() {
		var size = $("#tree").treeview("size");
		if (size == undefined || size <= 1) {
			alert("什么都没写，你测试个毛啊～～～");
			return;
		}
		createTask(true);
	});
	$("#btn_submit").click(function() {
		var size = $("#tree").treeview("size");
		if (size == undefined || size <= 1) {
			alert("什么都没写，你执行个毛啊～～～");
			return;
		}
		createTask(false);
	});
	$("#btn_quit").click(function() {
		stopTask();
	});
	clearLog();
});

function appendLog(log) {
	var html = "<span>" + log + "</span><br/>";
	$("#log-box>div").append(html);
	if ($("#log-box>div").children().length > 1000) {
		$("#log-box>div span:first").remove();
	}
}

function appendError(error) {
	var html = '<span style="color:red">' + error + "</span><br/>";
	$("#log-box>div").append(html);
	if ($("#log-box>div").children().length > 1000) {
		$("#log-box>div span:first").remove();
	}
}

function clearLog() {
	$("#log-box>div").empty();
	appendLog("暂时没有日志，等待新任务 :-)");
}

function createTask(test) {
	var template;
	if (isTree) {
		var root = $("#tree").treeview("getRoot");
		template = treeToXML(root, 0, template);
	} else {
		template = $("#xml_text").val();
	}
	$.ajax({
		type : "POST",
		url : URL_CREATE_TASK,
		data : {
			template : template,
			test : test
		},
		success : function(result) {
			switch (result.status) {
			case "success":
				tid = result.tid;
				if (tid != undefined) {
					$("#btn_test").attr('disabled', true);
					$("#btn_quit").attr('disabled', false);
					$("#btn_submit").attr('disabled', true);
					clearLog();
					appendLog("开始爬取数据，任务ID：" + tid);
					showLogs();
				}
				break;
			}
		}
	});
}

var after = undefined;

function showLogs() {
	if (tid == undefined) {
		return;
	}
	$("body").oneTime("2s", function() {
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
					for ( var i in result.data) {
						var log = result.data[i];
						after = log.time;
						var str = after + ": " + log.content;
						if (log.type == 0) {
							appendLog(str);
						} else {
							appendError(str);
						}
					}
					$('#log-box').scrollTop($('#log-box')[0].scrollHeight);
					var info;
					switch (result.task.status) {
					case 0:
						showLogs();
						break;
					case 1:
						info = "爬取顺利完成";
						break;
					case 2:
						info = "爬取已停止";
						break;
					case -1:
						info = "爬取遇到致命错误";
						break;
					default:
						info = "任务状态错误";
					}
					if (result.task.status != 0) {
						info = "<span>" + info + "</span><br/>";
						$("#log-box>div").append(info);
						$("#btn_test").attr('disabled', false);
						$("#btn_submit").attr('disabled', false);
						$("#btn_quit").attr('disabled', true);
					}
					break;
				}
			}
		});
	});
}

function stopTask() {
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