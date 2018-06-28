var app = angular.module('app', [ "ngRoute", "ngSanitize" ]);

app.config(['$routeProvider', '$qProvider', function($routeProvider, $qProvider) {
	$routeProvider.when("/", {
		controller : "card",
		templateUrl : "./views/card.tpl.jsp"
	}).when("/admin", {
		controller : "admin",
		templateUrl : "./views/card.tpl.jsp"
	}).when("/datamastersetting", {
		controller : "datamastersetting",
		templateUrl : "./views/card.tpl.jsp"
	}).when("/cardmastersetting",{
		controller : "cardmastersetting",
		templateUrl : "./views/cardmastersetting.tpl.jsp"
	})
	.when("/usermanagement", {
		controller : "usermanagement",
		templateUrl : "./views/usermanagement.tpl.jsp"
	}).otherwise({
		redirectTo : "/"
	});
}]);

app.directive("navigation", function() {
	return {
		restrict : "A",
		template : "<li ng-repeat='n in navi'><a href='{{n.url}}'>{{n.name}}</a></li>"
	};
});

app.directive("loader", function() {
	return {
		restrict : "A",
		template : "<div class='loader'></div>"
	};
});
