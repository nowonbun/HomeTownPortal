var app = angular.module('app', ["ngRoute","ngSanitize"]);

app.config(function($routeProvider) {
    $routeProvider
    .when("/", {
    	controller: "card",
        templateUrl : "./views/card.jsp"
    })
    .when("/admin",{
    	templateUrl : "./views/test.jsp"
    })
    .otherwise({
    	redirectTo: "/"
    });
});



/*
app.controller('main', [ '$scope', '_ws', function($scope, _ws) {
	$scope.datatest = "hello world";

	_ws.message(function(msg) {
		$scope.datatest = msg.data;
		$scope.$apply();
	});

	_ws.send("index", "hello world");

	$scope.clickTest = function() {
		$scope.datatest = $scope.datatest;
	}
} ]);


app.controller('aaabbbccc', [ '$scope', function($scope) {

} ]);
app.directive("testaaa", function() {
	return {
		restrict : "C",
		// templateUrl : "test.html"
		template : "I was made in a directive constructor!"
	};
});
*/

