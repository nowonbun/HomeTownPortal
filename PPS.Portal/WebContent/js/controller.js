app.run([ '$rootScope', '_ws', '_loader', function($rootScope, _ws, _loader) {
	_ws.message("login", "init", function(data) {
		if (data === "NG") {
			location.href = "./Logout";
		}
	});
} ]);

app.controller("navigate", [ '$scope', '_ws', function($scope, _ws) {
	_ws.message("navigate", "init", function(data) {
		$scope.navi = JSON.parse(data);
	});
} ]);

app.controller("card", [ '$scope', '_ws', '_loader', function($scope, _ws, _loader) {
	_ws.message("card", "init", function(data) {
		$scope.cards = JSON.parse(data);
	});
	_ws.send("card", "init");
} ]);

app.controller("application", [ '$scope', '_ws', function($scope, _ws) {
	_ws.message("application", "init", function(msg) {
		// var node = JSON.parse(msg.data);
		console.log(msg);
	});
	_ws.send("application", "init");
} ]);

app.controller("admin", [ '$scope', '_ws', function($scope, _ws) {
	_ws.message("admin", "init", function(data) {
		// TODO: This program is if the internet is connected,
		// we can not work it.
		console.log(data);
		$scope.cards = JSON.parse(data);
	});
	_ws.send("admin", "init");
} ]);

app.controller("profile", [ '$scope', '_ws', function($scope, _ws) {
	_ws.message("profile", "init", function(data) {
	});
	_ws.send("profile", "init");
} ]);
