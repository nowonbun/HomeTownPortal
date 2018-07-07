app.controller("comgroupadd", [ '$scope', '_ws', function($scope, _ws) {
	_ws.message("comgroupsetting", "initAdd", function(data) {
		$("#comgroupModal").modal("show");
	});
	_ws.send("comgroupsetting", "initAdd");
} ]);