<%@page import="authentication.AuthServlet"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
	AuthServlet.auth(request, response);
%>
<jsp:include page="./common/header.jsp"></jsp:include>
<link href="./css/index.css" rel="stylesheet">
<div class="content-wrapper" ng-controller="main">
	<div class="container-fluid">
		<!-- Breadcrumbs-->
		<ol class="breadcrumb">
			<li class="breadcrumb-item"><a href="#">Dashboard</a></li>
			<li class="breadcrumb-item active">My Dashboard</li>
		</ol>
		<div class="row">
			<div class="col-md-12">
				<!-- button ng-click="clickTest()">test</button-->
				<p ng-bind="datatest"></p>
			</div>
		</div>
	</div>
</div>
<script>

</script>
<jsp:include page="./common/footer.jsp"></jsp:include>
