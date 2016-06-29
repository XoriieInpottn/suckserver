<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<div id="dialog-print" title="Set print">
	<fieldset>
		<label for="print-content" style="display:block;margin-bottom:5px" >Content</label> 
	    <input type="text" id="print-content" name="content" placeholder="This part cannot be none" style="display:block;width:100%">
	</fieldset>
	<fieldset>
		<label for="print-html" style="display:block;margin-bottom:5px" >Html</label> 
	    <input type="text" id="print-html" name="html" placeholder="false" style="display:block;width:100%">
	</fieldset>
</div>