<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<div id="dialog-table" title="设置表 ">
	<fieldset>
		<label for="table-name">表名称</label>
		<input type="text" name="name" id="table-name" style="width:70%;margin-left:10px"/>
	</fieldset>
	<fieldset>
		<label class="control-label">是否覆盖</label> 
		<label class="checkbox-inline"> 
			<input type="radio" name="overlap" id="overlap1" value="true" checked style="margin-left:10px"> 是
		</label>
		<label class="checkbox-inline"> 
		 	<input type="radio" name="overlap" id="overlap2" value="false"> 否
		</label>
	</fieldset>
</div>
