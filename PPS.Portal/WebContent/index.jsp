<%@page import="authentication.AuthServlet"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%AuthServlet.auth(request, response);%>
<!DOCTYPE html>
<html ng-app="app">
<head>
	<meta charset="utf-8">
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<meta name="viewport" content="width=device-width, initial-scale=1">
	<meta name="description" content="">
	<meta name="author" content="">
	<title>Portal</title>
	<link href="./favicon.ico" rel="icon">
	<!-- link href="//maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css" rel="stylesheet"-->
	<link href="./css/vendor/mdb.min.css" rel="stylesheet">
	<link href="//maxcdn.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css" rel="stylesheet">
	<link href="//netdna.bootstrapcdn.com/font-awesome/3.2.1/css/font-awesome.css" rel="stylesheet">
	
	<link href="//cdn.datatables.net/1.10.16/css/dataTables.bootstrap4.min.css" rel="stylesheet">
	<link href="//fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">
	<link href="//code.getmdl.io/1.3.0/material.indigo-pink.min.css" rel="stylesheet">
	<link href="./css/vendor/sb-admin.css" rel="stylesheet">	
	<link href="./css/common.css" rel="stylesheet">
	<link href="./css/size/1extrasmall.css" rel="stylesheet">
	<link href="./css/size/2small.css" rel="stylesheet">
	<link href="./css/size/3medium.css" rel="stylesheet">
	<link href="./css/size/4large.css" rel="stylesheet">
	<link href="./css/size/5extralarge.css" rel="stylesheet">
</head>
<body class="fixed-nav sticky-footer bg-dark" id="page-top" ng-controller="main">
	<!-- Navigation-->
	<nav class="navbar navbar-expand-lg navbar-dark bg-dark fixed-top" id="mainNav">
    	<a class="navbar-brand" href="./#!/">Home Portal</a>
    	<!-- Breadcrumbs-->
		<ul id="breadcrumb" ng-controller="navigate">
			<li ng-repeat="n in navi"><a href="{{n.url}}">{{n.name}}</a></li>
		</ul>
		<button class="navbar-toggler navbar-toggler-right" type="button" data-toggle="collapse" data-target="#navbarResponsive" aria-controls="navbarResponsive" aria-expanded="false" aria-label="Toggle navigation">
			<span class="navbar-toggler-icon"></span>
		</button>
    	<div class="collapse navbar-collapse" id="navbarResponsive">
			<ul class="navbar-nav ml-auto">
				<li class="nav-item dropdown">
					<a class="nav-link dropdown-toggle mr-lg-2" id="messagesDropdown" href="#" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
						<i class="fa fa-fw fa-envelope"></i>
						<span class="d-lg-none">Messages
							<span class="badge badge-pill badge-primary">12 New</span>
						</span>
						<span class="indicator text-primary d-none d-lg-block">
							<i class="fa fa-fw fa-circle"></i>
						</span>
					</a>
					<div class="dropdown-menu dropdown-menu-right-position" aria-labelledby="messagesDropdown">
						<h6 class="dropdown-header">New Messages:</h6>
						<div class="dropdown-divider"></div>
						<a class="dropdown-item" href="#">
							<strong>David Miller</strong>
							<span class="small float-right text-muted">11:21 AM</span>
							<div class="dropdown-message small">Hey there! This new version of SB Admin is pretty awesome! These messages clip off when they reach the end of the box so they don't overflow over to the sides!</div>
						</a>
						<div class="dropdown-divider"></div>
						<a class="dropdown-item" href="#">
							<strong>Jane Smith</strong>
							<span class="small float-right text-muted">11:21 AM</span>
							<div class="dropdown-message small">I was wondering if you could meet for an appointment at 3:00 instead of 4:00. Thanks!</div>
						</a>
						<div class="dropdown-divider"></div>
						<a class="dropdown-item" href="#">
							<strong>John Doe</strong>
							<span class="small float-right text-muted">11:21 AM</span>
							<div class="dropdown-message small">I've sent the final files over to you for review. When you're able to sign off of them let me know and we can discuss distribution.</div>
						</a>
						<div class="dropdown-divider"></div>
						<a class="dropdown-item small" href="#">View all messages</a>
					</div>
				</li>
				<li class="nav-item dropdown">
					<a class="nav-link dropdown-toggle mr-lg-2" id="alertsDropdown" href="#" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
						<i class="fa fa-fw fa-bell"></i>
						<span class="d-lg-none">Alerts
							<span class="badge badge-pill badge-warning">6 New</span>
						</span>
						<span class="indicator text-warning d-none d-lg-block">
							<i class="fa fa-fw fa-circle"></i>
						</span>
					</a>
					<div class="dropdown-menu dropdown-menu-right-position" aria-labelledby="alertsDropdown">
						<h6 class="dropdown-header">New Alerts:</h6>
						<div class="dropdown-divider"></div>
						<a class="dropdown-item" href="#">
							<span class="text-success">
								<strong><i class="fa fa-long-arrow-up fa-fw"></i>Status Update</strong>
							</span>
							<span class="small float-right text-muted">11:21 AM</span>
							<div class="dropdown-message small">This is an automated server response message. All systems are online.</div>
						</a>
						<div class="dropdown-divider"></div>
						<a class="dropdown-item" href="#">
							<span class="text-danger">
								<strong><i class="fa fa-long-arrow-down fa-fw"></i>Status Update</strong>
							</span>
							<span class="small float-right text-muted">11:21 AM</span>
							<div class="dropdown-message small">This is an automated server response message. All systems are online.</div>
						</a>
						<div class="dropdown-divider"></div>
						<a class="dropdown-item" href="#">
							<span class="text-success">
								<strong><i class="fa fa-long-arrow-up fa-fw"></i>Status Update</strong>
							</span>
							<span class="small float-right text-muted">11:21 AM</span>
							<div class="dropdown-message small">This is an automated server response message. All systems are online.</div>
						</a>
						<div class="dropdown-divider"></div>
            			<a class="dropdown-item small" href="#">View all alerts</a>
					</div>
				</li>
				<li class="nav-item">
					<a class="nav-link" data-toggle="modal" data-target="#exampleModal">
						<i class="fa fa-fw fa-sign-out"></i>Logout
					</a>
				</li>
			</ul>
		</div>
	</nav>
	<div class="content-wrapper" ng-view></div>
	<div class="modal fade" id="exampleModal" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
		<div class="modal-dialog" role="document">
			<div class="modal-content">
				<div class="modal-header">
					<h5 class="modal-title" id="exampleModalLabel">Ready to Leave?</h5>
					<button class="close" type="button" data-dismiss="modal" aria-label="Close">
						<span aria-hidden="true">×</span>
					</button>
				</div>
				<div class="modal-body">Select "Logout" below if you are ready to end your current session.</div>
				<div class="modal-footer">
					<button class="btn btn-secondary" type="button" data-dismiss="modal">Cancel</button>
					<a class="btn btn-primary" href="./Logout">Logout</a>
				</div>
			</div>
		</div>
	</div>
	</body>
	<script src="//code.jquery.com/jquery-3.2.1.min.js" type="text/javascript"></script>
	<script src="//cdnjs.cloudflare.com/ajax/libs/angular.js/1.6.5/angular.min.js" type="text/javascript"></script>
	<script src="//cdnjs.cloudflare.com/ajax/libs/angular.js/1.6.5/angular-route.min.js" type="text/javascript"></script>
	<script src="//cdnjs.cloudflare.com/ajax/libs/angular.js/1.6.5/angular-sanitize.min.js" type="text/javascript"></script>
	
	<script src="//cdnjs.cloudflare.com/ajax/libs/popper.js/1.12.9/umd/popper.min.js" type="text/javascript"></script>
	<script src="./js/vendor/mdb.min.js" type="text/javascript"></script>
	<!-- script src="//maxcdn.bootstrapcdn.com/bootstrap/4.0.0/js/bootstrap.min.js" type="text/javascript"></script-->
	<script src="//cdnjs.cloudflare.com/ajax/libs/jquery-easing/1.4.1/jquery.easing.min.js" type="text/javascript"></script>
	<script src="//cdnjs.cloudflare.com/ajax/libs/Chart.js/2.7.1/Chart.min.js" type="text/javascript"></script>
	<script src="//cdn.datatables.net/1.10.16/js/jquery.dataTables.min.js" type="text/javascript"></script>
	<script src="//cdn.datatables.net/1.10.16/js/dataTables.bootstrap4.min.js" type="text/javascript"></script>
	<script src="//code.getmdl.io/1.3.0/material.min.js" type="text/javascript"></script>
	<script src="//cdnjs.cloudflare.com/ajax/libs/jquery-cookie/1.4.1/jquery.cookie.min.js"></script>
	<script src="./js/vendor/sb-admin.js" type="text/javascript"></script>
	<script src="./js/vendor/sb-admin-datatables.js" type="text/javascript"></script>
	
	<script src="./js/common.js"></script>
	<script src="./js/app.js"></script>
	<script src="./js/service.js"></script>
	<script src="./js/controller.js"></script>
</html>
