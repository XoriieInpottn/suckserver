/*
 * The entry.
 */
$(document).ready(function() {
	$("#task-list").addClass("ui-state-active");
	$("#add-task").removeClass("ui-state-active");
	//
	// Item list.
	$(".datalist").datalist({
		pageSize : 20,
		url : URL_GET_TASKS,
		pagers : [ ".pager1", ".pager2" ],
		onDataLoaded : onDataLoaded,
		onItemCreated : function(e, item) {
			var data = item.datalistitem("data");
			// console.log(data.name);
			item.find(".btn-remove").button({
				text : false,
				icons : {
					primary : "ui-icon-trash"
				}
			}).click(function() {
				removeTask(data);
			});
			item.find("div:last").hide();
			item.mouseover(function() {
				item.find("div:last").show();
			});
			item.mouseleave(function() {
				item.find("div:last").hide();
			});
		}
	});
	//
	// Pager.
	$(".pager1, .pager2").pager({
		datalist : ".datalist",
		rangeSize : 11,
		onButtonCreated : function(e, btn) {
			if (btn.hasClass("ui-pager-first")) {
				btn.button({
					text : false,
					icons : {
						primary : "ui-icon-seek-start"
					}
				});
			} else if (btn.hasClass("ui-pager-prev")) {
				btn.button({
					text : false,
					icons : {
						primary : "ui-icon-seek-prev"
					}
				});
			} else if (btn.hasClass("ui-pager-next")) {
				btn.button({
					text : false,
					icons : {
						primary : "ui-icon-seek-next"
					}
				});
			} else if (btn.hasClass("ui-pager-last")) {
				btn.button({
					text : false,
					icons : {
						primary : "ui-icon-seek-end"
					}
				});
			} else {
				btn.button();
			}
		}
	});
	//
	// Load data.
	$(".datalist").datalist("refresh");
});

function onDataLoaded(e, data) {
	var task = data[0];
	if (task.endTime == null) {
		task.endTime = "?";
	}
	task.type = task.type == 0 ? "Deploy" : "Test";
	switch (task.status) {
	case 0:
		task.status = "Running";
		break;
	case 1:
		task.status = "Complete";
		break;
	case 2:
		task.status = "Terminated";
		break;
	case -1:
		task.status = "Error";
		break;
	}
}

function removeTask(data) {
	var tid = data[0].id;
	if (!tid) {
		return;
	}
	if (!confirm("Do you really want to remove task \"" + data[0].name + "\" ?")) {
		return;
	}
	$.ajax({
		type : "POST",
		url : URL_REMOVE_TASK,
		data : {
			tid : tid
		},
		success : function(result) {
			switch (result.status) {
			case "success":
				$(".datalist").datalist("refresh");
				break;
			}
		}
	});
}
