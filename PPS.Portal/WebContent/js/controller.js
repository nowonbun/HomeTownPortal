app.controller("main", [ '$scope', '_ws', 
	function($scope, _ws) {
	_ws.message(function(msg) {
		var node = JSON.parse(msg.data);
		if(node.key === "login"){
			if(node.data === "NG"){
				location.href="./Logout";
			}
		}
	});
} ]);

app.controller("card", [ '$scope', '$sanitize', '$sce', '_ws',
	function($scope, $sanitize, $sce, _ws) {
	_ws.message(function(msg) {
		var node = JSON.parse(msg.data);
		if(node.key === "card"){
			$scope.cards = JSON.parse(node.data);
			$scope.$apply();
		}
	});

	_ws.send("card", "initialize");
	
	/*$scope.cards = [ {
		typeHeaderClass : "card-image",
		background : "url('https://getmdl.io/assets/demos/image_card.jpg') center / cover",
		header : "",
		border : "",
		body : "<span class='card-image__body'>Image.jpg</span>"
	},
	{
		typeHeaderClass : "card-event",
		background : "#3E4EB8",
		header : "<h4>Featured event:<br>May 24, 2016<br>7-11pm</h4>",
		border : "mdl-card--border",
		body : $sce.trustAsHtml("<a class='mdl-button mdl-button--colored mdl-js-button mdl-js-ripple-effect'>Add to Calendar</a><div class='mdl-layout-spacer'></div><i class='material-icons'>event</i>")
	} ];*/
}]);