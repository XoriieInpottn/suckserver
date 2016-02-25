<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<div id="dialog-match" title="文字匹配">
	<fieldset>
		<label for="match-var" style="display:block;margin-bottom:5px">*对象</label>
		<input type="text" id="match-var" name="var" 
				placeholder="此部分不能为空" style="display:block;width:100%" />
	</fieldset>
	<fieldset>
		<label for="match-path" style="display:block;margin-bottom:5px">路径</label> 
		<input type="text" class="form-control" id="match-path" name="path" style="display:block;width:100%"/>
	</fieldset>
	<fieldset>
		<label for="match-attr" style="display:block;margin-bottom:5px">属性</label> 
		<input type="text" id="match-attr" name="attr" style="display:block;width:100%" />
	</fieldset>
</div>
