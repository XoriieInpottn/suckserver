/*
 * data list item
 */

var _datalistitem = {
	/*
	 * Options.
	 */
	options : {
		data : null
	},
	/*
	 * Create.
	 */
	_create : function() {
		this.fields = {
			data : this.options.data
		};
	},
	/*
	 * Get or set data object.
	 */
	data : function(data) {
		if (data == undefined) {
			return this.fields.data;
		}
		this.fields.data = data;
	}
};

$.widget("lioxa.datalistitem", _datalistitem);

/*
 * data list
 */

var _datalist = {
	/*
	 * Options.
	 */
	options : {
		url : "",
		pageSize : 0,
		pageNum : 1,
		params : {},
		pagers : [],
		onDataLoaded : null,
		onItemCreated : null,
		onRefresh : null
	},
	/*
	 * Create.
	 */
	_create : function() {
		//
		// Fields.
		this.fields = {
			url : this.options.url,
			pageSize : this.options.pageSize,
			pageNum : this.options.pageNum,
			params : this.options.params,
			pagers : this.options.pagers,
			titleItems : null,
			itemTpl : null,
			totalCount : 0,
			data : [],
			pageCount : 1
		};
		//
		// Title items and (data) item template.
		var dataItem = this.element.find("li:last");
		this.fields.titleItems = dataItem.siblings();
		this.fields.itemTpl = dataItem.html();
		this.fields.itemTpl = this.fields.itemTpl ? "<li>" + this.fields.itemTpl + "</li>" : "<li></li>";
		//
		// Empty existing content.
		this.empty();
		this.element.html("<div style='text-align: center; height: 3em; line-height: 3em;'>loading data...</div>");
	},
	/*
	 * Get or set page number.
	 */
	pageNum : function(pageNum) {
		if (pageNum == undefined) {
			return this.fields.pageNum;
		}
		pageNum = parseInt(pageNum);
		this.fields.pageNum = pageNum < 1 ? 1 : pageNum;
	},
	/*
	 * Get page count.
	 */
	pageCount : function() {
		return this.fields.pageCount;
	},
	/*
	 * Get or set the request parameters.
	 */
	params : function(params) {
		if (params == undefined) {
			return this.fields.params;
		}
		this.fields.params = params;
	},
	/*
	 * Refresh the list.
	 */
	refresh : function(pageNum) {
		//
		// Set page number.
		this.pageNum(pageNum);
		//
		// Merge parameters.
		var postData = this.fields.params;
		postData.start = (this.fields.pageNum - 1) * this.fields.pageSize;
		postData.length = this.fields.pageSize;
		//
		// Request data.
		var that = this;
		$.ajax({
			type : "POST",
			url : this.fields.url,
			data : postData,
			success : function(result) {
				switch (result.status) {
				case "exception":
					console.log(result.exceptionMessage);
					break;
				case "error":
					console.log(result.errorInfo);
					break;
				case "success":
					that._refreshItems(result);
					break;
				}
			}
		});
	},
	/*
	 * Refresh items.
	 */
	_refreshItems : function(result) {
		//
		// Assign data and total count.
		this.fields.data = result.data;
		this.fields.totalCount = result.totalCount;
		//
		// Assign page count.
		if (this.fields.pageSize == 0) {
			this.fields.pageCount = 1;
		} else {
			this.fields.pageCount = Math.ceil(this.fields.totalCount / this.fields.pageSize);
			if (this.fields.totalCount == 0) {
				this.fields.pageCount = 1;
			}
		}
		//
		// Empty the list and fill in with new items.
		this.empty();
		var that = this;
		$.each(this.fields.data, function(i, data) {
			that.addItem(data);
		});
		//
		// Refresh pagers.
		$.each(this.fields.pagers, function(i, pager) {
			$(pager).pager("refresh");
		});
		//
		// After all items are created, onRefresh will be called.
		that._trigger("onRefresh");
	},

	/*
	 * Add item for the given data.
	 */
	addItem : function(data) {
		//
		// Before create item, onDataLoaded will be called.
		this._trigger("onDataLoaded", null, [ data ]);
		//
		// Create item.
		var item = this.fields.itemTpl.replace(/\{\{([\d\w_\.]+)\}\}/ig, function(m, $1) {
			var tmp = data;
			$.each($1.split("."), function(i, e) {
				tmp = tmp[e];
			});
			return tmp;
		});
		item = $(item);
		this.element.append(item);
		item.datalistitem({
			data : data
		});
		//
		// After create item, onItemCreated will be called.
		this._trigger("onItemCreated", null, [ item ]);
	},
	/*
	 * Empty the data items. (Does not include the title item)
	 */
	empty : function() {
		this.element.empty();
		this.element.append(this.fields.titleItems);
	}
};

$.widget("ringo.datalist", _datalist);

/*
 * pager
 */

var _pager = {
	/*
	 * Options.
	 */
	options : {
		datalist : "#datalist",
		rangeSize : 5,
		onButtonCreated : null
	},
	/*
	 * Create.
	 */
	_create : function() {
		//
		// Fields.
		this.fields = {
			datalist : this.options.datalist,
			rangeSize : this.options.rangeSize,
			rangeTpl : null,
			btnClickHandler : null
		};
		//
		// 4 important buttons.
		var first = this.element.find(".ui-pager-first");
		var prev = this.element.find(".ui-pager-prev");
		var next = this.element.find(".ui-pager-next");
		var last = this.element.find(".ui-pager-last");
		//
		// Attach button handlers.
		var that = this;
		this.fields.btnClickHandler = function() {
			//
			// Get page number, then reload.
			var pageNum = $(this).data("pageNum");
			if (pageNum) {
				$(that.options.datalist).datalist("refresh", pageNum);
			}
		}
		first.click(this.fields.btnClickHandler);
		prev.click(this.fields.btnClickHandler);
		next.click(this.fields.btnClickHandler);
		last.click(this.fields.btnClickHandler);
		//
		// "onButtonCreated" will be called.
		this._trigger("onButtonCreated", null, [ first ]);
		this._trigger("onButtonCreated", null, [ prev ]);
		this._trigger("onButtonCreated", null, [ next ]);
		this._trigger("onButtonCreated", null, [ last ]);
		//
		// Get range's template.
		var range = this.element.find(".ui-pager-range");
		this.fields.rangeTpl = range.html();
	},
	/*
	 * Refresh the pager. (Don't refresh the corresponding data list)
	 */
	refresh : function() {
		var datalist = $(this.options.datalist);
		var pageNum = datalist.datalist("pageNum");
		var pageCount = datalist.datalist("pageCount");
		var first = this.element.find(".ui-pager-first");
		var prev = this.element.find(".ui-pager-prev");
		var next = this.element.find(".ui-pager-next");
		var last = this.element.find(".ui-pager-last");
		if (pageNum == 1) {
			first.attr("disabled", "disabled");
			first.removeClass("ui-state-hover");
			first.removeClass("ui-state-focus");
			first.addClass("ui-state-disabled");
			prev.attr("disabled", "disabled");
			prev.removeClass("ui-state-hover");
			prev.removeClass("ui-state-focus");
			prev.addClass("ui-state-disabled");
		} else {
			first.removeAttr("disabled");
			first.removeClass("ui-state-disabled");
			first.data("pageNum", 1);
			prev.removeAttr("disabled");
			prev.removeClass("ui-state-disabled");
			prev.data("pageNum", pageNum - 1);
		}
		if (pageNum == pageCount) {
			next.attr("disabled", "disabled");
			next.removeClass("ui-state-hover");
			next.removeClass("ui-state-focus");
			next.addClass("ui-state-disabled");
			last.attr("disabled", "disabled");
			last.removeClass("ui-state-hover");
			last.removeClass("ui-state-focus");
			last.addClass("ui-state-disabled");
		} else {
			next.removeAttr("disabled");
			next.removeClass("ui-state-disabled");
			next.data("pageNum", pageNum + 1);
			last.removeAttr("disabled");
			last.removeClass("ui-state-disabled");
			last.data("pageNum", pageCount);
		}
		//
		// Get left, right.
		var r = this.fields.rangeSize;
		if (r < 3) {
			console.error("too small rangeSize");
			return;
		}
		var ln = Math.floor(r / 2);
		var rn = ln;
		if (r % 2 == 0) {
			ln--;
		}
		var left = pageNum;
		var right = pageNum;
		var i, j;
		for (i = 0; i < ln && left > 1; i++) {
			left--;
		}
		for (j = 0; j < rn && right < pageCount; j++) {
			right++;
		}
		if (i < ln && j == rn) {
			while (i < ln && right < pageCount) {
				right++;
				i++;
			}
		} else if (i == ln && j < rn) {
			while (j < rn && left > 1) {
				left--;
				j++;
			}
		}
		//
		// Create range.
		var range = this.element.find(".ui-pager-range");
		range.empty();
		for (var i = left; i <= right; i++) {
			var rangeBtn = this.fields.rangeTpl.replace(/\{\{pageNum\}\}/ig, i);
			rangeBtn = $(rangeBtn);
			range.append(rangeBtn);
			rangeBtn.click(this.fields.btnClickHandler);
			rangeBtn.data("pageNum", i);
			//
			// Callback.
			this._trigger("onButtonCreated", null, [ rangeBtn ]);
			if (i == pageNum) {
				rangeBtn.attr("disabled", "disabled");
				rangeBtn.removeClass("ui-state-hover");
				rangeBtn.removeClass("ui-state-focus");
				rangeBtn.addClass("ui-state-disabled");
			}
		}
	}
};

$.widget("lioxa.pager", _pager);
