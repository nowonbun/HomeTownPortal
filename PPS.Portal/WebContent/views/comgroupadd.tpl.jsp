<%@page import="authentication.AuthServlet"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%AuthServlet.auth(request, response);%>
<div class="modal fade top" id="comgroupModal" tabindex="-1" role="dialog" style="display: none;" aria-hidden="true">
    <div class="modal-dialog modal-full-height modal-top modal-notify modal-pps max-page" role="document">
        <div class="modal-content" >
        	<div class="modal-header">
			    <p class="heading lead">Company & Group Addition</p>
			    <button type="button" class="close" data-dismiss="modal" aria-label="Close">
			        <span aria-hidden="true" class="white-text"><i class="fa fa-close"></i></span>
			    </button>
			</div>
			<div class="modal-body">
				hello world
			</div>
			<div class="modal-footer">
			    <button type="button" class="btn btn-success float-right" ng-click="apply();">Apply</button>
			</div>
        </div>
    </div>
</div>
