<%@page import="servlet.InstanceServlet"%>
<%@page import="common.PropertyMap"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
//https://mdbootstrap.com/
//https://getmdl.io/
	InstanceServlet.get().login(request, response);
%>
<!DOCTYPE html>
<html>
<head>  
	<meta charset="utf-8">
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<meta name="viewport" content="width=device-width, initial-scale=1">
	<meta name="description" content="">
	<meta name="author" content="">
	<title>Hometown Portal</title>
	<link href="./favicon.ico" rel="icon" >
	<!-- link href="//maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css" rel="stylesheet"-->
	<link href="./css/mdb.min.css" rel="stylesheet">
	<link href="//maxcdn.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css" rel="stylesheet">
	<link href="//fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">
	<link href="//code.getmdl.io/1.3.0/material.indigo-pink.min.css" rel="stylesheet">
	<link href="./css/sb-admin.css" rel="stylesheet">	
	<link href="./css/common.css" rel="stylesheet">
	<link href="./css/login.css" rel="stylesheet">
</head>
<body class="bg-dark">
	<div class="notification-zone"></div>
	<div class="container">
		<div class="card card-login mx-auto mt-5 z-depth-5">
			<div class="card-header">Login</div>
			<div class="card-body">
				<form role="form" id="google_form" method="get" action="https://accounts.google.com/o/oauth2/auth">
					<input type="hidden" name="scope" value="https://www.googleapis.com/auth/plus.login https://www.googleapis.com/auth/plus.me" />
					<input type="hidden" name="client_id" value="<%=PropertyMap.getInstance().getProperty("googleApiConfig", "client_id")%>" />
					<input type="hidden" name="redirect_uri" value="<%=PropertyMap.getInstance().getProperty("googleApiConfig", "redirect_url")%>" />
					<input type="hidden" name="access_type" value="offline" />
					<input type="hidden" name="response_type" value="code" />
					<input type="hidden" name="approval_prompt" value="force" />
					<input type="image" class="loginBtn z-depth-1" src="./contents/loginBtn.png">
				</form>
				<div id="private_form" style="display:none;">
					<div>
						<div class="form-group">
							<div class="md-form">
							    <i class="fa fa-user prefix"></i>
							    <input type="text" id="pid" class="form-control">
							    <label for="pid">ID</label>
							</div>
							<div class="md-form">
							    <i class="fa fa-lock prefix"></i>
							    <input type="password" id="pwd" class="form-control validate">
							    <label for="pwd">Type your password</label>
							</div>
							<div class="login-error"></div>
							<button class="btn btn-primary btn-block" id="plogin">Login</button>
						</div>
					</div>
				</div>
				<div class="location-link" data-state="0">
					<a href="#" id="private_link" class="view-link">private login</a>
					<a href="#" id="google_link" class="view-link" style="display:none;">google login</a>
				</div>
			</div>
		</div>
	</div>
</body>
<script src="//code.jquery.com/jquery-3.2.1.min.js" type="text/javascript"></script>
<script src="//cdnjs.cloudflare.com/ajax/libs/popper.js/1.12.9/umd/popper.min.js" type="text/javascript"></script>
<!-- script src="//maxcdn.bootstrapcdn.com/bootstrap/4.0.0/js/bootstrap.min.js" type="text/javascript"></script-->
<script src="./js/mdb.min.js" type="text/javascript"></script>
<script src="//cdnjs.cloudflare.com/ajax/libs/jquery-easing/1.4.1/jquery.easing.min.js" type="text/javascript"></script>
<script src="//code.getmdl.io/1.3.0/material.min.js" type="text/javascript"></script>
<script src="./js/common.js" type="text/javascript"></script>
<script src="./js/login.js" type="text/javascript"></script>
</html>
