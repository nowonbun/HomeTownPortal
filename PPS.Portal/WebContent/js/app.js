var app = angular.module('app', [ "ngRoute", "ngSanitize" ]);

app.config(['$routeProvider', '$qProvider', function($routeProvider, $qProvider) {
	$routeProvider.when("/",{
		controller : "main",
		templateUrl : "./views/main.tpl.jsp"
	}).when("/cardmastersetting",{
		controller : "cardmastersetting",
		templateUrl : "./views/cardmastersetting.tpl.jsp"
	}).when("/cardviewrole",{
		controller : "cardviewrole",
		templateUrl : "./views/viewrole.tpl.jsp"
	}).when("/actionrole",{
		controller : "actionrole",
		templateUrl : "./views/viewrole.tpl.jsp"
	}).when("/comgroupsetting",{
		controller : "comgroupsetting",
		templateUrl : "./views/comgroupsetting.tpl.jsp"
	}).when("/usermanagement", {
		controller : "usermanagement",
		templateUrl : "./views/usermanagement.tpl.jsp"
	}).otherwise({
		redirectTo : "/"
	});
}]);

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
