<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<div id="dialog-column" title="Set column ">
	<fieldset>
		<label for="column-name">Name</label>
		<input type="text" name="name" id="column-name" style="margin-left:10px;width:70%"/>
	</fieldset>
	<fieldset>
		<label>Type</label> 
		<input type="text" class="form-control" id="column-type"
				placeholder="text"  name="type" style="margin-left:17px;width:70%"/>
	</fieldset>
</div>
