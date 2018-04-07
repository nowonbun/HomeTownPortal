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
				if (node.key === delegate.message[i].key) {
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
				if (node.key === delegate.message[i].key) {
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
	function define2(type, key, func) {
		if (func !== null && func !== undefined && typeof func === "function") {
			delegate[type].push({
				key : key,
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
	this.error = function(key, func) {
		define2("error", key, func);
	}
	this.message = function(key, func) {
		define2("message", key, func);
	};
	this.send = function(key, data) {
		sendNode(JSON.stringify({
			key : key,
			data : data
		}));
	}
} ]);
