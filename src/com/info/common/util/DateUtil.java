package com.info.common.util;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class DateUtil {
	/**
	 * 日
	 */
	public final static int INTERVAL_DAY = 1;
	/**
	 * 周
	 */
	public final static int INTERVAL_WEEK = 2;
	/**
	 * 月
	 */
	public final static int INTERVAL_MONTH = 3;
	/**
	 * 年
	 */
	public final static int INTERVAL_YEAR = 4;
	/**
	 * 小时
	 */
	public final static int INTERVAL_HOUR = 5;
	/**
	 * 分钟
	 */
	public final static int INTERVAL_MINUTE = 6;
	/**
	 * 秒
	 */
	public final static int INTERVAL_SECOND = 7;

	private static SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
	private static SimpleDateFormat dateFormat = new SimpleDateFormat(
			"yyyy-MM-dd HH:mm:ss");

	
	public static Date Now() throws ParseException {
		return dateFormat.parse(format());
	}
	
	public static Timestamp getTime()
	{
		return new Timestamp(new Date().getTime());
	}
	
	
	/**
	 * 输出当前时刻格式为yyyy-MM-dd HH:mm:ss
	 * 
	 * @return
	 */
	public static String format() {
		Date date = new Date();
		return dateFormat.format(date);
	}

	public static String format(String datestr) {
		Date date;
		try {
			date = dateFormat.parse(datestr);
		} catch (ParseException e) {
			date = new Date();
		}
		return dateFormat.format(date);
	}

	/**
	 * 格式化日期格式字符串的显示. 列2012-03-15 10:36:01
	 * 
	 * @return - String 格式化后的时间
	 */
	public static String formatDateToString(java.util.Date date) {
		if (date == null) {
			return "";
		}
		return dateFormat.format(date);
	}
	public static String formatShortDate(java.util.Date date) {
		if (date == null) {
			return "";
		}
		return formatter.format(date);
	}
	@SuppressWarnings("static-access")
	public static boolean isToday(Date date) {
		Calendar c1 = Calendar.getInstance();
		Calendar c2 = Calendar.getInstance();
		c2.setTime(date);
		boolean result = true;
		result &= c1.YEAR == c2.YEAR;
		result &= c1.MONTH == c2.MONTH;
		result &= c1.DAY_OF_MONTH == c2.DAY_OF_MONTH;
		return result;
	}

	/**
	 * 两个日期相减，取天数
	 * 
	 * @param date1
	 * @param date2
	 * @return
	 */
	public static long DaysBetween(Date date1, Date date2) {
		if (date2 == null)
			date2 = new Date();
		long day = (date2.getTime() - date1.getTime()) / (24 * 60 * 60 * 1000);
		return day;
	}

	/**
	 * 比较两个日期 if date1<=date2 return true
	 * 
	 * @param date1
	 * @param date2
	 * @return
	 */
	public static boolean compareDate(String date1, String date2) {
		try {
			Date d1 = formatter.parse(date1);
			Date d2 = formatter.parse(date2);
			return !d1.after(d2);
		} catch (ParseException e) {
			e.printStackTrace();
			return false;
		}
	}

	public static Date getYesterday() {
		Date date = new Date();
		long time = (date.getTime() / 1000) - 60 * 60 * 24;
		date.setTime(time * 1000);
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		try {
			date = format.parse(format.format(date));
		} catch (Exception ex) {
			System.out.println(ex.getMessage());
		}
		return date;
	}

	public static Date getWeekAgo() {
		Date date = new Date();
		long time = (date.getTime() / 1000) - 7 * 60 * 60 * 24;
		date.setTime(time * 1000);
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		try {
			date = format.parse(format.format(date));
		} catch (Exception ex) {
			System.out.println(ex.getMessage());
		}
		return date;
	}

	public static String getDaysAgo(int interval) {
		Date date = new Date();
		long time = (date.getTime() / 1000) - interval * 60 * 60 * 24;
		date.setTime(time * 1000);
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		try {
			return format.format(date);
		} catch (Exception ex) {
			System.out.println(ex.getMessage());
		}
		return "";
	}

	public static Date getTomorrow() {
		Date date = new Date();
		long time = (date.getTime() / 1000) + 60 * 60 * 24;
		date.setTime(time * 1000);
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		try {
			date = format.parse(format.format(date));
		} catch (Exception ex) {
			System.out.println(ex.getMessage());
		}
		return date;
	}

	/**
	 * 增加时间
	 * 
	 * @param interval
	 *            [INTERVAL_DAY,INTERVAL_WEEK,INTERVAL_MONTH,INTERVAL_YEAR,
	 *            INTERVAL_HOUR,INTERVAL_MINUTE]
	 * @param date
	 * @param n
	 *            可以为负数
	 * @return
	 */
	public static Date dateAdd(int interval, Date date, int n) {
		long time = (date.getTime() / 1000); // 单位秒
		switch (interval) {
		case INTERVAL_DAY:
			time = time + n * 86400;// 60 * 60 * 24;
			break;
		case INTERVAL_WEEK:
			time = time + n * 604800;// 60 * 60 * 24 * 7;
			break;
		case INTERVAL_MONTH:
			time = time + n * 2678400;// 60 * 60 * 24 * 31;
			break;
		case INTERVAL_YEAR:
			time = time + n * 31536000;// 60 * 60 * 24 * 365;
			break;
		case INTERVAL_HOUR:
			time = time + n * 3600;// 60 * 60 ;
			break;
		case INTERVAL_MINUTE:
			time = time + n * 60;
			break;
		case INTERVAL_SECOND:
			time = time + n;
			break;
		default:
		}

		Date result = new Date();
		result.setTime(time * 1000);
		return result;
	}

	/**
	 * 计算两个时间间隔
	 * 
	 * @param interval
	 *            [INTERVAL_DAY,INTERVAL_WEEK,INTERVAL_MONTH,INTERVAL_YEAR,
	 *            INTERVAL_HOUR,INTERVAL_MINUTE]
	 * @param begin
	 * @param end
	 * @return
	 */
	public static int dateDiff(int interval, Date begin, Date end) {
		long beginTime = (begin.getTime() / 1000); // 单位：秒
		long endTime = (end.getTime() / 1000); // 单位: 秒
		long tmp = 0;
		if (endTime == beginTime) {
			return 0;
		}
		// 确定endTime 大于 beginTime 结束时间秒数 大于 开始时间秒数
		if (endTime < beginTime) {
			tmp = beginTime;
			beginTime = endTime;
			endTime = tmp;
		}

		long intervalTime = endTime - beginTime;
		long result = 0;
		switch (interval) {
		case INTERVAL_DAY:
			result = intervalTime / 86400;// 60 * 60 * 24;
			break;
		case INTERVAL_WEEK:
			result = intervalTime / 604800;// 60 * 60 * 24 * 7;
			break;
		case INTERVAL_MONTH:
			result = intervalTime / 2678400;// 60 * 60 * 24 * 31;
			break;
		case INTERVAL_YEAR:
			result = intervalTime / 31536000;// 60 * 60 * 24 * 365;
			break;
		case INTERVAL_HOUR:
			result = intervalTime / 3600;// 60 * 60 ;
			break;
		case INTERVAL_MINUTE:
			result = intervalTime / 60;
			break;
		case INTERVAL_SECOND:
			result = intervalTime / 1;
			break;
		default:
		}

		// 做过交换
		if (tmp > 0) {
			result = 0 - result;
		}
		return (int) result;
	}

	/**
	 * 当前年份
	 * 
	 * @return
	 */
	public static int getCurrentYear() {
		int Year = Calendar.YEAR;
		return Year;
	}

	public static Date getBeforeDate(String range) {
		Calendar today = Calendar.getInstance();
		if ("week".equalsIgnoreCase(range))
			today.add(Calendar.WEEK_OF_MONTH, -1);
		else if ("month".equalsIgnoreCase(range))
			today.add(Calendar.MONTH, -1);
		else
			today.clear();
		return today.getTime();
	}

	public static Date getThisWeekStartTime() {
		Calendar today = Calendar.getInstance();
		today.set(Calendar.DAY_OF_WEEK, today.getFirstDayOfWeek());
		Calendar weekFirstDay = Calendar.getInstance();
		weekFirstDay.clear();
		weekFirstDay.set(Calendar.YEAR, today.get(Calendar.YEAR));
		weekFirstDay.set(Calendar.MONTH, today.get(Calendar.MONTH));
		weekFirstDay.set(Calendar.DATE, today.get(Calendar.DATE));
		return weekFirstDay.getTime();
	}

	/**
	 * 获取比当前日期后一天与前一天
	 * 
	 * @param days
	 * @param format
	 *            返回日期的格式
	 * @return 格式化好的字符串
	 */
	public static Map<String, String> dateAfterBefore(String data) {
		Map<String, String> map = new HashMap<String, String>();
		String[] sp = data.split("-");
		Calendar c1 = Calendar.getInstance();
		c1.set(Integer.parseInt(sp[0]), Integer.parseInt(sp[1]) - 1,
				Integer.parseInt(sp[2]));
		c1.add(Calendar.DAY_OF_MONTH, 1);
		map.put("after", formatter.format(c1.getTime()));
		c1.add(Calendar.DAY_OF_MONTH, -2);
		map.put("before", formatter.format(c1.getTime()));
		return map;
	}

	/**
	 * 获取某一日期在一周的开始日期与结束日期
	 * 
	 * @param date
	 * @return
	 */
	public static Map<String, String> weekStartAndEnd(String date) {
		Map<String, String> map = new HashMap<String, String>();
		String[] sp = date.split("-");
		Calendar cal = Calendar.getInstance();
		cal.set(Integer.parseInt(sp[0]), Integer.parseInt(sp[1]) - 1,
				Integer.parseInt(sp[2]));
		int day_of_week = cal.get(Calendar.DAY_OF_WEEK) - 1;
		cal.add(Calendar.DATE, -day_of_week);
		map.put("weekStart", formatter.format(cal.getTime()));
		cal.add(Calendar.DATE, 6);
		map.put("weekEnd", formatter.format(cal.getTime()));
		return map;
	}

	/**
	 * 得到两时间差多少天，多少小时，多少分钟，多少秒
	 * 
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	public static Map<String, Long> getDateDiscrepancy(String startDate, String endDate) {
		try {
			Map<String, Long> result = new HashMap<String, Long>();
			Date start = dateFormat.parse(startDate);
			Date end = dateFormat.parse(endDate);
			long allsecond = (end.getTime() - start.getTime()) / 1000;
			long day = allsecond / (24 * 3600);
			long hour = allsecond % (24 * 3600) / 3600;
			long minute = allsecond % 3600 / 60;
			long second = allsecond % 60;

			result.put("day", day);
			result.put("hour", hour);
			result.put("minute", minute);
			result.put("second", second);
			return result;
		} catch (ParseException e) {
			return null;
		}
	}

	/**
	 * 得到某具体日期的前多少天或后多少天
	 * 
	 * @param count
	 * @return
	 */
	public static String dateBefore(String date, int count) {
		String[] sp = date.split("-");
		Calendar c1 = Calendar.getInstance();
		c1.set(Integer.parseInt(sp[0]), Integer.parseInt(sp[1]) - 1,
				Integer.parseInt(sp[2]));
		c1.add(Calendar.DAY_OF_MONTH, count);
		return formatter.format(c1.getTime());
	}
}
