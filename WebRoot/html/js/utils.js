// base64编码转换
var Base64 = {
	// private property
	_keyStr : "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/=",
	// public method for encoding
	encode : function(input) {
		var output = "";
		var chr1, chr2, chr3, enc1, enc2, enc3, enc4;
		var i = 0;
		input = Base64._utf8_encode(input);
		while (i < input.length) {
			chr1 = input.charCodeAt(i++);
			chr2 = input.charCodeAt(i++);
			chr3 = input.charCodeAt(i++);
			enc1 = chr1 >> 2;
			enc2 = ((chr1 & 3) << 4) | (chr2 >> 4);
			enc3 = ((chr2 & 15) << 2) | (chr3 >> 6);
			enc4 = chr3 & 63;
			if (isNaN(chr2)) {
				enc3 = enc4 = 64;
			} else if (isNaN(chr3)) {
				enc4 = 64;
			}
			output = output + this._keyStr.charAt(enc1)
					+ this._keyStr.charAt(enc2) + this._keyStr.charAt(enc3)
					+ this._keyStr.charAt(enc4);
		}
		return output;
	},
	// public method for decoding
	decode : function(input) {
		var output = "";
		var chr1, chr2, chr3;
		var enc1, enc2, enc3, enc4;
		var i = 0;
		input = input.replace(/[^A-Za-z0-9\+\/\=]/g, "");
		while (i < input.length) {
			enc1 = this._keyStr.indexOf(input.charAt(i++));
			enc2 = this._keyStr.indexOf(input.charAt(i++));
			enc3 = this._keyStr.indexOf(input.charAt(i++));
			enc4 = this._keyStr.indexOf(input.charAt(i++));
			chr1 = (enc1 << 2) | (enc2 >> 4);
			chr2 = ((enc2 & 15) << 4) | (enc3 >> 2);
			chr3 = ((enc3 & 3) << 6) | enc4;
			output = output + String.fromCharCode(chr1);
			if (enc3 != 64) {
				output = output + String.fromCharCode(chr2);
			}
			if (enc4 != 64) {
				output = output + String.fromCharCode(chr3);
			}
		}
		output = Base64._utf8_decode(output);
		return output;
	},
	// private method for UTF-8 encoding
	_utf8_encode : function(string) {
		string = string.replace(/\r\n/g, "\n");
		var utftext = "";
		for (var n = 0; n < string.length; n++) {

			var c = string.charCodeAt(n);

			if (c < 128) {
				utftext += String.fromCharCode(c);
			} else if ((c > 127) && (c < 2048)) {
				utftext += String.fromCharCode((c >> 6) | 192);
				utftext += String.fromCharCode((c & 63) | 128);
			} else {
				utftext += String.fromCharCode((c >> 12) | 224);
				utftext += String.fromCharCode(((c >> 6) & 63) | 128);
				utftext += String.fromCharCode((c & 63) | 128);
			}
		}
		return utftext;
	},
	// private method for UTF-8 decoding
	_utf8_decode : function(utftext) {
		var string = "";
		var i = 0;
		var c = c1 = c2 = 0;
		while (i < utftext.length) {
			c = utftext.charCodeAt(i);
			if (c < 128) {
				string += String.fromCharCode(c);
				i++;
			} else if ((c > 191) && (c < 224)) {
				c2 = utftext.charCodeAt(i + 1);
				string += String.fromCharCode(((c & 31) << 6) | (c2 & 63));
				i += 2;
			} else {
				c2 = utftext.charCodeAt(i + 1);
				c3 = utftext.charCodeAt(i + 2);
				string += String.fromCharCode(((c & 15) << 12)
						| ((c2 & 63) << 6) | (c3 & 63));
				i += 3;
			}
		}
		return string;
	}
}
// 字符串格式化
String.prototype.format = function() {
	var args = arguments;
	return this.replace(/\{(\d+)\}/g, function(m, i) {
				return args[i];
			});
}
// 带精度的四舍五入
Number.prototype.round = function(arg) {
	with (Math) {
		return round(this * pow(10, arg)) / pow(10, arg);
	}
}
//
Date.prototype.Format = function(fmt) { // author: meizz
	var o = {
		"M+" : this.getMonth() + 1, // 月份
		"d+" : this.getDate(), // 日
		"h+" : this.getHours(), // 小时
		"m+" : this.getMinutes(), // 分
		"s+" : this.getSeconds(), // 秒
		"q+" : Math.floor((this.getMonth() + 3) / 3), // 季度
		"S" : this.getMilliseconds()
		// 毫秒
	};
	if (/(y+)/.test(fmt))
		fmt = fmt.replace(RegExp.$1, (this.getFullYear() + "").substr(4
						- RegExp.$1.length));
	for (var k in o)
		if (new RegExp("(" + k + ")").test(fmt))
			fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1)
							? (o[k])
							: (("00" + o[k]).substr(("" + o[k]).length)));
	return fmt;
}
// 判断字符是否是空
function isEmpty(paramvalue) {
	if (paramvalue == null)
		return true;
	if (paramvalue.length <= 0)
		return true;
	return false;
}
// 去左空格;
function ltrim(s) {
	return s.replace(/^\s*/, "");
}
// 去右空格;
function rtrim(s) {
	return s.replace(/\s*$/, "");
}
// 去左右空格;
function trim(s) {
	return rtrim(ltrim(s));
}

function formatTime(t1, format) {
	if (!t1)
		return "";
	var o = {
		"M+" : t1.getMonth() + 1, // month
		"d+" : t1.getDate(), // day
		"h+" : t1.getHours(), // hour
		"m+" : t1.getMinutes(), // minute
		"s+" : t1.getSeconds(), // second
		"q+" : Math.floor((t1.getMonth() + 3) / 3), // quarter
		"S" : t1.getMilliseconds()
		// millisecond
	}
	if (/(y+)/.test(format))
		format = format.replace(RegExp.$1, (t1.getFullYear() + "").substr(4
						- RegExp.$1.length));
	for (var k in o)
		if (new RegExp("(" + k + ")").test(format))
			format = format.replace(RegExp.$1, RegExp.$1.length == 1
							? o[k]
							: ("00" + o[k]).substr(("" + o[k]).length));
	return format;
}

function getChecked(checked) {
	if (checked) {
		return 1;
	} else {
		return 0;
	}
}
function setChecked(value) {
	if (value == 1) {
		return true;
	} else {
		return false;
	}
}

Date.prototype.format = function(format) {
	var o = {
		"M+" : this.getMonth() + 1, // month
		"d+" : this.getDate(), // day
		"h+" : this.getHours(), // hour
		"m+" : this.getMinutes(), // minute
		"s+" : this.getSeconds(), // second
		"q+" : Math.floor((this.getMonth() + 3) / 3), // quarter
		"S" : this.getMilliseconds()
		// millisecond
	}
	if (/(y+)/.test(format))
		format = format.replace(RegExp.$1, (this.getFullYear() + "").substr(4
						- RegExp.$1.length));
	for (var k in o)
		if (new RegExp("(" + k + ")").test(format))
			format = format.replace(RegExp.$1, RegExp.$1.length == 1
							? o[k]
							: ("00" + o[k]).substr(("" + o[k]).length));
	return format;
}

// 获取url(?id=1&name=xx)参数，使用Request.QueryString('id')
Request = {
	QueryString : function(item) {
		var sValue = unescape(location.search).match(new RegExp("[\?\&]" + item
						+ "=([^\&]*)(\&?)", "i"));
		return sValue ? sValue[1] : sValue;
	}
}
// 获取location.host
function getHttpHost() {
	return "http://" + window.location.host;
}

// 屏蔽页面中不可编辑的文本框中的backspace按钮事件
function keydown(e) {
	var isie = (document.all) ? true : false;
	var key;
	var ev;
	if (isie) { // IE和谷歌浏览器
		key = window.event.keyCode;
		ev = window.event;
	} else {// 火狐浏览器
		key = e.which;
		ev = e;
	}
	if (key == 8) {// IE和谷歌浏览器
		if (isie) {
			if (document.activeElement.readOnly == undefined
					|| document.activeElement.readOnly == true) {
				return event.returnValue = false;
			}
		} else {// 火狐浏览器
			if (document.activeElement.readOnly == undefined
					|| document.activeElement.readOnly == true) {
				ev.which = 0;
				ev.preventDefault();
			}
		}
	}
}
document.onkeydown = keydown;
//去除字符串左右空格
String.prototype.trim=function(){return this.replace(/(^\s*)|(\s*$)/g,"");}