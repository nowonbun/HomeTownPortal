app.controller("useradd", [ '$scope', '_ws', '_loader','_filereader', '_safeApply','_notification', 
	function($scope, _ws, _loader,_filereader,_safeApply,_notification) {
	_loader.controller.hide();
	$scope.title = "User Management";
	$scope.canUserId = true;
	$scope.canCorrentPassword = false;
	$scope.canModifyPassword = true;
	$scope.canModifyCompany = true;
	$scope.canModifyGroup = true;
	
	_ws.message("usermanagement", "initAdd", function(data) {
		var node = JSON.parse(data);
		$scope.img_url = CONTENTS + "no_photo.png";
		$scope.companyList = node.companyList;
		$(document).off("change", "#company").on("change", "#company", function() {
			_safeApply(function() {
				$scope.company = $("#company").val();
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
			$('#profileModal').on('hidden.bs.modal', function () {
				_extendModal.mainModal();
			});
		});
		_loader.controller.show();
	});
	
	_ws.message("usermanagement", "addUser", function(data) {
		var msg = JSON.parse(data);
		_loader.hide();
		_notification(msg.type, msg.msg);
		location.href="./#!/usermanagement";
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
		if ($.trim($scope.uid) === "") {
			_notification("danger", "Please input the text of 'User Id'", function() {
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

		var data = {
			uid : $scope.uid,
			given_name : $scope.given_name,
			name : $scope.name,
			nick_name : $scope.nick_name,
			img_url : $scope.img_url,
			is_img_blob : $scope.is_img_blob,			
			password : $scope.password,
			company : $scope.company,
			group : $scope.group
		};
		_loader.show();
		_ws.send("usermanagement", "addUser", JSON.stringify(data));
		return;
	}
	_ws.send("usermanagement", "initAdd");
} ]);