<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<div id="dialog-validate" title="Set type">
	<fieldset>
		<label style="display:block;margin-bottom:5px">path</label>
		<input type="text" id="validate-path" name="path" style="display:block;width:100%" /> 
	</fieldset>
	<fieldset>
		<label  style="display:block;margin-bottom:5px">valuePath</label>
		<input type="text" id="validate-valuePath" placeholder="this part cannot be null." name="value" style="display:block;width:100%"/> 
	</fieldset>
	<fieldset>
		<label  style="display:block;margin-bottom:5px">delay</label>
		<input type="text" id="validate-delay" name="delay" style="display:block;width:100%"/> 
	</fieldset>
</div>
