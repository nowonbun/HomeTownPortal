app.controller("usermanagement", [ '$scope', '_ws', '_loader', '_safeApply', '_extendModal', '_notification', '_table', function($scope, _ws, _loader, _safeApply, _extendModal, _notification, _table) {
	_loader.controller.hide();
	_ws.send("usermanagement", "init", null, function(data) {
		var table = _table({
			element : "#tablelist",
			url : JSON.parse(data).data,
			columns : [ "id", "given_name", "name", "nick_name", "company_name", "group_name", "type", "active" ],
			complete : function() {
				$("#tablelist_wrapper").addClass("box-shadow-0");
				$("#tablelist").css("width", "");
				$("#tablelist thead th").css("width", "");
			},
			error : function(xhr, error, thrown) {
				if (xhr.status === 401) {
					_notification("danger", "You don't have permission.");
					location.href = "./#!/"
				}
			},
			select : function(table, idx) {
				$("#editbtn").prop("disabled", false);
				$("#deletebtn").prop("disabled", false);
				$scope.selectid = table.rows(idx).data().pluck('id')[0];
			},
			deselect : function(table, idx) {
				$("#editbtn").prop("disabled", true);
				$("#deletebtn").prop("disabled", true);
				$scope.selectid = null;
			}
		});
		$scope.userAdd = function() {
			_extendModal.mainModal("./views/profile.tpl.jsp", "useradd", $scope);
		}
		$scope.userEdit = function() {
			_extendModal.mainModal("./views/profile.tpl.jsp", "useredit", $scope);
		}
		$scope.userDelete = function() {
			_loader.show();
			_ws.send("usermanagement", "delete", $scope.selectid, function(data) {
				var msg = JSON.parse(data);
				_loader.hide();
				_notification(msg.type, msg.msg);
				$scope.reloadTable();
				$("#deleteModal").modal("hide");
			});
		}
		$scope.reloadTable = function() {
			table.ajax.reload();
		}
		_loader.controller.show();

	});
} ]);