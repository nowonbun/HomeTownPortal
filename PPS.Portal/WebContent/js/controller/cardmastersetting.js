app.controller("cardmastersetting", [ '$scope', '_ws', '_loader','_safeApply', '_extendModal', '_notification', 
	function($scope, _ws, _loader,_safeApply, _extendModal, _notification) {
	_loader.controller.hide();
	_ws.message("cardmastersetting", "init", function(data) {
		var node = JSON.parse(data);
		var table = $("#tablelist").DataTable({
			ajax : {
				url : node.data,
				type : "POST",
				complete : function() {
					$("#tablelist_wrapper").addClass("box-shadow-0");
					$("#tablelist").css("width", "");
					$("#tablelist thead th").css("width", "");
				},
				error : function(xhr, error, thrown) {
					if(xhr.status === 401){
						_notification("danger", "You don't have permission.");
						location.href = "./#!/"
					}
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
				{ data : "code" }, 
				{ data : "name" }, 
				{ data : "title" }, 
				{ data : "description" }, 
				{ data : "hasImg" }, 
				{ data : "icon" }, 
				{ data : "color" }, 
				{ data : "type" }, 
				{ data : "step" },
				{ data : "seq" } ]
		});
		table.on('select', function(e, dt, type, indexes) {
			if (type === 'row') {
				_safeApply(function() {
					$("#editbtn").prop("disabled", false);
					$scope.selectid = table.rows(indexes).data().pluck('code')[0];
				});
			}
		});
		table.on('deselect', function(e, dt, type, indexes) {
			if (type === 'row') {
				_safeApply(function() {
					$("#editbtn").prop("disabled", true);
					$scope.selectid = null;
				});
			}
		});
		$scope.cardEdit = function() {
			_extendModal("./views/cardmaster.tpl.jsp","cardmaster",$scope);
		}
		$scope.reloadTable = function() {
			table.ajax.reload();
		}
		_loader.controller.show();
	});
	_ws.message("cardmastersetting", "permission", function(data) {
		var node = JSON.parse(data);
		_notification(node.type, node.msg);
		location.href="./#!/";
		return;
	});
	
	_ws.send("cardmastersetting", "init");
} ]);