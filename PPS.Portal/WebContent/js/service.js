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
		var phase = $rootScope.$$phase;
		if (phase == '$apply' || phase == '$digest') {
			if (util.isFunction(fn)) {
				fn.call(this);
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
				if (util.isFunction(cb)) {
					cb.call(this);
				}
			}, 5000);
		});
	}
} ]);

app.service('_extendModal', [ '$rootScope', '$http', '$compile', '$timeout', function($rootScope, $http, $compile, $timeout) {
	return {
		mainModal : function(templeteUrl, controller, $scope, callback) {
			if (templeteUrl === undefined) {
				var dom = $("#extendModal").attr("ng-controller", "").html("");
				$(".content-wrapper").removeClass("position-fix");
				return;
			}
			$http.get(templeteUrl).then(function(result) {
				var dom = $("#extendModal").attr("ng-controller", controller).html(result.data);
				$(".content-wrapper").addClass("position-fix");
				$compile(dom)($scope == null ? $rootScope : $scope);
				if (util.isFunction(callback)) {
					callback.call(this);
				}
			});
		},
		menuModal : function(templateUrl, control, $scope, callback) {
			var backdrop = $("div.menu-backdrop");
			if (backdrop.length < 1) {
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
			$http.get(templateUrl).then(function(result) {
				var dom = $("#menuFrame").attr("ng-controller", control).html(result.data);
				$compile(dom)($scope == null ? $rootScope : $scope);
				$timeout(function() {
					$(".content-wrapper").addClass("position-fix");
					$(".menu.fade.top").addClass("show");
					if (util.isFunction(callback)) {
						callback.call(this);
					}
				}, 1);
				dom.on("click", function() {
					$(".menu-backdrop").click();
				});
			});
		}
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
		if (util.isFunction(delegate.open.cb)) {
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
		if (util.isFunction(delegate.close.cb)) {
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
		if (util.isFunction(item.cb)) {
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
		if (util.isFunction(item.cb)) {
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
		if (util.isFunction(func)) {
			delegate[type] = {
				func : func,
				cb : cb
			};
			return;
		}
		console.error("It's not defined because not function method.");
	}
	function define2(type, control, action, func, cb) {
		if (util.isFunction(func)) {
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
		send : function(control, action, data, func, cb) {
			if (data === undefined || data === null) {
				data = "";
			}
			if (util.isFunction(func)) {
				define2("message", control, action, func, cb);
			}
			sendNode(JSON.stringify({
				control : control,
				action : action,
				data : data
			}));
		}
	}
} ]);

app.service('_table', [ '_safeApply', function(_safeApply) {
	return function(option) {
		function convertColumn(val) {
			if (val == null) {
				return null;
			}
			var ret = [ {
				data : null
			} ];
			for ( var i in val) {
				ret.push({
					data : val[i]
				});
			}
			return ret;
		}

		var table = $(option.element).DataTable({
			ajax : {
				url : option.url,
				type : "POST",
				complete : function() {
					if (util.isFunction(option.complete)) {
						option.complete.call(this);
					}
				},
				error : function(xhr, error, thrown) {
					if (util.isFunction(option.error)) {
						option.error.call(this, xhr, error, thrown);
					}
				}
			},
			ordering : false,
			select : {
				style : 'single'
			},
			columnDefs : [ {
				className : 'control',
				targets : 0,
				data : null,
				defaultContent : ''
			} ],
			responsive : {
				details : {
					type : 'column',
					target : 0
				}
			},
			columns : convertColumn(option.columns)
		});
		table.on('select', function(e, dt, type, indexes) {
			if (type === 'row') {
				_safeApply(function() {
					if (util.isFunction(option.select)) {
						option.select.call(this, table, indexes);
					}
				});
			}
		});
		table.on('deselect', function(e, dt, type, indexes) {
			if (type === 'row') {
				_safeApply(function() {
					if (util.isFunction(option.deselect)) {
						option.deselect.call(this, table, indexes);
					}
				});
			}
		});
		return table;
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
		}
	}
} ]);