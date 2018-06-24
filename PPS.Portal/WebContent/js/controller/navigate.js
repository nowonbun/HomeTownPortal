app.controller("navigate", [ '$scope', '_ws', function($scope, _ws) {
	_ws.message("navigate", "init", function(data) {
		$scope.navi = JSON.parse(data);
	});
} ]);