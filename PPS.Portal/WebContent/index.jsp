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
				<div id="testCtrl">
				{{test}}
				</div>
				<div class="card-columns" >
					<!-- Example Social Card-->
					<%
						for (int i = 0; i < 0; i++) {
					%>
					<div class="card my-card">
						<a href="#">
							<div class="card-header">
								<img class="card-img-top img-fluid w-100"
									src="./contents/no_photo.png">
								<!-- i class="fa fa-fw fa-list card-header-icon"></i-->
							</div>
							<hr class="my-0">
							<div class="card-body text-center bg-success">
								<h6 class="card-title mb-1">Household</h6>
							</div>
						</a>
					</div>
					<%
						}
					%>
				</div>
			</div>
		</div>
	</div>
</div>
<jsp:include page="./common/middle.jsp"></jsp:include>
<script type="text/javascript" src="./js/index.js"></script>
<jsp:include page="./common/footer.jsp"></jsp:include>
