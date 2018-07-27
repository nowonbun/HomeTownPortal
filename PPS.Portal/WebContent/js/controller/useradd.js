app.controller("useradd", [ '$scope', '_ws', '_loader', '_filereader', '_safeApply', '_notification', '_extendModal', function($scope, _ws, _loader, _filereader, _safeApply, _notification, _extendModal) {
	_loader.controller.hide();
	$scope.title = "User Management";
	$scope.canUserId = true;
	$scope.canCorrentPassword = false;
	$scope.canModifyPassword = true;
	$scope.canModifyCompany = true;
	$scope.canModifyGroup = true;
	$scope.existID = false;

	_ws.send("usermanagement", "initAdd", null, function(data) {
		var node = JSON.parse(data);
		$scope.img_blob = CONTENTS + "no_photo.png";
		$scope.companyList = node.companyList;
		$(document).off("change", "#company").on("change", "#company", function() {
			_safeApply(function() {
				$scope.company = $("#company").val();
			});
			_ws.send("usermanagement", "getGroup", JSON.stringify({
				key : Number($scope.company)
			}), function(data) {
				var node = JSON.parse(data);
				$scope.groupList = node.data;
				_loader.ready(function() {
					$('.mdb-select').material_select('destroy');
					$('.mdb-select').material_select();
				});
			});
		});
		$scope.groupList = node.groupList;
		$(document).off("change", "#group").on("change", "#group", function() {
			_safeApply(function() {
				$scope.group = $("#group").val();
			});
		});
		_loader.ready(function() {
			$('.mdb-select').material_select();
			$("#profileModal").modal("show");
			$('#profileModal').on('hidden.bs.modal', function() {
				_extendModal.mainModal();
			});
		});
		_loader.controller.show();
	});

	$scope.fileupload = function() {
		var file = _filereader.getFile($("#img_file"));
		if (file.size > CARD_FILE_SIZE_LIMIT) {
			_notification("danger", "The file maximum size has been exceeded. max-size : 60KB");
			return;
		}
		_filereader.readFile(file, function(node) {
			$scope.img_blob = node.binary;
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

	$scope.uidCheck = function() {
		_ws.send("usermanagement", "checkUid", $scope.uid, function(data) {
			var node = JSON.parse(data);
			if (node.data) {
				$("#uid").addClass('error-focus');
				$scope.existID = true;
				return true;
			} else {
				$("#uid").removeClass('error-focus');
				$scope.existID = false;
				return false;
			}
		});
	}

	$scope.apply = function() {
		if ($.trim($scope.uid) === "") {
			_notification("danger", "Please input the text of 'User Id'", function() {
				$("#uid").removeClass('error-focus');
			});
			$("#uid").addClass('error-focus');
			return;
		}
		if ($scope.existID) {
			_notification("danger", "This id is exist.", function() {
				$("#uid").removeClass('error-focus');
			});
			$("#uid").addClass('error-focus');
			return;
		}
		if ($.trim($scope.given_name) === "") {
			_notification("danger", "Please input the text of 'Given name'", function() {
				$("#given_name").removeClass('error-focus');
			});
			$("#given_name").addClass('error-focus');
			return;
		}
		$("#given_name").removeClass('error-focus');

		if ($.trim($scope.name) === "") {
			_notification("danger", "Please input the text of 'name'", function() {
				$("#name").removeClass('error-focus');
			});
			$("#name").addClass('error-focus');
			return;
		}
		$("#name").removeClass('error-focus');

		if ($.trim($scope.nick_name) === "") {
			_notification("danger", "Please input the text of 'Nick name'", function() {
				$("#nick_name").removeClass('error-focus');
			});
			$("#nick_name").addClass('error-focus');
			return;
		}
		$("#nick_name").removeClass('error-focus');

		var noChangePassword = true;
		if ($.trim($scope.password) === "" && noChangePassword) {
			_notification("danger", "Please input the text of 'Password'", function() {
				$("#password").removeClass('error-focus');
			});
			$("#password").addClass('error-focus');
			return;
		}
		$("#password").removeClass('error-focus');
		if ($.trim($scope.password_confirm) === "" && noChangePassword) {
			_notification("danger", "Please input the text of 'Password confirm'", function() {
				$("#password_confirm").removeClass('error-focus');
			});
			$("#password_confirm").addClass('error-focus');
			return;
		}
		$("#password_confirm").removeClass('error-focus');
		if ($.trim($scope.password) !== $.trim($scope.password_confirm) && noChangePassword) {
			_notification("danger", "Please input the text of 'Password is incorrect'", function() {
				$("#password").removeClass('error-focus');
				$("#password_confirm").removeClass('error-focus');
			});
			$("#password").addClass('error-focus');
			$("#password_confirm").addClass('error-focus');
			return;
		}
		$("#password").removeClass('error-focus');
		$("#password_confirm").removeClass('error-focus');

		if ($.trim($scope.company) === "") {
			_notification("danger", "Please select the value of 'company'", function() {
				$("#company").removeClass('error-focus');
			});
			$("#company").addClass('error-focus');
			return;
		}
		$("#company").removeClass('error-focus');

		if ($.trim($scope.group) === "") {
			_notification("danger", "Please select the value of 'group'", function() {
				$("#group").removeClass('error-focus');
			});
			$("#group").addClass('error-focus');
			return;
		}
		$("#group").removeClass('error-focus');

		if ($scope.img_blob.indexOf("no_photo.png") > 0) {
			$scope.img_blob = null;
		}

		var data = {
			uid : $scope.uid,
			given_name : $scope.given_name,
			name : $scope.name,
			nick_name : $scope.nick_name,
			img_blob : $scope.img_blob,
			password : $scope.password,
			company : $scope.company,
			group : $scope.group
		};
		_loader.show();
		_ws.send("usermanagement", "addUser", JSON.stringify(data), function(data) {
			var msg = JSON.parse(data);
			_loader.hide();
			_notification(msg.type, msg.msg);
			if (msg.type === "success") {
				$scope.reloadTable();
				$("#profileModal").modal("hide");
			}
		});
		return;
	}
} ]);