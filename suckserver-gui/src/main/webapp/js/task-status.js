var tid = null;
var after = null;
var list = new Array();
var i = 0;
/*
 * The entry.
 */
$(document).ready(function() {
	tid = getUrlVars().id;
	if (tid == undefined) {
		console.error("No id !");
		return;
	}
	var sideA = $("<a>Task Status</a>");
	$(".ui-sidebar").append(sideA);
	sideA.addClass("side-a");
	sideA.addClass("ui-state-active");
	sideA.attr("href", "#");
	$(".stop-task").button({
		text : false,
		icons : {
			primary : "ui-icon-stop"
		}
	}).click(function() {
		stopTask();
	});
	showLogs();
});

function stopTask() {
	if (!confirm("Do you really want to stop this task ?")) {
		return;
	}
	$.ajax({
		type : "POST",
		url : URL_STOP_TASK,
		data : {
			tid : tid
		},
		success : function(result) {
			switch (result.status) {
			case "success":
				$(".stop-task").attr("disabled", "disabled");
				break;
			}
		}
	});
}

function showLogs() {
	var data = {
		tid : tid
	};
	if (after != null) {
		data.after = after;
	}
	$.ajax({
		type : "POST",
		url : URL_GET_LOGS,
		data : data,
		success : function(result) {
			switch (result.status) {
			case "success":
				var task = result.task;
				var taskStat = result.taskStat;
				$(".name").text(task.name);
				$(".type").text(task.type ? "Test" : "Deploy");
				$(".start-time").text(task.startTime);
				$(".end-time").text(task.endTime);
				if (taskStat != undefined && taskStat != null) {
					taskStat.successCount;
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
				$('.log-box').scrollTop($('.log-box')[0].scrollHeight);
				switch (task.status) {
				case 0:
					$(".status").text("running");
					setTimeout(showLogs, 2000);
					break;
				case 1:
					$(".status").text("complete");
					appendLog("Task complete.", {
						time : after,
						color : "green"
					});
					break;
				case 2:
					$(".status").text("terminated");
					appendLog("Task terminated.", {
						time : after,
						color : "blue"
					});
					break;
				case -1:
					$(".status").text("error");
					appendLog("Task error.", {
						time : after,
						color : "red"
					});
					break;
				default:
					$(".status").text("unknown");
					appendLog("Unknown status.", {
						time : after,
						color : "red"
					});
				}
				if (result.task.status != 0) {
					$(".stop-task").attr("disabled", "disabled");
				}
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
	if(id != undefined) {
		html = "<p id = "+id+">";
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
	if(id != undefined) {
		for (x in list) {
			if(id == list[x]){
				return;
			}
		}
		list[i] = id;
		i = (i + 1)%100;
	}
	$(".log-box>div").append(html);
	if ($(".log-box>div").children().length > 1000) {
		$(".log-box>div span:first").remove();
	}
}
