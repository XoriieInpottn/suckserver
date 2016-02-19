/*
 * The entry.
 */
$(document).ready(function() {
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
//			item.find(".btn-remove").button({
//				text : false,
//				icons : {
//					primary : "ui-icon-trash"
//				}
//			});
//			// item.find("div:last").hide();
//			item.mouseover(function() {
//				item.find("div:last").show();
//			});
//			item.mouseleave(function() {
//				item.find("div:last").hide();
//			});
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
	task.type = task.type == 0 ? "deploy" : "test";
	switch (task.status) {
	case 0:
		task.status = "running";
		break;
	case 1:
		task.status = "complete";
		break;
	case 2:
		task.status = "stopped";
		break;
	case -1:
		task.status = "error";
		break;
	}
}
