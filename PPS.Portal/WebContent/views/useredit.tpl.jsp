<%@page import="authentication.AuthServlet"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
	AuthServlet.auth(request, response);
%>
<link href="./css/views/profile.css" rel="stylesheet">
<div class="row">
	<div class="col-12 text-center">
		<div class="mdl-shadow--2dp pps-page max-page-1200">
			<div class="pps-title box-shadow-1 text-shadow-1">User Edit</div>
			<div class="pps-contents box-shadow-0">
				<div class="pps-body">
					<div class="row">
						<div class="col-md-3 col-sm-4">
							<div class="row">
								<div class="col-12 pps-group">
									<div class="pps-image">
										<img ng-src="{{img_url}}" class="img-thumbnail pps-thumnail">
									</div>
									<form class="md-form">
										<div class="file-field">
											<div class="d-flex justify-content-center">
												<div class="btn btn-primary btn-sm float-left">
													<span>Choose file</span> 
													<input type="file" id="img_file" ng-model="img_file" onchange="angular.element('#img_file').scope().fileupload()">
												</div>
											</div>
										</div>
									</form>
								</div>
							</div>
						</div>
						<div class="col-md-9 col-sm-8">
							<div class="row">
								<div class="col-12 pps-group">
									<div class="md-form form-group pps-group">
										<input type="text" class="form-control pps-input" id="given_name" ng-model="given_name"> 
										<label for="given_name" class="pps-label" id="given_name_label">Given name</label>
									</div>
								</div>
							</div>
							<div class="row">
								<div class="col-12">
									<div class="md-form form-group pps-group">
										<input type="text" class="form-control pps-input" id="name" ng-model="name"> <label for="name" class="pps-label" id="name_label">Name</label>
									</div>
								</div>
							</div>
							<div class="row">
								<div class="col-12">
									<div class="md-form form-group pps-group">
										<input type="text" class="form-control pps-input" id="nick_name" ng-model="nick_name"> 
										<label for="nick_name" class="pps-label" id="nick_name_label">Nick name</label>
									</div>
								</div>
							</div>
						</div>
					</div>
					<div class="row" ng-show="canModifyPassword">
						<div class="offset-sm-1 col-sm-10">
							<div class="md-form form-group pps-group">
								<input type="password" class="form-control pps-input" id="current_password" ng-model="current_password"> 
								<label for="current_password" class="pps-label" id="current_password_label">Current Password</label>
							</div>
						</div>
					</div>
					<div class="row" ng-show="canModifyPassword">
						<div class="offset-sm-1 col-sm-5">
							<div class="md-form form-group pps-group">
								<input type="password" class="form-control pps-input" id="password" ng-model="password" ng-change="checkPassword()"> 
								<label for="password" class="pps-label" id="password_label">Password</label>
							</div>
						</div>
						<div class="col-sm-5">
							<div class="md-form form-group pps-group">
								<input type="password" class="form-control pps-input" id="password_confirm" ng-model="password_confirm" ng-change="checkPassword()"> 
								<label for="password_confirm" class="pps-label" id="password_confirm_label">Passwordconfirm</label>
							</div>
						</div>
					</div>
					<div class="row" ng-show="isPasswordError">
						<div class="offset-sm-1 col-sm-10">
							<label class="error-label">Please input the text of 'Password is incorrect'</label>
						</div>
					</div>
					<div class="row select-row" ng-show="canModifyCompany">
						<div class="offset-sm-1 col-sm-10">
							<select class="mdb-select" id="company">
								<option value="" disabled selected>Choose your option</option>
								<option ng-repeat="option in companyList" value="{{option.value}}">{{option.name}}</option>
							</select> 
							<label>Company</label>
						</div>
					</div>
					<div class="row select-row" ng-show="canModifyGroup">
						<div class="offset-sm-1 col-sm-10">
							<select class="mdb-select" id="group">
								<option value="" disabled selected>Choose your option</option>
								<option ng-repeat="option in groupList" value="{{option.value}}">{{option.name}}</option>
							</select> 
							<label>Group</label>
						</div>
					</div>
					<div class="row" style="margin-top: 20px;">
						<div class="col-12">
							<button type="button" class="btn btn-success float-right" ng-click="apply();">Apply</button>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>