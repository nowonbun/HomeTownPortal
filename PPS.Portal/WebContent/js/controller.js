app.controller("navigate", [ '$scope', '_ws', function($scope, _ws) {
	_ws.message(function(msg) {
		var node = JSON.parse(msg.data);
		if (node.key === "navigate") {
			$scope.navi = JSON.parse(node.data);
			$scope.$apply();
		}
	});
} ]);

app.controller("main", [ '$scope', '_ws', function($scope, _ws) {
	_ws.message(function(msg) {
		var node = JSON.parse(msg.data);
		if (node.key === "login") {
			if (node.data === "NG") {
				location.href = "./Logout";
			}
		}
	});
} ]);

app.controller("card", [ '$scope', '_ws', function($scope, _ws) {
	_ws.message(function(msg) {
		var node = JSON.parse(msg.data);
		if (node.key === "card") {
			$scope.cards = JSON.parse(node.data);
			$scope.$apply();
		}
	});

	_ws.send("card", "initialize");
} ]);

app.controller("admin", [ '$scope', '_ws', function($scope, _ws) {
	_ws.message(function(msg) {
		var node = JSON.parse(msg.data);
		if (node.key === "admin") {
			// TODO: This program is if the internet is connected,
			// we can not work it.
			console.log(node.data);
			$scope.cards = JSON.parse(node.data);
			// console.log("TEST");
			$scope.$apply();
		}
	});

	_ws.send("admin", "initialize");
} ]);

app.controller("profile", [ '$scope', '_ws', function($scope, _ws) {
	_ws.message(function(msg) {
		var node = JSON.parse(msg.data);
		if (node.key === "profile") {
			$scope.$apply();
		}
	});
	_ws.send("profile", "initialize");
} ]);
