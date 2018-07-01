app.run([ '$rootScope', '_ws', '_loader', '_notification', '_extendModal', '$timeout',
	function($rootScope, _ws, _loader, _notification, _extendModal, $timeout) {
	_ws.message("login", "init", function(data) {
		if (data === "NG") {
			location.href = "./Logout";
		}
	});
	_ws.open(function(){
		//_notification("success", "The Websocket connection success.");
	});
	_ws.close(function(){
		_notification("danger", "The Websocket connection was lost.");
		var timer = 5;
		function reloadNotice(){
			if(timer == 0){
				location.reload();
				return;
			}
			_notification("danger", "It's reloaded in " + timer + " seconds.");
			timer--;
			$timeout(reloadNotice, 1000, true);
		}
		$timeout(reloadNotice, 1000, true);
	});
	$rootScope.profileEdit = function(){
		$('#navitoggler').click();
		_extendModal("./views/profile.tpl.jsp","profile",null,null);
	}
} ]);