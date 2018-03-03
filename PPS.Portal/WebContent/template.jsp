<%@page import="authentication.AuthServlet"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
	AuthServlet.auth(request, response);
%>
<jsp:include page="./common/header.jsp"></jsp:include>
<link href="./css/index.css" rel="stylesheet">
<div class="content-wrapper">
	<div class="container-fluid">
		<!-- Breadcrumbs-->
		<ol class="breadcrumb">
			<li class="breadcrumb-item"><a href="#">Dashboard</a></li>
			<li class="breadcrumb-item active">My Dashboard</li>
		</ol>
		<div class="row">
			<div class="col-md-12">
				Hello world
			</div>
		</div>
	</div>
</div>
<jsp:include page="./common/middle.jsp"></jsp:include>
<script type="text/javascript" src="./js/template.js"></script>
<jsp:include page="./common/footer.jsp"></jsp:include>
