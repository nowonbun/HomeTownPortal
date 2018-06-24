app.controller("card", [ '$scope', '_ws', '_loader', function($scope, _ws, _loader) {
	_loader.controller.hide();
	_ws.message("card", "init", function(data) {
		$scope.cards = JSON.parse(data);
		_loader.controller.show();
	});
	_ws.send("card", "init");
} ]);