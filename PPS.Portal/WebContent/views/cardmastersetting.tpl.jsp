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
				<span>Card Master Management</span>
			</div>
			<div class="pps-contents box-shadow-0">
				<div>
					<div class="row">
						<div class="col align-self-end text-align-right">
							<button type="button" id="editbtn" class="btn btn-warning custom-button" disabled ng-click="cardEdit();">Edit</button>
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
										<th width='10%;'>Code</th>
										<th width='15%;'>Name</th>
										<th width='10%;'>Title</th>
										<th width='15%;'>Description</th>
										<th width='5%;'>Image</th>
										<th width='8%;'>Icon</th>
										<th width='8%;'>Color</th>
										<th width='13%;'>Type</th>
										<th width='10%;'>Step</th>
										<th width='5%;'>Seq</th>
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