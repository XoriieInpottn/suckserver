<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<div id="dialog-table" title="Set table ">
	<fieldset>
		<label for="table-name">Name</label>
		<input type="text" name="name" id="table-name" style="width:70%;margin-left:10px"/>
	</fieldset>
	<fieldset>
		<label class="control-label">Overlapped</label> 
		<label class="checkbox-inline"> 
			<input type="radio" name="overlap" id="overlap1" value="true" checked style="margin-left:10px"> true
		</label>
		<label class="checkbox-inline"> 
		 	<input type="radio" name="overlap" id="overlap2" value="false"> false
		</label>
	</fieldset>
</div>
