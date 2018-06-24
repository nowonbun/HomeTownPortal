app.run([ '$rootScope', '_ws', '_loader', function($rootScope, _ws, _loader) {
	_ws.message("login", "init", function(data) {
		if (data === "NG") {
			location.href = "./Logout";
		}
	});
} ]);