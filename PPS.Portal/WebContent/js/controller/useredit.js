app.controller("useredit", [ '$scope', '_ws', '_loader', '_safeApply', function($scope, _ws, _loader, _safeApply) {
	_loader.controller.hide();
	$scope.title = "User Management";
	$scope.canCorrentPassword = false;
	_ws.message("usermanagement", "initEdit", function(data) {
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
			$scope.company = node.company;
			$(document).off("change", "#company").on("change", "#company", function() {
				_safeApply(function() {
					$scope.company = $("#company").val();
				});
			});
		}
		$scope.canModifyGroup = node.canModifyGroup;
		if (node.canModifyGroup) {
			$scope.groupList = node.groupList;
			$scope.group = node.group;
			$(document).off("change", "#group").on("change", "#group", function() {
				_safeApply(function() {
					$scope.group = $("#group").val();
				});
			});
		}
		_loader.ready(function() {
			if (node.canModifyCompany) {
				$("#company").val(node.company);
			}
			if (node.canModifyGroup) {
				$("#group").val(node.group);
			}
			$('.mdb-select').material_select();
			$("#profileModal").modal("show");
		});
		_loader.controller.show();
	});
	
	_ws.send("usermanagement", "initEdit", $scope.selectid);
} ]);