app.controller("cardviewroleedit", [ '$scope', '_ws', '_loader', '$http', '_extendModal', '_notification', '$timeout', function($scope, _ws, _loader, $http, _extendModal, _notification, $timeout) {
	_loader.controller.hide();
	$scope.title = "Card view role edit";
	function createSelect(id) {
		var select = $("<select></select>").addClass("browser-default").addClass(id);
		select.append(createOption(0, "ALL"));
		return select;
	}
	function createOption(value, text) {
		var option = $("<option></option>").prop("value", value).text(text);
		return option;
	}
	function createRowDiv() {
		return $("<div></div>").addClass("row grid-table").css("margin", "0px");
	}
	function createColDiv() {
		return $("<div></div>").addClass("col-sm-12 col-md-6 col-lg-3");
	}
	function getRow(idx) {
		if (idx < $("#tableList").children().length) {
			return $($("#tableList").children()[idx]);
		} else {
			$scope.addRow(true);
			return $($("#tableList").children()[$("#tableList").children().length - 1]);
		}
	}
	$scope.setValue = function(idx, com, grp, usr) {
		if (com === null) {
			com = 0;
		}
		if (grp === null) {
			grp = 0;
		}
		if (usr === null) {
			usr = 0;
		}

		_ws.send("cardviewrole", "getCompany", JSON.stringify({
			key : null,
			val : com,
			idx : idx
		}), function(data) {
			var node = JSON.parse(data);
			var row = getRow(node.data3);
			row.find(".company-select").children().remove();
			var list = node.data2;
			var select = row.find(".company-select");
			select.append(createOption(0, "ALL"));
			for (var i = 0; i < list.length; i++) {
				select.append(createOption(list[i].value, list[i].name));
			}
			select.val(node.data);
		});
		_ws.send("cardviewrole", "getGroup", JSON.stringify({
			key : com,
			val : grp,
			idx : idx
		}), function(data) {
			var node = JSON.parse(data);
			var row = getRow(node.data3);
			row.find(".group-select").children().remove();
			var list = node.data2;
			var select = row.find(".group-select");
			select.append(createOption(0, "ALL"));
			for (var i = 0; i < list.length; i++) {
				select.append(createOption(list[i].value, list[i].name));
			}
			select.val(node.data);
		});
		_ws.send("cardviewrole", "getUser", JSON.stringify({
			key : grp,
			val : usr,
			idx : idx
		}), function(data) {
			var node = JSON.parse(data);
			var row = getRow(node.data3);
			row.find(".user-select").children().remove();
			var list = node.data2;
			var select = row.find(".user-select");
			select.append(createOption(0, "ALL"));
			for (var i = 0; i < list.length; i++) {
				select.append(createOption(list[i].value, list[i].name));
			}
			select.val(node.data);
		});
	}
	$scope.addRow = function(isBtn) {
		var comSel = createSelect("company-select");
		_ws.send("cardviewrole", "getCompany", JSON.stringify({}), function(data) {
			var node = JSON.parse(data);
			var list = node.data2;
			for (var i = 0; i < list.length; i++) {
				comSel.append(createOption(list[i].value, list[i].name));
			}
		});
		comSel.on("change", function() {
			var _this = $(this);
			_ws.send("cardviewrole", "getGroup", JSON.stringify({
				key : Number(_this.val())
			}), function(data) {
				var select = _this.parent().parent().find(".group-select");
				select.children().remove();
				var node = JSON.parse(data);
				var list = node.data2;
				select.append(createOption(0, "ALL"));
				for (var i = 0; i < list.length; i++) {
					select.append(createOption(list[i].value, list[i].name));
				}
			});
		});

		var grpSel = createSelect("group-select");
		grpSel.on("change", function() {
			var _this = $(this);
			_ws.send("cardviewrole", "getUser", JSON.stringify({
				key : Number(_this.val())
			}), function(data) {
				var select = _this.parent().parent().find(".user-select");
				select.children().remove();
				var node = JSON.parse(data);
				var list = node.data2;
				select.append(createOption(0, "ALL"));
				for (var i = 0; i < list.length; i++) {
					select.append(createOption(list[i].value, list[i].name));
				}
			});
		});
		var usrSel = createSelect("user-select");

		var dom = createRowDiv();
		dom.append(createColDiv().append(comSel));
		dom.append(createColDiv().append(grpSel));
		dom.append(createColDiv().append(usrSel));
		if (isBtn) {
			var btn = $("<button></button>").addClass("btn btn-danger custom-button row-button").text("Delete");
			btn.on("click", function() {
				$(this).parent().parent().remove();
			});
			dom.append(createColDiv().append(btn));
		}
		$("#tableList").append(dom);
	}
	$scope.roleSave = function() {
		var list = [];
		function checkGroupAll(com, grp, idx) {
			for (var i = 0; i < list.length; i++) {
				if (i === idx) {
					continue;
				}
				if (list[i].com === com) {
					if (list[i].grp === "0" || list[i].grp === grp) {
						return false;
					}
				}
			}
			return true;
		}
		function checkUserAll(com, grp, usr, idx) {
			for (var i = 0; i < list.length; i++) {
				if (i === idx) {
					continue;
				}
				if (list[i].com === com && list[i].grp === grp) {
					if (list[i].usr === "0" || list[i].usr === usr) {
						return false;
					}
				}
			}
			return true;
		}
		$(".company-select").each(function() {
			list.push({
				com : $(this).val(),
				grp : $(this).parent().parent().find(".group-select").val(),
				usr : $(this).parent().parent().find(".user-select").val()
			});
		});
		if (list.length > 1) {
			for (var i = 0; i < list.length; i++) {
				if (list[i].com === "0") {
					_notification("danger", "The role expression is incorrect.");
					$($("#tableList").children()[i]).addClass("error-row");
					$timeout(function() {
						$("#tableList").children().each(function() {
							$(this).removeClass("error-row");
						});
					}, 3000, true);
					return;
				}
				if (!checkGroupAll(list[i].com, list[i].grp, i)) {
					_notification("danger", "The role expression is incorrect.");
					$($("#tableList").children()[i]).addClass("error-row");
					$timeout(function() {
						$("#tableList").children().each(function() {
							$(this).removeClass("error-row");
						});
					}, 3000, true);
					return;
				}
				if (!checkUserAll(list[i].com, list[i].grp, list[i].usr, i)) {
					_notification("danger", "The role expression is incorrect.");
					$($("#tableList").children()[i]).addClass("error-row");
					$timeout(function() {
						$("#tableList").children().each(function() {
							$(this).removeClass("error-row");
						});
					}, 3000, true);
					return;
				}
			}
		}
		_ws.send("cardviewrole", "saveRole", JSON.stringify({
			key : $scope.selectid,
			data : list
		}), function(data) {
			var msg = JSON.parse(data);
			_notification(msg.type, msg.msg);
		});

	}
	$scope.addRow(false);
	_ws.send("cardviewrole", "editRole", $scope.selectid, function(data) {
		var node = JSON.parse(data);
		_loader.ready(function() {
			for (var i = 0; i < node.length; i++) {
				$scope.setValue(i, node[i].company, node[i].group, node[i].user);
			}
			$("#roleEditModal").modal("show");
			$('#roleEditModal').on('hidden.bs.modal', function() {
				_extendModal.mainModal();
			});
		});
		_loader.controller.show();
	});
} ]);