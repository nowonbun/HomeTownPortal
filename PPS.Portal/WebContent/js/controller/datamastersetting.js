app.controller("datamastersetting", [ '$scope', '_ws', '_loader', '_notification', function($scope, _ws, _loader, _notification) {
	_ws.message("datamastersetting", "init", function(data) {
		$scope.cards = JSON.parse(data);
	});
	_ws.send("datamastersetting", "init");
} ]);