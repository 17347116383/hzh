
package com.itdy.hqsm.common.lang;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.FastDateFormat;

import java.lang.management.ManagementFactory;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.time.Clock;
import java.time.Instant;
import java.util.*;

/**
 * 日期工具类, 继承org.apache.commons.lang.time.DateUtils类
 * @author
 * @version
 */
public class DateUtils extends org.apache.commons.lang3.time.DateUtils {



	
	private static String[] parsePatterns = {
		"yyyy-MM-dd", "yyyy-MM-dd HH:mm:ss", "yyyy-MM-dd HH:mm", "yyyy-MM-dd HH", "yyyy-MM",
		"yyyy/MM/dd", "yyyy/MM/dd HH:mm:ss", "yyyy/MM/dd HH:mm", "yyyy/MM/dd HH", "yyyy/MM",
		"yyyy.MM.dd", "yyyy.MM.dd HH:mm:ss", "yyyy.MM.dd HH:mm", "yyyy.MM.dd HH", "yyyy.MM", 
		"yyyy年MM月dd日", "yyyy年MM月dd日 HH时mm分ss秒", "yyyy年MM月dd日 HH时mm分", "yyyy年MM月dd日 HH时", "yyyy年MM月",
		"yyyy"};
	
	/**
	 * 得到日期字符串 ，转换格式（yyyy-MM-dd）
	 */
	public static String formatDate(Date date) {

		return formatDate(date, "yyyy-MM-dd");
	}
	
	/**
	 * 得到日期字符串 默认格式（yyyy-MM-dd） pattern可以为："yyyy-MM-dd" "HH:mm:ss" "E"
	 */
	public static String formatDate(long dateTime, String pattern) {

		return formatDate(new Date(dateTime), pattern);
	}
	
	/**
	 * 得到日期字符串 默认格式（yyyy-MM-dd） pattern可以为："yyyy-MM-dd" "HH:mm:ss" "E"
	 */
	public static String formatDate(Date date, String pattern) {
		String formatDate = null;
		if (date != null){
//			if (StringUtils.isNotBlank(pattern)) {
//				formatDate = DateFormatUtils.format(date, pattern);
//			} else {
//				formatDate = DateFormatUtils.format(date, "yyyy-MM-dd");
//			}
			if (StringUtils.isBlank(pattern)) {
				pattern = "yyyy-MM-dd";
			}
			formatDate = FastDateFormat.getInstance(pattern).format(date);
		}
		return formatDate;
	}
	
	/**
	 * 得到日期时间字符串，转换格式（yyyy-MM-dd HH:mm:ss）
	 */
	public static String formatDateTime(Date date) {

		return formatDate(date, "yyyy-MM-dd HH:mm:ss");
	}
    
	/**
	 * 得到当前日期字符串 格式（yyyy-MM-dd）
	 */
	public static String getDate() {
		return getDate("yyyy-MM-dd");
	}
	
	/**
	 * 得到当前日期字符串 格式（yyyy-MM-dd） pattern可以为："yyyy-MM-dd" "HH:mm:ss" "E"
	 */
	public static String getDate(String pattern) {
//		return DateFormatUtils.format(new Date(), pattern);
		return FastDateFormat.getInstance(pattern).format(new Date());
	}
	
	/**
	 * 得到当前日期前后多少天，月，年的日期字符串
	 * @param pattern 格式（yyyy-MM-dd） pattern可以为："yyyy-MM-dd" "HH:mm:ss" "E"
	 * @param amont 数量，前为负数，后为正数
	 * @param type 类型，可参考Calendar的常量(如：Calendar.HOUR、Calendar.MINUTE、Calendar.SECOND)
	 * @return
	 */
	public static String getDate(String pattern, int amont, int type) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(new Date());
		calendar.add(type, amont);
//		return DateFormatUtils.format(calendar.getTime(), pattern);
		return FastDateFormat.getInstance(pattern).format(calendar.getTime());
	}
	
	/**
	 * 得到当前时间字符串 格式（HH:mm:ss）
	 */
	public static String getTime() {
		return formatDate(new Date(), "HH:mm:ss");
	}

	/**
	 * 得到当前日期和时间字符串 格式（yyyy-MM-dd HH:mm:ss）
	 */
	public static String getDateTime() {
		return formatDate(new Date(), "yyyy-MM-dd HH:mm:ss");
	}

	/**
	 * 得到当前年份字符串 格式（yyyy）
	 */
	public static String getYear() {
		return formatDate(new Date(), "yyyy");
	}

	/**
	 * 得到当前月份字符串 格式（MM）
	 */
	public static String getMonth() {
		return formatDate(new Date(), "MM");
	}

	/**
	 * 得到当天字符串 格式（dd）
	 */
	public static String getDay() {
		return formatDate(new Date(), "dd");
	}

	/**
	 * 得到当前星期字符串 格式（E）星期几
	 */
	public static String getWeek() {
		return formatDate(new Date(), "E");
	}
	
	/**
	 * 日期型字符串转化为日期 格式   see to DateUtils#parsePatterns
	 */
	public static Date parseDate(Object str) {
		if (str == null){
			return null;
		}
		try {
			return parseDate(str.toString(), parsePatterns);
		} catch (ParseException e) {
			return null;
		}
	}

	/**
	 * 获取过去的天数
	 * @param date
	 * @return
	 */
	public static long pastDays(Date date) {
		long t = System.currentTimeMillis()-date.getTime();
		return t/(24*60*60*1000);
	}

	/**
	 * 获取过去的小时
	 * @param date
	 * @return
	 */
	public static long pastHour(Date date) {
		long t = System.currentTimeMillis()-date.getTime();
		return t/(60*60*1000);
	}
	
	/**
	 * 获取过去的分钟
	 * @param date
	 * @return
	 */
	public static long pastMinutes(Date date) {
		long t = System.currentTimeMillis()-date.getTime();
		return t/(60*1000);
	}
    
	/**
	 * 获取两个日期之间的天数
	 * 
	 * @param before
	 * @param after
	 * @return
	 */
	public static double getDistanceOfTwoDate(Date before, Date after) {
		long beforeTime = before.getTime();
		long afterTime = after.getTime();
		return (afterTime - beforeTime) / (1000 * 60 * 60 * 24);
	}
	
	/**
	 * 获取某月有几天
	 * @param date 日期
	 * @return 天数
	 */
	public static int getMonthHasDays(Date date){
//		String yyyyMM = new SimpleDateFormat("yyyyMM").format(date);
		String yyyyMM = FastDateFormat.getInstance("yyyyMM").format(date);
		String year = yyyyMM.substring(0, 4);
		String month = yyyyMM.substring(4, 6);
		String day31 = ",01,03,05,07,08,10,12,";
		String day30 = "04,06,09,11";
		int day = 0;
		if (day31.contains(month)) {
			day = 31;
		} else if (day30.contains(month)) {
			day = 30;
		} else {
			int y = Integer.parseInt(year);
			if ((y % 4 == 0 && (y % 100 != 0)) || y % 400 == 0) {
				day = 29;
			} else {
				day = 28;
			}
		}
		return day;
	}
	
	/**
	 * 获取日期是当年的第几周
	 * @param date
	 * @return
	 */
	public static int getWeekOfYear(Date date){
		Calendar cal = Calendar.getInstance(); 
		cal.setTime(date);
		return cal.get(Calendar.WEEK_OF_YEAR);
	}

	/**
	 * 获取一天的开始时间（如：2015-11-3 00:00:00.000）
	 * @param date 日期
	 * @return
	 */
	public static Date getOfDayFirst(Date date) {
		if (date == null){
			return null;
		}
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(Calendar.HOUR, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);
		return calendar.getTime();
	}
	
	/**
	 * 获取一天的最后时间（如：2015-11-3 23:59:59.999）
	 * @param date 日期
	 * @return
	 */
	public static Date getOfDayLast(Date date) {
		if (date == null){
			return null;
		}
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(Calendar.HOUR, 23);
		calendar.set(Calendar.MINUTE, 59);
		calendar.set(Calendar.SECOND, 59);
		calendar.set(Calendar.MILLISECOND, 999);
		return calendar.getTime();
	}
	
	/**
	 * 获取服务器启动时间
	 * @param
	 * @return
	 */
	public static Date getServerStartDate(){
		long time = ManagementFactory.getRuntimeMXBean().getStartTime();
		return new Date(time);
	}
	
	/**
	 * 格式化为日期范围字符串
	 * @param beginDate 2018-01-01
	 * @param endDate 2018-01-31
	 * @return 2018-01-01 ~ 2018-01-31
	 * @author ThinkGem
	 */
	public static String formatDateBetweenString(Date beginDate, Date endDate){
		String begin = DateUtils.formatDate(beginDate);
		String end = DateUtils.formatDate(endDate);
		if (StringUtils.isNoneBlank(begin, end)){
			return begin + " ~ " + end;
		}
		return null;
	}
	
	/**
	 * 解析日期范围字符串为日期对象
	 * @param dateString 2018-01-01 ~ 2018-01-31
	 * @return new Date[]{2018-01-01, 2018-01-31}
	 * @author ThinkGem
	 */
	public static Date[] parseDateBetweenString(String dateString){
		Date beginDate = null; Date endDate = null;
		if (StringUtils.isNotBlank(dateString)){
			String[] ss = StringUtils.split(dateString, "~");
			if (ss != null && ss.length == 2){
				String begin = StringUtils.trim(ss[0]);
				String end = StringUtils.trim(ss[1]);
				if (StringUtils.isNoneBlank(begin, end)){
					beginDate = DateUtils.parseDate(begin);
					endDate = DateUtils.parseDate(end);
				}
			}
		}
		return new Date[]{beginDate, endDate};
	}

	/**
	 *
	 * @return  Date
	 */
	public static Date  mainDate() {
		Clock clock = Clock.systemDefaultZone();
		//System.out.println("------"+clock);
		long millis = clock.millis();
		//System.out.println("------"+millis);
		Instant instant = clock.instant();
		//System.out.println("------"+instant);
		Date legacyDate = Date.from(instant);   // legacy java.util.Date

		return legacyDate;
	}
	/*public static void main(String[] args) throws ParseException {
		System.out.println(formatDate(parseDate("2010/3/6")));
		System.out.println(getDate("yyyy年MM月dd日 E"));
		long time = new Date().getTime()-parseDate("2012-11-19").getTime();
		System.out.println(time/(24*60*60*1000));
		System.out.println(getWeekOfYear(new Date()));
		System.out.println(formatDate(getOfDayFirst(parseDate("2015/3/6")),"yyyy-MM-dd HH:mm:ss.sss"));
		System.out.println(formatDate(getOfDayLast(parseDate("2015/6/6")),"yyyy-MM-dd HH:mm:ss.sss"));
	    mainDate();
	}
	*/

	//-----------------------------------------------------------------------------------------

	/**
	 * 获取现在时间
	 *
	 * @return 返回时间类型 yyyy-MM-dd HH:mm:ss
	 */
	public static Date getNowDate() {
		Date currentTime = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String dateString = formatter.format(currentTime);
		ParsePosition pos = new ParsePosition(8);
		Date currentTime_2 = formatter.parse(dateString, pos);
		return currentTime_2;
	}
	/**
	 * 获取现在时间
	 *
	 * @return返回短时间格式 yyyy-MM-dd
	 */
	DateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
	DateFormat format2= new SimpleDateFormat("yyyy年MM月dd日 HH时mm分ss秒");
	Date date = null;
	String str = null;

/*// String转Date
	String  str = "2007-1-18";

		try {
			date = format1.parse(str);
		} catch (ParseException e1) {
			e1.printStackTrace();
		}
		try {
			data = format2.parse(str);
		} catch (ParseException e1) {
			e1.printStackTrace();
		}*/

	/**
	 * 获取现在时间
	 *
	 * @return返回字符串格式 yyyy-MM-dd HH:mm:ss
	 */
	public static String getStringDate() {
		Date currentTime = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String dateString = formatter.format(currentTime);
		return dateString;
	}
	/**
	 * 获取现在时间
	 *
	 * @return 返回短时间字符串格式yyyy-MM-dd
	 */
	public static String getStringDateShort() {
		Date currentTime = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		String dateString = formatter.format(currentTime);
		return dateString;
	}
	/**
	 * 获取时间 小时:分;秒 HH:mm:ss
	 *
	 * @return
	 */
	public static String getTimeShort() {
		SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss");
		Date currentTime = new Date();
		String dateString = formatter.format(currentTime);
		return dateString;
	}
	/**
	 * 将长时间格式字符串转换为时间 yyyy-MM-dd HH:mm:ss
	 *
	 * @param strDate
	 * @return
	 */
	public static Date strToDateLong(String strDate) {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		ParsePosition pos = new ParsePosition(0);
		Date strtodate = formatter.parse(strDate, pos);
		return strtodate;
	}
	/**
	 * 将长时间格式时间转换为字符串 yyyy-MM-dd HH:mm:ss
	 *
	 * @param dateDate
	 * @return
	 */
	public static String dateToStrLong(java.util.Date dateDate) {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String dateString = formatter.format(dateDate);
		return dateString;
	}
	/**
	 * 将短时间格式时间转换为字符串 yyyy-MM-dd
	 *
	 * @param dateDate
	 * @param
	 * @return
	 */
	public static String dateToStr(java.util.Date dateDate) {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		String dateString = formatter.format(dateDate);
		return dateString;
	}
	/**
	 * 将短时间格式字符串转换为时间 yyyy-MM-dd
	 *
	 * @param strDate
	 * @return
	 */
	public static Date strToDate(String strDate) {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		ParsePosition pos = new ParsePosition(0);
		Date strtodate = formatter.parse(strDate, pos);
		return strtodate;
	}
	/**
	 * 得到现在时间
	 *
	 * @return
	 */
	public static Date getNow() {
		Date currentTime = new Date();
		return currentTime;
	}
	/**
	 * 提取一个月中的最后一天
	 *
	 * @param day
	 * @return
	 */
	public static Date getLastDate(long day) {
		Date date = new Date();
		long date_3_hm = date.getTime() - 3600000 * 34 * day;
		Date date_3_hm_date = new Date(date_3_hm);
		return date_3_hm_date;
	}
	/**
	 * 得到现在时间
	 *
	 * @return 字符串 yyyyMMdd HHmmss
	 */
	public static String getStringToday() {
		Date currentTime = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd HHmmss");
		String dateString = formatter.format(currentTime);
		return dateString;
	}
	/**
	 * 得到现在小时
	 */
	public static String getHour() {
		Date currentTime = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String dateString = formatter.format(currentTime);
		String hour;
		hour = dateString.substring(11, 13);
		return hour;
	}
	/**
	 * 得到现在分钟
	 *
	 * @return
	 */
	public static String getTimes() {
		Date currentTime = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String dateString = formatter.format(currentTime);
		String min;
		min = dateString.substring(14, 16);
		return min;
	}
	/**
	 * 根据用户传入的时间表示格式，返回当前时间的格式 如果是yyyyMMdd，注意字母y不能大写。
	 *
	 * @param sformat
	 *             yyyyMMddhhmmss
	 * @return
	 */
	public static String getUserDate(String sformat) {
		Date currentTime = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat(sformat);
		String dateString = formatter.format(currentTime);
		return dateString;
	}
//--------------------------------------------------------------------------------------------------------------------------------




		/**
		 * 获取现在时间
		 *
		 * @return 返回时间类型 yyyy-MM-dd HH:mm:ss
		 */
		public static Date getNowDates() {
			Date currentTime = new Date();
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String dateString = formatter.format(currentTime);
			ParsePosition pos = new ParsePosition(8);
			Date currentTime_2 = formatter.parse(dateString, pos);
			return currentTime_2;
		}

		/**
		 * 获取现在时间
		 *
		 * @return返回短时间格式 yyyy-MM-dd
		 */
		public static Date getNowDateShort() {
			Date currentTime = new Date();
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
			String dateString = formatter.format(currentTime);
			ParsePosition pos = new ParsePosition(8);
			Date currentTime_2 = formatter.parse(dateString, pos);
			return currentTime_2;
		}

		/**
		 * 获取现在时间
		 *
		 * @return返回字符串格式 yyyy-MM-dd HH:mm:ss
		 */
		public static String getStringDatess() {
			Date currentTime = new Date();
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String dateString = formatter.format(currentTime);
			return dateString;
		}

		/**
		 * 获取现在时间
		 *
		 * @return 返回短时间字符串格式yyyy-MM-dd
		 */
		public static String getStringDateShortss() {
			Date currentTime = new Date();
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
			String dateString = formatter.format(currentTime);
			return dateString;
		}

		/**
		 * 获取时间 小时:分;秒 HH:mm:ss
		 *
		 * @return
		 */
		public static String getTimeShortS() {
			SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss");
			Date currentTime = new Date();
			String dateString = formatter.format(currentTime);
			return dateString;
		}

		/**
		 * 将长时间格式字符串转换为时间 yyyy-MM-dd HH:mm:ss
		 *
		 * @param strDate
		 * @return
		 */
		public static Date strToDateLongs(String strDate) {
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			ParsePosition pos = new ParsePosition(0);
			Date strtodate = formatter.parse(strDate, pos);
			return strtodate;
		}

		/**
		 * 将长时间格式时间转换为字符串 yyyy-MM-dd HH:mm:ss
		 *
		 * @param dateDate
		 * @return
		 */
		public static String dateToStrLongs(java.util.Date dateDate) {
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String dateString = formatter.format(dateDate);
			return dateString;
		}

		/**
		 * 将短时间格式时间转换为字符串 yyyy-MM-dd
		 *
		 * @param dateDate
		 * @param
		 * @return
		 */
		public static String dateToStrd(java.util.Date dateDate) {
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
			String dateString = formatter.format(dateDate);
			return dateString;
		}

		/**
		 * 将短时间格式字符串转换为时间 yyyy-MM-dd
		 *
		 * @param strDate
		 * @return
		 */
		public static Date strToDates(String strDate) {
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
			ParsePosition pos = new ParsePosition(0);
			Date strtodate = formatter.parse(strDate, pos);
			return strtodate;
		}

		/**
		 * 得到现在时间
		 *
		 * @return
		 */
		public static Date getNows() {
			Date currentTime = new Date();
			return currentTime;
		}

		/**
		 * 提取一个月中的最后一天
		 *
		 * @param day
		 * @return
		 */
		public static Date getLastDates(long day) {
			Date date = new Date();
			long date_3_hm = date.getTime() - 3600000 * 34 * day;
			Date date_3_hm_date = new Date(date_3_hm);
			return date_3_hm_date;
		}

		/**
		 * 得到现在时间
		 *
		 * @return 字符串 yyyyMMdd HHmmss
		 */
		public static String getStringTodays() {
			Date currentTime = new Date();
			SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd HHmmss");
			String dateString = formatter.format(currentTime);
			return dateString;
		}

		/**
		 * 得到现在小时
		 */
		public static String getHours() {
			Date currentTime = new Date();
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String dateString = formatter.format(currentTime);
			String hour;
			hour = dateString.substring(11, 13);
			return hour;
		}

		/**
		 * 得到现在分钟
		 *
		 * @return
		 */
		public static String getTimesa() {
			Date currentTime = new Date();
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String dateString = formatter.format(currentTime);
			String min;
			min = dateString.substring(14, 16);
			return min;
		}

		/**
		 * 根据用户传入的时间表示格式，返回当前时间的格式 如果是yyyyMMdd，注意字母y不能大写。
		 *
		 * @param sformat
		 *            yyyyMMddhhmmss
		 * @return
		 */
		public static String getUserDatedd(String sformat) {
			Date currentTime = new Date();
			SimpleDateFormat formatter = new SimpleDateFormat(sformat);
			String dateString = formatter.format(currentTime);
			return dateString;
		}

		/**
		 * 二个小时时间间的差值,必须保证二个时间都是"HH:MM"的格式，返回字符型的分钟
		 */
		public static String getTwoHour(String st1, String st2) {
			String[] kk = null;
			String[] jj = null;
			kk = st1.split(":");
			jj = st2.split(":");
			if (Integer.parseInt(kk[0]) < Integer.parseInt(jj[0]))
				return "0";
			else {
				double y = Double.parseDouble(kk[0]) + Double.parseDouble(kk[1]) / 60;
				double u = Double.parseDouble(jj[0]) + Double.parseDouble(jj[1]) / 60;
				if ((y - u) > 0)
					return y - u + "";
				else
					return "0";
			}
		}

		/**
		 * 得到二个日期间的间隔天数
		 */
		public static String getTwoDay(String sj1, String sj2) {
			SimpleDateFormat myFormatter = new SimpleDateFormat("yyyy-MM-dd");
			long day = 0;
			try {
				java.util.Date date = myFormatter.parse(sj1);
				java.util.Date mydate = myFormatter.parse(sj2);
				day = (date.getTime() - mydate.getTime()) / (24 * 60 * 60 * 1000);
			} catch (Exception e) {
				return "";
			}
			return day + "";
		}

		/**
		 * 时间前推或后推分钟,其中JJ表示分钟.
		 */
		public static String getPreTime(String sj1, String jj) {
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String mydate1 = "";
			try {
				Date date1 = format.parse(sj1);
				long Time = (date1.getTime() / 1000) + Integer.parseInt(jj) * 60;
				date1.setTime(Time * 1000);
				mydate1 = format.format(date1);
			} catch (Exception e) {
			}
			return mydate1;
		}

		/**
		 * 得到一个时间延后或前移几天的时间,nowdate为时间,delay为前移或后延的天数
		 */
		public static String getNextDay(String nowdate, String delay) {
			try{
				SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
				String mdate = "";
				Date d = strToDate(nowdate);
				long myTime = (d.getTime() / 1000) + Integer.parseInt(delay) * 24 * 60 * 60;
				d.setTime(myTime * 1000);
				mdate = format.format(d);
				return mdate;
			}catch(Exception e){
				return "";
			}
		}

		/**
		 * 判断是否润年
		 *
		 * @param ddate
		 * @return
		 */
		public static boolean isLeapYear(String ddate) {

			/**
			 * 详细设计： 1.被400整除是闰年，否则： 2.不能被4整除则不是闰年 3.能被4整除同时不能被100整除则是闰年
			 * 3.能被4整除同时能被100整除则不是闰年
			 */
			Date d = strToDate(ddate);
			GregorianCalendar gc = (GregorianCalendar) Calendar.getInstance();
			gc.setTime(d);
			int year = gc.get(Calendar.YEAR);
			if ((year % 400) == 0)
				return true;
			else if ((year % 4) == 0) {
				if ((year % 100) == 0)
					return false;
				else
					return true;
			} else
				return false;
		}

		/**
		 * 返回美国时间格式 26 Apr 2006
		 *
		 * @param str
		 * @return
		 */
		public static String getEDate(String str) {
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
			ParsePosition pos = new ParsePosition(0);
			Date strtodate = formatter.parse(str, pos);
			String j = strtodate.toString();
			String[] k = j.split(" ");
			return k[2] + k[1].toUpperCase() + k[5].substring(2, 4);
		}

		/**
		 * 获取一个月的最后一天
		 *
		 * @param dat
		 * @return
		 */
		public static String getEndDateOfMonth(String dat) {// yyyy-MM-dd
			String str = dat.substring(0, 8);
			String month = dat.substring(5, 7);
			int mon = Integer.parseInt(month);
			if (mon == 1 || mon == 3 || mon == 5 || mon == 7 || mon == 8 || mon == 10 || mon == 12) {
				str += "31";
			} else if (mon == 4 || mon == 6 || mon == 9 || mon == 11) {
				str += "30";
			} else {
				if (isLeapYear(dat)) {
					str += "29";
				} else {
					str += "28";
				}
			}
			return str;
		}

		/**
		 * 判断二个时间是否在同一个周
		 *
		 * @param date1
		 * @param date2
		 * @return
		 */
		public static boolean isSameWeekDates(Date date1, Date date2) {
			Calendar cal1 = Calendar.getInstance();
			Calendar cal2 = Calendar.getInstance();
			cal1.setTime(date1);
			cal2.setTime(date2);
			int subYear = cal1.get(Calendar.YEAR) - cal2.get(Calendar.YEAR);
			if (0 == subYear) {
				if (cal1.get(Calendar.WEEK_OF_YEAR) == cal2.get(Calendar.WEEK_OF_YEAR))
					return true;
			} else if (1 == subYear && 11 == cal2.get(Calendar.MONTH)) {
				// 如果12月的最后一周横跨来年第一周的话则最后一周即算做来年的第一周
				if (cal1.get(Calendar.WEEK_OF_YEAR) == cal2.get(Calendar.WEEK_OF_YEAR))
					return true;
			} else if (-1 == subYear && 11 == cal1.get(Calendar.MONTH)) {
				if (cal1.get(Calendar.WEEK_OF_YEAR) == cal2.get(Calendar.WEEK_OF_YEAR))
					return true;
			}
			return false;
		}

		/**
		 * 产生周序列,即得到当前时间所在的年度是第几周
		 *
		 * @return
		 */
		public static String getSeqWeek() {
			Calendar c = Calendar.getInstance(Locale.CHINA);
			String week = Integer.toString(c.get(Calendar.WEEK_OF_YEAR));
			if (week.length() == 1)
				week = "0" + week;
			String year = Integer.toString(c.get(Calendar.YEAR));
			return year + week;
		}

		/**
		 * 获得一个日期所在的周的星期几的日期，如要找出2002年2月3日所在周的星期一是几号
		 *
		 * @param sdate
		 * @param num
		 * @return
		 */
		/*public static String getWeek(String sdate, String num) {
			// 再转换为时间
			Date dd = VeDate.strToDate(sdate);
			Calendar c = Calendar.getInstance();
			c.setTime(dd);
			if (num.equals("1")) // 返回星期一所在的日期
				c.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
			else if (num.equals("2")) // 返回星期二所在的日期
				c.set(Calendar.DAY_OF_WEEK, Calendar.TUESDAY);
			else if (num.equals("3")) // 返回星期三所在的日期
				c.set(Calendar.DAY_OF_WEEK, Calendar.WEDNESDAY);
			else if (num.equals("4")) // 返回星期四所在的日期
				c.set(Calendar.DAY_OF_WEEK, Calendar.THURSDAY);
			else if (num.equals("5")) // 返回星期五所在的日期
				c.set(Calendar.DAY_OF_WEEK, Calendar.FRIDAY);
			else if (num.equals("6")) // 返回星期六所在的日期
				c.set(Calendar.DAY_OF_WEEK, Calendar.SATURDAY);
			else if (num.equals("0")) // 返回星期日所在的日期
				c.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
			return new SimpleDateFormat("yyyy-MM-dd").format(c.getTime());
		}*/

		/**
		 * 根据一个日期，返回是星期几的字符串
		 *
		 * @param sdate
		 * @return
		 */
		/*public static String getWeek(String sdate) {
			// 再转换为时间
			Date date = VeDate.strToDate(sdate);
			Calendar c = Calendar.getInstance();
			c.setTime(date);
			// int hour=c.get(Calendar.DAY_OF_WEEK);
			// hour中存的就是星期几了，其范围 1~7
			// 1=星期日 7=星期六，其他类推
			return new SimpleDateFormat("EEEE").format(c.getTime());
		}*/
		/*public static String getWeekStr(String sdate){
			String str = "";
			str = VeDate.getWeek(sdate);
			if("1".equals(str)){
				str = "星期日";
			}else if("2".equals(str)){
				str = "星期一";
			}else if("3".equals(str)){
				str = "星期二";
			}else if("4".equals(str)){
				str = "星期三";
			}else if("5".equals(str)){
				str = "星期四";
			}else if("6".equals(str)){
				str = "星期五";
			}else if("7".equals(str)){
				str = "星期六";
			}
			return str;
		}*/

		/**
		 * 两个时间之间的天数
		 *
		 * @param date1
		 * @param date2
		 * @return
		 */
		public static long getDays(String date1, String date2) {
			if (date1 == null || date1.equals(""))
				return 0;
			if (date2 == null || date2.equals(""))
				return 0;
			// 转换为标准时间
			SimpleDateFormat myFormatter = new SimpleDateFormat("yyyy-MM-dd");
			java.util.Date date = null;
			java.util.Date mydate = null;
			try {
				date = myFormatter.parse(date1);
				mydate = myFormatter.parse(date2);
			} catch (Exception e) {
			}
			long day = (date.getTime() - mydate.getTime()) / (24 * 60 * 60 * 1000);
			return day;
		}




		/**
		 * 取得数据库主键 生成格式为yyyymmddhhmmss+k位随机数
		 *
		 * @param k
		 *            表示是取几位随机数，可以自己定
		 */

		public static String getNo(int k) {
			return getUserDate("yyyyMMddhhmmss") + getRandom(k);
		}

		/**
		 * 返回一个随机数
		 *
		 * @param i
		 * @return
		 */
		public static String getRandom(int i) {
			Random jjj = new Random();
			// int suiJiShu = jjj.nextInt(9);
			if (i == 0)
				return "";
			String jj = "";
			for (int k = 0; k < i; k++) {
				jj = jj + jjj.nextInt(9);
			}
			return jj;
		}

		/**
		 *
		 * @param
		 */
		public static boolean RightDate(String date) {

			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
			;
			if (date == null)
				return false;
			if (date.length() > 10) {
				sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
			} else {
				sdf = new SimpleDateFormat("yyyy-MM-dd");
			}
			try {
				sdf.parse(date);
			} catch (ParseException pe) {
				return false;
			}
			return true;
		}

		/***************************************************************************
		 * //nd=1表示返回的值中包含年度 //yf=1表示返回的值中包含月份 //rq=1表示返回的值中包含日期 //format表示返回的格式 1
		 * 以年月日中文返回 2 以横线-返回 // 3 以斜线/返回 4 以缩写不带其它符号形式返回 // 5 以点号.返回
		 **************************************************************************/
	/*	public static String getStringDateMonth(String sdate, String nd, String yf, String rq, String format) {
			Date currentTime = new Date();
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
			String dateString = formatter.format(currentTime);
			String s_nd = dateString.substring(0, 4); // 年份
			String s_yf = dateString.substring(5, 7); // 月份
			String s_rq = dateString.substring(8, 10); // 日期
			String sreturn = "";
			roc.util.MyChar mc = new roc.util.MyChar();
			if (sdate == null || sdate.equals("") || !mc.Isdate(sdate)) { // 处理空值情况
				if (nd.equals("1")) {
					sreturn = s_nd;
					// 处理间隔符
					if (format.equals("1"))
						sreturn = sreturn + "年";
					else if (format.equals("2"))
						sreturn = sreturn + "-";
					else if (format.equals("3"))
						sreturn = sreturn + "/";
					else if (format.equals("5"))
						sreturn = sreturn + ".";
				}
				// 处理月份
				if (yf.equals("1")) {
					sreturn = sreturn + s_yf;
					if (format.equals("1"))
						sreturn = sreturn + "月";
					else if (format.equals("2"))
						sreturn = sreturn + "-";
					else if (format.equals("3"))
						sreturn = sreturn + "/";
					else if (format.equals("5"))
						sreturn = sreturn + ".";
				}
				// 处理日期
				if (rq.equals("1")) {
					sreturn = sreturn + s_rq;
					if (format.equals("1"))
						sreturn = sreturn + "日";
				}
			} else {
				// 不是空值，也是一个合法的日期值，则先将其转换为标准的时间格式
				sdate = roc.util.RocDate.getOKDate(sdate);
				s_nd = sdate.substring(0, 4); // 年份
				s_yf = sdate.substring(5, 7); // 月份
				s_rq = sdate.substring(8, 10); // 日期
				if (nd.equals("1")) {
					sreturn = s_nd;
					// 处理间隔符
					if (format.equals("1"))
						sreturn = sreturn + "年";
					else if (format.equals("2"))
						sreturn = sreturn + "-";
					else if (format.equals("3"))
						sreturn = sreturn + "/";
					else if (format.equals("5"))
						sreturn = sreturn + ".";
				}
				// 处理月份
				if (yf.equals("1")) {
					sreturn = sreturn + s_yf;
					if (format.equals("1"))
						sreturn = sreturn + "月";
					else if (format.equals("2"))
						sreturn = sreturn + "-";
					else if (format.equals("3"))
						sreturn = sreturn + "/";
					else if (format.equals("5"))
						sreturn = sreturn + ".";
				}
				// 处理日期
				if (rq.equals("1")) {
					sreturn = sreturn + s_rq;
					if (format.equals("1"))
						sreturn = sreturn + "日";
				}
			}
			return sreturn;
		}*/

		/*public static String getNextMonthDay(String sdate, int m) {
			sdate = getOKDate(sdate);
			int year = Integer.parseInt(sdate.substring(0, 4));
			int month = Integer.parseInt(sdate.substring(5, 7));
			month = month + m;
			if (month < 0) {
				month = month + 12;
				year = year - 1;
			} else if (month > 12) {
				month = month - 12;
				year = year + 1;
			}
			String smonth = "";
			if (month < 10)
				smonth = "0" + month;
			else
				smonth = "" + month;
			return year + "-" + smonth + "-10";
		}*/

	/*	public static String getOKDate(String sdate) {
			if (sdate == null || sdate.equals(""))
				return getStringDateShort();

			if (!VeStr.Isdate(sdate)) {
				sdate = getStringDateShort();
			}
			// 将“/”转换为“-”
			sdate = VeStr.Replace(sdate, "/", "-");
			// 如果只有8位长度，则要进行转换
			if (sdate.length() == 8)
				sdate = sdate.substring(0, 4) + "-" + sdate.substring(4, 6) + "-" + sdate.substring(6, 8);
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
			ParsePosition pos = new ParsePosition(0);
			Date strtodate = formatter.parse(sdate, pos);
			String dateString = formatter.format(strtodate);
			return dateString;
		}*/
}
