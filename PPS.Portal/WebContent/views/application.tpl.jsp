<%@page import="authentication.AuthServlet"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%AuthServlet.auth(request, response);%>
<link href="./css/views/application.css" rel="stylesheet">
<div class="row">
    <div class="col-12 text-center">
    	<div class="mdl-shadow--2dp pps-page max-page-1200">
    		<div class="pps-title z-depth-2">Application</div>
    		<div class="pps-contents z-depth-2">
    			<div class="pps-header">Infomation</div>
    			<div class="pps-body">
    				<div class="row">
    					<div class="col-12">
    						<div class="md-form form-group pps-group">
				                <input type="text" class="form-control pps-input" id="given_name" ng-model="given_name">
				                <label for="given_name" class="pps-label" id="given_name_label">Given name</label>
				            </div>
						</div>
					</div>
					<div class="row">
						<div class="col-12">
				            <div class="md-form form-group pps-group">
				                <input type="text" class="form-control pps-input" id="name" ng-model="name">
				                <label for="name" class="pps-label" id="name_label">Name</label>
				            </div>
    					</div>
    				</div>
    				<div class="row">
						<div class="col-12">
				            <div class="md-form form-group pps-group">
				                <input type="text" class="form-control pps-input" id="nick_name" ng-model="nick_name">
				                <label for="name" class="nick_name" id="nick_name_label">Nick name</label>
				            </div>
    					</div>
    				</div>
    				<div class="row">
    					<div class="col-12">
    						<div class="pps-group">
    							<div class="pps-image">
    								<img ng-src="{{img_url}}" class="img-thumbnail">
    							</div>
    							<form>
								    <div class="md-form pps-group-nomargin">
								        <div class="file-field">
								            <div class="btn btn-primary btn-sm float-left">
								                <span>Choose file</span>
								                <input type="file">
								            </div>
								            <div class="file-path-wrapper">
								                <input class="file-path validate" type="text" placeholder="Upload your file">
								            </div>
								        </div>
								    </div>
								</form>
    						</div>
    					</div>
    				</div>
    				<div class="row">
    					<div class="col-12">
    						<div class="md-form">
							    <textarea id="commenct" class="md-textarea md-textarea-auto form-control" rows="1" ng-model="comment"></textarea>
							    <label for="commenct" class="pps-label" id="comment_label">Comment</label>
							</div>
    					</div>
    				</div>
    				<div class="row">
						<div class="col-12">
				            <label class="pps-notice-label"> NOTICE : Please allow me a permission for will use this system.</label>
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
