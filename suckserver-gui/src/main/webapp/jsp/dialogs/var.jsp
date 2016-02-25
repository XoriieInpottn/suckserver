<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<div id="dialog-var" title="临时存储"> 
	<fieldset>
		<label for="var-name"  style="display:block;margin-bottom:5px">*名称</label>
        <input type="text" id="var-name" placeholder="该部分不能为空" name="name" style="display:block;width:100%">
	</fieldset>
	<fieldset>
		<label for="var-value" style="display:block;margin-bottom:5px">值</label> 
		<input type="text" id="var-value" name="value" style="display:block;width:100%">
	</fieldset>
</div>
