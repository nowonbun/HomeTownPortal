<%@page import="authentication.AuthServlet"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%AuthServlet.auth(request, response);%>
<div class="row">
	<div class="col-12 text-center">
		<div class="mdl-shadow--2dp pps-page max-page-1200">
			<div class="pps-title box-shadow-1 text-shadow-1">
				<span>{{title}}</span>
			</div>
			<div class="pps-contents box-shadow-0">
				<div>
					<div class="row">
						<div class="col align-self-end text-align-right">
							<button type="button" id="editbtn" class="btn btn-warning custom-button" disabled ng-click="roleedit();">Edit</button>
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
										<th width='40%;'>Role Code</th>
										<th width='59%;'>Name</th>
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