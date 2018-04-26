/**
 * API for connect with api by ajax
 */

var BASE_URL = jsOptions.acscadaUrl;
var BASE_WEB = jsOptions.web;

/**
 * patterns of url and param: 1. param directly append to url like
 * AJAX_GET_ONE('/acscada/xxx/1', '', function(data){}); 2. pass param with
 * key-value, supports multi-param like AJAX_GET_ONE('/acscada/xxx/search/xxx',
 * param, function(data){}); var param = { param1: value1, param1: value1, }
 */
function AJAX_GET_ONE(url, param, callback) {
	if (jsOptions.enabled) {
		$.ajax({
			type : 'GET',
			url : BASE_WEB + url,
			dataType : 'JSON',
			contentType : 'application/json',
			data : param,
			success : function(data) {
				if (callback != null && typeof callback == 'function') {
					callback(data);
				}
			}
		});
	}
}

/**sync**/
function AJAX_GET_ONE_SYNC(url, param, callback) {
	if (jsOptions.enabled) {
		$.ajax({
			type : 'GET',
			url : BASE_WEB + url,
			dataType : 'JSON',
			contentType : 'application/json',
			async:false,
			timeout : 10000, //10 second
			data : param,
			success : function(data) {
				if (callback != null && typeof callback == 'function') {
					callback(data);
				}
			}
		});
	}
}

/**
 * AJAX_DELETE_ONE('/acscada/xxx/1', function(code){}); code 1:success;
 * 0:failure
 */
function AJAX_DELETE_ONE(url, callback) {
	if (jsOptions.enabled) {
		$.ajax({
			type : 'DELETE',
			url : BASE_WEB + url,
			dataType : 'JSON',
			contentType : 'application/json',
			success : function(data) {
				if (callback != null && typeof callback == 'function') {
					callback(1);
				}
			},
			failure : function() {
				if (callback != null && typeof callback == 'function') {
					callback(0);
				}
			}
		});
	}
}

/**
 * param: add object attribute with key-value pattern ; callback: return new
 * added data;AJAX_POST_ONE('/acscada/xxx', param, function(data){})
 */
function AJAX_POST_ONE(url, param, callback) {
	if (jsOptions.enabled) {
		$.ajax({
			type : 'POST',
			url : BASE_WEB + url,
			dataType : 'JSON',
			contentType : 'application/json',
			data : JSON.stringify(param),
			success : function(data) {
				if (callback != null && typeof callback == 'function') {
					callback(data);
				}
			},
			failure : function() {
			}
		});
	}
}

/**
 * async
 * 
 * @param url
 * @param param
 * @param callback
 */
function AJAX_POST_ONE_ASYNC(url, param, callback) {
	if (jsOptions.enabled) {
		$.ajax({
			type : 'POST',
			url : BASE_WEB + url,
			dataType : 'JSON',
			contentType : 'application/json',
			data : JSON.stringify(param),
			success : function(data) {
				if (callback != null && typeof callback == 'function') {
					callback(data);
				}
			},
			failure : function() {
			}
		});
	}
}

/**
 * param: update object attribute with key-value pattern ; callback: return
 * updated data; url should append value of key id like
 * AJAX_PUT_ONE('/acscada/xxx/1', param, function(data){})
 */
function AJAX_PUT_ONE(url, param, callback) {
	if (jsOptions.enabled) {
		$.ajax({
			type : 'PUT',
			url : BASE_WEB + url,
			dataType : 'JSON',
			contentType : 'application/json',
			data : JSON.stringify(param),
			success : function(data) {
				if (callback != null && typeof callback == 'function') {
					callback(data);
				}
			},
			failure : function() {
			}
		});
	}
}

/**
 * async
 * 
 * @param url
 * @param param
 * @param callback
 */
function AJAX_PUT_ONE_ASYNC(url, param, callback) {
	if (jsOptions.enabled) {
		$.ajax({
			type : 'PUT',
			url : BASE_WEB + url,
			async : true,
			dataType : 'JSON',
			contentType : 'application/json',
			data : JSON.stringify(param),
			success : function(data) {
				if (callback != null && typeof callback == 'function') {
					callback(data);
				}
			},
			failure : function() {
			}
		});
	}
}