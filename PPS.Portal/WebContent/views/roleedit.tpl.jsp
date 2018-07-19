<%@page import="authentication.AuthServlet"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
	AuthServlet.auth(request, response);
%>
<style>
.grid-table div {
	font-weight: bold;
	font-size: 15pt;
	text-align: center;
}

.browser-default {
	width: 100%;
	margin: 10px;
}

.row-button {
	height: 30px;
	padding: 0px;
}

.error-row {
	border: red 1px solid;
	box-shadow: 0px 1.1px 1px 1.2px rgba(255, 0, 0, 0.1), 0px 1.2px 2px 1.4px rgba(255, 0, 0, 0.1), 0px 1.4px 3px 1.6px rgba(255, 0, 0, 0.1);
}
</style>
<div class="modal fade top" id="roleEditModal" tabindex="-1" role="dialog" style="display: none;" aria-hidden="true">
	<div class="modal-dialog modal-full-height modal-top modal-notify modal-pps max-page" role="document">
		<div class="modal-content">
			<div class="modal-header">
				<p class="heading lead">{{title}}</p>
				<button type="button" class="close" data-dismiss="modal" aria-label="Close">
					<span aria-hidden="true" class="white-text"><i class="fa fa-close"></i></span>
				</button>
			</div>
			<div class="modal-body">
				<div class="row">
					<div class="col-12 text-center">
						<div class="mdl-shadow--2dp pps-page max-page-1200">
							<div class="pps-title box-shadow-1 text-shadow-1">
								<span>{{selectid}} : {{selectname}}</span>
							</div>
							<div class="pps-contents box-shadow-0">
								<div>
									<div class="row">
										<div class="col align-self-end text-align-right">
											<button type="button" id="" class="btn btn-success custom-button" ng-click="addRow(true);">Add</button>

										</div>
									</div>
								</div>
								<div class="pps-body box-shadow-0">
									<div class="row grid-table" style="margin: 0px">
										<div class="col-sm-12 col-md-6 col-lg-3">Company</div>
										<div class="col-sm-12 col-md-6 col-lg-3">Group</div>
										<div class="col-sm-12 col-md-6 col-lg-3">User</div>
										<div class="col-sm-12 col-md-6 col-lg-3"></div>
									</div>
									<div id="tableList"></div>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
			<div class="modal-footer">
				<button type="button" class="btn btn-success float-right" ng-click="roleSave();">Save</button>
			</div>
		</div>
	</div>
</div>



