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
	<link href="./css/vendor/mdb.min.css" rel="stylesheet">
	<link href="//maxcdn.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css" rel="stylesheet">
	<link href="//netdna.bootstrapcdn.com/font-awesome/3.2.1/css/font-awesome.css" rel="stylesheet">
	<link href="//cdn.datatables.net/1.10.18/css/jquery.dataTables.min.css" rel="stylesheet">
	<link href="//cdn.datatables.net/responsive/2.2.2/css/responsive.dataTables.min.css" rel="stylesheet">
	<link href="//cdn.datatables.net/1.10.16/css/dataTables.bootstrap4.min.css" rel="stylesheet">
	<link href="//cdn.datatables.net/buttons/1.5.2/css/buttons.dataTables.min.css" rel="stylesheet">
	<link href="//cdn.datatables.net/select/1.2.6/css/select.dataTables.min.css" rel="stylesheet">
	<link href="./css/vendor/sb-admin.css" rel="stylesheet">
	<link href="./css/common.css" rel="stylesheet">
	<link href="./css/shadow.css" rel="stylesheet">
	<link href="./css/size/1extrasmall.css" rel="stylesheet">
	<link href="./css/size/2small.css" rel="stylesheet">
	<link href="./css/size/3medium.css" rel="stylesheet">
	<link href="./css/size/4large.css" rel="stylesheet">
	<link href="./css/size/5extralarge.css" rel="stylesheet">
</head>
<body class="fixed-nav sticky-footer bg-dark" id="page-top">
	<!-- loading -->
	<div class="loader-loc" loader ng-show="isLoader"></div>
	<div class="notification-zone"></div>
	<!-- Navigation-->
	<nav class="navbar navbar-expand-lg navbar-dark bg-dark fixed-top" id="mainNav">
    	<a class="navbar-brand" ng-click="menu(true,'card','./views/card.tpl.jsp','');">
    		<i class="fa fa-th"></i>
		</a>
		<a class="system-title text-shadow-1" ng-click="menu(false,'main','./views/main.tpl.jsp','./#!/');">Public Portal System</a>
    	<!-- Breadcrumbs-->
		<ul id="breadcrumb" navigation ng-controller="navigate"></ul>
		<button id="navitoggler" class="navbar-toggler navbar-toggler-right" type="button" data-toggle="collapse" data-target="#navbarResponsive" aria-controls="navbarResponsive" aria-expanded="false" aria-label="Toggle navigation">
			<span class="navbar-toggler-icon"></span>
		</button>
    	<div class="collapse navbar-collapse" id="navbarResponsive">
			<ul class="navbar-nav ml-auto">
				<li class="nav-item">
					<a class="nav-link" ng-click="profileEdit();">
						<i class="fa fa-fw fa-user"></i>Profile
					</a>
				</li>
				<li class="nav-item">
					<a class="nav-link" data-toggle="modal" data-target="#logoutModal">
						<i class="fa fa-fw fa-sign-out"></i>Logout
					</a>
				</li>
			</ul>
		</div>
	</nav>
	<div class="content-wrapper">
		<div class="container-fluid" ng-view ng-show="isController"></div>
	</div>
	<div class="modal fade" id="logoutModal" tabindex="-1" role="dialog" aria-labelledby="logoutModalLabel" aria-hidden="true">
		<div class="modal-dialog" role="document">
			<div class="modal-content">
				<div class="modal-header">
					<h5 class="modal-title" id="exampleModalLabel">Ready to Leave?</h5>
					<button class="close" type="button" data-dismiss="modal" aria-label="Close">
						<span aria-hidden="true"><i class="fa fa-close"></i></span>
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
	<div id="extendModal"></div>
	<div id="menuFrame"></div>
	</body>
	<script src="//code.jquery.com/jquery-3.2.1.min.js" type="text/javascript"></script>
	<script src="//cdnjs.cloudflare.com/ajax/libs/angular.js/1.6.5/angular.min.js" type="text/javascript"></script>
	<script src="//cdnjs.cloudflare.com/ajax/libs/angular.js/1.6.5/angular-route.min.js" type="text/javascript"></script>
	<script src="//cdnjs.cloudflare.com/ajax/libs/angular.js/1.6.5/angular-sanitize.min.js" type="text/javascript"></script>
	
	<!-- script src="//cdnjs.cloudflare.com/ajax/libs/popper.js/1.12.9/umd/popper.min.js" type="text/javascript"></script-->
	<!-- script src="//cdnjs.cloudflare.com/ajax/libs/mdbootstrap/4.5.2/js/mdb.min.js" type="text/javascript"></script-->
	<script src="./js/vendor/mdb.min.js" type="text/javascript"></script>
	<!-- script src="//maxcdn.bootstrapcdn.com/bootstrap/4.0.0/js/bootstrap.min.js" type="text/javascript"></script-->
	<!-- script src="//cdnjs.cloudflare.com/ajax/libs/jquery-easing/1.4.1/jquery.easing.min.js" type="text/javascript"></script-->
	<!-- script src="//cdnjs.cloudflare.com/ajax/libs/Chart.js/2.7.1/Chart.min.js" type="text/javascript"></script-->
	<script src="//cdn.datatables.net/1.10.18/js/jquery.dataTables.min.js" type="text/javascript"></script>
	<script src="//cdn.datatables.net/responsive/2.2.1/js/dataTables.responsive.min.js" type="text/javascript"></script>
	<script src="//cdn.datatables.net/1.10.16/js/dataTables.bootstrap4.min.js" type="text/javascript"></script>
	<script src="//cdn.datatables.net/select/1.2.6/js/dataTables.select.min.js" type="text/javascript"></script>
	<!-- script src="./js/dataTables.editor.js" type="text/javascript"></script-->
	<!-- script src="//cdnjs.cloudflare.com/ajax/libs/jquery-cookie/1.4.1/jquery.cookie.min.js"></script-->
	<script src="./js/vendor/sb-admin.js" type="text/javascript"></script>
	<!-- script src="./js/vendor/sb-admin-datatables.js" type="text/javascript"></script-->
	
	<script src="./js/common.js" type="text/javascript"></script>
	<script src="./js/main.js" type="text/javascript"></script>
</html>
