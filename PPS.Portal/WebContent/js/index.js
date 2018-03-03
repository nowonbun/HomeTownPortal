ctl = {
	wslocation : "index",
	onLoad : function() {
		common.socketSend("init","");
	},
	message : function(node) {
		if (node.key === "login") {
			if (node.data === "NG") {
				location.href = "./login.jsp";
			}
		}
		console.log(node);
	}
};