app.controller("admin", [ '$scope', '_ws', '_notification', function($scope, _ws, _notification) {
	_ws.message("admin", "init", function(data) {
		$scope.cards = JSON.parse(data);
	});
	_ws.send("admin", "init");
} ]);