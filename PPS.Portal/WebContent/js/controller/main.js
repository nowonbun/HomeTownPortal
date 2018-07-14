app.controller("main", [ '$scope', '_ws', '_loader', function($scope, _ws, _loader) {
	_loader.controller.hide();
	_ws.send("main", "init", null, function(data) {
		_loader.controller.show();
	});
} ]);