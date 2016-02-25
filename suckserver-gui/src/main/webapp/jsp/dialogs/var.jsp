<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<div id="dialog-var" title="Set var"> 
	<fieldset>
		<label for="var-name"  style="display:block;margin-bottom:5px">Name</label>
        <input type="text" id="var-name" placeholder="This part cannot be none" name="name" style="display:block;width:100%">
	</fieldset>
	<fieldset>
		<label for="var-value" style="display:block;margin-bottom:5px">value</label> 
		<input type="text" id="var-value" name="value" style="display:block;width:100%">
	</fieldset>
</div>
