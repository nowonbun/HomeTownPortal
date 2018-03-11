<%@page import="authentication.AuthServlet"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
	AuthServlet.auth(request, response);
%>
<jsp:include page="./common/header.jsp"></jsp:include>
<div class="content-wrapper" ng-controller="main">
	<div class="container-fluid">
		<!-- Breadcrumbs-->
		<ol class="breadcrumb">
			<li class="breadcrumb-item"><a href="#">Dashboard</a></li>
			<li class="breadcrumb-item active">My Dashboard</li>
		</ol>
		<div class="row">
			<div class="col-md-12">
				<div class="card-grid" >
				
					<div class="card-item card-image mdl-card mdl-shadow--2dp" style="background: url('https://getmdl.io/assets/demos/image_card.jpg') center / cover;">
					  <div class="mdl-card__title mdl-card--expand"></div>
					  <div class="card-body mdl-card__actions">
					    <span class="card-image__body">Image.jpg</span>
					  </div>
					</div>
					
					<div class="card-item card-event mdl-card mdl-shadow--2dp" style="background: #3E4EB8">
					  <div class="mdl-card__title mdl-card--expand">
					    <h4>
					      Featured event:<br>
					      May 24, 2016<br>
					      7-11pm
					    </h4>
					  </div>
					  <div class="card-body mdl-card__actions mdl-card--border">
					    <a class="mdl-button mdl-button--colored mdl-js-button mdl-js-ripple-effect">
					      Add to Calendar
					    </a>
					    <div class="mdl-layout-spacer"></div>
					    <i class="material-icons">event</i>
					  </div>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>
<jsp:include page="./common/footer.jsp"></jsp:include>
