var app = angular.module('app', ["ngRoute","ngSanitize"]);

app.config(function($routeProvider) {
    $routeProvider
    .when("/", {
    	controller: "card",
        templateUrl : "./views/card.jsp"
    })
    .when("/admin",{
    	controller: "admin",
    	templateUrl : "./views/admin.jsp"
    })
    .otherwise({
    	redirectTo: "/"
    });
});
app.directive("navigation", function() {
    return {
    	restrict : "E",
        template : "<li ng-repeat='n in navi'><a href='{{n.url}}'>{{n.name}}</a></li>"
    };
});