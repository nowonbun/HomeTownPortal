app.run([ '$rootScope', '_ws', '_loader', '_notification', '_extendModal', '$timeout', '$http', '$compile', 
	function($rootScope, _ws, _loader, _notification, _extendModal, $timeout, $http, $compile) {
	_ws.message("login", "init", function(data) {
		if (data === "NG") {
			location.href = "./Logout";
		}
	});
	_ws.open(function() {
		// _notification("success", "The Websocket connection success.");
	});
	_ws.close(function() {
		_notification("danger", "The Websocket connection was lost.");
		var timer = 5;
		function reloadNotice() {
			if (timer == 0) {
				location.reload();
				return;
			}
			_notification("danger", "It's reloaded in " + timer + " seconds.");
			timer--;
			$timeout(reloadNotice, 1000, true);
		}
		$timeout(reloadNotice, 1000, true);
	});
	$rootScope.profileEdit = function() {
		$('#navitoggler').click();
		_extendModal("./views/profile.tpl.jsp", "profile", null, null);
	}
	$rootScope.menu = function(ismenu, control, template, href) {
		if (!ismenu) {
			$(".menu-backdrop").click();
			location.href = href;
			return;
		}
		var backdrop = $("div.menu-backdrop");
		if(backdrop.length < 1) {
			backdrop = $("<div></div>").addClass("menu-backdrop").addClass("fade").addClass("show");
			backdrop.on("click", function() {
				$(".menu.fade.top").removeClass("show");
				$(".content-wrapper").removeClass("position-fix");
				$("#menuFrame").attr("ng-controller", "").html("");
				$(this).remove();
			});
			$("body").append(backdrop);
		} else {
			$("#menuFrame").off("click");
		}
		$http.get(template).then(function(result) {
			var dom = $("#menuFrame").attr("ng-controller", control).html(result.data);
			$compile(dom)($rootScope);
			$timeout(function() {
				$(".content-wrapper").addClass("position-fix");
				$(".menu.fade.top").addClass("show");
			}, 1);
			dom.on("click", function() {
				$(".menu-backdrop").click();
			});
		});
	}
} ]);