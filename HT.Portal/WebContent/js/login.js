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
		common.on("#plogin", "click", function() {
			var data = JSON.stringify({
				pid : $("#pid").val(),
				pwd : $("#pwd").val()
			});
			common.ajax(common.hosturl + "/login", data, function(ret) {
				console.log(ret);
				if (ret === 1) {
					$(".login-error").text(
							"Login invalid. Please check ID or Password.");
					$("#pwd").val("");
				} else {
					location.href = common.hosturl;
				}
			});
		});
	}
};