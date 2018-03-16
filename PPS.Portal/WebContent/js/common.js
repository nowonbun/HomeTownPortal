'use strict';
var IncludeJs = window.IncludeJs = function(jsfile) {
	var imported = document.createElement("script");
	imported.src = "./js/" + jsfile + ".js";
	document.body.appendChild(imported);
}
