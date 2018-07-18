app.controller("cardviewroleedit", [ '$scope', '_ws', '_loader', '$http', '_extendModal', function($scope, _ws, _loader, $http, _extendModal) {
	_loader.controller.hide();
	$scope.title = "Card view role edit";
	_ws.send("cardviewrole", "editrole", $scope.selectid, function(data) {
		
		/*var table = _table({
			element : "#tablelistedit",
			url : JSON.parse(data).data,
			columns : [ "company", "group", "user", "option" ],
			complete : function() {
				$("#tablelistedit").css("width", "");
				$("#tablelistedit thead th").css("width", "");
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
			},
			deselect : function(table, idx) {
				$("#editbtn").prop("disabled", true);
				$("#deletebtn").prop("disabled", true);
				$scope.selectid = null;
			},
			option : {
				searching : false,
				bPaginate : false,
				bLengthChange : false,
				bFilter : true,
				bInfo : false,
				bAutoWidth : false
			}
		});
		$scope.addRow = function() {
			table.row.add([null,
							"<select><option>all</option></select>",
							"<select><option>all</option></select>",
							"<select><option>all</option></select>",
							"<input type='button' value='X'>"]);
		}*/
		_loader.ready(function() {
			$("#roleEditModal").modal("show");
			$('#roleEditModal').on('hidden.bs.modal', function() {
				_extendModal.mainModal();
			});
		});
		_loader.controller.show();
	});
} ]);