app.run([ '$rootScope', '_ws', '_loader', '_notification', function($rootScope, _ws, _loader, _notification) {
	_ws.message("login", "init", function(data) {
		if (data === "NG") {
			location.href = "./Logout";
		}
	});
	_ws.open(function(){
		//_notification.setMessage("success", "The Websocket connection success.");
	});
	_ws.close(function(){
		_notification.setMessage("danger", "The Websocket connection was lost.");
	});
} ]);