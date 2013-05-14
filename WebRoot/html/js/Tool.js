var regex={
	sex:function(el){
	var reg = /^(['男']|['女'])$/;
			if (!reg.test(el)) {
				return '性别：填写错误！<br/>';
			}
			return "";
	},
	age:function(el){
		var reg=/^[1-9]{1,2}$/;
		if(!reg.test(el)){
			return '年龄：年龄格式不正确，填写请在1-100之间！<br/>';
		}
		return "";
	},
	phone:function(el){
		var reg=/^[1-9][0-9]{10}$/;
		if(!reg.test(el)){
			return '手机号码：填写错误，请填写正确手机号码！<br/>';
		}
		return "";
	}
};
