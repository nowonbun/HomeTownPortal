app.service('_loader', [ '$rootScope', function($rootScope) {
	this.show = function() {
		$rootScope.isLoader = true;
	}
	this.hide = function() {
		$rootScope.isLoader = false;
	}
} ]);

app.service('_ws', [ '$rootScope', '_loader', function($rootScope, _loader) {
	var socket = new WebSocket("ws://localhost:8080/Portal/socket");
	var delegate = {
		open : [],
		close : [],
		error : [],
		message : []
	};
	socket.onopen = function(msg) {
		$rootScope.$apply(function() {
			for ( var i in delegate.open) {
				delegate.open[i].call(this, msg);
			}
		});
	};
	socket.onclose = function(msg) {
		$rootScope.$apply(function() {
			for ( var i in delegate.close) {
				delegate.close[i].call(this, msg);
			}
		});
	};
	socket.onerror = function(msg) {
		$rootScope.$apply(function() {
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
		$rootScope.$apply(function() {
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
	this.open = function(func) {
		define("open", func);
	};
	this.close = function(func) {
		define("close", func);
	};
	this.error = function(control, action, func) {
		define2("error", control, action, func);
	}
	this.message = function(control, action, func) {
		define2("message", control, action, func);
	};
	this.send = function(control, action, data) {
		if (data === undefined) {
			data = "";
		}
		sendNode(JSON.stringify({
			control : control,
			action : action,
			data : data
		}));
	}
} ]);
