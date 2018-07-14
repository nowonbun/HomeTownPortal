<%@page import="authentication.AuthServlet"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%AuthServlet.auth(request, response);%>
<style>
.mdb-select {
	
}
</style>
<div class="modal fade top" id="comgroupModal" tabindex="-1" role="dialog" style="display: none;" aria-hidden="true">
	<div class="modal-dialog modal-full-height modal-top modal-notify modal-pps max-page" role="document">
		<div class="modal-content">
			<div class="modal-header">
				<p class="heading lead">Company & Group Addition</p>
				<button type="button" class="close" data-dismiss="modal" aria-label="Close">
					<span aria-hidden="true" class="white-text"><i class="fa fa-close"></i></span>
				</button>
			</div>
			<div class="modal-body">
				<div class="row select-row">
					<div class="offset-sm-1 col-sm-10">
						<select class="mdb-select" id="company">
							<option value="" disabled selected>Choose your option</option>
							<option ng-repeat="option in companyList" value="{{option.value}}">{{option.name}}</option>
						</select> <label>Company</label>
					</div>
				</div>
				<div class="row">
					<div class="offset-sm-1 col-sm-10">
						<div class="md-form form-group pps-group">
							<input type="text" class="form-control pps-input" id="companyIn" ng-model="companyIn" ng-change="dataCheck(true);">
						</div>
					</div>
				</div>
				<div class="row select-row">
					<div class="offset-sm-1 col-sm-10">
						<select class="mdb-select" id="group" ng-model="group">
							<option value="" disabled selected>Choose your option</option>
							<option ng-repeat="option in groupList" value="{{option.value}}">{{option.name}}</option>
						</select> <label>Group</label>
					</div>
				</div>
				<div class="row">
					<div class="offset-sm-1 col-sm-10">
						<div class="md-form form-group pps-group">
							<input type="text" class="form-control pps-input" id="groupIn" ng-model="groupIn" ng-change="dataCheck(false);">
						</div>
					</div>
				</div>
			</div>
			<div class="modal-footer">
				<button type="button" class="btn btn-success float-right" ng-disabled="applyView" ng-click="apply();">Apply</button>
			</div>
		</div>
	</div>
</div>
