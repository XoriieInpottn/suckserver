<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<div id="dialog-load" title="Set load">
	<fieldset>
		<label style="display:block;margin-bottom:5px">Url</label>
		<input type="text" id="load-url" placeholder="This part cannot be none" name="url" style="display:block;width:100%" /> 
	</fieldset>
	<fieldset>
		<label  style="display:block;margin-bottom:5px">Max-page</label>
		<input type="text" id="load-maxPage" placeholder="0" name="max-page" style="display:block;width:100%"/> 
	</fieldset>
	<fieldset>
		<label  style="display:block;margin-bottom:5px">Page</label>
		<input type="text" id="load-page" name="page" style="display:block;width:100%"/> 
	</fieldset>
	<fieldset>
		<label  style="display:block;margin-bottom:5px">From</label>
		<input type="text" id="load-from" name="from" style="display:block;width:100%"/> 
	</fieldset>
	<fieldset>
		<label  style="display:block;margin-bottom:5px">To</label>
		<input type="text" id="load-to" name="to" style="display:block;width:100%"/> 
	</fieldset>
	<fieldset>
		<label  style="display:block;margin-bottom:5px">Step</label>
		<input type="text" id="load-step" name="step" style="display:block;width:100%"/> 
	</fieldset>
</div>
