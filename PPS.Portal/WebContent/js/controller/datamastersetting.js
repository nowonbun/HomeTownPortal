app.controller("datamastersetting", [ '$scope', '_ws', '_loader', function($scope, _ws) {
	_loader.controller.hide();
	_ws.message("datamastersetting", "init", function(data) {
		// TODO: This program is if the internet is connected,
		// we can not work it.
		$scope.cards = JSON.parse(data);
		_loader.controller.show();
	});
	_ws.send("datamastersetting", "init");
} ]);