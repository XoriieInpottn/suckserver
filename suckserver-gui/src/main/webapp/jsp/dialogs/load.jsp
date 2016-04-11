<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<div id="dialog-load" title="Set load">
	<fieldset>
		<label style="display:block;margin-bottom:5px">Url</label>
		<input type="text" id="load-url" name="url" style="display:block;width:100%" /> 
	</fieldset>
	<fieldset>
		<label  style="display:block;margin-bottom:5px">MaxPage</label>
		<input type="text" id="load-maxPage" placeholder="1" name="maxPage" style="display:block;width:100%"/> 
	</fieldset>
	<fieldset>
		<label  style="display:block;margin-bottom:5px">NextPath</label>
		<input type="text" id="load-nextPath" name="nextPath" style="display:block;width:100%"/> 
	</fieldset>
</div>
