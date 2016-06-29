/**
 * 
 */

var _formdialogs = {
	/**
	 * 
	 */
	onSubmit : null,
	onCancel : null,
	options : {
		onSubmit : null,
		onCancel : null
	},
	/**
	 * 
	 */
	_create : function() {
		this.onSubmit = this.options.onSubmit;
		this.onCancel = this.options.onCancel;
	},
	/**
	 * 
	 * @param name
	 * @param title
	 * @param fields
	 */
	addDialog : function(name, title, fields) {
		var that = this;
		var dialog = $("<div id=\"dialog-" + name + "\" title=\"" + title + "\"></div>");
		this.element.append(dialog);
		dialog.data("name", name);
		for (var i = 0; i < fields.length; i++) {
			var field = fields[i];
			var fieldset = $("<fieldset></fieldset>");
			dialog.append(fieldset);
			fieldset.data("field", field);
			var label = $("<label style=\"display:block;margin-bottom:5px\"></label>");
			fieldset.append(label);
			var input = $("<input type=\"text\" style=\"display:block;width:100%\" />");
			fieldset.append(input);
			input.attr("name", field.name);
			if (field.tips && field.tips.length > 0) {
				input.attr("placeholder", field.tips);
			}
		}
		dialog.dialog({
			autoOpen : false,
			height : 400,
			width : 500,
			modal : true,
			show : {
				effect : "blind",
				duration : 200
			},
			buttons : {
				"OK" : function() {
					var dialog = $(this);
					var fieldValues = that._checkFields(dialog.find("fieldset"));
					if (fieldValues == null) {
						return;
					}
					that._trigger("onSubmit", null, [ dialog.data("name"), fieldValues ]);
					dialog.dialog("close");
				},
				"Cancel" : function() {
					$(this).dialog("close");
				}
			},
			close : function() {
				var dialog = $(this);
				that._resetFieldsets(dialog.find("fieldset"));
				that._trigger("onCancel", null, [ dialog.data("name") ]);
			}
		}).keydown(function(e) {
			if (e.which == 13) {
				e.preventDefault();
				$(this).parent().find("button:eq(1)").click();
			}
		});
		this._resetFieldsets(dialog.find("fieldset"));
	},
	/**
	 * 
	 * @param fieldsets
	 */
	_resetFieldsets : function(fieldsets) {
		var that = this;
		fieldsets.each(function(index, fieldset) {
			fieldset = $(fieldset);
			var field = fieldset.data("field");
			var labelHtml = that._makeLabelHtml(field);
			fieldset.find("label").html(labelHtml);
			fieldset.find("input[name=\"" + field.name + "\"]").val("");
		});
	},
	/**
	 * 
	 * @param fieldsets
	 * @returns
	 */
	_checkFields : function(fieldsets) {
		var that = this;
		var ok = true;
		var fieldValues = {};
		fieldsets.each(function(index, fieldset) {
			fieldset = $(fieldset);
			var field = fieldset.data("field");
			var value = fieldset.find("input[name=\"" + field.name + "\"]").val();
			fieldValues[field.name] = value;
			if (field.essential && value.length == 0) {
				var errorHtml = that._makeErrorLabelHtml(field, "cannot be empty.");
				fieldset.find("label").html(errorHtml);
				ok = false;
				return;
			}
			var okHtml = that._makeOkLabelHtml(field);
			fieldset.find("label").html(okHtml);
		});
		return ok ? fieldValues : null;
	},
	/**
	 * 
	 * @param field
	 * @returns
	 */
	_makeLabelHtml : function(field) {
		return field.essential ? field.name + "<span style=\"color:red\">*</span>" : field.name;
	},
	/**
	 * 
	 * @param field
	 * @returns {String}
	 */
	_makeOkLabelHtml : function(field) {
		var content = "<span style=\"color:green\">" + field.name;
		if (field.essential) {
			content += "*</span>";
		} else {
			content += "</span>";
		}
		return content;
	},
	/**
	 * 
	 * @param field
	 * @param errorInfo
	 * @returns {String}
	 */
	_makeErrorLabelHtml : function(field, errorInfo) {
		return "<span style=\"color:red\">" + field.name + " " + errorInfo + "</span>";
	},
	/**
	 * 
	 * @param name
	 */
	openDialog : function(name, fieldValues) {
		var dialog = this._getDialog(name);
		if (fieldValues) {
			//
			// Fill values to the empty form.
			dialog.find("fieldset").each(function(index, fieldset) {
				fieldset = $(fieldset);
				var field = fieldset.data("field");
				if (fieldValues[field.name] == undefined) {
					return;
				}
				var input = fieldset.find("input[name=\"" + field.name + "\"]");
				input.val(fieldValues[field.name]);
			});
		}
		dialog.dialog("open");
	},
	_getDialog : function(name) {
		return $("#dialog-" + name);
	}
};

$.widget("lioxa.formdialogs", _formdialogs);
