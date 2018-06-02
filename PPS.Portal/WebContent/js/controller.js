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
		debugger;
	});

	$scope.fileupload = function() {
		var file = _filereader.getFile($("#img_file"));
		_filereader.readFile(file, function(node) {
			$scope.img_url = node.binary;
			$scope.is_img_blob = true;
		});
	}

	$scope.apply = function() {
		if ($.trim($scope.given_name) === "") {
			_notification.setMessage("danger", "Please input the text of 'Given name'", function() {
				$("#given_name").removeClass('error-focus');
			});
			$("#given_name").addClass('error-focus');
		}
		debugger;
		var data = {
			given_name : $scope.given_name,
			name : $scope.name,
			nick_name : $scope.nick_name,
			img_url : $scope.img_url,
			is_img_blob : $scope.is_img_blob,
			company : $scope.company,
			group : $scope.group
		};
		_ws.send("profile", "apply", JSON.stringify(data));
		return;
	}
	_ws.send("profile", "init");
} ]);

app.controller("admin", [ '$scope', '_ws', function($scope, _ws) {
	_ws.message("admin", "init", function(data) {
		// TODO: This program is if the internet is connected,
		// we can not work it.
		$scope.cards = JSON.parse(data);
	});
	_ws.send("admin", "init");
} ]);
