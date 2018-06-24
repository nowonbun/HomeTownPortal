app.controller("admin", [ '$scope', '_ws', '_loader', function($scope, _ws, _loader) {
	_loader.controller.hide();
	_ws.message("admin", "init", function(data) {
		// TODO: This program is if the internet is connected,
		// we can not work it.
		$scope.cards = JSON.parse(data);
		_loader.controller.show();
	});
	_ws.send("admin", "init");
} ]);