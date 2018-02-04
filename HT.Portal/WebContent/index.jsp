<%@page import="HT.Portal.Servlet.InstanceServlet"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
	InstanceServlet.get().index(request, response);
%>
<jsp:include page="./common/header.jsp"></jsp:include>
<link href="./css/index.css" rel="stylesheet">
<%//=InstanceServlet.get().tile(request, response) %>
<div class="content-wrapper">
	<div class="container-fluid">
	<!-- Breadcrumbs-->
		<ol class="breadcrumb">
			<li class="breadcrumb-item">
				<a href="#">Dashboard</a>
			</li>
			<li class="breadcrumb-item active">My Dashboard</li>
		</ol>	
		<div class="row">
			<div class="col-md-12">

				<div class="card-columns">
				<!-- Example Social Card-->
					<div class="card mb-3">
						<a href="#">
							<img class="card-img-top img-fluid w-100" src="./contents/no_photo.png" alt="">
							<hr class="my-0">
							<div class="card-body text-center">
								<h4 class="card-title mb-1">Household</h4>
							</div>
						</a>
					</div>
				</div>

			</div>
		</div>
	</div>
</div>
<jsp:include page="./common/middle.jsp"></jsp:include>
<jsp:include page="./common/footer.jsp"></jsp:include>
