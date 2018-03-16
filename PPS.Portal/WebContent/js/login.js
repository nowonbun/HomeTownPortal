var ctl = null;
var common = (function(obj) {
	$(obj.onLoad);
	return obj;
})({
	logger: function(message){
		//console.log(message);
	},
	hosturl: "/Portal",
	websocketurl: "ws://localhost:8080/Portal/",
	ws: null,
	onLoad : function() {
		if (ctl === null || ctl === undefined || typeof ctl !== "object") {
			console.error("declare abstract controller");
			return;
		}
		/*
		if(ctl.wslocation !== undefined){
			common.ws = new WebSocket(common.websocketurl +  ctl.wslocation);
			common.ws.onopen = function(message){
				common.logger(message);
			};
			common.ws.onclose = function(message){
				common.logger(message);
			};
			common.ws.onerror = function(message){
				common.error(message);
			};
			common.ws.onmessage = function(message){
				common.logger(message);
				if(ctl.message !== undefined && typeof ctl.message === "function"){
					var node = JSON.parse(message.data)
					ctl.message.call(this,node);
				}
			};
		}*/
		if (ctl.onLoad !== undefined && typeof ctl.onLoad === "function") {
			ctl.onLoad.call(this);
		}
	},
	socketSend: function(key, data){
		var node = {
			key: key, 
			data:data
		};
		var json = JSON.stringify(node);
		if (common.ws.readyState === 1) {
			common.ws.send(json);
	    } else {
	        setTimeout(function () {
	        	common.ws.send(json);
	        }, 1000);
	    }
	},
	on : function(element, eventname, callback, isadd) {
		if (callback !== undefined && typeof callback === "function") {
			if (isadd !== true) {
				$(document).off(eventname, element);
			}
			$(document).on(eventname, element, callback);
		}
	},
	ajax : function(url, data, success, error, final) {
		$.ajax({
			url : url,
			type : 'POST',
			data : data,
			dataType : 'json'
		// contentType : 'application/x-www-form-urlencoded; charset=UTF-8'
		// timeout:1000,
		}).done(function(ret, textStatus, jqXHR) {
			if(success !== null && success !== undefined && typeof success === "function"){
				success.call(this,ret);	
			}
		}).fail(function(jqXHR, textStatus, errorThrown) {
			if(error !== null && error !== undefined && typeof error === "function"){
				error.call(this);	
			}
		}).always(function() {
			if(final !== null && final !== undefined && typeof final === "function"){
				final.call(this,ret);	
			}
		});
	},
	notification: function(type,msg){
		// danger warning success
		if(type !== "success" && type !== "warning"){
			type = "danger";
		}
		var dom = $("<div></div>").addClass("notification-row").append(
				$("<div></div>").addClass("alert alert-"+type).append(
						$("<button></button>").addClass("close").append(
								$("<span></span>").attr("aria-hidden","true").html("&times;"))
				).append($("<span></span>").addClass("glyphicon glyphicon-exclamation-sign").text(msg)));
		if($("div.notification-row").length > 5){
			$("div.notification-row")[0].remove();
		}
		$(".notification-zone").append(dom);
		setTimeout(function(){
			dom.remove();
		},3000);
	}
});
ctl = {
	//wslocation : "login",
	onLoad : function() {
		//common.socketSend("init","");
		common.on(".view-link", "click", function() {
			var tab = $(this).parent().data("state");
			if (tab === 0) {
				$("#google_form").hide();
				$("#private_form").show();
				$("#private_link").hide();
				$("#google_link").show();
				$(this).parent().data("state", 1);
			} else {
				$("#google_form").show();
				$("#private_form").hide();
				$("#private_link").show();
				$("#google_link").hide();
				$(this).parent().data("state", 0);
			}
		});
		common.on("#plogin", "click", function() {
			if ($.trim($("#pid").val()) === "") {
				$(".login-error").text("Please input ID.");
				$("#pid").addClass("border-foces");
				setTimeout(function() {
					$(".login-error").text("");
					$("#pid").removeClass("border-foces");
				}, 3000);
				return;
			}
			if ($.trim($("#pwd").val()) === "") {
				$(".login-error").text("Please input password.");
				$("#pwd").addClass("border-foces");
				setTimeout(function() {
					$(".login-error").text("");
					$("#pwd").removeClass("border-foces");
				}, 3000);
				return;
			}
			var data = JSON.stringify({
				pid : $("#pid").val(),
				pwd : $("#pwd").val()
			});
			common.ajax(common.hosturl + "/login", data, function(ret) {
				if (ret === 1) {
					$(".login-error").text(
							"Login invalid. Please check ID or Password.");
					$("#pwd").val("");
				} else {
					location.href = common.hosturl;
				}
			});
		});
	},
	message : function(node) {
		if (node.key === "login") {
			if (node.data === "OK") {
				location.href = "./";
			}
		}
		console.log(node);
	}
};