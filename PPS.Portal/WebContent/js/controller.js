app.controller("navigate",[ '$scope', '_ws', function($scope, _ws) {
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

app.controller("card", [ '$scope', '$sanitize', '$sce', '_ws',
		function($scope, $sanitize, $sce, _ws) {
			_ws.message(function(msg) {
				var node = JSON.parse(msg.data);
				if (node.key === "card") {
					$scope.cards = JSON.parse(node.data);
					$scope.$apply();
				}
			});

			_ws.send("card", "initialize");
		} ]);

app.controller("admin", [ '$scope', '$sanitize', '$sce', '_ws',
		function($scope, $sanitize, $sce, _ws) {
			_ws.message(function(msg) {
				var node = JSON.parse(msg.data);
				if (node.key === "admin") {
					console.log(node.data)
				}
			});

			_ws.send("admin", "initialize");
		} ]);
