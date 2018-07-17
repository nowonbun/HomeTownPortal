app.controller("comgroupedit", [ '$scope', '_ws', '_loader', '_extendModal', '_safeApply', '_notification', function($scope, _ws, _loader, _extendModal, _safeApply, _notification) {
	_loader.show();
	_ws.send("comgroupsetting", "initEdit", $scope.selectid, function(data) {
		var node = JSON.parse(data);
		$scope.companyIn = node.name;
		$scope.groupIn = node.groupname;
		$scope.editView = true;
		_loader.ready(function() {
			$("#companyIn_label").addClass("active");
			$("#groupIn_label").addClass("active");
			$("#comgroupModal").modal("show");
			$('#comgroupModal').on('hidden.bs.modal', function() {
				_extendModal.mainModal();
			});
			_loader.hide();
		});
	});
	$scope.edit = function() {
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
		_ws.send("comgroupsetting", "applyEdit", JSON.stringify({
			id : $scope.selectid,
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
} ]);