app.controller("cardmastersetting", [ '$scope', '_ws', '_loader', '_safeApply', '_extendModal', '_notification', '_table', function($scope, _ws, _loader, _safeApply, _extendModal, _notification, _table) {
	_loader.controller.hide();
	_ws.send("cardmastersetting", "init", null, function(data) {
		var table = _table({
			element : "#tablelist",
			url : JSON.parse(data).data,
			columns : [ "code", "name", "title", "description", "hasImg", "icon", "color", "type", "step", "seq" ],
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
				$scope.selectid = table.rows(idx).data().pluck('code')[0];
			},
			deselect : function(table, idx) {
				$("#editbtn").prop("disabled", true);
				$scope.selectid = null;
			}
		});
		$scope.cardEdit = function() {
			_extendModal.mainModal("./views/cardmaster.tpl.jsp", "cardmaster", $scope);
		}
		$scope.reloadTable = function() {
			table.ajax.reload();
		}
		_loader.controller.show();
	});
} ]);