/*
var ctl = null;
var common = (function(obj) {
	$(obj.onLoad);
	return obj;
})({
	onLoad: function() {
		if(ctl === undefined || typeof ctl !== "object") {
			//console.error("declare abstract controller");
			return;
		}
		if(ctl.onLoad !== undefined && typeof ctl.onLoad === "function") {
			ctl.onLoad.call(this);
		}
	}
});
*/