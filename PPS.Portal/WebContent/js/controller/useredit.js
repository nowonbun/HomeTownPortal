app.controller("useredit", [ '$scope', '_ws', '_loader', function($scope, _ws, _loader) {
	_loader.controller.hide();
	_ws.message("usermanagement", "initEdit", function(data) {
		_loader.controller.show();
	});
	_ws.send("usermanagement", "initEdit");
} ]);