app.controller("userdelete", [ '$scope', '_ws', '_loader', function($scope, _ws, _loader) {
	_loader.controller.hide();
	_ws.message("usermanagement", "initDelete", function(data) {
		_loader.controller.show();
	});
	_ws.send("usermanagement", "initDelete");
} ]);