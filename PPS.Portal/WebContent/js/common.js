'use strict';
var IncludeJs = window.IncludeJs = function(jsfile) {
	var imported = document.createElement("script");
	imported.src = "./js/" + jsfile + ".js";
	document.body.appendChild(imported);
}
// Text-area
var autotestarea = function() {
	$(this).css("height", "auto");
	$(this).css("height", this.scrollHeight + "px");
}
$(document).on("keydown", ".md-textarea-auto", autotestarea);
$(document).on("keyup", ".md-textarea-auto", autotestarea);
$(document).on("keypress", ".md-textarea-auto", autotestarea);

var HOST = location.origin + location.pathname;
var WS_HOST = HOST.replace("http", "ws");
var CONTENTS = HOST + "contents/";