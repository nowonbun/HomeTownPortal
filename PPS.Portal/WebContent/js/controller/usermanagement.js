app.controller("usermanagement", [ '$scope', '_ws', '_loader', '_scopeService', function($scope, _ws, _loader, _scopeService) {
	_loader.controller.hide();
	_ws.message("usermanagement", "init", function(data) {
		var table = $("#tablelist").DataTable({
			ajax : {
				url : data,
				type : "POST",
				complete : function() {
					$("#tablelist_wrapper").addClass("box-shadow-0");
					$("#tablelist").css("width", "");
					$("#tablelist thead th").css("width", "");
				},
				error : function(xhr, error, thrown) {
				}
			},
			ordering : false,
			select : {
				style : 'single'
			},
			columnDefs : [ {
				className : 'control',
				targets : 0,
				data : null,
				defaultContent : ''
			} ],
			responsive : {
				details : {
					type : 'column',
					target : 0
				}
			},
			columns : [ 
				{ data : null }, 
				{ data : "id" }, 
				{ data : "given" }, 
				{ data : "name" }, 
				{ data : "nick" }, 
				{ data : "company" }, 
				{ data : "group" }, 
				{ data : "type" }, 
				{ data : "active" } ]
		});
		table.on('select', function(e, dt, type, indexes) {
			if (type === 'row') {
				_scopeService.safeApply(function() {
					$("#editbtn").prop("disabled", false);
					$("#deletebtn").prop("disabled", false);
					$scope.selectid = table.rows(indexes).data().pluck('id')[0];
				});
			}
		});
		table.on('deselect', function(e, dt, type, indexes) {
			if (type === 'row') {
				_scopeService.safeApply(function() {
					$("#editbtn").prop("disabled", true);
					$("#deletebtn").prop("disabled", true);
					$scope.selectid = null;
				});
			}
		});
		$scope.userAdd = function() {
			location.href = "./#!/useradd/";
		}
		$scope.userEdit = function() {
			location.href = "./#!/useredit/" + $scope.selectid;
		}
		$scope.userDelete = function() {
			location.href = "./#!/userdelete/" + $scope.selectid;
		}
		_loader.controller.show();

	});
	_ws.send("usermanagement", "init");
} ]);