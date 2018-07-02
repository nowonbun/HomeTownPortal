app.controller("datamastersetting", [ '$scope', '_ws', '_loader', '_notification',function($scope, _ws, _loader, _notification) {
	_ws.message("datamastersetting", "init", function(data) {
		$scope.cards = JSON.parse(data);
	});
	_ws.message("datamastersetting", "permission", function(data) {
		var node = JSON.parse(data);
		_notification(node.type, node.msg);
		location.href="./#!/";
		return;
	});
	_ws.send("datamastersetting", "init");
} ]);