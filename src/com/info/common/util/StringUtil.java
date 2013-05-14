/**
 * @Title		: StringUtil.java
 * @Date		: 2012-03-19 10:50:35
 * @Author		: liwx
 * @Description	: 字符串常用处理方法实现，有getUUID，getFixedLen...
 * @TODO List	: 
 * 增加身份证号码校验、转换方法 by liwx at 2013-04-02 14:30
 * Copyright 2013 Info-Cycle, Inc. All rights reserved.
 */
package com.info.common.util;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Map;
import java.util.Random;
import java.util.regex.Pattern;

/**
 * @ClassName	: StringUtil   
 * @Description	: 字符串处理工具类
 * @Author		: liwx
 * @Date		: 2012-3-19 11:20   
 */
public class StringUtil {
	// 获取全球唯一标识
	public static String getUUID() {
		return java.util.UUID.randomUUID().toString().toUpperCase();
	}

	/**
	 * @Description	: 将整形数字转换成固定长度的字符串，不足前补0
	 * @Author		: liwx
	 * @Date		: 2013-04-02 14-26
	 * @param number
	 * @param length
	 * @return
	 */
	public static String getFixedLen(int number, int length) {
		String tmp = "";
		String strNumber = Integer.toString(number);
		for (int i = 0; i < length - strNumber.length(); i++) {
			tmp += "0";
		}
		return tmp + strNumber;
	}

	public static String ZeroFill(String str, int strLength) {
		int strLen = str.length();
		if (strLen < strLength) {
			while (strLen < strLength) {
				StringBuffer sb = new StringBuffer();
				sb.append("0").append(str);// 左补0
				str = sb.toString();
				strLen = str.length();
			}
		}
		return str;
	}

	/**
	 * @Description	: 字符串转换成数组
	 * @Author		: liwx
	 * @Date		: 2012-3-19 12:06:50
	 * @param src
	 * @param delimeter
	 * @return
	 */
	public static String[] split(String src, String delimeter) {
		int maxparts = (src.length() / delimeter.length()) + 2; // one more for
																// the last
		int[] positions = new int[maxparts];
		int dellen = delimeter.length();

		int i, j = 0;
		int count = 0;
		positions[0] = -dellen;
		while ((i = src.indexOf(delimeter, j)) != -1) {
			count++;
			positions[count] = i;
			j = i + dellen;
		}
		count++;
		positions[count] = src.length();

		String[] result = new String[count];

		for (i = 0; i < count; i++) {
			result[i] = src.substring(positions[i] + dellen, positions[i + 1]);
		}
		return result;
	}

	/**
	 * @Description	: 将一字符串数组用指定的分隔符接起来
	 * @Author		: liwx
	 * @Date		: 2013-1-19 上午12:00:26
	 * @param array
	 * @param split
	 * @return
	 */
	public static String addSplit(String[] array, String split) {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < array.length - 1; i++)
			sb.append(array[i] + split);
		sb.append(array[array.length - 1]);
		return sb.toString();
	}

	public static String escape(String src) {
		int i;
		char j;
		StringBuffer tmp = new StringBuffer();
		tmp.ensureCapacity(src.length() * 6);
		for (i = 0; i < src.length(); i++) {
			j = src.charAt(i);
			if (Character.isDigit(j) || Character.isLowerCase(j)
					|| Character.isUpperCase(j))
				tmp.append(j);
			else if (j < 256) {
				tmp.append("%");
				if (j < 16)
					tmp.append("0");
				tmp.append(Integer.toString(j, 16));
			} else {
				tmp.append("%u");
				tmp.append(Integer.toString(j, 16));
			}
		}
		return tmp.toString();
	}

	public static String unescape(String src) {
		StringBuffer tmp = new StringBuffer();
		tmp.ensureCapacity(src.length());
		int lastPos = 0, pos = 0;
		char ch;
		while (lastPos < src.length()) {
			pos = src.indexOf("%", lastPos);
			if (pos == lastPos) {
				if (src.charAt(pos + 1) == 'u') {
					ch = (char) Integer.parseInt(
							src.substring(pos + 2, pos + 6), 16);
					tmp.append(ch);
					lastPos = pos + 6;
				} else {
					ch = (char) Integer.parseInt(
							src.substring(pos + 1, pos + 3), 16);
					tmp.append(ch);
					lastPos = pos + 3;
				}
			} else {
				if (pos == -1) {
					tmp.append(src.substring(lastPos));
					lastPos = src.length();
				} else {
					tmp.append(src.substring(lastPos, pos));
					lastPos = pos;
				}
			}
		}
		return tmp.toString();
	}

	/**
	 * @Description	: 将字符串 source 中的 oldStr 替换为 newStr, 并以大小写敏感方式进行查找
	 * @Author		: liwx
	 * @Date		: 2013-04-02 14-25
	 * @param source
	 * @param oldStr
	 * @param newStr
	 * @return
	 */
	public static String replace(String source, String oldStr, String newStr) {
		return replace(source, oldStr, newStr, true);
	}

	/**
	 * @Description	: 将字符串 source 中的 oldStr 替换为 newStr, matchCase 为是否设置大小写敏感查找
	 * @Author		: liwx
	 * @Date		: 2013-04-02 14-22
	 * @param source 需要替换的源字符串
	 * @param oldStr 需要被替换的老字符串
	 * @param newStr 替换为的新字符串
	 * @param matchCase 是否需要按照大小写敏感方式查找
	 * @return
	 */
	public static String replace(String source, String oldStr, String newStr,
			boolean matchCase) {
		if (source == null) {
			return null;
		}
		// 首先检查旧字符串是否存在, 不存在就不进行替换
		if (source.toLowerCase().indexOf(oldStr.toLowerCase()) == -1) {
			return source;
		}
		int findStartPos = 0;
		int a = 0;
		while (a > -1) {
			int b = 0;
			String str1, str2, str3, str4, strA, strB;
			str1 = source;
			str2 = str1.toLowerCase();
			str3 = oldStr;
			str4 = str3.toLowerCase();
			if (matchCase) {
				strA = str1;
				strB = str3;
			} else {
				strA = str2;
				strB = str4;
			}
			a = strA.indexOf(strB, findStartPos);
			if (a > -1) {
				b = oldStr.length();
				findStartPos = a + b;
				StringBuffer bbuf = new StringBuffer(source);
				source = bbuf.replace(a, a + b, newStr) + "";
				// 新的查找开始点位于替换后的字符串的结尾
				findStartPos = findStartPos + newStr.length() - b;
			}
		}
		return source;
	}

	/**
	 * 清除字符串结尾的空格.
	 * @param input
	 *            String 输入的字符串
	 * @return 转换结果
	 */
	public static String trimTailSpaces(String input) {
		if (isEmpty(input)) {
			return "";
		}
		String trimedString = input.trim();
		if (trimedString.length() == input.length()) {
			return input;
		}
		return input.substring(0,
				input.indexOf(trimedString) + trimedString.length());
	}

	public static String trim(String input) {
		if (isEmpty(input)) {
			return "";
		}
		String trimedString = input.trim();
		return trimedString;
	}

	/**
	 * @Description	: 排除null字符串对象
	 * @Author		: liwx
	 * @Date		: 2013-04-02 14-21
	 * @param input
	 * @return
	 */
	public static String clearNull(String input) {
		return isEmpty(input) ? "" : input;
	}

	/**
	 * @Description	: 截取固定长度的字符串
	 * @Author		: liwx
	 * @Date		: 2013-04-02 14-20
	 * @param input
	 * @param maxLength
	 * @return
	 */
	public static String limitStringLength(String input, int maxLength) {
		if (isEmpty(input))
			return "";
		if (input.length() <= maxLength) {
			return input;
		} else {
			return input.substring(0, maxLength - 3) + "...";
		}
	}

	/**
	 * @Description	: 判断字符串是否全是数字字符.
	 * @Author		: liwx
	 * @Date		: 2013-04-02 14-19
	 * @param input
	 * @return
	 */
	public static boolean isNumeric(String input) {
		if (isEmpty(input)) {
			return false;
		}
		for (int i = 0; i < input.length(); i++) {
			char charAt = input.charAt(i);

			if (!Character.isDigit(charAt)) {
				return false;
			}
		}
		return true;
	}

	/**
	 * @Description	: 转换字符串的内码.
	 * @Author		: liwx
	 * @Date		: 2013-04-02 14-18
	 * @param input 输入的字符串
	 * @param sourceEncoding 源字符集名称
	 * @param targetEncoding 目标字符集名称
	 * @return
	 */
	public static String changeEncoding(String input, String sourceEncoding,
			String targetEncoding) {
		if (input == null || input.equals("")) {
			return input;
		}
		try {
			byte[] bytes = input.getBytes(sourceEncoding);
			return new String(bytes, targetEncoding);
		} catch (Exception ex) {
		}
		return input;
	}

	/**
	 * @Description	: 将单个的 ' 换成 ''; 
	 * SQL 规则:如果单引号中的字符串包含一个嵌入的引号,可以使用两个单引号表示嵌入的单引号.
	 * @Author		: liwx
	 * @Date		: 2013-04-02 14-18
	 * @param input
	 * @return
	 */
	public static String replaceSql(String input) {
		return replace(input, "'", "''");
	}

	/**
	 * @Description	: 对给定字符进行 URL 编码
	 * @Author		: liwx
	 * @Date		: 2013-04-02 14-17
	 * @param value
	 * @return
	 */
	public static String encode(String value) {
		if (isEmpty(value)) {
			return "";
		}
		try {
			value = java.net.URLEncoder.encode(value, "utf-8");
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return value;
	}

	/**
	 * @Description	: 对给定字符进行 URL 解码
	 * @Author		: liwx
	 * @Date		: 2013-04-02 14-17
	 * @param value
	 * @return
	 */
	public static String decode(String value) {
		if (isEmpty(value)) {
			return "";
		}
		try {
			return java.net.URLDecoder.decode(value, "utf-8");
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return value;
	}

	/**
	 * @Description	: 判断字符串是否未空, 如果为 null 或者长度为0, 均返回 true.
	 * @Author		: liwx
	 * @Date		: 2013-04-02 14-17
	 * @param input
	 * @return
	 */
	public static boolean isEmpty(String input) {
		return (input == null || input.length() == 0);
	}
	/**
	 * @Description	: 对象是否为空
	 * @Author		: liwx
	 * @Date		: 2013-04-02 13-25
	 * @param pObj
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public static boolean isEmpty(Object pObj) {
		if (pObj == null)
			return true;
		if (pObj == "")
			return true;
		if (pObj instanceof String) {
			if (((String) pObj).length() == 0) {
				return true;
			}
		} else if (pObj instanceof Collection) {
			if (((Collection) pObj).size() == 0) {
				return true;
			}
		} else if (pObj instanceof Map) {
			if (((Map) pObj).size() == 0) {
				return true;
			}
		}
		return false;
	}

	/**
	 * @Description	: 对象是否非空
	 * @Author		: liwx
	 * @Date		: 2013-04-02 13-26
	 * @param pObj
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public static boolean isNotEmpty(Object pObj) {
		if (pObj == null)
			return false;
		if (pObj == "")
			return false;
		if (pObj instanceof String) {
			if (((String) pObj).length() == 0) {
				return false;
			}
		} else if (pObj instanceof Collection) {
			if (((Collection) pObj).size() == 0) {
				return false;
			}
		} else if (pObj instanceof Map) {
			if (((Map) pObj).size() == 0) {
				return false;
			}
		}
		return true;
	}

	/**
	 * @Description	: 获得输入字符串的字节长度(即二进制字节数), 用于发送短信时判断是否超出长度.
	 * @Author		: liwx
	 * @Date		: 2013-04-02 14-11
	 * @param input
	 * @return
	 */
	public static int getBytesLength(String input) {
		if (input == null) {
			return 0;
		}

		int bytesLength = input.getBytes().length;
		return bytesLength;
	}

	/**
	 * @Description	: 检验字符串是否未空, 如果是, 则返回给定的出错信息.
	 * @Author		: liwx
	 * @Date		: 2013-04-02 14-12
	 * @param input
	 * @param errorMsg
	 * @return
	 */
	public static String isEmpty(String input, String errorMsg) {
		if (isEmpty(input)) {
			return errorMsg;
		}
		return "";
	}

	/**
	 * @Description	: 返回指定字节长度的字符串
	 * @Author		: liwx
	 * @Date		: 2013-04-02 14-12
	 * @param str
	 * @param length
	 * @return
	 */
	public static String toLength(String str, int length) {
		if (str == null) {
			return null;
		}
		if (length <= 0) {
			return "";
		}
		try {
			if (str.getBytes("GBK").length <= length) {
				return str;
			}
		} catch (Exception ex) {
		}
		StringBuffer buff = new StringBuffer();

		int index = 0;
		char c;
		length -= 3;
		while (length > 0) {
			c = str.charAt(index);
			if (c < 128) {
				length--;
			} else {
				length--;
				length--;
			}
			buff.append(c);
			index++;
		}
		buff.append("...");
		return buff.toString();
	}

	/**
	 * @Description	: 判断是否为整数
	 * @Author		: liwx
	 * @Date		: 2013-04-02 14-13
	 * @param str
	 * @return
	 */
	public static boolean isInteger(String str) {
		Pattern pattern = Pattern.compile("^[-\\+]?[\\d]*$");
		return pattern.matcher(str).matches();
	}

	/**
	 * @Description	: 判断是否为浮点数，包括double和float
	 * @Author		: liwx
	 * @Date		: 2013-04-02 14-13
	 * @param str
	 * @return
	 */
	public static boolean isDouble(String str) {
		Pattern pattern = Pattern.compile("^[-\\+]?[.\\d]*$");
		return pattern.matcher(str).matches();
	}

	/**
	 * @Description	: 人民币转成大写
	 * @Author		: liwx
	 * @Date		: 2013-04-02 14-13
	 * @param value
	 * @return
	 */
	public static String hangeToBig(double value) {
		char[] hunit = { '拾', '佰', '仟' }; // 段内位置表示
		char[] vunit = { '万', '亿' }; // 段名表示
		char[] digit = { '零', '壹', '贰', '叁', '肆', '伍', '陆', '柒', '捌', '玖' }; // 数字表示
		long midVal = (long) (value * 100); // 转化成整形

		String valStr = String.valueOf(midVal); // 转化成字符串

		String head = valStr.substring(0, valStr.length() - 2); // 取整数部分

		String rail = valStr.substring(valStr.length() - 2); // 取小数部分

		String prefix = ""; // 整数部分转化的结果

		String suffix = ""; // 小数部分转化的结果

		// 处理小数点后面的数

		if (rail.equals("00")) { // 如果小数部分为0
			suffix = "整";
		} else {
			suffix = digit[rail.charAt(0) - '0'] + "角"
					+ digit[rail.charAt(1) - '0'] + "分"; // 否则把角分转化出来

		}
		// 处理小数点前面的数

		char[] chDig = head.toCharArray(); // 把整数部分转化成字符数组
		char zero = '0'; // 标志'0'表示出现过0
		byte zeroSerNum = 0; // 连续出现0的次数

		for (int i = 0; i < chDig.length; i++) { // 循环处理每个数字
			int idx = (chDig.length - i - 1) % 4; // 取段内位置

			int vidx = (chDig.length - i - 1) / 4; // 取段位置
			if (chDig[i] == '0') { // 如果当前字符是0
				zeroSerNum++; // 连续0次数递增
				if (zero == '0') { // 标志
					zero = digit[0];
				} else if (idx == 0 && vidx > 0 && zeroSerNum < 4) {
					prefix += vunit[vidx - 1];
					zero = '0';
				}
				continue;
			}
			zeroSerNum = 0; // 连续0次数清零
			if (zero != '0') { // 如果标志不为0,则加上,例如万,亿什么的
				prefix += zero;
				zero = '0';
			}
			prefix += digit[chDig[i] - '0']; // 转化该数字表示

			if (idx > 0)
				prefix += hunit[idx - 1];
			if (idx == 0 && vidx > 0) {
				prefix += vunit[vidx - 1]; // 段结束位置应该加上段名如万,亿

			}
		}

		if (prefix.length() > 0)
			prefix += '圆'; // 如果整数部分存在,则有圆的字样
		return prefix + suffix; // 返回正确表示
	}
	/**
	 * @Description	: 转换成18位身份证号
	 * @Author		: liwx
	 * @Date		: 2013-04-02 14-14
	 * @param str
	 * @return
	 */
	public static String IdentityCardToEighteen(String str) {
		if (str.length() == 15) {
			Integer[] arrInt = { 7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8,
					4, 2 };
			String[] arrCh = { "1", "0", "X", "9", "8", "7", "6", "5", "4",
					"3", "2" };
			Integer cardTemp = 0;
			StringBuilder sb = new StringBuilder(str);
			sb.insert(6, 19);
			for (int i = 0; i < 17; i++) {
				cardTemp += Integer.valueOf(sb.toString().charAt(i))
						* arrInt[i];
			}

			sb.append(arrCh[cardTemp % 11]);
			return sb.toString();
		}
		return str;
	}

	/**
	 * @Description	: 从指定的字符数组中随机获取指定长度的字符串
	 * @Author		: liwx
	 * @Date		: 2013-04-02 14-15
	 * @param length
	 * @return
	 */
	public static String getRandomString(int length) {
		String RandomCode = "";
		char[] codeSequence = { '3', '4', '5', '6', 'A', 'B', 'C', 'D', 'E',
				'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q',
				'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', '0', '1', '2',
				'3', '4', '5', '6', '7', '8', '9' };
		Random random = new Random();
		StringBuffer randomCode = new StringBuffer();
		for (int i = 0; i < length; i++) {
			String strRand = String.valueOf(codeSequence[random.nextInt(40)]);
			randomCode.append(strRand);
		}
		RandomCode = randomCode.toString();
		return RandomCode.toLowerCase();
	}
	

	/**
	 * 将传入的身份证号码进行校验，并返回一个对应的18位身份证
	 * 
	 * @param personIDCode
	 *            身份证号码
	 * @return String 十八位身份证号码
	 * @throws 无效的身份证号
	 */
	public static String getFixedPersonIDCode(String personIDCode)
			throws Exception {
		if (personIDCode == null)
			throw new Exception("输入的身份证号无效，请检查");

		if (personIDCode.length() == 18) {
			if (isIdentity(personIDCode))
				return personIDCode;
			else
				throw new Exception("输入的身份证号无效，请检查");
		} else if (personIDCode.length() == 15)
			return fixPersonIDCodeWithCheck(personIDCode);
		else
			throw new Exception("输入的身份证号无效，请检查");
	}

	/**
	 * 修补15位居民身份证号码为18位，并校验15位身份证有效性
	 * 
	 * @param personIDCode
	 *            十五位身份证号码
	 * @return String 十八位身份证号码
	 * @throws 无效的身份证号
	 */
	public static String fixPersonIDCodeWithCheck(String personIDCode)
			throws Exception {
		if (personIDCode == null || personIDCode.trim().length() != 15)
			throw new Exception("输入的身份证号不足15位，请检查");

		if (!isIdentity(personIDCode))
			throw new Exception("输入的身份证号无效，请检查");

		return fixPersonIDCodeWithoutCheck(personIDCode);
	}

	/**
	 * 修补15位居民身份证号码为18位，不校验身份证有效性
	 * 
	 * @param personIDCode
	 *            十五位身份证号码
	 * @return 十八位身份证号码
	 * @throws 身份证号参数不是15位
	 */
	public static String fixPersonIDCodeWithoutCheck(String personIDCode)
			throws Exception {
		if (personIDCode == null || personIDCode.trim().length() != 15)
			throw new Exception("输入的身份证号不足15位，请检查");

		String id17 = personIDCode.substring(0, 6) + "19"
				+ personIDCode.substring(6, 15); // 15位身份证补'19'

		char[] code = { '1', '0', 'X', '9', '8', '7', '6', '5', '4', '3', '2' }; // 11个校验码字符
		int[] factor = { 7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2, 1 }; // 18个加权因子
		int[] idcd = new int[18];
		int sum; // 根据公式 ∑(ai×Wi) 计算
		int remainder; // 第18位校验码
		for (int i = 0; i < 17; i++) {
			idcd[i] = Integer.parseInt(id17.substring(i, i + 1));
		}
		sum = 0;
		for (int i = 0; i < 17; i++) {
			sum = sum + idcd[i] * factor[i];
		}
		remainder = sum % 11;
		String lastCheckBit = String.valueOf(code[remainder]);
		return id17 + lastCheckBit;
	}

	/**
	 * 判断是否是有效的18位或15位居民身份证号码
	 * 
	 * @param identity
	 *            18位或15位居民身份证号码
	 * @return 是否为有效的身份证号码
	 */
	public static boolean isIdentity(String identity) {
		if (identity == null)
			return false;
		if (identity.length() == 18 || identity.length() == 15) {
			String id15 = null;
			if (identity.length() == 18)
				id15 = identity.substring(0, 6) + identity.substring(8, 17);
			else
				id15 = identity;
			try {
				Long.parseLong(id15); // 校验是否为数字字符串

				String birthday = "19" + id15.substring(6, 12);
				SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
				sdf.parse(birthday); // 校验出生日期
				if (identity.length() == 18
						&& !fixPersonIDCodeWithoutCheck(id15).equals(identity))
					return false; // 校验18位身份证
			} catch (Exception e) {
				return false;
			}
			return true;
		} else
			return false;
	}

	/**
	 * 从身份证号中获取出生日期，身份证号可以为15位或18位
	 * 
	 * @param identity
	 *            身份证号
	 * @return 出生日期
	 * @throws 身份证号出生日期段有误
	 */
	public static Timestamp getBirthdayFromPersonIDCode(String identity)
			throws Exception {
		String id = getFixedPersonIDCode(identity);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		try {
			Timestamp birthday = new Timestamp(sdf.parse(id.substring(6, 14))
					.getTime());
			return birthday;
		} catch (ParseException e) {
			throw new Exception("不是有效的身份证号，请检查");
		}
	}

	/**
	 * 从身份证号获取性别
	 * 
	 * @param identity
	 *            身份证号
	 * @return 性别代码
	 * @throws Exception
	 *             无效的身份证号码
	 */
	public static String getGenderFromPersonIDCode(String identity)
			throws Exception {
		String id = getFixedPersonIDCode(identity);
		char sex = id.charAt(16);
		return sex % 2 == 0 ? "2" : "1";
	}
}
