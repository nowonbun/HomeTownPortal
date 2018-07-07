app.controller("actionrole", [ '$scope', '_ws', '_loader', '_table', function($scope, _ws, _loader, _table) {
	_loader.controller.hide();
	$scope.title = "Action role";
	_ws.message("actionrole", "init", function(data) {
		var table = _table({
			element : "#tablelist",
			url : JSON.parse(data).data,
			columns : [ "code", "name" ],
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
			// _extendModal.mainModal("./views/profile.tpl.jsp","useradd",$scope);
		}
		$scope.userEdit = function() {
			// _extendModal.mainModal("./views/profile.tpl.jsp","useredit",$scope);
		}
		$scope.userDelete = function() {
			// location.href = "./#!/userdelete/" + $scope.selectid;
		}
		_loader.controller.show();
	});

	_ws.send("actionrole", "init");
} ]);