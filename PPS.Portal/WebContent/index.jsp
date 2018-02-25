<%@page import="servlet.InstanceServlet"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
	InstanceServlet.get().index(request, response);
%>
<jsp:include page="./common/header.jsp"></jsp:include>
<link href="./css/index.css" rel="stylesheet">
<%
	//=InstanceServlet.get().tile(request, response)
%>
<div class="content-wrapper">
	<div class="container-fluid">
		<!-- Breadcrumbs-->
		<ol class="breadcrumb">
			<li class="breadcrumb-item"><a href="#">Dashboard</a></li>
			<li class="breadcrumb-item active">My Dashboard</li>
		</ol>
		<div class="row">
			<div class="col-md-12">
				<div ng-controller="testCtrl" id="testCtrl">
				{{test}}
				</div>
				<div class="card-columns" >
					<!-- Example Social Card-->
					<%
						for (int i = 0; i < 0; i++) {
					%>
					<div class="card my-card">
						<a href="#">
							<div class="card-header">
								<img class="card-img-top img-fluid w-100"
									src="./contents/no_photo.png">
								<!-- i class="fa fa-fw fa-list card-header-icon"></i-->
							</div>
							<hr class="my-0">
							<div class="card-body text-center bg-success">
								<h6 class="card-title mb-1">Household</h6>
							</div>
						</a>
					</div>
					<%
						}
					%>
				</div>
			</div>
		</div>
	</div>
</div>
<jsp:include page="./common/middle.jsp"></jsp:include>
<script type="text/javascript" src="./js/index.js"></script>
<script>
/*
angular.module('indexApp', ['ws'])
.config(function (wsProvider) {
  wsProvider.setUrl('ws://localhost:8080/Portal/index');
})
.controller('testCtrl', function ($scope, ws, $log) {
  ws.on('message', function (event) {
    $log.info('New message', event.data);
    $scope.test = event.data;
  });
  var a = {
			id : "test",
			url : "index",
			data : "data"
		};
		console.log(JSON.stringify(a));
  ws.send(JSON.stringify(a));
});*/
	/*var webSocket = new WebSocket("ws://localhost:8080/Portal/index");
	var app = angular.module('indexApp', []);
	this.send = function (message, callback) {
	    this.waitForConnection(function () {
	    	webSocket.send(message);
	        if (typeof callback !== 'undefined') {
	          callback();
	        }
	    }, 1000);
	};

	this.waitForConnection = function (callback, interval) {
	    if (webSocket.readyState === 1) {
	        callback();
	    } else {
	        var that = this;
	        setTimeout(function () {
	            that.waitForConnection(callback, interval);
	        }, interval);
	    }
	};
	app.controller("testCtrl", function($scope) {
		$scope.test = "test";
		
	});
	app.controller("testCtrl", function($scope) {
		$scope.test = "bbb";
	});
	webSocket.onmessage = function (message) {
		console.log(message.data);
		app.controller("testCtrl", function($scope) {
			$scope.test = message.data;
			debugger;
		});
	}
	var a = {
		id : "test",
		url : "index",
		data : "data"
	};
	console.log(JSON.stringify(a));
	send(JSON.stringify(a));*/
	/*
	app.controller("testCtrl", function($scope) {
		$scope.test = "test";
		var a = {
			id : "test",
			url : "index",
			data : "data"
		};
		console.log(JSON.stringify(a));
		send(JSON.stringify(a));
	});*/
	/*	.factory(
				'MyData',
				function($websocket) {
					debugger;
					var dataStream = $websocket('ws://localhost:8080/Portal/index');
					var collection = [];
					dataStream.onMessage(function(message) {
						collection.push(JSON.parse(message.data));
					});
					var a = {
						key : "test",
						url : "index",
						data : "data"
					};
					dataStream.send(JSON.stringify(a));
					return collection;
				}).controller('SomeController', function($scope, MyData) {
			//$scope.MyData = MyData;
		});*/
</script>
<jsp:include page="./common/footer.jsp"></jsp:include>
