app.controller("comgroupsetting", [ '$scope', '_ws', '_loader', function($scope, _ws, _loader) {
	_loader.controller.hide();
	_ws.message("comgroupsetting", "init", function(data) {
		_loader.controller.show();
	});
	_ws.send("comgroupsetting", "init");
} ]);