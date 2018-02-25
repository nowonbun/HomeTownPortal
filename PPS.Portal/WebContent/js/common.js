var ctl = null;
var common = (function(obj) {
	$(obj.onLoad);
	return obj;
})({
	hosturl: "/Portal",
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