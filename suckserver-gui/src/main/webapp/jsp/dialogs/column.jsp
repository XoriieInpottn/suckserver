<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<div id="dialog-column" title="Set column ">
	<fieldset>
		<label for="column-name"  style="display:block;margin-bottom:5px">Name</label>
		<input type="text" name="name" id="column-name" style="display:block;width:100%"/>
	</fieldset>
	<fieldset>
		<label  style="display:block;margin-bottom:5px">Type</label> 
		<input type="text" class="form-control" id="column-type"
				placeholder="text"  name="type" style="display:block;width:100%"/>
	</fieldset>
</div>
