package com.cpiinfo.iot.utility;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * 
 * @Title: DateUtils.java
 * @Package com.cpiinfo.iisp.utils
 * @Description: TODO(日期时间操作工具类)
 * @author 孙德
 * @date 2016年10月24日 上午9:30:04
 * @version V1.0
 */
public final class DateUtils {

	/**
	 * 获取当前时间
	 * 
	 * @return
	 */
	public static Date now() {
		return new Date();
	}

	/**
	 * 新建一个日期
	 * 
	 * @param year
	 * @param month
	 * @param day
	 * @return
	 */
	public static Date newDate(int year, int month, int day) {
		Calendar c = Calendar.getInstance();
		c.clear();
		c.set(year, month - 1, day);
		return c.getTime();
	}

	/**
	 * 新建一个日期
	 * 
	 * @param year
	 * @param month
	 * @param day
	 * @param hour
	 * @param minute
	 * @param second
	 * @return
	 */
	public static Date newDate(int year, int month, int day, int hour, int minute, int second) {
		Calendar c = Calendar.getInstance();
		c.clear();
		c.set(year, month - 1, day, hour, minute, second);
		return c.getTime();
	}

	/**
	 * 指定时间的小时开始
	 * 
	 * @param date
	 * @return ... HH:00:00.000
	 */
	public static Date hourBegin(final Date date) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.set(Calendar.MINUTE, 0);
		c.set(Calendar.SECOND, 0);
		c.set(Calendar.MILLISECOND, 0);
		return c.getTime();
	}

	/**
	 * 指定时间的小时最后一毫秒
	 * 
	 * @param date
	 * @return ... HH:59:59.999
	 */
	public static Date hourEnd(final Date date) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.set(Calendar.MINUTE, 59);
		c.set(Calendar.SECOND, 59);
		c.set(Calendar.MILLISECOND, 999);
		return c.getTime();
	}

	/**
	 * 获取指定时间的那天 00:00:00.000 的时间
	 * 
	 * @param date
	 * @return
	 */
	public static Date dayBegin(final Date date) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.set(Calendar.HOUR_OF_DAY, 0);
		c.set(Calendar.MINUTE, 0);
		c.set(Calendar.SECOND, 0);
		c.set(Calendar.MILLISECOND, 0);
		return c.getTime();
	}

	/**
	 * 获取今天 00:00:00.000 的时间
	 * 
	 * @param date
	 * @return
	 */
	public static Date dayBegin() {
		return dayBegin(now());
	}

	/**
	 * 获取指定时间的那天 23:59:59.999 的时间
	 * 
	 * @param date
	 * @return
	 */
	public static Date dayEnd(final Date date) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.set(Calendar.HOUR_OF_DAY, 23);
		c.set(Calendar.MINUTE, 59);
		c.set(Calendar.SECOND, 59);
		c.set(Calendar.MILLISECOND, 999);
		return c.getTime();
	}

	/**
	 * 获取今天 23:59:59.999 的时间
	 * 
	 * @param date
	 * @return
	 */
	public static Date dayEnd() {
		return dayEnd(now());
	}

	/**
	 * 月首
	 * 
	 * @param date
	 * @return
	 */
	public static Date monthBegin(final Date date) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.set(Calendar.DAY_OF_MONTH, 1);
		c.set(Calendar.HOUR_OF_DAY, 0);
		c.set(Calendar.MINUTE, 0);
		c.set(Calendar.SECOND, 0);
		c.set(Calendar.MILLISECOND, 0);
		return c.getTime();
	}

	/**
	 * 月末
	 * 
	 * @param date
	 * @return
	 */
	public static Date monthEnd(final Date date) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.set(Calendar.DAY_OF_MONTH, c.getActualMaximum(Calendar.DAY_OF_MONTH));
		c.set(Calendar.HOUR_OF_DAY, 23);
		c.set(Calendar.MINUTE, 59);
		c.set(Calendar.SECOND, 59);
		c.set(Calendar.MILLISECOND, 999);
		return c.getTime();
	}

	/**
	 * 获取对应的查询截止日期
	 */
	public static Date monthEndByYear(int year, int month, final Date date) {
		Calendar c = Calendar.getInstance();
		int y = c.get(Calendar.YEAR);
		int m = c.get(Calendar.MONTH) + 1;
		if (y == year && m == month) {
			return now();
		}
		c.setTime(date);
		c.set(Calendar.DAY_OF_MONTH, c.getActualMaximum(Calendar.DAY_OF_MONTH));
		c.set(Calendar.HOUR_OF_DAY, 23);
		c.set(Calendar.MINUTE, 59);
		c.set(Calendar.SECOND, 59);
		c.set(Calendar.MILLISECOND, 999);
		return c.getTime();
	}


	/**
	 * 年首
	 * 
	 * @param date
	 * @return
	 */
	public static Date yearBegin(final Date date) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.set(Calendar.DAY_OF_YEAR, 1);
		c.set(Calendar.HOUR_OF_DAY, 0);
		c.set(Calendar.MINUTE, 0);
		c.set(Calendar.SECOND, 0);
		c.set(Calendar.MILLISECOND, 0);
		return c.getTime();
	}

	/**
	 * 年末
	 * 
	 * @param date
	 * @return
	 */
	public static Date yearEnd(final Date date) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.set(Calendar.DAY_OF_YEAR, c.getActualMaximum(Calendar.DAY_OF_YEAR));
		c.set(Calendar.HOUR_OF_DAY, 23);
		c.set(Calendar.MINUTE, 59);
		c.set(Calendar.SECOND, 59);
		c.set(Calendar.MILLISECOND, 999);
		return c.getTime();
	}

	/**
	 * 是否是指定日期
	 * 
	 * @param date
	 * @param day
	 * @return
	 */
	public static boolean isTheDay(final Date date, final Date day) {
		return date.getTime() >= DateUtils.dayBegin(day).getTime() && date.getTime() <= DateUtils.dayEnd(day).getTime();
	}

	/**
	 * 是否是今天
	 * 
	 * @param date
	 * @return
	 */
	public static boolean isToday(final Date date) {
		return isTheDay(date, DateUtils.now());
	}

	/**
	 * 增加、减少指定时数
	 * 
	 * @param date
	 * @param hour 要增加的时数（减少则为 负数）
	 * @return
	 */
	public static Date addHour(final Date date, int hour) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.add(Calendar.HOUR, hour);
		return c.getTime();
	}

	/**
	 * 增加、减少指定天数
	 * 
	 * @param date
	 * @param day  要增加的天数（减少则为 负数）
	 * @return
	 */
	public static Date addDay(final Date date, int day) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.add(Calendar.DATE, day);
		return c.getTime();
	}

	/**
	 * 增加、减少指定月数
	 * 
	 * @param date
	 * @param month 要增加的月数（减少则为 负数）
	 * @return
	 */
	public static Date addMonth(final Date date, int month) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.add(Calendar.MONTH, month);
		return c.getTime();
	}

	/**
	 * 增加、减少指定年数
	 * 
	 * @param date
	 * @param year 要增加的年数（减少则为 负数）
	 * @return
	 */
	public static Date addYear(final Date date, int year) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.add(Calendar.YEAR, year);
		return c.getTime();
	}

	/**
	 * 获取两个日期间的天数
	 * 
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	public static int amountDays(final Date startDate, final Date endDate) {
		long ms = endDate.getTime() - startDate.getTime();
		int ret = (int) (ms / 86400000L);
		return ret;
	}

	/**
	 * 获取两个日期间的月数（待改进）
	 * 
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	public static int amountMonths(final Date startDate, final Date endDate) {
		Calendar c1 = Calendar.getInstance(), c2 = Calendar.getInstance();
		boolean rev = false; // 翻转（是否 startDate > endDate）
		Date d1 = startDate, d2 = endDate;
		if (startDate.getTime() > endDate.getTime()) {
			rev = true;
			d1 = endDate;
			d2 = startDate;
		}
		c1.setTime(d1);
		c2.setTime(d2);
		int year1 = c1.get(Calendar.YEAR), year2 = c2.get(Calendar.YEAR), cy = year2 - year1; // count
																								// of
																								// year
		int month1 = c1.get(Calendar.MONTH), month2 = c2.get(Calendar.MONTH);
		int ret;
		if (cy > 0) {
			ret = (cy - 1) * 12;
			ret += month2;
			ret += (12 - month1);
		} else {
			ret = month2 - month1;
		}
		return rev ? (-1 * ret) : ret;
	}

	/**
	 * 获取两个日期间的年数（待改进）
	 * 
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	public static int amountYears(final Date startDate, final Date endDate) {
		Calendar c = Calendar.getInstance();
		boolean rev = false; // 翻转（是否 startDate > endDate）
		Date d1 = startDate, d2 = endDate;
		if (startDate.getTime() > endDate.getTime()) {
			rev = true;
			d1 = endDate;
			d2 = startDate;
		}
		c.setTime(d1);
		int y1 = c.get(Calendar.YEAR);
		c.setTime(d2);
		int y2 = c.get(Calendar.YEAR);
		int ret = y2 - y1;
		return rev ? (-1 * ret) : ret;
	}

	/** yyyy-MM-dd HH:mm:ss.SSS */
	public static final int YMDHMS_MS = 0;
	/** yyyy-MM-dd HH:mm:ss */
	public static final int YMDHMS = 1;
	/** yyyy-MM-dd HH:mm */
	public static final int YMDHM = 2;
	/** yyyy-MM-dd HH */
	public static final int YMDH = 3;
	/** yyyy-MM-dd */
	public static final int YMD = 4;
	/** yyyy-MM */
	public static final int YM = 5;
	/** yyyy */
	public static final int Y = 6;

	/** yyyyMM */
	public static final int YMM = 7;
	/** yyyyMMDD */
	public static final int YMMD = 8;

	/** dd */
	public static final int DD = 9;

	/** 12小时制 */
	public static final int D = 10;

	/** 月份 */
	public static final int MM = 11;

	private static final String[] SDF = new String[] {
			"yyyy-MM-dd HH:mm:ss.SSS",
			"yyyy-MM-dd HH:mm:ss", 
			"yyyy-MM-dd HH:mm",
			"yyyy-MM-dd HH",
			"yyyy-MM-dd",
			"yyyy-MM",
			"yyyy",
			"yyyyMM",
			"yyyyMMdd",
			"dd",
			"yyyy-MM-dd hh:mm:ss",
			"MM"};

	/**
	 * yyyy-MM-dd HH:mm:ss
	 * 
	 * @param date
	 * @return
	 */
	public static String toString(final Date date) {
		if (date == null) {
			return "";
		}
		return new SimpleDateFormat(SDF[1]).format(date);
	}

	/**
	 * 转换到字符串
	 * 
	 * @param date
	 * @param type DateUtils.YMDHMS : "yyyy-MM-dd HH:mm:ss", DateUtils.YMD :
	 *             "yyyy-MM-dd", DateUtils.YM : "yyyy-MM", DateUtils.Y : "yyyy"
	 * @return
	 */
	public static String toString(final Date date, int type) {
		if (date == null) {
			return "null";
		}
		switch (type) {
		case YMDHMS:
			return new SimpleDateFormat(SDF[1]).format(date);
		case YMDHM:
			return new SimpleDateFormat(SDF[2]).format(date);
		case YMDH: 
			return new SimpleDateFormat(SDF[3]).format(date);
		case YMD:  
			return new SimpleDateFormat(SDF[4]).format(date);
		case YM:   
			return new SimpleDateFormat(SDF[5]).format(date);
		case Y:    
			return new SimpleDateFormat(SDF[6]).format(date);
		case YMM:  
			return new SimpleDateFormat(SDF[7]).format(date);
		case YMMD: 
			return new SimpleDateFormat(SDF[8]).format(date);
		case DD:   
			return new SimpleDateFormat(SDF[9]).format(date);
		case D:    
			return new SimpleDateFormat(SDF[10]).format(date);
		case MM:   
			return new SimpleDateFormat(SDF[11]).format(date);
		default:
			break;
		}
		return "unknow";
	}

	/**
	 * 转换到字符串
	 * 
	 * @param date
	 * @param fmt  日期格式化字符串
	 * @return
	 */
	public static String toString(final Date date, String fmt) {
		SimpleDateFormat sdf = new SimpleDateFormat(fmt);
		return sdf.format(date);
	}

	public static String toString(String fmt) {
		Date d = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat(fmt);
		return sdf.format(d);
	}

	/**
	 * 从字符串解析为日期型
	 * 
	 * @param s
	 * @param fmt
	 * @return
	 */
	public static Date parse(final String s, final String fmt) {
		SimpleDateFormat sdf = new SimpleDateFormat(fmt);
		try {
			return sdf.parse(s);
		} catch (ParseException e) {
		}
		return null;
	}

	/**
	 * 从字符串(yyyy-MM-dd HH:mm:ss)解析为日期型
	 * 
	 * @param s
	 * @return
	 */
	public static Date parse(final String s) {
		try {
			return new SimpleDateFormat(SDF[1]).parse(s);
		} catch (ParseException e) {
		}
		return null;
	}

	public static Date stringToDate(String str) {
		SimpleDateFormat sim = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date d = null;
		try {
			d = sim.parse(str);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return d;
	}

	public static Date stringToDate2(String str) {
		SimpleDateFormat sim = new SimpleDateFormat("yyyyMMdd");
		Date d = null;
		try {
			d = sim.parse(str);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return d;
	}

	public static Date stringToDate1(String str) {
		SimpleDateFormat sim = new SimpleDateFormat("yyyy-MM-dd");
		Date d = null;
		try {
			d = sim.parse(str);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return d;
	}

	public static String dateToString(Date str) {
		SimpleDateFormat sim = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		String string = sim.format(str);
		return string;
	}

	public static String dateToString1(Date str) {
		SimpleDateFormat sim = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String string = sim.format(str);
		return string;
	}

	public static long parseLong(final String s, final String fmt) {
		SimpleDateFormat sdf = new SimpleDateFormat(fmt);
		try {
			Date t = sdf.parse(s);
			return t == null ? 0L : t.getTime();
		} catch (ParseException e) {
		}
		return 0L;
	}

	public static long parseLong(final String s) {
		try {
			Date t = new SimpleDateFormat(SDF[1]).parse(s);
			return t == null ? 0L : t.getTime();
		} catch (ParseException e) {
		}
		return 0L;
	}

	/**
	 * 
	 * @Description:当前时间，往前后移动多少天后的日期
	 * @param day ： 前面移动就是负数，往后移动就是正数
	 * @return
	 * @author: XUJUN
	 * @time:2017年3月27日 下午1:26:28
	 */
	public static String getDateByday(int day) {
		Date date = new Date();// 取时间
		Calendar calendar = new GregorianCalendar();
		calendar.setTime(date);
		calendar.add(Calendar.DATE, day);// 把日期往后增加一天.整数往后推,负数往前移动
		date = calendar.getTime(); // 这个时间就是日期往后推一天的结果
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		String dateString = formatter.format(date);
		return dateString;
	}

	public static String getDateByday(int day, Date date) {
		Calendar calendar = new GregorianCalendar();
		calendar.setTime(date);
		calendar.add(Calendar.DATE, day);// 把日期往后增加一天.整数往后推,负数往前移动
		date = calendar.getTime(); // 这个时间就是日期往后推一天的结果
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		String dateString = formatter.format(date);
		return dateString;
	}

	public static Date getDateformate(int day, Date date) {
		return parse(getDateByday(day, date), "yyyy-MM-dd");
	}

	public static Date getDateformate(int day) {
		return parse(getDateByday(day), "yyyy-MM-dd");
	}

	/*
	 * public static String HSDateToJava(DB_Time dbtime) { String date = ""; DB_INT
	 * y = new DB_INT(); DB_INT m = new DB_INT(); DB_INT d = new DB_INT(); DB_INT h
	 * = new DB_INT(); DB_INT mm = new DB_INT(); DB_INT s = new DB_INT(); DB_INT u =
	 * new DB_INT(); DBTimeOperation.DB_MakeTime(dbtime, y, m, d, h, mm, s, u); date
	 * += y.getInt() + "-"; if (m.getInt() < 10) { date += "0" + m.getInt() + "-"; }
	 * else { date += m.getInt() + "-"; } if (d.getInt() < 10) { date += "0" +
	 * d.getInt() + " "; } else { date += d.getInt() + " "; } if (h.getInt() < 10) {
	 * date += "0" + h.getInt() + ":"; } else { date += h.getInt() + ":"; } if
	 * (mm.getInt() < 10) { date += "0" + mm.getInt() + ":"; } else { date +=
	 * mm.getInt() + ":"; } if (s.getInt() < 10) { date += "0" + s.getInt(); } else
	 * { date += s.getInt(); } return date; } //
	 */

	/** 
	 * 获取本周的开始时间
	 * @return
	 */
	public static Date getBeginDayOfWeek() {
		Date date = new Date();
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		int dayofweek = cal.get(Calendar.DAY_OF_WEEK);
		if (dayofweek == 1) {
			dayofweek += 7;
		}
		cal.add(Calendar.DATE, 2 - dayofweek);
		return getDayStartTime(cal.getTime());
	}

	/**
	 * 获取本周的结束时间
	 * @return
	 */
	public static Date getEndDayOfWeek() {
		Calendar cal = Calendar.getInstance();
		cal.setTime(getBeginDayOfWeek());
		cal.add(Calendar.DAY_OF_WEEK, 6);
		Date weekEndSta = cal.getTime();
		return getDayEndTime(weekEndSta);
	}

	/**
	 *  获取本周的开始时间
	 * @param date
	 * @return
	 */
	public static Date getBeginDayOfWeek1(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		int dayofweek = cal.get(Calendar.DAY_OF_WEEK);
		if (dayofweek == 1) {
			dayofweek += 7;
		}
		cal.add(Calendar.DATE, 2 - dayofweek);
		return getDayStartTime(cal.getTime());
	}

	/**
	 * 获取本周的结束时间
	 * @param date
	 * @return
	 */
	public static Date getEndDayOfWeek1(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.DAY_OF_WEEK, 6);
		Date weekEndSta = cal.getTime();
		return getDayEndTime(weekEndSta);
	}

	/**
	 * 获取某个日期的开始时间
	 * @param d
	 * @return
	 */
	public static Timestamp getDayStartTime(Date d) {
		Calendar calendar = Calendar.getInstance();
		if (null != d) {
			calendar.setTime(d);
		}
		calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH), 0,
				0, 0);
		calendar.set(Calendar.MILLISECOND, 0);
		return new Timestamp(calendar.getTimeInMillis());
	}

	/**
	 * 获取某个日期的结束时间
	 * @param d
	 * @return
	 */
	public static Timestamp getDayEndTime(Date d) {
		Calendar calendar = Calendar.getInstance();
		if (null != d) {
			calendar.setTime(d);
		}
		calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH), 23,
				59, 59);
		calendar.set(Calendar.MILLISECOND, 999);
		return new Timestamp(calendar.getTimeInMillis());
	}

	/**
	 * 取当前年份
	 * 
	 * @return
	 */
	public static String getYear() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
		return sdf.format(Calendar.getInstance().getTime());
	}

	/**
	 * 取当前年份
	 * 
	 * @return
	 */
	public static String getYear(Date date) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
		return sdf.format(date);
	}

	/**
	 * 获取年中第几周
	 * 
	 * @return
	 */
	public static int getWeekofYear(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		return calendar.get(Calendar.WEEK_OF_YEAR);
	}

	/**
	 * 获取去年的年份 .
	 * 
	 * @author liyuexing
	 * @return Date:2018年1月29日下午3:56:32
	 */
	public static String getLastYear() {
		Calendar c = Calendar.getInstance();
		c.setTime(new Date());
		c.add(Calendar.YEAR, -1);
		Date y = c.getTime();
		return new SimpleDateFormat(SDF[6]).format(y);
	}

	/**
	 * 取当前月份
	 * 
	 * @return
	 */
	public static String getMonth() {
		SimpleDateFormat sdf = new SimpleDateFormat("M");
		return sdf.format(Calendar.getInstance().getTime());
	}

	/**
	 * 取当前小时
	 * 
	 * @return
	 */
	public static String getHour() {
		SimpleDateFormat sdf = new SimpleDateFormat("H");
		return sdf.format(Calendar.getInstance().getTime());
	}

	/**
	 * 取当前分钟
	 * 
	 * @return
	 */
	public static String getMinute() {
		SimpleDateFormat sdf = new SimpleDateFormat("mm");
		return sdf.format(Calendar.getInstance().getTime());
	}

	/**
	 * 取当前年月
	 * 
	 * @return
	 */
	public static String getYearAndMonth() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
		return sdf.format(Calendar.getInstance().getTime());
	}

	/**
	 * 取一个月有多少天
	 * 
	 * @param year
	 * @param month
	 * @return
	 */
	public static int getMaxDayByYearMonth(int year, int month) {
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.YEAR, year);
		calendar.set(Calendar.MONTH, month - 1);
		return calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
	}

	public static int getDayByYearMonth(int year, int month) {
		Calendar cale = Calendar.getInstance();
		int day = cale.get(Calendar.DATE);
		int y = cale.get(Calendar.YEAR);
		int m = cale.get(Calendar.MONTH) + 1;
		if (y == year && m == month) {
			return day;
		}
		cale.set(Calendar.YEAR, year);
		cale.set(Calendar.MONTH, month - 1);
		return cale.getActualMaximum(Calendar.DAY_OF_MONTH);
	}

	/**
	 * 取某天是星期几
	 * 
	 * @param dt
	 * @return
	 */
	public static String getDayOfWeek(Date dt) {
		String[] weekDays = { "星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六" };
		Calendar cal = Calendar.getInstance();
		cal.setTime(dt);
		int w = cal.get(Calendar.DAY_OF_WEEK) - 1;
		if (w < 0) {
			w = 0;
		}
		return weekDays[w];
	}

	/**
	 * 取某天是一年中的第几天
	 * 
	 * @param dt
	 * @return
	 */
	public static int getDayOfYear(Date dt) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(dt);
		int w = cal.get(Calendar.DAY_OF_YEAR);
		return w;
	}

	/**
	 * 取某天是一月中的第几天
	 * 
	 * @param dt
	 * @return
	 */
	public static int getDayOfMonth(Date dt) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(dt);
		int w = cal.get(Calendar.DAY_OF_MONTH);
		return w;
	}

	/**
	 * 取指定日期前x天或后x天的日期
	 * 
	 * @param inputDate
	 * @param days
	 * @return
	 */
	public static Date getDate(Date dt, int days) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(dt);
		cal.add(Calendar.DATE, days);
		return cal.getTime();
	}

	public static String stampToDate(long lt) {
		String res;
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date(lt);
		res = simpleDateFormat.format(date);
		return res;
	}

	/**
	 * 取指定日期所在周的第一天和最后一天日期
	 * 
	 * @param dt
	 * @return
	 */
	public static Map<String, Date> getFirstLastDayOfWeek(Date dt) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(dt);
		int d = 0;
		if (cal.get(Calendar.DAY_OF_WEEK) == 1) {
			d = -6;
		} else {
			d = 2 - cal.get(Calendar.DAY_OF_WEEK);
		}
		cal.add(Calendar.DAY_OF_WEEK, d);
		// 所在周开始日期
		Date firstDate = cal.getTime();
		cal.add(Calendar.DAY_OF_WEEK, 6);
		// 所在周结束日期
		Date lastDate = cal.getTime();
		Map<String, Date> map = new HashMap<String, Date>();
		map.put("first", firstDate);
		map.put("last", lastDate);
		return map;
	}

	/**
	 * 
	 * <pre>
	 *<b>Description:</b> 
	 *    获取当前日期 格式如：2017-07-15
	 *<b>Author:</b> jiesong
	 *<b>Date:</b> 2017年7月15日 下午3:28:48
	 *&#64;param day
	 *&#64;return
	 * </pre>
	 */
	public static Date getDate(Date day) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(day);
		calendar.add(Calendar.DAY_OF_MONTH, 0);
		Date date1 = new Date(calendar.getTimeInMillis());
		return date1;
	}

	/**
	 * 
	 * <pre>
	 *<b>Description:</b> 
	 *    获取昨日日期： 格式如：2017-07-14
	 *<b>Author:</b> jiesong
	 *<b>Date:</b> 2017年7月15日 下午3:29:35
	 *&#64;param day
	 *&#64;return
	 * </pre>
	 */
	public static Date getDateLast(Date day) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(day);
		calendar.add(Calendar.DAY_OF_MONTH, -1);
		Date date1 = new Date(calendar.getTimeInMillis());
		return date1;
	}

	/**
	 * 返回某年第几周的开始日期和结束日期
	 * 
	 * @param year
	 * @param week
	 * @return
	 */
	public static Map<String, Object> getWeekDays(int year, int week) {
		Calendar cal = Calendar.getInstance();

		cal.set(Calendar.YEAR, year);
		cal.setFirstDayOfWeek(Calendar.MONDAY);
		cal.set(Calendar.WEEK_OF_YEAR, week);
		int weekNum = cal.get(Calendar.WEEK_OF_YEAR);
		if (week > weekNum) {
			return null;
		}
		cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String startDate = sdf.format(cal.getTime());
		cal.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
		String endDate = sdf.format(cal.getTime());
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("startDate", startDate);
		map.put("endDate", endDate);
		return map;
	}

	/**
	 * 取当前时间是本年的第几周 星期1为一周的第一天
	 * 
	 * @return
	 */
	public static int getWeek() {
		Calendar cal = Calendar.getInstance();
		cal.setFirstDayOfWeek(Calendar.MONDAY);
		int weekNum = cal.get(Calendar.WEEK_OF_YEAR);
		return weekNum;
	}

	/**
	 * 取当前日期返回slot 不在奇数点45分和偶数点15分之间的返回-1
	 * 
	 * @return
	 */
	public static int getLoadRecordSlot() {
		Calendar cal = Calendar.getInstance();
		int hour = cal.get(Calendar.HOUR_OF_DAY);
		int minute = cal.get(Calendar.MINUTE);
		if (hour % 2 == 0) {
			if (minute < 15) {
				return hour;
			}
		} else {
			if (minute > 45) {
				if (hour != 23) {
					return hour + 1;
				}
				else {
					return 0;
				}
			}
		}
		return -1;
	}

	/**
	 * 获取固定间隔时刻集合
	 * 
	 * @param start    开始时间
	 * @param end      结束时间
	 * @param interval 时间间隔(单位：分钟)
	 * @return
	 */
	public static List<String> getIntervalTimeList(String start, String end, int interval) {
		Date startDate = stringToDate(start);
		Date endDate = stringToDate(end);
		List<String> list = new ArrayList<>();
		while (startDate.getTime() <= endDate.getTime()) {
			list.add(DateUtils.toString(startDate, 1));
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(startDate);
			calendar.add(Calendar.MINUTE, interval);
			if (calendar.getTime().getTime() > endDate.getTime()) {
				if (!startDate.equals(endDate)) {
					list.add(DateUtils.toString(endDate, 1));
				}
				startDate = calendar.getTime();
			} else {
				startDate = calendar.getTime();
			}

		}
		return list;
	}

	public static String getLastMonth(Date date) {
		SimpleDateFormat format = new SimpleDateFormat("yyyyMM");
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.add(Calendar.MONTH, -1);
		Date m = c.getTime();
		return format.format(m);
	}

	public static String getNowMonth(Date date) {
		SimpleDateFormat format = new SimpleDateFormat("yyyyMM");
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		Date m = c.getTime();
		return format.format(m);
	}

	/**
	 * 获取两个年份之间的年份集合
	 * 
	 * @author liuzhipeng
	 * @date 2018年8月30日下午2:20:11
	 * @param start
	 * @param end
	 * @return
	 */
	public static List<String> getYearBetweenCount(String start, String end) {
		List<String> strList = new ArrayList<String>();
		for (int i = Integer.parseInt(start); i <= Integer.parseInt(end); i++) {
			strList.add(String.valueOf(i));
		}
		return strList;
	}

	/**
	 * 获取当前月份的天数集合
	 * 
	 * @author liuzhipeng
	 * @date 2018年8月30日下午2:19:37
	 * @return
	 */
	public static List<String> getDayByMonth() {
		Calendar calendar = Calendar.getInstance();
		int count = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
		List<String> strList = new ArrayList<String>();
		for (int i = 1; i <= count; i++) {
			strList.add(String.valueOf(i));
		}
		return strList;

	}

	/**
	 * 根据 年、月 获取对应的月份 的 天数
	 */
	public static int getDaysByYearMonth(int year, int month) {
		Calendar a = Calendar.getInstance();
		a.set(Calendar.YEAR, year);
		a.set(Calendar.MONTH, month - 1);
		a.set(Calendar.DATE, 1);
		a.roll(Calendar.DATE, -1);
		int maxDate = a.get(Calendar.DATE);
		return maxDate;
	}

	/**
	 * 获取月的最后一天
	 * 
	 * @param year
	 * @param month
	 * @return
	 */
	public static String getDateLastDay(String year, String month) {
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.YEAR, Integer.parseInt(year));
		calendar.set(Calendar.MONTH, Integer.parseInt(month) - 1);
		calendar.set(Calendar.DAY_OF_MONTH, 1);
		calendar.set(Calendar.DATE, calendar.getActualMaximum(Calendar.DATE));
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		return format.format(calendar.getTime());
	}

	/**
	 * 获取日期到年初的天数
	 * 
	 * @param date 天
	 * @return 天数
	 * @throws Exception 异常
	 */
	public static int getDayCount(String strDate) throws Exception {
		int days = 0;
		if (strDate != null && strDate.length() > 4) {
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String starttime = strDate.substring(0, 4) + "-01-01 00:00:00";
			String endtime = strDate + " 23:59:59";
			Date d1 = format.parse(starttime);
			Date d2 = format.parse(endtime);
			long s1 = d1.getTime();
			long s2 = d2.getTime();
			days = (int) Math.ceil((s2 - s1) * 1.0 / (1000 * 60 * 60 * 24));
		}
		return days;
	}

	/**
	 * 获取年度比值 查询月底自然日历天数/当年总天数
	 * 
	 */
	public static BigDecimal getDateYearRate(String yearMonth) {
		BigDecimal dDateYearRate = null;
		try {
			String year = yearMonth.substring(0, 4);
			String month = yearMonth.substring(4);
			dDateYearRate = BigDecimal.valueOf(
					getDayCount(getDateLastDay(year, month)) * 1.0 / getDayCount(getDateLastDay(year, "12")) * 100);
		} catch (Exception e) {

		}
		return dDateYearRate;
	}

	public static void main(String[] args) {
		try {
			System.out.println(getDateYearRate("201906"));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
