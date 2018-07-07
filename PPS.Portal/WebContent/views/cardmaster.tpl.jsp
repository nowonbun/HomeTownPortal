<%@page import="authentication.AuthServlet"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%AuthServlet.auth(request, response);%>
<style>
</style>
<div class="modal fade top" id="cardMasterModal" tabindex="-1" role="dialog" style="display: none;" aria-hidden="true">
    <div class="modal-dialog modal-full-height modal-top modal-notify modal-pps max-page" role="document">
        <div class="modal-content" >
        	<div class="modal-header">
			    <p class="heading lead">Card Master</p>
			    <button type="button" class="close" data-dismiss="modal" aria-label="Close">
			        <span aria-hidden="true" class="white-text"><i class="fa fa-close"></i></span>
			    </button>
			</div>
			<div class="modal-body">
				<div class="row">
					<div class="offset-sm-1 col-sm-4">
						<div class="md-form form-group pps-group">
							<input type="text" class="form-control pps-input" id="code" ng-model="code" disabled> 
							<label for="code" class="pps-label" id="code_label">Code</label>
						</div>
					</div>
					<div class="offset-sm-1 col-sm-4">
						<div class="md-form form-group pps-group">
							<input type="text" class="form-control pps-input" id="step" ng-model="step" disabled> 
							<label for="step" class="pps-label" id="step_label">Step</label>
						</div>
					</div>
				</div>
				<div class="row">
					<div class="offset-sm-1 col-sm-4">
						<div class="md-form form-group pps-group">
							<input type="text" class="form-control pps-input" id="controller" ng-model="controller" disabled> 
							<label for="controller" class="pps-label" id="controler_label">Controller</label>
						</div>
					</div>
					<div class="offset-sm-1 col-sm-4">
						<div class="md-form form-group pps-group">
							<input type="text" class="form-control pps-input" id="template" ng-model="template" disabled> 
							<label for="template" class="pps-label" id="template_label">Template</label>
						</div>
					</div>
				</div>
				<div class="row">
					<div class="offset-sm-1 col-sm-4">
						<div class="md-form form-group pps-group">
							<input type="text" class="form-control pps-input" id="name" ng-model="name" disabled> 
							<label for="name" class="pps-label" id="name_label">Name</label>
						</div>
					</div>
					<div class="offset-sm-1 col-sm-4">
						<div class="md-form form-group pps-group">
							<input type="text" class="form-control pps-input" id="sequence" ng-model="sequence"> 
							<label for="sequence" class="pps-label" id="sequence_label">Sequence</label>
						</div>
					</div>
				</div>
				<div class="row">
					<div class="offset-sm-1 col-sm-10">
						<div class="md-form form-group pps-group">
							<input type="text" class="form-control pps-input" id="title" ng-model="title"> 
							<label for="title" class="pps-label" id="title_label">Title</label>
						</div>
					</div>
				</div>
				<div class="row">
					<div class="offset-sm-1 col-sm-10">
						<div class="md-form form-group pps-group">
							<input type="text" class="form-control pps-input" id="description" ng-model="description"> 
							<label for="description" class="pps-label" id="description_label">Description</label>
						</div>
					</div>
				</div>
				<div class="row select-row">
					<div class="offset-sm-1 col-sm-10">
						<select class="mdb-select" id="cardtype" ng-model="cardtype">
							<option value="" disabled selected>Choose your option</option>
							<option ng-repeat="option in cardtypeList" value="{{option.value}}">{{option.name}}</option>
						</select> <label>Card Type</label>
					</div>
				</div>
				<div class="row" ng-show="isImageType">
					<div class="col align-self-center pps-group">
						<div class="pps-image" style="max-height: inherit;max-width: inherit;">
							<img ng-src="{{img_url}}" class="img-thumbnail pps-thumnail" style="max-width: 300px;max-height: 200px;">
							<label class="image-legend">Maximum size : 60KB</label>
						</div>
						<form class="md-form">
							<div class="file-field">
								<div class="d-flex justify-content-center">
									<div class="btn btn-primary btn-sm float-left">
										<span>Choose file</span> <input type="file" id="img_file" ng-model="img_file" onchange="angular.element('#img_file').scope().fileupload()">
									</div>
								</div>
							</div>
						</form>
					</div>
				</div>
				<div class="row" ng-show="isEventType">
					<div class="offset-sm-1 col-sm-4">
						<div class="md-form form-group pps-group">
							<input type="text" class="form-control pps-input" id="icon" ng-model="icon"> 
							<label for="icon" class="pps-label" id="icon_label">Icon</label>
						</div>
					</div>
					<div class="offset-sm-1 col-sm-4">
						<div class="md-form form-group pps-group">
							<input type="text" class="form-control pps-input" id="color" ng-model="color"> 
							<label for="color" class="pps-label" id="color_label">Color</label>
						</div>
					</div>
				</div>
			</div>
			<div class="modal-footer">
			    <button type="button" class="btn btn-success float-right" ng-click="apply();">Apply</button>
			</div>
        </div>
    </div>
</div>
