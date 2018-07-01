app.controller("actionrole", [ '$scope', '_ws', '_loader', function($scope, _ws, _loader) {
	_loader.controller.hide();
	_ws.message("actionrole", "init", function(data) {
		_loader.controller.show();
	});
	_ws.send("actionrole", "init");
} ]);