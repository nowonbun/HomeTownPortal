app.controller("cardviewrole", [ '$scope', '_ws', '_loader', '_table', function($scope, _ws, _loader, _table) {
	_loader.controller.hide();
	$scope.title = "View role";
	_ws.send("cardviewrole", "init", null, function(data){
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
		$scope.userEdit = function() {
			// _extendModal.mainModal("./views/profile.tpl.jsp","useredit",$scope);
		}
		_loader.controller.show();
	});
} ]);