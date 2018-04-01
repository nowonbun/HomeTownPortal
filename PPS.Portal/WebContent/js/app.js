var app = angular.module('app', ["ngRoute","ngSanitize"]);

app.config(function($routeProvider) {
    $routeProvider
    .when("/", {
    	controller: "card",
        templateUrl: "./views/card.tpl.jsp"
    })
    .when("/admin",{
    	controller: "admin",
    	templateUrl: "./views/admin.tpl.jsp"
    })
    .when("/application",{
    	controller: "application",
    	templateUrl: "./views/application.tpl.jsp"
    })
    .when("/profile",{
    	controller: "profile",
    	templateUrl: "./views/profile.tpl.jsp"
    })
    .otherwise({
    	redirectTo: "/"
    });
});
app.directive("navigation", function() {
    return {
    	restrict : "A",
        template : "<li ng-repeat='n in navi'><a href='{{n.url}}'>{{n.name}}</a></li>"
    };
});