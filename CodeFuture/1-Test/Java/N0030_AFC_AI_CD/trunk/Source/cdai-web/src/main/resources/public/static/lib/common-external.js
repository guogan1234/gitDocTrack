/*变电器数量 全局使用*/
var voltageCapacityCount = 4;
/* 添加客户时使用的 */
var ownerId = 40080001;
/* 用户登录记录数量 */
var userLoginCount = 5;
/* 提示信息N秒后消失*/
var ALERTMESSAGE = 3000;
var ALERTMESSAGET = 1;

var USER_ID = $('#userId').text();
var USER_LOGICAL_ID = $('#userLogicalId').text();
var USER_ROLE = $('#currentRoles').text();


/**
 * common external js file
 */

Date.prototype.toLocaleFormat = Date.prototype.toLocaleFormat
		|| function(pattern) {
			return pattern.replace(/%Y/g, this.getFullYear()).replace(/%m/g,
					(addZeroForDate(this.getMonth() + 1))).replace(/%d/g,
					addZeroForDate(this.getDate())).replace(/%H/g,
					addZeroForDate(this.getHours())).replace(/%M/g,
					addZeroForDate(this.getMinutes())).replace(/%S/g,
					addZeroForDate(this.getSeconds()));
		};
		
function initInteger(param){
	return param == null ? 0 : param;
}

function initString(param){
	return param == null ? '' : param;
}

function getMaxOfArray(numArray) {
    return Math.max.apply(null, numArray);
}

function getMinOfArray(numArray) {
    return Math.min.apply(null, numArray);
}

function addZeroForDate(d){
	if (d < 10) {
		d = "0" + d;
	}
	return d;
}

function date_time(id) {
	date = new Date;
	year = date.getFullYear();
	month = date.getMonth() + 1;
	d = date.getDate();
	h = date.getHours();
	if (month < 10) {
		month = "0" + month;
	}
	if (h < 10) {
		h = "0" + h;
	}
	m = date.getMinutes();
	if (m < 10) {
		m = "0" + m;
	}
	s = date.getSeconds();
	if (s < 10) {
		s = "0" + s;
	}
	result = month + '-' + d + ' ' + h + ':' + m;
	$('#' + id).html(result);
	setTimeout('date_time("' + id + '");', '1000');
	return true;
}

function getToday(){
	var today = new Date();
	var dd = today.getDate();
	var mm = today.getMonth()+1;
	var yyyy = today.getFullYear();
	if(dd<10) {
	    dd='0'+dd
	} 
	if(mm<10) {
	    mm='0'+mm
	} 
	today = yyyy + '/' + mm+'/' + dd;
	return today;
}

function getTomorrow(){
	var today = new Date();
	var dd = today.getDate() + 1;
	var mm = today.getMonth()+1;
	var yyyy = today.getFullYear();
	if(dd<10) {
	    dd='0'+dd
	} 
	if(mm<10) {
	    mm='0'+mm
	} 
	tomorrow = yyyy + '/' + mm+'/' + dd;
	return tomorrow;
}

function getNextDate(oriDate){
	var today = new Date(oriDate);
	var dd = today.getDate() + 1;
	var mm = today.getMonth()+1;
	var yyyy = today.getFullYear();
	if(dd<10) {
	    dd='0'+dd
	} 
	if(mm<10) {
	    mm='0'+mm
	} 
	tomorrow = yyyy + '/' + mm+'/' + dd;
	return tomorrow;
}

function getMonthCus(year, month){
	var date = new Date(year, month);
	return date.toLocaleFormat('%Y-%m');
}

function convertDateFromTimestamp(data){
	if(data != null){
		var d = new Date(data);
		var gmtHours = d.getTimezoneOffset()/60;
		var localTime = d.getTime();
		var localOffset=d.getTimezoneOffset()*60000;
		var utc = localTime - localOffset;
		var bj = utc + (3600000*gmtHours);
		var nd = new Date(bj);
		data = nd.toLocaleFormat('%Y-%m-%d %H:%M:%S');

		var dataTmp = data.toString().substring(0, 19);
		dataTmp = dataTmp.replace(/T/g,'\ ');
		return dataTmp;
	}
	return data;
}

function convertDateFromTimestamp2(data){
	if(data != null){
		var d = new Date(data);
		var gmtHours = d.getTimezoneOffset()/60;
		var localTime = d.getTime();
		var localOffset=d.getTimezoneOffset()*60000;
		var utc = localTime - localOffset;
		var bj = utc + (3600000*gmtHours);
		var nd = new Date(bj); 
		data = nd.toLocaleFormat('%Y-%m-%d');
		
		var dataTmp = data.toString().substring(0, 19);
		dataTmp = dataTmp.replace(/T/g,'\ ');
		return dataTmp;
	}
	return data;
}

function convertDateFromTimestamp3(data){
	if(data != null){
		var d = new Date(data);
		var gmtHours = d.getTimezoneOffset()/60;
		var localTime = d.getTime();
		var localOffset=d.getTimezoneOffset()*60000;
		var utc = localTime - localOffset;
		var bj = utc + (3600000*gmtHours);
		var nd = new Date(bj); 
		data = nd.toLocaleFormat('%Y-%m');
		
		var dataTmp = data.toString().substring(0, 19);
		dataTmp = dataTmp.replace(/T/g,'\ ');
		return dataTmp;
	}
	return data;
}

function convertDateFromTimestamp4(data){
	if(data != null){
		var d = new Date(data);
		var gmtHours = d.getTimezoneOffset()/60;
		var localTime = d.getTime();
		var localOffset=d.getTimezoneOffset()*60000;
		var utc = localTime - localOffset;
		var bj = utc + (3600000*gmtHours);
		var nd = new Date(bj); 
		data = nd.toLocaleFormat('%Y/%m');
		
		var dataTmp = data.toString().substring(0, 19);
		dataTmp = dataTmp.replace(/T/g,'\ ');
		return dataTmp;
	}
	return data;
}

function convertDateFromTimestamp5(data){
	if(data != null){
		var d = new Date(data);
		var gmtHours = d.getTimezoneOffset()/60;
		var localTime = d.getTime();
		var localOffset=d.getTimezoneOffset()*60000;
		var utc = localTime - localOffset;
		var bj = utc + (3600000*gmtHours);
		var nd = new Date(bj); 
		data = nd.toLocaleFormat('%Y/%m/%d');
		
		var dataTmp = data.toString().substring(0, 19);
		dataTmp = dataTmp.replace(/T/g,'\ ');
		return dataTmp;
	}
	return data;
}

function getLastDay(year, month) {
	var new_year = year; // 取当前的年份
	var new_month = month++;// 取下一个月的第一天，方便计算（最后一天不固定）
	if (month > 12) // 如果当前大于12月，则年份转到下一年
	{
		new_month -= 12; // 月份减
		new_year++; // 年份增
	}
	var new_date = new Date(new_year, new_month, 1); // 取当年当月中的第一天
	return (new Date(new_date.getTime() - 1000 * 60 * 60 * 24)).getDate();// 获取当月最后一天日期
}  

//return timestamp
function getLastDayOfMonth(year, month){
	var day = new Date(year, month, 0);
	var timeStamp = day.getTime();
	return timeStamp;
}

//return timestamp
function getFirstDayOfMonth(year, month){
	var day = new Date(year, month - 1, 1);
	var timeStamp = day.getTime();
	return timeStamp;
}

//date eg 2016-04
function getMdValueFromDate(date, arr){
	var res = 0;
	if(arr){
		$.each(arr, function(i, v){
			if(v.date == date){
				res = v.value;
			}
		})
	}
	return res;
}

function getDeclaredValue(json,key){
	if(isNotEmpty(json.declared)){
		if(json.declared.hasOwnProperty(key)){
			return getRowValue(json.declared[key]);
		}
	}
	return 0;
}


/**********************************************************************************************************************/

/**
 * 判断数据为空（）
 * @param value
 * @returns {string}
 */
function getRowValue(value){
	if(isNotEmpty(value) && "请选择" == value){
		return "";
	}
	return isNotEmpty(value)?value:"";
}



function stringInsertionStrByIndex(str,newStr,index){
	if(isNotEmpty(str)){
		if(str.length > index){
			//var html = $("#dt_test_length").html();
			//var btnHtml = '<button id="redraw" type="button" class="btn btn-success">添加</button>';
			var start = str.substring(0,index);
			var end = str.substring(index, str.length);
			var newHtml = start  + newStr + end;
			//$("#dt_test_length").html(newHtml);
			return start + newStr + end;
		}
	}
}


/**
 * 日志输出
 * @param str
 * @returns
 */
function log(str){
	var date = new Date();
	console.log(date.toLocaleString() + ":" +date.getTime()+ "==>> "+ str)
}

/**
 * 日志输出
 * @param str
 * @returns
 */
function logInfo(obj){
	console.log(obj)
}



/**
 * 时间戳转 时间
 * @param tm
 * @returns {string}
 */
function getTimeToDate(tm){
	if(isNotEmpty(tm)){
		var tt=new Date(tm);
		return tt;
	}
}

/**
 * String转Date
 * @param str
 * @returns {Date}
 */
function getStringToDate(str){
	if(isNotEmpty(str)){
		return new Date(Date.parse(str.replace(/-/g,"/")));
	}
}

/**
 * 获取时间控件的值 ， 时间字符串数组。
 * @param id
 * @returns {Array|*}
 */
function  getDateById(id){
	var j, l, i;
	i = $("#"+id).val();
	l = /^\d{4}-\d{2}-\d{2} \d{2}:\d{2} - \d{4}-\d{2}-\d{2} \d{2}:\d{2}$/;
	if (!i.match(l)) {
		alert("error date range format.");
		return
	}
	return i.split(" - ");
}


/**
 * 判断是否是 Int
 * @param x
 * @returns {boolean}
 */
function isInteger(x) {
	return (x | 0) === x;
}

function isNotEmpty(v) {
	if (null != v && undefined != v && "" != v.toString()) {
		return true;
	}
	return false;
}

/**
 * 获取 当前时间的几分钟前时间
 * @param minute
 * @returns {*}
 */
function getDateMinuteFront(minute) {
	if (isNotEmpty(minute)) {
		date = new Date();
		date.setMinutes(date.getMinutes() - minute);
		return date;
	} else {
		return date = new Date();
	}
}

/**
 * 'YYYY-MM-DD HH:mm:ss'
 * @param time 时间
 * @param key   时间格式
 */
function dateToStr(time, key) {
	return moment(time).format(key);
}

/**
 * 获取当天的最后一秒 2016-5-25 23:59:59
 */
function getDateFinallySeconds(){
	return getDateFinallySeconds(new Date());
}

/**
 * 获取指定最后一秒 2016-5-25 23:59:59
 */
function getDateFinallySeconds(time ){
	return getData(time,23,59,59);
}


/**
 * 获取当前天的 2016-5-25 00:00:00
 */
function getDateFirstSeconds(){
	return getDateFirstSeconds(new Date());
}

/**
 * 获取指定天的 XXXX 00:00:00
 * @param time
 */
function getDateFirstSeconds(time){
	return getData(time,00,00,00);
}

/**
 * 获取指定时间的
 * @param time
 * @param h 小时
 * @param n 分钟
 * @param s 秒
 * @returns {*}
 */
function getData(time , h , n , s){
	time.setHours(h);
	time.setMinutes(n);
	time.setSeconds(s);
	return time;
}

/**
 * 获取两个时间的时差 分
 * @param t1	较大的时间
 * @param t2	较小的时间
 * @returns {number}
 */
function getDateMinuteDifference(t1,t2){
	return (t1.getTime() - t2.getTime()) / 1000 / 60 ;
}


/**
 * uuid
 * @returns {string}
 */
function uuid() {
	var s = [];
	var hexDigits = "0123456789abcdef";
	for (var i = 0; i < 36; i++) {
		s[i] = hexDigits.substr(Math.floor(Math.random() * 0x10), 1);
	}
	s[14] = "4";  // bits 12-15 of the time_hi_and_version field to 0010
	s[19] = hexDigits.substr((s[19] & 0x3) | 0x8, 1);  // bits 6-7 of the clock_seq_hi_and_reserved to 01
	s[8] = s[13] = s[18] = s[23] = "-";

	var uuid = s.join("");
	return uuid;
}


function uuid(len, radix) {
	var chars = '0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz'.split('');
	var uuid = [], i;
	radix = radix || chars.length;

	if (len) {
		// Compact form
		for (i = 0; i < len; i++) uuid[i] = chars[0 | Math.random()*radix];
	} else {
		// rfc4122, version 4 form
		var r;

		// rfc4122 requires these characters
		uuid[8] = uuid[13] = uuid[18] = uuid[23] = '-';
		uuid[14] = '4';

		// Fill in random data.  At i==19 set the high bits of clock sequence as
		// per rfc4122, sec. 4.1.5
		for (i = 0; i < 36; i++) {
			if (!uuid[i]) {
				r = 0 | Math.random()*16;
				uuid[i] = chars[(i == 19) ? (r & 0x3) | 0x8 : r];
			}
		}
	}

	return uuid.join('');
}


function guid() {
	function S4() {
		return (((1+Math.random())*0x10000)|0).toString(16).substring(1);
	}
	return (S4()+S4()+"-"+S4()+"-"+S4()+"-"+S4()+"-"+S4()+S4()+S4());
}


function guidS4() {
	return 'xxxxxxxx-xxxx-4xxx-yxxx-xxxxxxxxxxxx'.replace(/[xy]/g, function(c) {
		var r = Math.random()*16|0, v = c == 'x' ? r : (r&0x3|0x8);
		return v.toString(16);
	});
}


/**
 * 刷新DataTable
 * @param table		DataTable实例
 * @param tableKey	DataTable Html 的Id 或者 class
 * @param url		请求的Url
 * @param dataSrc	数据截取位置。
 * Demo : client.js  reloadDataTable();
 */
function reloadTable(table,tableKey,url,dataSrc) {
	var oldoptions = table.fnSettings();
	var selectName = tableKey + "_length";
	selectName = selectName.substring(1,selectName.size);
	var newoptions = $.extend(oldoptions, {
		"ajax" : {
			url: url,
			dataSrc: dataSrc
		},
		"sServerMethod" : "GET",
		//"fnServerParams" : function(aoData) {
		//	aoData.push({
		//		"name" : "uid",
		//		"value" : userId
		//	}, {
		//		"name" : "propName",
		//		"value" : "circuit-equipment"
		//	});
		//},
		"iDisplayLength" : Number($('select[name='+selectName+']').val()),
		fnRowCallback: function(nRow, aData, iDisplayIndex, iDisplayIndexFull)    {
		}
	})
	table.fnDestroy();
	$(tableKey).dataTable(newoptions);
}

function getToken(){
	$.get('/pqtm/user', function(data){
		_TOKEN = data.details.tokenValue;
	})
}



$(document).ready(function(){
	var user = $('.currentUser').text();
	if(user.length > 9){
		$('.currentUser').text(user.substring(0,5) + '...');
	}
})



/**
 * 自定义 MAP 性能不咋地。勿吐槽
 * put();   				添加数据到MAP 中
 * get();					使用Key 取值
 * keySet();				获取Key集合  Array 类型
 * size()					获取MAP 的长度
 * remove()					移除指定Key 对象
 */
/****************************************************** start MAP  **********************************************************/
function Map(){
	this.container = new Object();
}

Map.prototype.put = function(key, value){
	this.container[key] = value;
}

Map.prototype.get = function(key){
	return this.container[key];
}

Map.prototype.keySet = function() {
	var keyset = new Array();
	var count = 0;
	for (var key in this.container) {
// 跳过object的extend函数
		if (key == 'extend') {
			continue;
		}
		keyset[count] = key;
		count++;
	}
	return keyset;
}

Map.prototype.size = function() {
	var count = 0;
	for (var key in this.container) {
// 跳过object的extend函数
		if (key == 'extend'){
			continue;
		}
		count++;
	}
	return count;
}

Map.prototype.remove = function(key) {
	delete this.container[key];
}
/****************************************************** end MAP  **********************************************************/


/****************************************************** start SELECT2_MULTIPLE  **********************************************************/
function initSelet2Multiple(domId,selections){
	//selections = [
	//    {id:1,text:'Enhancement'},
	//    {id:2,text:'Bug'}
	//];
	var extract_preselected_ids = function(element){
		var preselected_ids = [];
		if(element.val())
			$(element.val().split(",")).each(function () {
				preselected_ids.push({id: this});
			});
		return preselected_ids;
	};

	var preselect = function(preselected_ids){
		var pre_selections = [];
		for(index in selections)
			for(id_index in preselected_ids)
				if (selections[index].id == preselected_ids[id_index].id)
					pre_selections.push(selections[index]);
		return pre_selections;
	};

	$(domId).select2({
		placeholder: '请选择',
		minimumInputLength: 0,
		multiple: true,
		allowClear: true,
		data:selections,
		initSelection: function(element, callback){
			var preselected_ids = extract_preselected_ids(element);
			var preselections = preselect(preselected_ids);
			callback(preselections);
		}
	});
}
/****************************************************** end SELECT2_MULTIPLE  **********************************************************/






function initMyTree(domKey,url,isOpen,pluginsArr,eventClick,eventInsert,eventUpdate){
	_Tree(domKey,url,isOpen,pluginsArr);
	function _Tree(domKey,url,isOpen,pluginsArr){
		$(domKey).jstree({
			"core": {
				"animation": 0,
				"check_callback": true,
				//"themes": {"stripes": true},
				'data': {
					'url': url,
					'dataType': 'json',
					'data': function (node) {
					}
				}
			},
			"types": {
				"#": {"max_children": 1, "max_depth": 3, "valid_children": ["root"]},
				"root": {"icon": "", "valid_children": ["default"]},
				"default": {"valid_children": ["default", "file"]},
				"file": {"icon": "", "valid_children": []}
			},
			"plugins": pluginsArr
		});

		// 展开所有子节点
		$(domKey).on("loaded.jstree", function (e, data) {
			if(isOpen){
				$(this).jstree("open_all");
//				$(domKey).jstree('select_node', 'ul > li:first');
			}
		});
		//编辑节点事件
		$(domKey).on("rename_node.jstree", function (e, data) {
			if (eventUpdate != null && typeof eventUpdate == 'function') {
				eventUpdate(e, data);
			}
		})

		//添加节点事件
		$(domKey).on("create_node.jstree", function (e, data) {
			if (eventInsert != null && typeof eventInsert == 'function') {
				eventInsert(e, data);
			}
		})

		// 点击节点事件
		$(domKey).on("changed.jstree", function(e, data) {
			if (eventClick != null && typeof eventClick == 'function') {
				eventClick(e, data);
			}
		});

	}

	function treenode_create() {
		var ref = $(domKey).jstree(true),
				sel = ref.get_selected();
		if (!sel.length) {
			return false;
		}
		sel = sel[0];
		sel = ref.create_node(sel, {"type": "file"});
		if (sel) {
			ref.edit(sel);
		}
	};

	function treenode_rename() {
		var ref = $(domKey).jstree(true),
				sel = ref.get_selected();
		if (!sel.length) {
			return false;
		}
		sel = sel[0];
		ref.edit(sel);
	};

	function treenode_delete() {
		var ref = $(domKey).jstree(true),
				sel = ref.get_selected();
		if (!sel.length) {
			return false;
		}
		ref.delete_node(sel);
	};
}


function initMyDataTree(domKey,data,isOpen,pluginsArr,eventClick,eventInsert,eventUpdate){
	_Tree(domKey,data,isOpen,pluginsArr);
	function _Tree(domKey,data,isOpen,pluginsArr){
		$(domKey).jstree({
			"core": {
				"animation": 0,
				"check_callback": true,
				'data': data
			},
			"types": {
				"#": {"max_children": 1, "max_depth": 3, "valid_children": ["root"]},
				"root": {"icon": "", "valid_children": ["default"]},
				"default": {"valid_children": ["default", "file"]},
				"file": {"icon": "", "valid_children": []}
			},
			"plugins": pluginsArr
		});

		// 展开所有子节点
		$(domKey).on("loaded.jstree", function (e, data) {
			if(isOpen){
				$(this).jstree("open_all");
//				$(domKey).jstree('select_node', 'ul > li:first');
			}
		});
		//编辑节点事件
		$(domKey).on("rename_node.jstree", function (e, data) {
			if (eventUpdate != null && typeof eventUpdate == 'function') {
				eventUpdate(e, data);
			}
		})

		//添加节点事件
		$(domKey).on("create_node.jstree", function (e, data) {
			if (eventInsert != null && typeof eventInsert == 'function') {
				eventInsert(e, data);
			}
		})

		// 点击节点事件
		$(domKey).on("changed.jstree", function(e, data) {
			if (eventClick != null && typeof eventClick == 'function') {
				eventClick(e, data);
			}
		});

	}

	function treenode_create() {
		var ref = $(domKey).jstree(true),
				sel = ref.get_selected();
		if (!sel.length) {
			return false;
		}
		sel = sel[0];
		sel = ref.create_node(sel, {"type": "file"});
		if (sel) {
			ref.edit(sel);
		}
	};

	function treenode_rename() {
		var ref = $(domKey).jstree(true),
				sel = ref.get_selected();
		if (!sel.length) {
			return false;
		}
		sel = sel[0];
		ref.edit(sel);
	};

	function treenode_delete() {
		var ref = $(domKey).jstree(true),
				sel = ref.get_selected();
		if (!sel.length) {
			return false;
		}
		ref.delete_node(sel);
	};
}

function getNumber(obj){
	if(isNotEmpty(obj)){
		if(!isNaN(obj)){
			return Number(obj);
		}
	}
	return 0;
}

function initLeftActive(domKey){
	$('#previewMenu').removeClass('active');
	$('#' + domKey).addClass('active').parent().css('display',
		'block').parent().addClass('active open');
}
