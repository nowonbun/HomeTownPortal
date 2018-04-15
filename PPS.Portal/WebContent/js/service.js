app.service('_loader', [ '$rootScope', function($rootScope) {
	return {
		show : function() {
			$rootScope.isLoader = true;
		},
		hide : function() {
			$rootScope.isLoader = false;
		}
	}
} ]);
app.service('_scopeService', [ '$rootScope', function($rootScope) {
	return {
		safeApply : function(fn) {
			// var phase = $scope.$root.$$phase;
			var phase = $rootScope.$$phase;
			if (phase == '$apply' || phase == '$digest') {
				if (fn && typeof fn === 'function') {
					fn();
				}
			} else {
				$rootScope.$apply(fn);
			}
		},
	};
} ]);
app.service('_notification', [
		'$rootScope',
		'$timeout',
		'_scopeService',
		function($rootScope, $timeout, _scopeService) {
			return {
				setMessage : function(type, msg, cb) {
					_scopeService.safeApply(function() {
						if (type !== "success" && type !== "warning") {
							type = "danger";
						}
						var dom = $("<div></div>").addClass("notification-row").append(
								$("<div></div>").addClass("alert alert-" + type)
										.append($("<button></button>").addClass("close").append($("<span></span>").attr("aria-hidden", "true").html("&times;"))).append(
												$("<span></span>").addClass("glyphicon glyphicon-exclamation-sign").text(msg)));
						if ($("div.notification-row").length > 5) {
							$("div.notification-row")[0].remove();
						}
						$(".notification-zone").append(dom);
						$timeout(function() {
							dom.remove();
							if (cb !== undefined) {
								cb.call(this);
							}
						}, 5000);
					});
				}
			}
		} ]);

app.service('_ws', [ '$rootScope', '_loader', '_scopeService', function($rootScope, _loader, _scopeService) {
	var socket = new WebSocket("ws://localhost:8080/Portal/socket");
	var delegate = {
		open : [],
		close : [],
		error : [],
		message : []
	};
	socket.onopen = function(msg) {
		_scopeService.safeApply(function() {
			for ( var i in delegate.open) {
				delegate.open[i].call(this, msg);
			}
		});
	};
	socket.onclose = function(msg) {
		_scopeService.safeApply(function() {
			for ( var i in delegate.close) {
				delegate.close[i].call(this, msg);
			}
		});
	};
	socket.onerror = function(msg) {
		_scopeService.safeApply(function() {
			_loader.show();
			var node = JSON.parse(msg.data);
			for ( var i in delegate.error) {
				if (node.control === delegate.message[i].control && node.action === delegate.message[i].action) {
					delegate.error[i].func.call(this, node.data);
				}
			}
			_loader.hide();
		});
	};
	socket.onmessage = function(msg) {
		_scopeService.safeApply(function() {
			_loader.show();
			var node = JSON.parse(msg.data);
			for ( var i in delegate.message) {
				if (node.control === delegate.message[i].control && node.action === delegate.message[i].action) {
					delegate.message[i].func.call(this, node.data);
				}
			}
			_loader.hide();
		});
	};
	sendNode = function(node) {
		if (socket.readyState === 1) {
			socket.send(node);
		} else {
			setTimeout(function() {
				sendNode(node);
			}, 1000);
		}
	}
	function define(type, func) {
		if (func !== null && func !== undefined && typeof func === "function") {
			delegate[type].push(func);
			return;
		}
		console.error("It's not defined because not function method.");
	}
	function define2(type, control, action, func) {
		if (func !== null && func !== undefined && typeof func === "function") {
			delegate[type].push({
				control : control,
				action : action,
				func : func
			});
			return;
		}
		console.error("It's not defined because not function method.");
	}
	return {
		open : function(func) {
			define("open", func);
		},
		close : function(func) {
			define("close", func);
		},
		error : function(control, action, func) {
			define2("error", control, action, func);
		},
		message : function(control, action, func) {
			define2("message", control, action, func);
		},
		send : function(control, action, data) {
			if (data === undefined) {
				data = "";
			}
			sendNode(JSON.stringify({
				control : control,
				action : action,
				data : data
			}));
		}
	}
} ]);
