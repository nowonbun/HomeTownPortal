app.controller("cardmaster", [ '$scope', '_ws', '_loader', function($scope, _ws, _loader) {
	_loader.controller.hide();
	_ws.message("cardmastersetting", "initEdit", function(data) {
		_loader.controller.show();
		$("#cardMasterModal").modal("show");
	});
	_ws.send("cardmastersetting", "initEdit");
} ]);