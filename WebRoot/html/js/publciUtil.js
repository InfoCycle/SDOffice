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
Date.prototype.Format = function(fmt)
		{ //author: meizz   
		  var o = {   
		    "M+" : this.getMonth()+1,                 //月份   
		    "d+" : this.getDate(),                    //日   
		    "h+" : this.getHours(),                   //小时   
		    "m+" : this.getMinutes(),                 //分   
		    "s+" : this.getSeconds(),                 //秒   
		    "q+" : Math.floor((this.getMonth()+3)/3), //季度   
		    "S"  : this.getMilliseconds()             //毫秒   
		  };   
		  if(/(y+)/.test(fmt))   
		    fmt=fmt.replace(RegExp.$1, (this.getFullYear()+"").substr(4 - RegExp.$1.length));   
		  for(var k in o)   
		    if(new RegExp("("+ k +")").test(fmt))   
		  fmt = fmt.replace(RegExp.$1, (RegExp.$1.length==1) ? (o[k]) : (("00"+ o[k]).substr((""+ o[k]).length)));   
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

//短时间，形如 (23:30:06)
function isTime(str)
{
	var a = str.match(/^(\d{1,2})(:)?(\d{1,2})\2(\d{1,2})$/);
	if (a == null) {
		alert('输入的参数不是时间格式'); 
		return false;}
	if (a[1]>24 || a[3]>60 || a[4]>60)
	{
	alert("时间格式不对");
	return false
	}
	return true;
}

//短日期，形如 (2008-09-13)
function strShortDateTime(str)
{
	var r = str.match(/^(\d{1,4})(-|\/)(\d{1,2})\2(\d{1,2})$/);
	if(r==null)return false;
	var d= new Date(r[1], r[3]-1, r[4]);	
	return (d.getFullYear()==r[1]&&(d.getMonth()+1)==r[3]&&d.getDate()==r[4]);
}

//长时间，形如 (2008-09-13 23:30:06)
function strLangDateTime(str)
{
	var reg = /^(\d{1,4})(-|\/)(\d{1,2})\2(\d{1,2}) (\d{1,2}):(\d{1,2}):(\d{1,2})$/;
	var r = str.match(reg);
	if(r==null)return false;
	var d= new Date(r[1], r[3]-1,r[4],r[5],r[6],r[7]);
	return (d.getFullYear()==r[1]&&(d.getMonth()+1)==r[3]&&d.getDate()==r[4]&&d.getHours()==r[5]&&d.getMinutes()==r[6]&&d.getSeconds()==r[7]);
} 
function formatTime(t1, format) {
	if(!t1) return "";
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

//获取url(?id=1&name=xx)参数，使用Request.QueryString('id')
Request = {
    QueryString : function(item){
            var sValue = unescape(location.search).match(new RegExp("[\?\&]" + item + "=([^\&]*)(\&?)","i"));
            return sValue ? sValue[1] : sValue;
    }
}

function checkUserLogin(){	
	var CurrentUser;
	$.post("/system/SvrService/AppIndex/4/", {}, function(data) {
		if (data.success) {			
			CurrentUser= data;			
		}else
		{
			CurrentUser=null;
			alert('错误！');
		}
	}, "json");
    return CurrentUser;
}


//获取相应方法的drop数据
/*
BoolAsync 是否是异步（异步：true，同步：false）
ControlName 页面上select控件的ID
Url 后台取数地址
Data 传递参数
ValueName绑定时value的字段的名称
TextName绑定时值的字段名称
 */
function GetDropData(BoolAsync, ControlName, Url, DataParams, ValueName, TextName,SelectValue) {
    var GetDropData = "";
    $.ajax({
        type: "post",       
        url: Url,
        data:DataParams,
        async: BoolAsync,
        dataType: 'json',
        success: function (result) {        	
            var json = eval(result);
            $.each(json, function (i) {
                //$("<option></option>").val(json[i][ValueName]).html(json[i][TextName]).appendTo(ControlName);
            	if(json[i][ValueName]==SelectValue)
            		$("<option></option>").val(json[i][ValueName]).attr("selected","selected").html(json[i][TextName]).appendTo(ControlName);
            	else
            		$("<option></option>").val(json[i][ValueName]).html(json[i][TextName]).appendTo(ControlName);
            });
        },
        error: function (result) {
            alert("获取数据错误！");
        }
    });           
}