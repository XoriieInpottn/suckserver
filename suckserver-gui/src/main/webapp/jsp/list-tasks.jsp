<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Suckserver-爬虫任务列表</title>
</head>
<body>
	<table>
		<thead>
			<tr>
				<td>任务号</td>
				<td>任务名称</td>
				<td>类型</td>
				<td>开始时间</td>
				<td>结束时间</td>
				<td>状态</td>
			</tr>
		</thead>
		<tbody>
			<s:iterator value="taskInfos" id="taskInfos">
				<tr>
					<td><s:property value="task.id" /></td>
					<td><s:if test="task.status == 0">
							<a href="<s:url value="/" />"><s:property value="task.name" /></a>
						</s:if>
						<s:else>
							<s:property value="task.name" />
						</s:else></td>
					<td><s:property value="task.type" /></td>
					<td><s:date format="yyyy-MM-dd hh:mm:ss" name="task.startTime" /></td>
					<td><s:date format="yyyy-MM-dd hh:mm:ss" name="task.endTime" /></td>
					<td><s:if test="taskStat != null">
							<s:property value="taskStat.successCount" />
							<s:property value="taskStat.errorCount" />
						</s:if> <s:else>
							<s:if test="task.status == -1">
						异常
						</s:if>
							<s:elseif test="task.status == 2">
						手动停止
						</s:elseif>
							<s:else>
							已完成
							</s:else>
						</s:else></td>
				</tr>
			</s:iterator>
		</tbody>
	</table>
</body>
</html>