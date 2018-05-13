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
    					<div class="col-2">
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
    					<div class="col-10">
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
    					</div>
    				</div>
    				
    				
    				<div class="row">
    					<div class="col-12">
    						<div class="md-form">
							    <textarea id="commenct" class="md-textarea md-textarea-auto form-control" rows="5" ng-model="comment"></textarea>
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
