var ctl = null;
var common = (function(obj) {
	$(obj.onLoad);
	return obj;
})({

	onLoad : function() {
		if (ctl === null || ctl === undefined || typeof ctl !== "object") {
			console.error("declare abstract controller");
			return;
		}
		if (ctl.onLoad !== undefined && typeof ctl.onLoad === "function") {
			ctl.onLoad.call(this);
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
	onLoad : function() {
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
		common.on("#pid, #pwd","keyup", function(){
			if(event.keyCode === 13){
				ctl.login();	
			}
		});
		common.on("#plogin", "click", function() {
			ctl.login();
		});
	},
	login : function() {
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
		common.ajax("login", data, function(ret) {
			if (ret === 1) {
				$(".login-error").text("Login invalid. Please check ID or Password.");
				$("#pwd").val("");
			} else {
				location.href = ".";
			}
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