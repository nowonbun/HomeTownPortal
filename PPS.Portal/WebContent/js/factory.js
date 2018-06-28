app.factory('_filereader', [ '$rootScope', '_safeApply', function($rootScope, _safeApply) {
	read = function(file, cb) {
		node = new function() {
			return {
				reader : new FileReader(),
				file : file
			}
		};
		node.reader.onload = function(e) {
			node.binary = this.result;
			if (cb !== null && cb !== undefined && typeof cb === "function") {
				_safeApply(function() {
					cb.call(this, node);
				});
				return;
			}
		}
		node.reader.readAsDataURL(file);
		return node;
	}
	copyArray = function(source, sourceIdx, destination, destinationIdx, length) {
		for (var i = sourceIdx, j = destinationIdx; i < sourceIdx + length; i++, j++) {
			destination[j] = source[i];
		}
		return destination;
	}
	return {
		getFile : function(element) {
			return $(element)[0].files[0];
		},
		readFile : function(file, cb) {
			return read(file, cb);
		},
	}
} ]);