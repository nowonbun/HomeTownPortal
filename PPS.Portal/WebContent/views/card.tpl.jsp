<%@page import="authentication.AuthServlet"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%AuthServlet.auth(request, response);%>
<div class="container-fluid">
	<div class="row">
		<div class="col-md-12">
			<div class="card-grid" >
				<a ng-repeat="card in cards" ng-href="{{card.href}}" class="card-item box-shadow-0 margin-5px {{card.typeHeaderClass}}" style="background: {{card.background}}">
					<div class="card-header" ng-bind-html="card.header"></div>
					<div class="card-body mdl-card__actions {{card.border}}" ng-bind-html="card.body"></div>
				</a>
			</div>
		</div>
	</div>
</div>