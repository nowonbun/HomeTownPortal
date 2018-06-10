app.run([ '$rootScope', '_ws', '_loader', function($rootScope, _ws, _loader) {
	_ws.message("login", "init", function(data) {
		if (data === "NG") {
			location.href = "./Logout";
		}
	});
} ]);

app.controller("navigate", [ '$scope', '_ws', function($scope, _ws) {
	_ws.message("navigate", "init", function(data) {
		$scope.navi = JSON.parse(data);
	});
} ]);

app.controller("card", [ '$scope', '_ws', '_loader', function($scope, _ws, _loader) {
	_ws.message("card", "init", function(data) {
		$scope.cards = JSON.parse(data);
	});
	_ws.send("card", "init");
} ]);

app.controller("admin", [ '$scope', '_ws', function($scope, _ws) {
	_ws.message("admin", "init", function(data) {
		// TODO: This program is if the internet is connected,
		// we can not work it.
		$scope.cards = JSON.parse(data);
	});
	_ws.send("admin", "init");
} ]);

app.controller("datamastersetting", [ '$scope', '_ws', function($scope, _ws) {
	_ws.message("datamastersetting", "init", function(data) {
		// TODO: This program is if the internet is connected,
		// we can not work it.
		$scope.cards = JSON.parse(data);
	});
	_ws.send("datamastersetting", "init");
} ]);

app.controller("profile", [ '$scope', '_ws', '_notification', '_filereader', '_loader', '_scopeService', function($scope, _ws, _notification, _filereader, _loader, _scopeService) {
	_loader.controller.hide();
	_ws.message("profile", "init", function(data) {
		var node = JSON.parse(data);
		$scope.given_name = node.given_name;
		if ($scope.given_name !== null) {
			$("#given_name_label").addClass("active");
		}
		$scope.name = node.name;
		if ($scope.name !== null) {
			$("#name_label").addClass("active");
		}
		$scope.nick_name = node.nick_name;
		if ($scope.nick_name !== null) {
			$("#nick_name_label").addClass("active");
		}
		// ./contents/no_photo.png
		if (node.is_img_blob) {
			if ($scope.is_img_blob == null) {
				node.is_img_blob = false;
				$scope.img_url = CONTENTS + "no_photo.png";
			} else {
				$scope.is_img_blob = true;
			}
		} else {
			$scope.is_img_blob = false;
			if ($scope.img_url == null) {
				$scope.img_url = CONTENTS + "no_photo.png";
			} else {
				$scope.img_url = node.img_url;
			}
		}
		$scope.canModifyPassword = node.canModifyPassword;
		$scope.canModifyCompany = node.canModifyCompany;
		if (node.canModifyCompany) {
			$scope.companyList = node.companyList;
			$("#company").val(node.company);
			$scope.company = node.company;
			$(document).off("change", "#company").on("change", "#company", function() {
				_scopeService.safeApply(function() {
					$scope.company = $("#company").val();
				});
			});
		}
		$scope.canModifyGroup = node.canModifyGroup;
		if (node.canModifyGroup) {
			$scope.groupList = node.groupList;
			$("#group").val(node.group);
			$scope.group = node.group;
			$(document).off("change", "#group").on("change", "#group", function() {
				_scopeService.safeApply(function() {
					$scope.group = $("#group").val();
				});
			});
		}

		_loader.ready(function() {
			$('.mdb-select').material_select();
		});

		_loader.controller.show();
	});
	_ws.message("profile", "apply", function(data) {
		var msg = JSON.parse(data);
		_notification.setMessage(msg.type, msg.msg);
	});

	$scope.fileupload = function() {
		var file = _filereader.getFile($("#img_file"));
		_filereader.readFile(file, function(node) {
			$scope.img_url = node.binary;
			$scope.is_img_blob = true;
		});
	}

	$scope.checkPassword = function() {
		if ($.trim($scope.password) !== $.trim($scope.password_confirm)) {
			$scope.isPasswordError = true;
			$("#password").addClass('error-focus');
			$("#password_confirm").addClass('error-focus');
			return;
		}
		$scope.isPasswordError = false;
		$("#password").removeClass('error-focus');
		$("#password_confirm").removeClass('error-focus');
	}

	$scope.apply = function() {
		if ($.trim($scope.given_name) === "") {
			_notification.setMessage("danger", "Please input the text of 'Given name'", function() {
				$("#given_name").removeClass('error-focus');
			});
			$("#given_name").addClass('error-focus');
			return;
		}
		$("#given_name").removeClass('error-focus');

		if ($.trim($scope.name) === "") {
			_notification.setMessage("danger", "Please input the text of 'name'", function() {
				$("#name").removeClass('error-focus');
			});
			$("#name").addClass('error-focus');
			return;
		}
		$("#name").removeClass('error-focus');

		if ($.trim($scope.nick_name) === "") {
			_notification.setMessage("danger", "Please input the text of 'Nick name'", function() {
				$("#nick_name").removeClass('error-focus');
			});
			$("#nick_name").addClass('error-focus');
			return;
		}
		$("#nick_name").removeClass('error-focus');

		if ($scope.canModifyPassword) {
			var noChangePassword = true;
			if ($.trim($scope.current_password) === "") {
				noChangePassword = false;
			}
			if ($.trim($scope.password) === "" && noChangePassword) {
				_notification.setMessage("danger", "Please input the text of 'Password'", function() {
					$("#password").removeClass('error-focus');
				});
				$("#password").addClass('error-focus');
				return;
			}
			$("#password").removeClass('error-focus');
			if ($.trim($scope.password_confirm) === "" && noChangePassword) {
				_notification.setMessage("danger", "Please input the text of 'Password confirm'", function() {
					$("#password_confirm").removeClass('error-focus');
				});
				$("#password_confirm").addClass('error-focus');
				return;
			}
			$("#password_confirm").removeClass('error-focus');
			if ($.trim($scope.password) !== $.trim($scope.password_confirm) && noChangePassword) {
				_notification.setMessage("danger", "Please input the text of 'Password is incorrect'", function() {
					$("#password").removeClass('error-focus');
					$("#password_confirm").removeClass('error-focus');
				});
				$("#password").addClass('error-focus');
				$("#password_confirm").addClass('error-focus');
				return;
			}
			$("#password").removeClass('error-focus');
			$("#password_confirm").removeClass('error-focus');
		}

		if ($scope.canModifyCompany) {
			if ($.trim($scope.company) === "") {
				_notification.setMessage("danger", "Please select the value of 'company'", function() {
					$("#company").removeClass('error-focus');
				});
				$("#company").addClass('error-focus');
				return;
			}
			$("#company").removeClass('error-focus');
		}

		if ($scope.canModifyGroup) {
			if ($.trim($scope.group) === "") {
				_notification.setMessage("danger", "Please select the value of 'group'", function() {
					$("#group").removeClass('error-focus');
				});
				$("#group").addClass('error-focus');
				return;
			}
			$("#group").removeClass('error-focus');
		}

		var data = {
			given_name : $scope.given_name,
			name : $scope.name,
			nick_name : $scope.nick_name,
			img_url : $scope.img_url,
			is_img_blob : $scope.is_img_blob,
			current_password : $scope.current_password,
			password : $scope.password,
			company : $scope.company,
			group : $scope.group
		};
		_ws.send("profile", "apply", JSON.stringify(data));
		return;
	}
	_ws.send("profile", "init");
} ]);

app.controller("usermanagement", [ '$scope', '_ws', function($scope, _ws) {
	_ws.message("usermanagement", "init", function(data) {
		console.log("LOGGER");
	});
	_ws.send("usermanagement", "init");
} ]);
