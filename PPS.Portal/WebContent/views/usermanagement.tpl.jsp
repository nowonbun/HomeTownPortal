<%@page import="authentication.AuthServlet"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%AuthServlet.auth(request, response);%>
<div class="row">
	<div class="col-12 text-center">
		<div class="mdl-shadow--2dp pps-page max-page-1200">
			<div class="pps-title box-shadow-1 text-shadow-1">
				<span>User Management</span>
			</div>
			<div class="pps-contents box-shadow-0">
				<div>
					<div class="row">
						<div class="col align-self-end text-align-right">
							<button type="button" id="addbtn" class="btn btn-success custom-button" ng-click="userAdd();">Add</button>
							<button type="button" id="editbtn" class="btn btn-warning custom-button" disabled ng-click="userEdit();">Edit</button>
							<button type="button" id="deletebtn" class="btn btn-danger custom-button" disabled data-toggle="modal" data-target="#deleteModal">Delete</button>
						</div>
					</div>
				</div>
				<div class="pps-body">
					<div class="row">
						<div class="col-12">
							<table id="tablelist">
								<thead>
									<tr>
										<th width='1%;'></th>
										<th width='14%;'>ID</th>
										<th width='15%;'>Given name</th>
										<th width='15%;'>Name</th>
										<th width='15%;'>Nick name</th>
										<th width='15%;'>Company</th>
										<th width='10%;'>Group</th>
										<th width='10%;'>ID Type</th>
										<th width='5%;'>Active</th>
									</tr>
								</thead>
								<tbody></tbody>
							</table>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>
<div class="modal fade" id="deleteModal" tabindex="-1" role="dialog" aria-labelledby="deleteModalLabel" aria-hidden="true">
	<div class="modal-dialog" role="document">
		<div class="modal-content">
			<div class="modal-header">
				<button class="close" type="button" data-dismiss="modal" aria-label="Close">
					<span aria-hidden="true"><i class="fa fa-close"></i></span>
				</button>
			</div>
			<div class="modal-body">Are you sure want to delete?</div>
			<div class="modal-footer">
				<button class="btn btn-secondary" type="button" data-dismiss="modal">Cancel</button>
				<button class="btn btn-danger " type="button" ng-click="userDelete();">Delete</button>
			</div>
		</div>
	</div>
</div>