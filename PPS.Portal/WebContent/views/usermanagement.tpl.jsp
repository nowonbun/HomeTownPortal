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

.dataTables_length, .dataTables_filter {
	text-align: left !important;
}

.data-item-column {
	white-space: nowrap;
}

.dtr-details {
	width: 100%;
}

.responsive-column {
	width: 1px!important;
}
</style>
<table id="tableTest">
	<thead>
		<tr>
			<th class="responsive-column"></th>
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
	<tbody></tbody>
</table>