app.run([ '$rootScope', '_ws', '_loader', '_notification', '_extendModal', '$timeout', '$http', '$compile', function($rootScope, _ws, _loader, _notification, _extendModal, $timeout, $http, $compile) {
	_ws.message("login", "init", function(data) {
		if (data === "NG") {
			location.href = "./Logout";
		}
	});
	_ws.message("login", "permission", function(data) {
		var node = JSON.parse(data);
		_notification(node.type, node.msg);
		location.href = "./#!/";
		return;
	});
	_ws.open(function() {
		// _notification("success", "The Websocket connection success.");
	});
	_ws.close(function() {
		_notification("danger", "The Websocket connection was lost.");
		function reloadNotice() {
			_notification("danger", "It's reloading Now.");
			location.reload();
		}
		$timeout(reloadNotice, 1000, true);
	});
	$rootScope.profileEdit = function() {
		$('#navitoggler').click();
		_extendModal.mainModal("./views/profile.tpl.jsp", "profile", null, null);
	}
	$rootScope.menu = function(ismenu, control, template, href) {
		if (!ismenu) {
			$(".menu-backdrop").click();
			location.href = href;
			return;
		}
		_extendModal.menuModal(template, control);
	}
} ]);