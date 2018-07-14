app.controller("comgroupadd", [ '$scope', '_ws', '_loader', '_extendModal', '_safeApply', '_notification', function($scope, _ws, _loader, _extendModal, _safeApply, _notification) {
	_loader.show();
	function getNamefromValue(list, val) {
		if (list === undefined || list === null) {
			return null;
		}
		for (var i = 0; i < list.length; i++) {
			if (list[i].value === val) {
				return list[i].name;
			}
		}
		return null;
	}
	function getValuefromName(list, val) {
		if (list === undefined || list === null) {
			return null;
		}
		for (var i = 0; i < list.length; i++) {
			if (list[i].name === val) {
				return list[i].value;
			}
		}
		return null;
	}
	function hasGetName(list, val) {
		if (list === undefined || list === null) {
			return false;
		}
		for (var i = 0; i < list.length; i++) {
			if (list[i].name === val) {
				return true;
			}
		}
		return false;
	}
	$scope.applyView = true;
	$scope.dataCheck = function(isGetGroup) {
		if (isGetGroup) {
			if (hasGetName($scope.companyList, $scope.companyIn)) {
				_loader.show();
				_ws.send("comgroupsetting", "getGroup", getValuefromName($scope.companyList, $scope.companyIn), function(data) {
					var node = JSON.parse(data);
					$scope.groupList = node;
					$(document).off("change", "#group").on("change", "#group", function() {
						_safeApply(function() {
							$scope.group = $("#group").val();
							$scope.groupIn = getNamefromValue($scope.groupList, $("#group").val());
							$scope.dataCheck(false);
						});
					});
					_loader.ready(function() {
						$('.mdb-select').material_select('destroy');
						$('.mdb-select').material_select();
						_loader.hide();
					});
				});
			}
		}
		if ($.trim($scope.companyIn) === "") {
			$scope.applyView = true;
		} else if ($.trim($scope.groupIn) === "") {
			$scope.applyView = true;
		} else if (hasGetName($scope.companyList, $scope.companyIn) && hasGetName($scope.groupList, $scope.groupIn)) {
			$scope.applyView = true;
		} else {
			$scope.applyView = false;
		}
	}
	$scope.apply = function() {
		if ($.trim($scope.companyIn) === "") {
			_notification("danger", "Please input the text of company.", function() {
				$("#companyIn").removeClass('error-focus');
			});
			$("#companyIn").addClass('error-focus');
			return;
		}
		if ($.trim($scope.groupIn) === "") {
			_notification("danger", "Please input the text of group.", function() {
				$("#groupIn").removeClass('error-focus');
			});
			$("#groupIn").addClass('error-focus');
			return;
		}
		_loader.show();
		_ws.send("comgroupsetting", "applyAdd", JSON.stringify({
			company : $scope.companyIn,
			group : $scope.groupIn
		}), function(data) {
			var msg = JSON.parse(data);
			_loader.hide();
			_notification(msg.type, msg.msg);
			if (msg.type === "success") {
				$scope.reloadTable();
				$("#comgroupModal").modal("hide");
			}
		});
	}
	_ws.send("comgroupsetting", "initAdd", null, function(data) {
		var node = JSON.parse(data);
		$scope.companyList = node;
		$(document).off("change", "#company").on("change", "#company", function() {
			_safeApply(function() {
				$scope.company = $("#company").val();
				$scope.companyIn = getNamefromValue($scope.companyList, $("#company").val());
				$scope.dataCheck(true);
			});
		});
		_loader.ready(function() {
			$('.mdb-select').material_select();
			$("#comgroupModal").modal("show");
			$('#comgroupModal').on('hidden.bs.modal', function() {
				_extendModal.mainModal();
			});
			_loader.hide();
		});
	});
} ]);