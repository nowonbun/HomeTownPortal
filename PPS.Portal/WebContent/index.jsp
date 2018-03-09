<%@page import="authentication.AuthServlet"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
	AuthServlet.auth(request, response);
%>
<jsp:include page="./common/header.jsp"></jsp:include>
<style>
.demo-card-image.mdl-card {
  width: 256px;
  height: 256px;
  background: url('https://getmdl.io/assets/demos/image_card.jpg') center / cover;
}
.demo-card-image > .mdl-card__actions {
  height: 52px;
  padding: 16px;
  background: rgba(0, 0, 0, 0.2);
}
.demo-card-image__filename {
  color: #fff;
  font-size: 14px;
  font-weight: 500;
}
.demo-card-event.mdl-card {
  width: 256px;
  height: 256px;
  background: #3E4EB8;
}
.demo-card-event > .mdl-card__actions {
  border-color: rgba(255, 255, 255, 0.2);
}
.demo-card-event > .mdl-card__title {
  align-items: flex-start;
}
.demo-card-event > .mdl-card__title > h4 {
  margin-top: 0;
}
.demo-card-event > .mdl-card__actions {
  display: flex;
  box-sizing:border-box;
  align-items: center;
}
.demo-card-event > .mdl-card__actions > .material-icons {
  padding-right: 10px;
}
.demo-card-event > .mdl-card__title,
.demo-card-event > .mdl-card__actions,
.demo-card-event > .mdl-card__actions > .mdl-button {
  color: #fff;
}
</style>
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
				<div id="testCtrl">
				{{test}}
				</div>
				<div class="card-columns" >
				
				
					<div class="demo-card-image mdl-card mdl-shadow--2dp">
					  <div class="mdl-card__title mdl-card--expand"></div>
					  <div class="mdl-card__actions">
					    <span class="demo-card-image__filename">Image.jpg</span>
					  </div>
					</div>
					
					<div class="demo-card-event mdl-card mdl-shadow--2dp">
					  <div class="mdl-card__title mdl-card--expand">
					    <h4>
					      Featured event:<br>
					      May 24, 2016<br>
					      7-11pm
					    </h4>
					  </div>
					  <div class="mdl-card__actions mdl-card--border">
					    <a class="mdl-button mdl-button--colored mdl-js-button mdl-js-ripple-effect">
					      Add to Calendar
					    </a>
					    <div class="mdl-layout-spacer"></div>
					    <i class="material-icons">event</i>
					  </div>
					</div>
					<!-- Example Social Card-->
					<%
						//for (int i = 0; i < 0; i++) {
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
						//}
					%>
				</div>
			</div>
		</div>
	</div>
</div>
<jsp:include page="./common/footer.jsp"></jsp:include>
