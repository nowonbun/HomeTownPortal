var app = angular.module('app', [ "ngRoute", "ngSanitize" ]);

app.config([ '$routeProvider', '$qProvider', function($routeProvider, $qProvider) {
	$routeProvider.when("/", {
		controller : "main",
		templateUrl : "./views/main.tpl.jsp"
	}).when("/cardmastersetting", {
		controller : "cardmastersetting",
		templateUrl : "./views/cardmastersetting.tpl.jsp"
	}).when("/cardviewrole", {
		controller : "cardviewrole",
		templateUrl : "./views/rolelist.tpl.jsp"
	}).when("/actionrole", {
		controller : "actionrole",
		templateUrl : "./views/rolelist.tpl.jsp"
	}).when("/comgroupsetting", {
		controller : "comgroupsetting",
		templateUrl : "./views/comgroupsetting.tpl.jsp"
	}).when("/usermanagement", {
		controller : "usermanagement",
		templateUrl : "./views/usermanagement.tpl.jsp"
	}).otherwise({
		redirectTo : "/"
	});
} ]);

app.directive("navigation", function() {
	return {
		restrict : "A",
		template : "<li ng-repeat='n in navi'><a ng-click='menu(n.menu,n.control,n.template,n.href);'>{{n.name}}</a></li>"
	};
});

app.directive("loader", function() {
	return {
		restrict : "A",
		template : "<div class='loader'></div>"
	};
});

app.filter('trusted', [ '$sce', function($sce) {
	return function(html) {
		return $sce.trustAsHtml(html)
	}
} ]);

app.controller("actionrole", [ '$scope', '_ws', '_loader', '_table', function($scope, _ws, _loader, _table) {
	_loader.controller.hide();
	$scope.title = "Action role";
	_ws.send("actionrole", "init", null, function(data) {
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
		$scope.userAdd = function() {
			// _extendModal.mainModal("./views/profile.tpl.jsp","useradd",$scope);
		}
		$scope.userEdit = function() {
			// _extendModal.mainModal("./views/profile.tpl.jsp","useredit",$scope);
		}
		$scope.userDelete = function() {
			// location.href = "./#!/userdelete/" + $scope.selectid;
		}
		_loader.controller.show();
	});
} ]);
app.controller("admin", [ '$scope', '_ws', '_loader', function($scope, _ws, _loader) {
	_loader.show();
	_ws.send("admin", "init", null, function(data) {
		$scope.cards = JSON.parse(data);
		_loader.hide();
	});
} ]);
app.controller("card", [ '$scope', '_ws', '_loader', function($scope, _ws, _loader) {
	_loader.show();
	_ws.send("card", "init", null, function(data) {
		$scope.cards = JSON.parse(data);
		_loader.hide();
	});
} ]);
app.controller("cardmaster", [ '$scope', '_ws', '_loader', '_safeApply', '_filereader', '_util', '_extendModal', '_notification', function($scope, _ws, _loader, _safeApply, _filereader, _util, _extendModal, _notification) {
	_loader.controller.hide();
	_ws.send("cardmastersetting", "initEdit", $scope.selectid, function(data) {
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
		$scope.sequence = util.parseInt($scope.sequence);
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
		_ws.send("cardmastersetting", "applyEdit", JSON.stringify(data), function(data) {
			var msg = JSON.parse(data);
			_loader.hide();
			_notification(msg.type, msg.msg);
			$scope.reloadTable();
			$("#cardMasterModal").modal("hide");
		});
	}
} ]);
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
app.controller("cardviewrole", [ '$scope', '_ws', '_loader', '_table', '_extendModal', function($scope, _ws, _loader, _table, _extendModal) {
	_loader.controller.hide();
	$scope.title = "View role";
	_ws.send("cardviewrole", "init", null, function(data) {
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
				$scope.selectid = table.rows(idx).data().pluck('code')[0];
				$scope.selectname = table.rows(idx).data().pluck('name')[0];
			},
			deselect : function(table, idx) {
				$("#editbtn").prop("disabled", true);
				$("#deletebtn").prop("disabled", true);
				$scope.selectid = null;
				$scope.selectname = null;
			}
		});
		$scope.roleedit = function() {
			_extendModal.mainModal("./views/roleedit.tpl.jsp", "cardviewroleedit", $scope);
		}
		_loader.controller.show();
	});
} ]);
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
app.controller("comgroupadd", [ '$scope', '_ws', '_loader', '_extendModal', '_safeApply', '_notification', function($scope, _ws, _loader, _extendModal, _safeApply, _notification) {
	_loader.show();
	function getNamefromValue(list, val) {
		if (list === undefined || list === null) {
			return null;
		}
		for (var i = 0; i < list.length; i++) {
			if (list[i].value === val) {
				return list[i].name;
			}
		}
		return null;
	}
	function getValuefromName(list, val) {
		if (list === undefined || list === null) {
			return null;
		}
		for (var i = 0; i < list.length; i++) {
			if (list[i].name === val) {
				return list[i].value;
			}
		}
		return null;
	}
	function hasGetName(list, val) {
		if (list === undefined || list === null) {
			return false;
		}
		for (var i = 0; i < list.length; i++) {
			if (list[i].name === val) {
				return true;
			}
		}
		return false;
	}
	$scope.applyView = true;
	$scope.dataCheck = function(isGetGroup) {
		if (isGetGroup) {
			if (hasGetName($scope.companyList, $scope.companyIn)) {
				_loader.show();
				_ws.send("comgroupsetting", "getGroup", getValuefromName($scope.companyList, $scope.companyIn), function(data) {
					var node = JSON.parse(data);
					$scope.groupList = node;
					$(document).off("change", "#group").on("change", "#group", function() {
						_safeApply(function() {
							$scope.group = $("#group").val();
							$scope.groupIn = getNamefromValue($scope.groupList, $("#group").val());
							$scope.dataCheck(false);
						});
					});
					_loader.ready(function() {
						$('.mdb-select').material_select('destroy');
						$('.mdb-select').material_select();
						_loader.hide();
					});
				});
			}
		}
		if ($.trim($scope.companyIn) === "") {
			$scope.applyView = true;
		} else if ($.trim($scope.groupIn) === "") {
			$scope.applyView = true;
		} else if (hasGetName($scope.companyList, $scope.companyIn) && hasGetName($scope.groupList, $scope.groupIn)) {
			$scope.applyView = true;
		} else {
			$scope.applyView = false;
		}
	}
	$scope.apply = function() {
		if ($.trim($scope.companyIn) === "") {
			_notification("danger", "Please input the text of company.", function() {
				$("#companyIn").removeClass('error-focus');
			});
			$("#companyIn").addClass('error-focus');
			return;
		}
		if ($.trim($scope.groupIn) === "") {
			_notification("danger", "Please input the text of group.", function() {
				$("#groupIn").removeClass('error-focus');
			});
			$("#groupIn").addClass('error-focus');
			return;
		}
		_loader.show();
		_ws.send("comgroupsetting", "applyAdd", JSON.stringify({
			company : $scope.companyIn,
			group : $scope.groupIn
		}), function(data) {
			var msg = JSON.parse(data);
			_loader.hide();
			_notification(msg.type, msg.msg);
			if (msg.type === "success") {
				$scope.reloadTable();
				$("#comgroupModal").modal("hide");
			}
		});
	}
	_ws.send("comgroupsetting", "initAdd", null, function(data) {
		var node = JSON.parse(data);
		$scope.companyList = node;
		$(document).off("change", "#company").on("change", "#company", function() {
			_safeApply(function() {
				$scope.company = $("#company").val();
				$scope.companyIn = getNamefromValue($scope.companyList, $("#company").val());
				$scope.dataCheck(true);
			});
		});
		_loader.ready(function() {
			$('.mdb-select').material_select();
			$("#comgroupModal").modal("show");
			$('#comgroupModal').on('hidden.bs.modal', function() {
				_extendModal.mainModal();
			});
			_loader.hide();
		});
	});
} ]);
app.controller("comgroupedit", [ '$scope', '_ws', '_loader', '_extendModal', '_safeApply', '_notification', function($scope, _ws, _loader, _extendModal, _safeApply, _notification) {
	_loader.show();
	_ws.send("comgroupsetting", "initEdit", $scope.selectid, function(data) {
		var node = JSON.parse(data);
		$scope.companyIn = node.name;
		$scope.groupIn = node.groupname;
		$scope.editView = true;
		_loader.ready(function() {
			$("#companyIn_label").addClass("active");
			$("#groupIn_label").addClass("active");
			$("#comgroupModal").modal("show");
			$('#comgroupModal').on('hidden.bs.modal', function() {
				_extendModal.mainModal();
			});
			_loader.hide();
		});
	});
	$scope.edit = function() {
		if ($.trim($scope.companyIn) === "") {
			_notification("danger", "Please input the text of company.", function() {
				$("#companyIn").removeClass('error-focus');
			});
			$("#companyIn").addClass('error-focus');
			return;
		}
		if ($.trim($scope.groupIn) === "") {
			_notification("danger", "Please input the text of group.", function() {
				$("#groupIn").removeClass('error-focus');
			});
			$("#groupIn").addClass('error-focus');
			return;
		}
		_loader.show();
		_ws.send("comgroupsetting", "applyEdit", JSON.stringify({
			id : $scope.selectid,
			company : $scope.companyIn,
			group : $scope.groupIn
		}), function(data) {
			var msg = JSON.parse(data);
			_loader.hide();
			_notification(msg.type, msg.msg);
			if (msg.type === "success") {
				$scope.reloadTable();
				$("#comgroupModal").modal("hide");
			}
		});
	}
} ]);
app.controller("comgroupsetting", [ '$scope', '_ws', '_loader', '_safeApply', '_extendModal', '_notification', '_table', function($scope, _ws, _loader, _safeApply, _extendModal, _notification, _table) {
	_loader.controller.hide();
	_ws.send("comgroupsetting", "init", null, function(data) {
		var table = _table({
			element : "#tablelist",
			url : JSON.parse(data).data,
			columns : [ "id", "name", "groupname", "active" ],
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
		$scope.userAdd = function() {
			_extendModal.mainModal("./views/comgroupadd.tpl.jsp", "comgroupadd", $scope);
		}
		$scope.userEdit = function() {
			_extendModal.mainModal("./views/comgroupedit.tpl.jsp", "comgroupedit", $scope);
		}
		$scope.userDelete = function() {
			_loader.show();
			_ws.send("comgroupsetting", "delete", $scope.selectid, function(data) {
				var msg = JSON.parse(data);
				_loader.hide();
				_notification(msg.type, msg.msg);
				$scope.reloadTable();
				$("#deleteModal").modal("hide");
			});
		}
		$scope.reloadTable = function() {
			table.ajax.reload();
		}
		_loader.controller.show();
	});
} ]);
app.controller("datamastersetting", [ '$scope', '_ws', '_loader', '_notification', function($scope, _ws, _loader, _notification) {
	_loader.show();
	_ws.send("datamastersetting", "init", null, function(data) {
		$scope.cards = JSON.parse(data);
		_loader.hide();
	});
} ]);
app.controller("main", [ '$scope', '_ws', '_loader', function($scope, _ws, _loader) {
	_loader.controller.hide();
	_ws.send("main", "init", null, function(data) {
		_loader.controller.show();
	});
} ]);
app.controller("navigate", [ '$scope', '_ws', function($scope, _ws) {
	_ws.message("navigate", "init", function(data) {
		$scope.navi = JSON.parse(data);
	});
} ]);
app.controller("profile", [ '$scope', '_ws', '_notification', '_filereader', '_loader', '_safeApply', '_extendModal', function($scope, _ws, _notification, _filereader, _loader, _safeApply, _extendModal) {
	_loader.controller.hide();
	$scope.title = "Profile";
	$scope.canUserId = false;
	_ws.send("profile", "init", null, function(data) {
		var node = JSON.parse(data);
		$scope.given_name = node.given_name;
		if ($scope.given_name !== null) {
			$("#given_name_label").addClass("active");
		}
		$scope.name = node.name;
		if ($scope.name !== null) {
			$("#name_label").addClass("active");
		}
		$scope.nick_name = node.nick_name;
		if ($scope.nick_name !== null) {
			$("#nick_name_label").addClass("active");
		}
		if (node.is_img_blob) {
			if ($scope.is_img_blob == null) {
				node.is_img_blob = false;
				$scope.img_url = CONTENTS + "no_photo.png";
			} else {
				$scope.is_img_blob = true;
			}
		} else {
			$scope.is_img_blob = false;
			if ($scope.img_url == null) {
				$scope.img_url = CONTENTS + "no_photo.png";
			} else {
				$scope.img_url = node.img_url;
			}
		}
		$scope.canCorrentPassword = node.canModifyPassword;
		$scope.canModifyPassword = node.canModifyPassword;
		$scope.canModifyCompany = node.canModifyCompany;
		if (node.canModifyCompany) {
			$scope.companyList = node.companyList;
			$scope.company = node.company;
			$(document).off("change", "#company").on("change", "#company", function() {
				_safeApply(function() {
					$scope.company = $("#company").val();
				});
			});
		}
		$scope.canModifyGroup = node.canModifyGroup;
		if (node.canModifyGroup) {
			$scope.groupList = node.groupList;
			$scope.group = node.group;
			$(document).off("change", "#group").on("change", "#group", function() {
				_safeApply(function() {
					$scope.group = $("#group").val();
				});
			});
		}

		_loader.ready(function() {
			if (node.canModifyCompany) {
				$("#company").val(node.company);
			}
			if (node.canModifyGroup) {
				$("#group").val(node.group);
			}
			$('.mdb-select').material_select();
			$("#profileModal").modal("show");
			$('#profileModal').on('hidden.bs.modal', function() {
				_extendModal.mainModal();
			});
		});
		_loader.controller.show();
	});
	_ws.message("profile", "apply", function(data) {
		var msg = JSON.parse(data);
		_loader.hide();
		_notification(msg.type, msg.msg);
	});

	$scope.fileupload = function() {
		var file = _filereader.getFile($("#img_file"));
		_filereader.readFile(file, function(node) {
			$scope.img_url = node.binary;
			$scope.is_img_blob = true;
		});
	}

	$scope.checkPassword = function() {
		if ($.trim($scope.password) !== $.trim($scope.password_confirm)) {
			$scope.isPasswordError = true;
			$("#password").addClass('error-focus');
			$("#password_confirm").addClass('error-focus');
			return;
		}
		$scope.isPasswordError = false;
		$("#password").removeClass('error-focus');
		$("#password_confirm").removeClass('error-focus');
	}

	$scope.apply = function() {
		if ($.trim($scope.given_name) === "") {
			_notification("danger", "Please input the text of 'Given name'", function() {
				$("#given_name").removeClass('error-focus');
			});
			$("#given_name").addClass('error-focus');
			return;
		}
		$("#given_name").removeClass('error-focus');

		if ($.trim($scope.name) === "") {
			_notification("danger", "Please input the text of 'name'", function() {
				$("#name").removeClass('error-focus');
			});
			$("#name").addClass('error-focus');
			return;
		}
		$("#name").removeClass('error-focus');

		if ($.trim($scope.nick_name) === "") {
			_notification("danger", "Please input the text of 'Nick name'", function() {
				$("#nick_name").removeClass('error-focus');
			});
			$("#nick_name").addClass('error-focus');
			return;
		}
		$("#nick_name").removeClass('error-focus');

		if ($scope.canModifyPassword) {
			var noChangePassword = true;
			if ($.trim($scope.current_password) === "") {
				noChangePassword = false;
			}
			if ($.trim($scope.password) === "" && noChangePassword) {
				_notification("danger", "Please input the text of 'Password'", function() {
					$("#password").removeClass('error-focus');
				});
				$("#password").addClass('error-focus');
				return;
			}
			$("#password").removeClass('error-focus');
			if ($.trim($scope.password_confirm) === "" && noChangePassword) {
				_notification("danger", "Please input the text of 'Password confirm'", function() {
					$("#password_confirm").removeClass('error-focus');
				});
				$("#password_confirm").addClass('error-focus');
				return;
			}
			$("#password_confirm").removeClass('error-focus');
			if ($.trim($scope.password) !== $.trim($scope.password_confirm) && noChangePassword) {
				_notification("danger", "Please input the text of 'Password is incorrect'", function() {
					$("#password").removeClass('error-focus');
					$("#password_confirm").removeClass('error-focus');
				});
				$("#password").addClass('error-focus');
				$("#password_confirm").addClass('error-focus');
				return;
			}
			$("#password").removeClass('error-focus');
			$("#password_confirm").removeClass('error-focus');
		}

		if ($scope.canModifyCompany) {
			if ($.trim($scope.company) === "") {
				_notification("danger", "Please select the value of 'company'", function() {
					$("#company").removeClass('error-focus');
				});
				$("#company").addClass('error-focus');
				return;
			}
			$("#company").removeClass('error-focus');
		}

		if ($scope.canModifyGroup) {
			if ($.trim($scope.group) === "") {
				_notification("danger", "Please select the value of 'group'", function() {
					$("#group").removeClass('error-focus');
				});
				$("#group").addClass('error-focus');
				return;
			}
			$("#group").removeClass('error-focus');
		}

		var data = {
			given_name : $scope.given_name,
			name : $scope.name,
			nick_name : $scope.nick_name,
			img_url : $scope.img_url,
			is_img_blob : $scope.is_img_blob,
			current_password : $scope.current_password,
			password : $scope.password,
			company : $scope.company,
			group : $scope.group
		};
		_loader.show();
		_ws.send("profile", "apply", JSON.stringify(data));
		return;
	}
} ]);
app.run([ '$rootScope', '_ws', '_loader', '_notification', '_extendModal', '$timeout', '$http', '$compile', function($rootScope, _ws, _loader, _notification, _extendModal, $timeout, $http, $compile) {
	_ws.message("login", "init", function(data) {
		if (data === "NG") {
			location.href = "./Logout";
		}
	});
	_ws.message("login", "permission", function(data) {
		var node = JSON.parse(data);
		_notification(node.type, node.msg);
		location.href = "./#!/";
		return;
	});
	_ws.open(function() {
		// _notification("success", "The Websocket connection success.");
	});
	_ws.close(function() {
		_notification("danger", "The Websocket connection was lost.");
		function reloadNotice() {
			_notification("danger", "It's reloading Now.");
			location.reload();
		}
		$timeout(reloadNotice, 1000, true);
	});
	$rootScope.profileEdit = function() {
		$('#navitoggler').click();
		_extendModal.mainModal("./views/profile.tpl.jsp", "profile", null, null);
	}
	$rootScope.menu = function(ismenu, control, template, href) {
		if (!ismenu) {
			$(".menu-backdrop").click();
			location.href = href;
			return;
		}
		_extendModal.menuModal(template, control);
	}
} ]);
app.controller("useradd", [ '$scope', '_ws', '_loader', '_filereader', '_safeApply', '_notification', function($scope, _ws, _loader, _filereader, _safeApply, _notification) {
	_loader.controller.hide();
	$scope.title = "User Management";
	$scope.canUserId = true;
	$scope.canCorrentPassword = false;
	$scope.canModifyPassword = true;
	$scope.canModifyCompany = true;
	$scope.canModifyGroup = true;

	_ws.send("usermanagement", "initAdd", null, function(data) {
		var node = JSON.parse(data);
		$scope.img_url = CONTENTS + "no_photo.png";
		$scope.companyList = node.companyList;
		$(document).off("change", "#company").on("change", "#company", function() {
			_safeApply(function() {
				$scope.company = $("#company").val();
			});
		});
		$scope.groupList = node.groupList;
		$(document).off("change", "#group").on("change", "#group", function() {
			_safeApply(function() {
				$scope.group = $("#group").val();
			});
		});
		_loader.ready(function() {
			$('.mdb-select').material_select();
			$("#profileModal").modal("show");
			$('#profileModal').on('hidden.bs.modal', function() {
				_extendModal.mainModal();
			});
		});
		_loader.controller.show();
	});

	$scope.fileupload = function() {
		var file = _filereader.getFile($("#img_file"));
		_filereader.readFile(file, function(node) {
			$scope.img_url = node.binary;
			$scope.is_img_blob = true;
		});
	}

	$scope.checkPassword = function() {
		if ($.trim($scope.password) !== $.trim($scope.password_confirm)) {
			$scope.isPasswordError = true;
			$("#password").addClass('error-focus');
			$("#password_confirm").addClass('error-focus');
			return;
		}
		$scope.isPasswordError = false;
		$("#password").removeClass('error-focus');
		$("#password_confirm").removeClass('error-focus');
	}

	$scope.apply = function() {
		if ($.trim($scope.uid) === "") {
			_notification("danger", "Please input the text of 'User Id'", function() {
				$("#uid").removeClass('error-focus');
			});
			$("#uid").addClass('error-focus');
			return;
		}
		if ($.trim($scope.given_name) === "") {
			_notification("danger", "Please input the text of 'Given name'", function() {
				$("#given_name").removeClass('error-focus');
			});
			$("#given_name").addClass('error-focus');
			return;
		}
		$("#given_name").removeClass('error-focus');

		if ($.trim($scope.name) === "") {
			_notification("danger", "Please input the text of 'name'", function() {
				$("#name").removeClass('error-focus');
			});
			$("#name").addClass('error-focus');
			return;
		}
		$("#name").removeClass('error-focus');

		if ($.trim($scope.nick_name) === "") {
			_notification("danger", "Please input the text of 'Nick name'", function() {
				$("#nick_name").removeClass('error-focus');
			});
			$("#nick_name").addClass('error-focus');
			return;
		}
		$("#nick_name").removeClass('error-focus');

		var noChangePassword = true;
		if ($.trim($scope.password) === "" && noChangePassword) {
			_notification("danger", "Please input the text of 'Password'", function() {
				$("#password").removeClass('error-focus');
			});
			$("#password").addClass('error-focus');
			return;
		}
		$("#password").removeClass('error-focus');
		if ($.trim($scope.password_confirm) === "" && noChangePassword) {
			_notification("danger", "Please input the text of 'Password confirm'", function() {
				$("#password_confirm").removeClass('error-focus');
			});
			$("#password_confirm").addClass('error-focus');
			return;
		}
		$("#password_confirm").removeClass('error-focus');
		if ($.trim($scope.password) !== $.trim($scope.password_confirm) && noChangePassword) {
			_notification("danger", "Please input the text of 'Password is incorrect'", function() {
				$("#password").removeClass('error-focus');
				$("#password_confirm").removeClass('error-focus');
			});
			$("#password").addClass('error-focus');
			$("#password_confirm").addClass('error-focus');
			return;
		}
		$("#password").removeClass('error-focus');
		$("#password_confirm").removeClass('error-focus');

		if ($.trim($scope.company) === "") {
			_notification("danger", "Please select the value of 'company'", function() {
				$("#company").removeClass('error-focus');
			});
			$("#company").addClass('error-focus');
			return;
		}
		$("#company").removeClass('error-focus');

		if ($.trim($scope.group) === "") {
			_notification("danger", "Please select the value of 'group'", function() {
				$("#group").removeClass('error-focus');
			});
			$("#group").addClass('error-focus');
			return;
		}
		$("#group").removeClass('error-focus');

		var data = {
			uid : $scope.uid,
			given_name : $scope.given_name,
			name : $scope.name,
			nick_name : $scope.nick_name,
			img_url : $scope.img_url,
			is_img_blob : $scope.is_img_blob,
			password : $scope.password,
			company : $scope.company,
			group : $scope.group
		};
		_loader.show();
		_ws.send("usermanagement", "addUser", JSON.stringify(data), function(data) {
			var msg = JSON.parse(data);
			_loader.hide();
			_notification(msg.type, msg.msg);
			location.href = "./#!/usermanagement";
		});
		return;
	}
} ]);
app.controller("useredit", [ '$scope', '_ws', '_loader', '_safeApply', '_extendModal', function($scope, _ws, _loader, _safeApply, _extendModal) {
	_loader.controller.hide();
	$scope.title = "User Management";
	$scope.canCorrentPassword = false;
	_ws.send("usermanagement", "initEdit", $scope.selectid, function(data) {
		var node = JSON.parse(data);
		$scope.given_name = node.given_name;
		if ($scope.given_name !== null) {
			$("#given_name_label").addClass("active");
		}
		$scope.name = node.name;
		if ($scope.name !== null) {
			$("#name_label").addClass("active");
		}
		$scope.nick_name = node.nick_name;
		if ($scope.nick_name !== null) {
			$("#nick_name_label").addClass("active");
		}
		if (node.is_img_blob) {
			if ($scope.is_img_blob == null) {
				node.is_img_blob = false;
				$scope.img_url = CONTENTS + "no_photo.png";
			} else {
				$scope.is_img_blob = true;
			}
		} else {
			$scope.is_img_blob = false;
			if ($scope.img_url == null) {
				$scope.img_url = CONTENTS + "no_photo.png";
			} else {
				$scope.img_url = node.img_url;
			}
		}
		$scope.canModifyPassword = node.canModifyPassword;
		$scope.canModifyCompany = node.canModifyCompany;
		if (node.canModifyCompany) {
			$scope.companyList = node.companyList;
			$scope.company = node.company;
			$(document).off("change", "#company").on("change", "#company", function() {
				_safeApply(function() {
					$scope.company = $("#company").val();
				});
			});
		}
		$scope.canModifyGroup = node.canModifyGroup;
		if (node.canModifyGroup) {
			$scope.groupList = node.groupList;
			$scope.group = node.group;
			$(document).off("change", "#group").on("change", "#group", function() {
				_safeApply(function() {
					$scope.group = $("#group").val();
				});
			});
		}
		_loader.ready(function() {
			if (node.canModifyCompany) {
				$("#company").val(node.company);
			}
			if (node.canModifyGroup) {
				$("#group").val(node.group);
			}
			$('.mdb-select').material_select();
			$("#profileModal").modal("show");
			$('#profileModal').on('hidden.bs.modal', function() {
				_extendModal.mainModal();
			});
		});
		_loader.controller.show();
	});
} ]);
app.controller("usermanagement", [ '$scope', '_ws', '_loader', '_safeApply', '_extendModal', '_notification', '_table', function($scope, _ws, _loader, _safeApply, _extendModal, _notification, _table) {
	_loader.controller.hide();
	_ws.send("usermanagement", "init", null, function(data) {
		var table = _table({
			element : "#tablelist",
			url : JSON.parse(data).data,
			columns : [ "id", "given_name", "name", "nick_name", "company_name", "group_name", "type", "active" ],
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
		$scope.userAdd = function() {
			_extendModal.mainModal("./views/profile.tpl.jsp", "useradd", $scope);
		}
		$scope.userEdit = function() {
			_extendModal.mainModal("./views/profile.tpl.jsp", "useredit", $scope);
		}
		$scope.userDelete = function() {
			// location.href = "./#!/userdelete/" + $scope.selectid;
		}
		_loader.controller.show();

	});
} ]);
app.factory('_filereader', [ '$rootScope', '_safeApply', function($rootScope, _safeApply) {
	read = function(file, cb) {
		node = new function() {
			return {
				reader : new FileReader(),
				file : file
			}
		};
		node.reader.onload = function(e) {
			node.binary = this.result;
			if (cb !== null && cb !== undefined && typeof cb === "function") {
				_safeApply(function() {
					cb.call(this, node);
				});
				return;
			}
		}
		node.reader.readAsDataURL(file);
		return node;
	}
	copyArray = function(source, sourceIdx, destination, destinationIdx, length) {
		for (var i = sourceIdx, j = destinationIdx; i < sourceIdx + length; i++, j++) {
			destination[j] = source[i];
		}
		return destination;
	}
	return {
		getFile : function(element) {
			return $(element)[0].files[0];
		},
		readFile : function(file, cb) {
			return read(file, cb);
		},
	}
} ]);
app.service('_loader', [ '$rootScope', '_safeApply', function($rootScope, _safeApply) {
	$rootScope.isController = true;
	$rootScope.isLoader = false;
	return {
		show : function() {
			_safeApply(function() {
				$rootScope.isLoader = true;
			});
		},
		hide : function() {
			_safeApply(function() {
				$rootScope.isLoader = false;
			});
		},
		controller : {
			show : function() {
				_safeApply(function() {
					$rootScope.isController = true;
					$rootScope.isLoader = false;
				});
			},
			hide : function() {
				_safeApply(function() {
					$rootScope.isController = false;
					$rootScope.isLoader = true;
				});
			}
		},
		ready : function(fn) {
			if (fn && typeof fn === 'function') {
				$(fn);
			}
		}
	}
} ]);

app.service('_safeApply', [ '$rootScope', function($rootScope) {
	return function(fn) {
		var phase = $rootScope.$$phase;
		if (phase == '$apply' || phase == '$digest') {
			if (util.isFunction(fn)) {
				fn.call(this);
			}
		} else {
			$rootScope.$apply(fn);
		}
	}
} ]);

app.service('_notification', [ '$rootScope', '$timeout', '_safeApply', function($rootScope, $timeout, _safeApply) {
	return function(type, msg, cb) {
		_safeApply(function() {
			if (type !== "success" && type !== "warning") {
				type = "danger";
			}
			var title = $("<span></span>");
			if (type === "danger" || type === "warning") {
				title.append($("<i></i>").addClass("fa fa-warning"));
			}
			title.append("&nbsp;" + type.toUpperCase());
			var button = $("<button></button>").addClass("close").append($("<span></span>").attr("aria-hidden", "true").html("&times;"));
			var header = $("<span></span>").css("display", "block").append(title).append(button);
			var body = $("<div></div>").addClass("alert alert-" + type).append(header).append($("<span></span>").addClass("glyphicon glyphicon-exclamation-sign").text(msg));
			var dom = $("<div></div>").addClass("notification-row").append(body);
			if ($("div.notification-row").length > 5) {
				$("div.notification-row")[0].remove();
			}
			$(".notification-zone").append(dom);
			$timeout(function() {
				dom.remove();
				if (util.isFunction(cb)) {
					cb.call(this);
				}
			}, 5000);
		});
	}
} ]);

app.service('_extendModal', [ '$rootScope', '$http', '$compile', '$timeout', function($rootScope, $http, $compile, $timeout) {
	return {
		mainModal : function(templeteUrl, controller, $scope, callback) {
			if (templeteUrl === undefined) {
				var dom = $("#extendModal").attr("ng-controller", "").html("");
				$(".content-wrapper").removeClass("position-fix");
				return;
			}
			$http.get(templeteUrl).then(function(result) {
				var dom = $("#extendModal").attr("ng-controller", controller).html(result.data);
				$(".content-wrapper").addClass("position-fix");
				$compile(dom)($scope == null ? $rootScope : $scope);
				if (util.isFunction(callback)) {
					callback.call(this);
				}
			});
		},
		menuModal : function(templateUrl, control, $scope, callback) {
			var backdrop = $("div.menu-backdrop");
			if (backdrop.length < 1) {
				backdrop = $("<div></div>").addClass("menu-backdrop").addClass("fade").addClass("show");
				backdrop.on("click", function() {
					$(".menu.fade.top").removeClass("show");
					$(".content-wrapper").removeClass("position-fix");
					$("#menuFrame").attr("ng-controller", "").html("");
					$(this).remove();
				});
				$("body").append(backdrop);
			} else {
				$("#menuFrame").off("click");
			}
			$http.get(templateUrl).then(function(result) {
				var dom = $("#menuFrame").attr("ng-controller", control).html(result.data);
				$compile(dom)($scope == null ? $rootScope : $scope);
				$timeout(function() {
					$(".content-wrapper").addClass("position-fix");
					$(".menu.fade.top").addClass("show");
					if (util.isFunction(callback)) {
						callback.call(this);
					}
				}, 1);
				dom.on("click", function() {
					$(".menu-backdrop").click();
				});
			});
		}
	}
} ]);

app.service('_ws', [ '$rootScope', '_safeApply', function($rootScope, _safeApply) {
	var socket = new WebSocket(WS_HOST + "/socket");
	var delegate = {
		open : null,
		close : null,
		error : [],
		message : []
	};
	var delegate2 = [];
	function executeDelegate2(msg) {
		var item = null;
		var node = JSON.parse(msg.data);
		for (var i = 0; i < delegate2.length; i++) {
			if (node.control === delegate2[i].control && node.action === delegate2[i].action) {
				item = delegate2[i];
				delegate2.splice(i, 1);
				break;
			}
		}
		if (item === null) {
			return;
		}

		_safeApply(function() {
			item.func.call(this, node.data);
		});
		if (util.isFunction(item.cb)) {
			item.cb.call(this);
		}
	}

	socket.onopen = function(msg) {
		if (delegate.open === null) {
			return;
		}
		_safeApply(function() {
			delegate.open.func.call(this, msg);
		});
		if (util.isFunction(delegate.open.cb)) {
			delegate.open.cb.call(this);
		}
	};
	socket.onclose = function(msg) {
		if (delegate.close === null) {
			return;
		}
		_safeApply(function() {
			delegate.close.func.call(this, msg);
		});
		if (util.isFunction(delegate.close.cb)) {
			delegate.close.cb.call(this);
		}
	};
	socket.onerror = function(msg) {
		executeDelegate2(msg);
		var item = null;
		var node = JSON.parse(msg.data);
		for (var i = 0; i < delegate.error.length; i++) {
			if (node.control === delegate.error[i].control && node.action === delegate.error[i].action) {
				item = delegate.error[i];
				break;
			}
		}
		if (item === null) {
			return;
		}
		_safeApply(function() {
			item.func.call(this, node.data);
		});
		if (util.isFunction(item.cb)) {
			item.cb.call(this);
		}

	};
	socket.onmessage = function(msg) {
		executeDelegate2(msg);
		var item = null;
		var node = JSON.parse(msg.data);
		for (var i = 0; i < delegate.message.length; i++) {
			if (node.control === delegate.message[i].control && node.action === delegate.message[i].action) {
				item = delegate.message[i];
				break;
			}
		}
		if (item === null) {
			return;
		}
		_safeApply(function() {
			item.func.call(this, node.data);
		});
		if (util.isFunction(item.cb)) {
			item.cb.call(this);
		}
	};
	sendNode = function(node) {
		if (socket.readyState === 1) {
			try {
				socket.send(node);
			} catch (err) {
				console.log(err);
			}
		} else {
			setTimeout(function() {
				sendNode(node);
			}, 1000);
		}
	}
	function define(type, func, cb) {
		if (util.isFunction(func)) {
			delegate[type] = {
				func : func,
				cb : cb
			};
			return;
		}
		console.error("It's not defined because not function method.");
	}
	function define2(type, control, action, func, cb) {
		if (util.isFunction(func)) {
			for ( var i in delegate[type]) {
				var temp = delegate[type][i];
				if (temp.control === control && temp.action === action) {
					temp.func = func;
					return;
				}
			}
			delegate[type].push({
				control : control,
				action : action,
				func : func,
				cb : cb,
				type : 2,
			});
			return;
		}
		console.error("It's not defined because not function method.");
	}
	function define3(control, action, func, cb) {
		if (util.isFunction(func)) {
			delegate2.push({
				control : control,
				action : action,
				func : func,
				cb : cb
			});
			return;
		}
		console.error("It's not defined because not function method.");
	}
	return {
		open : function(func, cb) {
			define("open", func, cb);
		},
		close : function(func, cb) {
			define("close", func, cb);
		},
		error : function(control, action, func, cb) {
			define2("error", control, action, func, cb);
		},
		message : function(control, action, func, cb) {
			define2("message", control, action, func, cb);
		},
		send : function(control, action, data, func, cb) {
			if (data === undefined || data === null) {
				data = "";
			}
			data += "";
			if (util.isFunction(func)) {
				define3(control, action, func, cb);
			}
			sendNode(JSON.stringify({
				control : control,
				action : action,
				data : data
			}));
		}
	}
} ]);

app.service('_table', [ '_safeApply', function(_safeApply) {
	return function(param) {
		function convertColumn(val) {
			if (val == null) {
				return null;
			}
			var ret = [ {
				data : null
			} ];
			for ( var i in val) {
				ret.push({
					data : val[i]
				});
			}
			return ret;
		}
		var op = {
			ajax : {
				url : param.url,
				type : "POST",
				complete : function() {
					if (util.isFunction(param.complete)) {
						param.complete.call(this);
					}
				},
				error : function(xhr, error, thrown) {
					if (util.isFunction(param.error)) {
						param.error.call(this, xhr, error, thrown);
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
			columns : convertColumn(param.columns)
		};
		if (param.option !== undefined) {
			op = $.extend(op, param.option);
		}
		var table = $(param.element).DataTable(op);
		table.on('select', function(e, dt, type, indexes) {
			if (type === 'row') {
				_safeApply(function() {
					if (util.isFunction(param.select)) {
						param.select.call(this, table, indexes);
					}
				});
			}
		});
		table.on('deselect', function(e, dt, type, indexes) {
			if (type === 'row') {
				_safeApply(function() {
					if (util.isFunction(param.deselect)) {
						param.deselect.call(this, table, indexes);
					}
				});
			}
		});
		return table;
	}
} ]);

app.service('_util', [ '_notification', function(_notification) {
	return {
		validateInput : function(val, lbl, name) {
			if ($.trim(val) === "") {
				_notification("danger", "Please input the text of '" + name + "'", function() {
					$(lbl).removeClass('error-focus');
				});
				$(lbl).addClass('error-focus');
				return false;
			}
			$(lbl).removeClass('error-focus');
			return true;
		}
	}
} ]);
