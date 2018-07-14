app.controller("datamastersetting", [ '$scope', '_ws', '_loader', '_notification', function($scope, _ws, _loader, _notification) {
	_loader.show();
	_ws.send("datamastersetting", "init", null, function(data) {
		$scope.cards = JSON.parse(data);
		_loader.hide();
	});
} ]);