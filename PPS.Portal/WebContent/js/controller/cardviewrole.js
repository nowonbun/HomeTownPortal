app.controller("cardviewrole", [ '$scope', '_ws', '_loader', function($scope, _ws, _loader) {
	_loader.controller.hide();
	_ws.message("cardviewrole", "init", function(data) {
		_loader.controller.show();
	});
	_ws.send("cardviewrole", "init");
} ]);