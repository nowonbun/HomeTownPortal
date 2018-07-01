<%@page import="authentication.AuthServlet"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%AuthServlet.auth(request, response);%>
<style>
#tablelist_wrapper select {
	display: inherit !important;
}

#tablelist {
	width: 100%;
}

#tablelist_wrapper {
	padding: 10px;
}

.dataTables_length, .dataTables_filter {
	text-align: left !important;
}

.data-item-column {
	white-space: nowrap;
}

.dtr-details {
	width: 100%;
}

.dataTable tbody td {
	cursor: pointer;
}

.text-align-right {
	text-align: right;
}

.custom-button {
	width: 80px;
	padding: 10px;
}
</style>
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
							<button type="button" id="deletebtn" class="btn btn-danger custom-button" disabled  ng-click="userDelete();">Delete</button>
						</div>
					</div>
				</div>
				<div class="pps-body">
					<div class="row">
						<div class="col-12">
							<table id="tablelist">
								<colgroup>
									<col width='1%;'></col>
									<col width='14%;'></col>
									<col width='15%;'></col>
									<col width='15%;'></col>
									<col width='15%;'></col>
									<col width='15%;'></col>
									<col width='10%;'></col>
									<col width='10%;'></col>
									<col width='5%;'></col>
								</colgroup>
								<thead>
									<tr>
										<th></th>
										<th>ID</th>
										<th>Given name</th>
										<th>Name</th>
										<th>Nick name</th>
										<th>Company</th>
										<th>Group</th>
										<th>ID Type</th>
										<th>Active</th>
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