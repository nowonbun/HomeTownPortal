app.controller("admin", [ '$scope', '_ws', '_loader', function($scope, _ws, _loader) {
	_loader.show();
	_ws.send("admin", "init", null, function(data) {
		$scope.cards = JSON.parse(data);
		_loader.hide();
	});
} ]);