app.controller("main", [ '$scope', '_ws', '_loader', function($scope, _ws, _loader) {
	_loader.controller.hide();
	_ws.message("main", "init", function(data) {
		_loader.controller.show();
	});
	_ws.send("main", "init");
} ]);