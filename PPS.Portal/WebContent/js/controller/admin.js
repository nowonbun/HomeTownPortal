app.controller("admin", [ '$scope', '_ws', '_notification', function($scope, _ws, _notification) {
	_ws.message("admin", "init", function(data) {
		$scope.cards = JSON.parse(data);
	});
	_ws.message("admin", "permission", function(data) {
		var node = JSON.parse(data);
		_notification(node.type, node.msg);
		location.href="./#!/";
	});
	_ws.send("admin", "init");
} ]);