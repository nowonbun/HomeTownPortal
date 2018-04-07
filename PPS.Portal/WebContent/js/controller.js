app.run([ '$rootScope', '_ws', '_loader', function($rootScope, _ws, _loader) {
	_ws.message("login", function(data) {
		if (data === "NG") {
			location.href = "./Logout";
		}
	});
} ]);

app.controller("navigate", [ '$scope', '_ws', function($scope, _ws) {
	_ws.message("navigate", function(data) {
		$scope.navi = JSON.parse(data);
	});
} ]);

app.controller("card", [ '$scope', '_ws', '_loader', function($scope, _ws, _loader) {
	_ws.message("card", function(data) {
		$scope.cards = JSON.parse(data);
	});
	_ws.send("card", "initialize");
} ]);

app.controller("application", [ '$scope', '_ws', function($scope, _ws) {
	_ws.message("application", function(msg) {
		//var node = JSON.parse(msg.data);
		console.log(msg);
	});
	_ws.send("application", "initialize");
} ]);

app.controller("admin", [ '$scope', '_ws', function($scope, _ws) {
	_ws.message("admin", function(data) {
		// TODO: This program is if the internet is connected,
		// we can not work it.
		console.log(data);
		$scope.cards = JSON.parse(data);
	});
	_ws.send("admin", "initialize");
} ]);

app.controller("profile", [ '$scope', '_ws', function($scope, _ws) {
	_ws.message("profile", function(data) {
	});
	_ws.send("profile", "initialize");
} ]);
