<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<div id="dialog-scan" title="Set scan">
	<fieldset>
		<label for="scan-name" style="display:block;margin-bottom:5px">Name</label>
		<input type="text" id="scan-name" name="name" 
				placeholder="This part cannot be none" style="display:block;width:100%" />
	</fieldset>
	<fieldset>
		<label for="scan-field" style="display:block;margin-bottom:5px">Field</label> 
		<input type="text" class="form-control" id="scan-field" name="field" style="display:block;width:100%"/>
	</fieldset>
	<fieldset>
		<label for="scan-var" style="display:block;margin-bottom:5px">Var</label> 
		<input type="text" id="scan-var" name="var" style="display:block;width:100%" />
	</fieldset>
	<fieldset>
		<label for="scan-count" style="display:block;magin-bottom:5px">Count</label>
		<input type="text" id="scan-count" name="count"  style="display:block;width:100%" />
	</fieldset>
</div>