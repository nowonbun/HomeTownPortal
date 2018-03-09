var app = angular.module('app', []);
app.service('_ws', function() {
	var socket = new WebSocket("ws://localhost:8080/Portal/socket");
	var delegate = {
		open : null,
		close : null,
		error : null,
		message : null
	};
	socket.onopen = function(msg) {
		if (delegate.open != null) {
			delegate.open.call(this, msg);
		}
	};
	socket.onclose = function(msg) {
		if (delegate.close != null) {
			delegate.close.call(this, msg);
		}
	};
	socket.onerror = function(msg) {
		if (delegate.error != null) {
			delegate.error.call(this, msg);
		}
	};
	socket.onmessage = function(msg) {
		if (delegate.message != null) {
			delegate.message.call(this, JSON.parse(msg.data));
		}
	};
	sendNode = function(node) {
		if (socket.readyState === 1) {
			socket.send(node);
		} else {
			setTimeout(function() {
				sendNode(node);
			}, 1000);
		}
	}
	function define(key, func) {
		if (func !== null && func !== undefined && typeof func === "function") {
			delegate[key] = func;
			return;
		}
		console.error("It's not defined because not function method.");
	}
	this.open = function(func) {
		define("open", func);
	};
	this.close = function(func) {
		define("close", func);
	};
	this.error = function(func) {
		define("error", func);
	}
	this.message = function(func) {
		define("message", func);
	};
	this.send = function(key, data) {
		sendNode(JSON.stringify({
			key : key,
			data : data
		}));
	}
});
app.controller('main', [ '$scope', '_ws', function($scope, _ws) {
	$scope.datatest = "hello world";

	_ws.message(function(msg) {
		$scope.datatest = msg.data;
		$scope.$apply();
	});

	_ws.send("index", "hello world");

	$scope.clickTest = function() {
		$scope.datatest = $scope.datatest;
	}
} ]);

app.controller('aaabbbccc', [ '$scope', function($scope) {

} ]);
app.directive("testaaa", function() {
	return {
		restrict : "C",
		// templateUrl : "test.html"
		template : "I was made in a directive constructor!"
	};
});