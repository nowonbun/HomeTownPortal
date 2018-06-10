<%@page import="authentication.AuthServlet"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
	AuthServlet.auth(request, response);
%>
<style>
#tableTest_wrapper select {
	display: inherit !important;
}

#tableTest {
	width: 100%;
}

#tableTest_wrapper {
	padding: 10px;
}
</style>
<table id="tableTest">
	<thead>
		<tr>
			<th>ID</th>
			<th>Given name</th>
			<th>Name</th>
			<th>Nick name</th>
			<th>Company</th>
			<th>Group</th>
			<th>ID Type</th>
			<th>Active</th>
		</tr>
	</thead>
	<tbody>
		<tr ng-repeat="item in list">
			<td>{{item.id}}</td>
			<td>{{item.given}}</td>
			<td>{{item.name}}</td>
			<td>{{item.nick}}</td>
			<td>{{item.company}}</td>
			<td>{{item.group}}</td>
			<td>{{item.type}}</td>
			<td>{{item.active}}</td>
		</tr>
	</tbody>
</table>