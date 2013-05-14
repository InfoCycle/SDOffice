function isCardID(sId) {
	var Errors = new Array("true", "身份证号码：位数不等于18位!<br/>", "身份证号码：出生日期格式错误!<br/>",
			"身份证号码：校验错误!<br/>", "身份证号码：不合法!<br/>");
	if (sId.length != 18) {
		return Errors[1];
	}
	
	var area = {
		11 : "北京",
		12 : "天津",
		13 : "河北",
		14 : "山西",
		15 : "内蒙古",
		21 : "辽宁",
		22 : "吉林",
		23 : "黑龙江",
		31 : "上海",
		32 : "江苏",
		33 : "浙江",
		34 : "安徽",
		35 : "福建",
		36 : "江西",
		37 : "山东",
		41 : "河南",
		42 : "湖北",
		43 : "湖南",
		44 : "广东",
		45 : "广西",
		46 : "海南",
		50 : "重庆",
		51 : "四川",
		52 : "贵州",
		53 : "云南",
		54 : "西藏",
		61 : "陕西",
		62 : "甘肃",
		63 : "青海",
		64 : "宁夏",
		65 : "新疆",
		71 : "台湾",
		81 : "香港",
		82 : "澳门",
		91 : "国外"
	};
	var retflag = false;
	var sId, Y, JYM;
	var S, M;
	var sId_array = new Array();
	sId_array = sId.split("");
	// 地区检验
	if (area[parseInt(sId.substr(0, 2))] == null)
		return Errors[4];
	// 身份号码位数及格式检验
	switch (sId.length) {
		case 15 :
			if ((parseInt(sId.substr(6, 2)) + 1900) % 4 == 0
					|| ((parseInt(sId.substr(6, 2)) + 1900) % 100 == 0 && (parseInt(sId
							.substr(6, 2)) + 1900)
							% 4 == 0)) {
				ereg = /^[1-9][0-9]{5}[0-9]{2}((01|03|05|07|08|10|12)(0[1-9]|[1-2][0-9]|3[0-1])|(04|06|09|11)(0[1-9]|[1-2][0-9]|30)|02(0[1-9]|[1-2][0-9]))[0-9]{3}$/;// 测试出生日期的合法性
			} else {
				ereg = /^[1-9][0-9]{5}[0-9]{2}((01|03|05|07|08|10|12)(0[1-9]|[1-2][0-9]|3[0-1])|(04|06|09|11)(0[1-9]|[1-2][0-9]|30)|02(0[1-9]|1[0-9]|2[0-8]))[0-9]{3}$/;// 测试出生日期的合法性
			}
			if (ereg.test(sId))
				return Errors[0];
			else {
				return Errors[2];
			}
			break;
		case 18 :
			// 18位身份号码检测
			// 出生日期的合法性检查
			// 闰年月日:((01|03|05|07|08|10|12)(0[1-9]|[1-2][0-9]|3[0-1])|(04|06|09|11)(0[1-9]|[1-2][0-9]|30)|02(0[1-9]|[1-2][0-9]))
			// 平年月日:((01|03|05|07|08|10|12)(0[1-9]|[1-2][0-9]|3[0-1])|(04|06|09|11)(0[1-9]|[1-2][0-9]|30)|02(0[1-9]|1[0-9]|2[0-8]))
			if (parseInt(sId.substr(6, 4)) % 4 == 0
					|| (parseInt(sId.substr(6, 4)) % 100 == 0 && parseInt(sId
							.substr(6, 4))
							% 4 == 0)) {
				ereg = /^[1-9][0-9]{5}19[0-9]{2}((01|03|05|07|08|10|12)(0[1-9]|[1-2][0-9]|3[0-1])|(04|06|09|11)(0[1-9]|[1-2][0-9]|30)|02(0[1-9]|[1-2][0-9]))[0-9]{3}[0-9Xx]$/;// 闰年出生日期的合法性正则表达式
			} else {
				ereg = /^[1-9][0-9]{5}19[0-9]{2}((01|03|05|07|08|10|12)(0[1-9]|[1-2][0-9]|3[0-1])|(04|06|09|11)(0[1-9]|[1-2][0-9]|30)|02(0[1-9]|1[0-9]|2[0-8]))[0-9]{3}[0-9Xx]$/;// 平年出生日期的合法性正则表达式
			}
			if (ereg.test(sId)) {// 测试出生日期的合法性
				// 计算校验位
				S = (parseInt(sId_array[0]) + parseInt(sId_array[10])) * 7
						+ (parseInt(sId_array[1]) + parseInt(sId_array[11]))
						* 9
						+ (parseInt(sId_array[2]) + parseInt(sId_array[12]))
						* 10
						+ (parseInt(sId_array[3]) + parseInt(sId_array[13]))
						* 5
						+ (parseInt(sId_array[4]) + parseInt(sId_array[14]))
						* 8
						+ (parseInt(sId_array[5]) + parseInt(sId_array[15]))
						* 4
						+ (parseInt(sId_array[6]) + parseInt(sId_array[16]))
						* 2 + parseInt(sId_array[7]) * 1
						+ parseInt(sId_array[8]) * 6 + parseInt(sId_array[9])
						* 3;
				Y = S % 11;
				M = "F";
				JYM = "10X98765432";
				M = JYM.substr(Y, 1);// 判断校验位
				if (M == sId_array[17])
					return Errors[0]; // 检测ID的校验位
				else
					return Errors[3];
			} else
				return Errors[2];
			break;
		default :
			return Errors[1];
			break;
	}
}

function getSex(sId) {
	var codesex;
	if (sId.length == 15) {
		codesex = sId.substr(14, 1);
		if (codesex % 2 == 0) {
			codesex = '女';
		} else {
			codesex = '男';
		}
	}
	if (sId.length == 18) {
		codesex = sId.substr(16, 1);
		if (codesex % 2 == 0) {
			codesex = '女';
		} else {
			codesex = '男';
		}
	}
	return codesex;
}

function getBirthday(sId) {
	var Birthday, y, m, d;
	if (sId.length == 15) {
		y = sId.substr(6, 2);
		m = sId.substr(8, 2);
		d = sId.substr(10, 2);
		Birthday = "19" + y + "-" + m + "-" + d;
	}

	if (sId.length == 18) {
		y = sId.substr(6, 4);
		m = sId.substr(10, 2);
		d = sId.substr(12, 2);
		Birthday = y + "-" + m + "-" + d;
	}
	return Birthday;
}

function getAge(sId) {
	var age, y;
	var now = new Date().getFullYear();
	if (sId.length == 15) {
		y = "19" + sId.substr(6, 2);
		age = now - parseInt(y);
	}

	if (sId.length == 18) {
		y = sId.substr(6, 4);
		age = now - parseInt(y);
	}
	return age;
}