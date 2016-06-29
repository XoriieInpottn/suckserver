<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<div id="dialog-task" title="Set task ">
	<fieldset>
		<label for="task-name" style="display:block;margin-bottom:5px">Name</label>
		<input type="text" name="name" id="task-name" style="width:100%;display:block" placeholder="This part cannot be none"/>
	</fieldset>
	<fieldset>
		<label  style="display:block;margin-bottom:5px">Disallow</label>
		<input type="text" id="task-disallow" name="disallow" style="display:block;width:100%"/>
	</fieldset>
	<fieldset>
		<label  style="display:block;margin-bottom:5px">SwitchUA</label>
		<input type="text" id="task-switchUA" name="switchUA" style="display:block;width:100%"/>
	</fieldset>
</div>
