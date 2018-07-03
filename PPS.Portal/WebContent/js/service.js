app.service('_loader', [ '$rootScope', '_safeApply', function($rootScope, _safeApply) {
	$rootScope.isController = true;
	$rootScope.isLoader = false;
	return {
		show : function() {
			_safeApply(function() {
				$rootScope.isLoader = true;
			});
		},
		hide : function() {
			_safeApply(function() {
				$rootScope.isLoader = false;
			});
		},
		controller : {
			show : function() {
				_safeApply(function() {
					$rootScope.isController = true;
					$rootScope.isLoader = false;
				});
			},
			hide : function() {
				_safeApply(function() {
					$rootScope.isController = false;
					$rootScope.isLoader = true;
				});
			}
		},
		ready : function(fn) {
			if (fn && typeof fn === 'function') {
				$(fn);
			}
		}
	}
} ]);

app.service('_safeApply', [ '$rootScope', function($rootScope) {
	return function(fn) {
		// var phase = $scope.$root.$$phase;
		var phase = $rootScope.$$phase;
		if (phase == '$apply' || phase == '$digest') {
			if (fn && typeof fn === 'function') {
				fn();
			}
		} else {
			$rootScope.$apply(fn);
		}
	}
} ]);

app.service('_notification', [ '$rootScope', '$timeout', '_safeApply', function($rootScope, $timeout, _safeApply) {
	return function(type, msg, cb) {
		_safeApply(function() {
			if (type !== "success" && type !== "warning") {
				type = "danger";
			}
			var title = $("<span></span>");
			if (type === "danger" || type === "warning") {
				title.append($("<i></i>").addClass("fa fa-warning"));
			}
			title.append("&nbsp;" + type.toUpperCase());
			var button = $("<button></button>").addClass("close").append($("<span></span>").attr("aria-hidden", "true").html("&times;"));
			var header = $("<span></span>").css("display", "block").append(title).append(button);
			var body = $("<div></div>").addClass("alert alert-" + type).append(header).append($("<span></span>").addClass("glyphicon glyphicon-exclamation-sign").text(msg));
			var dom = $("<div></div>").addClass("notification-row").append(body);
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
} ]);

app.service('_extendModal', [ '$rootScope', '$http', '$compile', function($rootScope, $http, $compile) {
	return function(templeteUrl, controller, $scope, callback) {
		if (templeteUrl === undefined) {
			var dom = $("#extendModal").attr("ng-controller", "").html("");
			$(".content-wrapper").removeClass("position-fix");
			return;
		}
		$http.get(templeteUrl).then(function(result) {
			var dom = $("#extendModal").attr("ng-controller", controller).html(result.data);
			$(".content-wrapper").addClass("position-fix");
			$compile(dom)($scope == null ? $rootScope : $scope);
			if (callback && typeof callback === 'function') {
				callback.call(this);
			}
		});
	}
} ]);

app.service('_ws', [ '$rootScope', '_safeApply', function($rootScope, _safeApply) {
	var socket = new WebSocket(WS_HOST + "/socket");
	var delegate = {
		open : null,
		close : null,
		error : [],
		message : []
	};
	socket.onopen = function(msg) {
		if (delegate.open === null) {
			return;
		}
		_safeApply(function() {
			delegate.open.func.call(this, msg);
		});
		if (delegate.open.cb !== null && delegate.open.cb !== undefined && typeof delegate.open.cb === "function") {
			delegate.open.cb.call(this);
		}
	};
	socket.onclose = function(msg) {
		if (delegate.close === null) {
			return;
		}
		_safeApply(function() {
			delegate.close.func.call(this, msg);
		});
		if (delegate.close.cb !== null && delegate.close.cb !== undefined && typeof delegate.close.cb === "function") {
			delegate.close.cb.call(this);
		}
	};
	socket.onerror = function(msg) {
		var item = null;
		var node = JSON.parse(msg.data);
		for ( var i in delegate.error) {
			if (node.control === delegate.error[i].control && node.action === delegate.error[i].action) {
				item = delegate.error[i];
				break;
			}
		}
		if (item === null) {
			return;
		}
		_safeApply(function() {
			item.func.call(this, node.data);
		});
		if (item.cb !== null && item.cb !== undefined && typeof item.cb === "function") {
			item.cb.call(this);
		}
	};
	socket.onmessage = function(msg) {
		var item = null;
		var node = JSON.parse(msg.data);
		for ( var i in delegate.message) {
			if (node.control === delegate.message[i].control && node.action === delegate.message[i].action) {
				item = delegate.message[i];
				break;
			}
		}
		if (item === null) {
			return;
		}
		_safeApply(function() {
			item.func.call(this, node.data);
		});
		if (item.cb !== null && item.cb !== undefined && typeof item.cb === "function") {
			item.cb.call(this);
		}
	};
	sendNode = function(node) {
		if (socket.readyState === 1) {
			try {
				socket.send(node);
			} catch (err) {
				console.log(err);
			}
		} else {
			setTimeout(function() {
				sendNode(node);
			}, 1000);
		}
	}
	function define(type, func, cb) {
		if (func !== null && func !== undefined && typeof func === "function") {
			delegate[type] = {
				func : func,
				cb : cb
			};
			return;
		}
		console.error("It's not defined because not function method.");
	}
	function define2(type, control, action, func, cb) {
		if (func !== null && func !== undefined && typeof func === "function") {
			for ( var i in delegate[type]) {
				var temp = delegate[type][i];
				if (temp.control === control && temp.action === action) {
					temp.func = func;
					return;
				}
			}
			delegate[type].push({
				control : control,
				action : action,
				func : func,
				cb : cb
			});
			return;
		}
		console.error("It's not defined because not function method.");
	}
	return {
		open : function(func, cb) {
			define("open", func, cb);
		},
		close : function(func, cb) {
			define("close", func, cb);
		},
		error : function(control, action, func, cb) {
			define2("error", control, action, func, cb);
		},
		message : function(control, action, func, cb) {
			define2("message", control, action, func, cb);
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

app.service('_util', [ '_notification', function(_notification) {
	return {
		validateInput : function(val, lbl, name) {
			if ($.trim(val) === "") {
				_notification("danger", "Please input the text of '" + name + "'", function() {
					$(lbl).removeClass('error-focus');
				});
				$(lbl).addClass('error-focus');
				return false;
			}
			$(lbl).removeClass('error-focus');
			return true;
		},
		parseInt : function(value) {
			var ret = Number(value);
			if (Number.isNaN(ret)) {
				return -1;
			}
			return ret;
		}
	}
} ]);
