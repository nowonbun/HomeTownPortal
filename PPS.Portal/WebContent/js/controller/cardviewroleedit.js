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
	$scope.setValue = function(idx, com, grp, usr) {
		var row;
		if (idx < $("#tableList").children().length) {
			row = $($("#tableList").children()[idx]);
		} else {
			$scope.addRow(true);
			row = $($("#tableList").children()[$("#tableList").children().length - 1]);
		}
		_ws.send("cardviewrole", "getCompany", null, function(data) {
			row.find(".company-select").children().remove();
			var list = JSON.parse(data);
			for (var i = 0; i < list.length; i++) {
				row.find(".company-select").append(createOption(list[i].value, list[i].name));
			}
			row.find(".company-select").val(com);
		});
		_ws.send("cardviewrole", "getGroup", com, function(data) {
			row.find(".group-select").children().remove();
			var list = JSON.parse(data);
			for (var i = 0; i < list.length; i++) {
				row.find(".group-select").append(createOption(list[i].value, list[i].name));
			}
			row.find(".group-select").val(grp);
		});
		_ws.send("cardviewrole", "getUser", grp, function(data) {
			row.find(".user-select").children().remove();
			var list = JSON.parse(data);
			for (var i = 0; i < list.length; i++) {
				row.find(".user-select").append(createOption(list[i].value, list[i].name));
			}
			row.find(".user-select").val(usr);
		});
	}
	$scope.addRow = function(isBtn) {
		var comSel = createSelect("company-select");
		_ws.send("cardviewrole", "getCompany", null, function(data) {
			var list = JSON.parse(data);
			for (var i = 0; i < list.length; i++) {
				comSel.append(createOption(list[i].value, list[i].name));
			}
		});
		comSel.on("change", function() {
			var _this = $(this);
			_ws.send("cardviewrole", "getGroup", _this.val(), function(data) {
				var grpSel = _this.parent().parent().find(".group-select");
				grpSel.children().remove();
				var list = JSON.parse(data);
				for (var i = 0; i < list.length; i++) {
					grpSel.append(createOption(list[i].value, list[i].name));
				}
			});
		});

		var grpSel = createSelect("group-select");
		grpSel.on("change", function() {
			var _this = $(this);
			_ws.send("cardviewrole", "getUser", _this.val(), function(data) {
				var usrSel = _this.parent().parent().find(".user-select");
				usrSel.children().remove();
				var list = JSON.parse(data);
				for (var i = 0; i < list.length; i++) {
					usrSel.append(createOption(list[i].value, list[i].name));
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
		_ws.send("cardviewrole", "saveRole", JSON.stringify(list), function(data) {
			debugger;
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