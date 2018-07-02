app.controller("cardmaster", [ '$scope', '_ws', '_loader', '_safeApply', function($scope, _ws, _loader, _safeApply) {
	_loader.controller.hide();
	_ws.message("cardmastersetting", "initEdit", function(data) {
		
		var node = JSON.parse(data);
		$scope.code = node.code;
		if ($scope.code !== null) {
			$("#code_label").addClass("active");
		}
		$scope.step = node.step;
		if($scope.step !== null) {
			$("#step_label").addClass("active");
		}
		$scope.controller = node.controller;
		if($scope.controller !== null) {
			$("#controler_label").addClass("active");
		}
		$scope.template = node.template;
		if($scope.template !== null) {
			$("#template_label").addClass("active");
		}
		$scope.name = node.name;
		if($scope.name !== null) {
			$("#name_label").addClass("active");
		}
		$scope.sequence = node.seq;
		if($scope.sequence !== null) {
			$("#sequence_label").addClass("active");
		}
		$scope.description = node.description;
		if($scope.description !== null) {
			$("#description_label").addClass("active");
		}
		$scope.cardtypeList = node.cardtypelist;
		$scope.cardtype = node.type;
		$(document).off("change", "#cardtype").on("change", "#cardtype", function() {
			_safeApply(function() {
				$scope.cardtype = $("#cardtype").val();
			});
		});
		if($scope.img == null){
			$scope.img_url = CONTENTS + "no_photo.png";
		} else {
			$scope.img_url = node.img_url;
		}
		$scope.title = node.title;
		if($scope.title !== null) {
			$("#title_label").addClass("active");
		}
		$scope.icon = node.icon;
		if($scope.icon !== null) {
			$("#icon_label").addClass("active");
		}
		$scope.color = node.color;
		if($scope.color !== null) {
			$("#color_label").addClass("active");
		}
		
		_loader.ready(function() {
			$("#cardtype").val(node.type);
			$('.mdb-select').material_select();
			$("#cardMasterModal").modal("show");
		});
		_loader.controller.show();
	});
	_ws.send("cardmastersetting", "initEdit", $scope.selectid);
} ]);