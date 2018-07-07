app.controller("cardmaster", [ '$scope', '_ws', '_loader', '_safeApply', '_filereader', '_util', '_extendModal', '_notification', function($scope, _ws, _loader, _safeApply, _filereader, _util, _extendModal, _notification) {
	_loader.controller.hide();
	_ws.message("cardmastersetting", "initEdit", function(data) {

		function selectType() {
			if ($scope.cardtype === "IMC" || $scope.cardtype === "IMG") {
				$scope.isImageType = true;
				$scope.isEventType = false;
			} else if ($scope.cardtype === "EMC" || $scope.cardtype === "EVT") {
				$scope.isImageType = false;
				$scope.isEventType = true;
			} else {
				$scope.isImageType = false;
				$scope.isEventType = false;
			}
		}
		var node = JSON.parse(data);
		$scope.code = node.code;
		if ($scope.code !== null) {
			$("#code_label").addClass("active");
		}
		$scope.step = node.step;
		if ($scope.step !== null) {
			$("#step_label").addClass("active");
		}
		$scope.controller = node.controller;
		if ($scope.controller !== null) {
			$("#controler_label").addClass("active");
		}
		$scope.template = node.template;
		if ($scope.template !== null) {
			$("#template_label").addClass("active");
		}
		$scope.name = node.name;
		if ($scope.name !== null) {
			$("#name_label").addClass("active");
		}
		$scope.sequence = node.seq;
		if ($scope.sequence !== null) {
			$("#sequence_label").addClass("active");
		}
		$scope.description = node.description;
		if ($scope.description !== null) {
			$("#description_label").addClass("active");
		}
		$scope.cardtypeList = node.cardtypelist;
		$scope.cardtype = node.type;
		selectType();
		$(document).off("change", "#cardtype").on("change", "#cardtype", function() {
			_safeApply(function() {
				$scope.cardtype = $("#cardtype").val();
				selectType();
			});
		});
		if (node.img == null) {
			$scope.img_url = CONTENTS + "no_card.jpg";
		} else {
			$scope.img_url = node.img;
		}
		$scope.title = node.title;
		if ($scope.title !== null) {
			$("#title_label").addClass("active");
		}
		$scope.icon = node.icon;
		if ($scope.icon !== null) {
			$("#icon_label").addClass("active");
		}
		$scope.color = node.color;
		if ($scope.color !== null) {
			$("#color_label").addClass("active");
		}

		_loader.ready(function() {
			$("#cardtype").val(node.type);
			$('.mdb-select').material_select();
			$("#cardMasterModal").modal("show");
			$('#cardMasterModal').on('hidden.bs.modal', function() {
				_extendModal.mainModal();
			});
		});
		_loader.controller.show();
	});
	$scope.fileupload = function() {
		var file = _filereader.getFile($("#img_file"));
		if (file.size > CARD_FILE_SIZE_LIMIT) {
			_notification("danger", "The file maximum size has been exceeded. max-size : 60KB");
			return;
		}
		_filereader.readFile(file, function(node) {
			$scope.img_url = node.binary;
		});
	}
	$scope.apply = function() {
		if (!_util.validateInput($scope.name, "#name", 'Name')) {
			return;
		}
		if (!_util.validateInput($scope.sequence, "#sequence", 'Sequence')) {
			return;
		}
		$scope.sequence = _util.parseInt($scope.sequence);
		if ($scope.img_url == CONTENTS + "no_card.jpg") {
			$scope.img_url = null;
		}
		var data = {
			code : $scope.code,
			name : $scope.name,
			seq : $scope.sequence,
			type : $scope.cardtype,
			title : $scope.title,
			icon : $scope.icon,
			color : $scope.color,
			img : $scope.img_url
		};
		_loader.show();
		_ws.send("cardmastersetting", "applyEdit", JSON.stringify(data));
	}
	_ws.message("cardmastersetting", "applyEdit", function(data) {
		var msg = JSON.parse(data);
		_loader.hide();
		_notification(msg.type, msg.msg);
		$scope.reloadTable();
		$("#cardMasterModal").modal("hide");
	});
	_ws.send("cardmastersetting", "initEdit", $scope.selectid);
} ]);