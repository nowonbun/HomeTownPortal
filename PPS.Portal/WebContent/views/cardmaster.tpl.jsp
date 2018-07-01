<%@page import="authentication.AuthServlet"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%AuthServlet.auth(request, response);%>
<div class="modal fade top" id="cardMasterModal" tabindex="-1" role="dialog" style="display: none;" aria-hidden="true">
    <div class="modal-dialog modal-full-height modal-top modal-notify modal-pps max-page" role="document">
        <div class="modal-content" >
        	<div class="modal-header">
			    <p class="heading lead">Card Master</p>
			    <button type="button" class="close" data-dismiss="modal" aria-label="Close">
			        <span aria-hidden="true" class="white-text">×</span>
			    </button>
			</div>
			<div class="modal-body">
				<div class="row">
					<div class="offset-sm-1 col-sm-4">
						<div class="md-form form-group pps-group">
							<input type="text" class="form-control pps-input" id="code" ng-model="code" disabled> 
							<label for="code" class="pps-label" id="code_lavel">Code</label>
						</div>
					</div>
					<div class="offset-sm-1 col-sm-4">
						<div class="md-form form-group pps-group">
							<input type="text" class="form-control pps-input" id="name" ng-model="name" disabled> 
							<label for="name" class="pps-label" id="name_lavel">Name</label>
						</div>
					</div>
					<div class="offset-sm-1 col-sm-4">
						<div class="md-form form-group pps-group">
							<input type="text" class="form-control pps-input" id="step" ng-model="step" disabled> 
							<label for="step" class="pps-label" id="step_lavel">Step</label>
						</div>
					</div>
					<div class="offset-sm-1 col-sm-4">
						<div class="md-form form-group pps-group">
							<input type="text" class="form-control pps-input" id="sequence" ng-model="sequence" disabled> 
							<label for="sequence" class="pps-label" id="sequence_lavel">Sequence</label>
						</div>
					</div>
				</div>
				<div class="row">
					<div class="offset-sm-1 col-sm-10">
						<div class="md-form form-group pps-group">
							<input type="text" class="form-control pps-input" id="description" ng-model="description"> 
							<label for="description" class="pps-label" id="description_lavel">Description</label>
						</div>
					</div>
				</div>
				<div class="row">
					<div class="offset-sm-1 col-sm-10">
						<select class="mdb-select" id="cardtype" ng-model="cardtype">
							<option value="" disabled selected>Choose your option</option>
							<option ng-repeat="option in cardtypeList" value="{{option.value}}">{{option.name}}</option>
						</select> <label>Group</label>
					</div>
				</div>
			</div>
			<div class="modal-footer">
			    <button type="button" class="btn btn-success float-right" ng-click="apply();">Apply</button>
			</div>
        </div>
    </div>
</div>
