<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<div id="dialog-select" title="Set select">
	
	<fieldset>
		<label class="control-label">IsJSON</label> 
		<label class="checkbox-inline"> 
			<input type="radio" name="isJSON" id="isJSON1" value="false" style="margin-left:10px" checked> false
		</label>
		<label class="checkbox-inline"> 
		 	<input type="radio" name="isJSON" id="isJSON2" value="true"> true
		</label>
	</fieldset>
	<fieldset>
		<label  style="display:block;margin-bottom:5px">Path</label>
		<input type="text" id="select-path" name="path" style="display:block;width:100%"/>
	</fieldset>
	<fieldset>
		<label  style="display:block;margin-bottom:5px" for="select-field">Field</label>
		<input type="text" name="field" id="select-Field" style="display:block;width:100%"/>
	</fieldset>
</div>