app.service('_ws', function() {
	var socket = new WebSocket("ws://localhost:8080/Portal/socket");
	var delegate = {
		open : [],
		close : [],
		error : [],
		message : []
	};
	socket.onopen = function(msg) {
		for ( var i in delegate.open) {
			delegate.open[i].call(this, msg);
		}
	};
	socket.onclose = function(msg) {
		for ( var i in delegate.close) {
			delegate.close[i].call(this, msg);
		}
	};
	socket.onerror = function(msg) {
		for ( var i in delegate.error) {
			delegate.error[i].call(this, msg);
		}
	};
	socket.onmessage = function(msg) {
		for ( var i in delegate.message) {
			delegate.message[i].call(this, msg);
		}
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
	function define(key, func) {
		if (func !== null && func !== undefined && typeof func === "function") {
			delegate[key].push(func);
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
	this.error = function(func) {
		define("error", func);
	}
	this.message = function(func) {
		define("message", func);
	};
	this.send = function(key, data) {
		sendNode(JSON.stringify({
			key : key,
			data : data
		}));
	}
});