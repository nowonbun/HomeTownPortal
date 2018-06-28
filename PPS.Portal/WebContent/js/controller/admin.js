app.controller("admin", [ '$scope', '_ws', '_loader', '_notification', function($scope, _ws, _loader, _notification) {
	_loader.controller.hide();
	_ws.message("admin", "init", function(data) {
		// TODO: This program is if the internet is connected,
		// we can not work it.
		$scope.cards = JSON.parse(data);
		_loader.controller.show();
	});
	_ws.message("admin", "permission", function(data) {
		var node = JSON.parse(data);
		_notification(node.type, node.msg);
		location.href="./#!/";
	});
	_ws.send("admin", "init");
} ]);