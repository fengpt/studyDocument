package com.app.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public abstract class DateUtils {
	/** {yyyy-MM-dd HH:mm:ss.fff}使用24小时制格式化日期 **/
	/** {yyyy-MM-dd hh:mm:ss.fff}使用12小时制格式化日期 **/
	/** 从左至右，依次为 年-月-日 时：分：秒.毫秒 **/
	public static int dateCompare(String dateSrcStr,String dateDestStr){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			Date dateSrc = sdf.parse(dateSrcStr);
			Date dateDest = sdf.parse(dateDestStr);
			return dateSrc.compareTo(dateDest);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return (Integer) null;
	}
	
	/**
	 * 与当前日期进行比较
	 * @param dateSrcStr 要比较的字符串，格式必须为：yyyy-MM-dd HH:mm
	 * @return 如果比当前日期早，则返回-1,如果比当前日期晚，则返回1，反之为0
	 */
	public static int dateCompare2Current(String dateSrcStr){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return dateCompare(dateSrcStr, sdf.format(new Date()));
	}

	/**
	 * 是否早于当前时间
	 * @param dateStr 要比较的字符串，格式必须为：yyyy-MM-dd HH:mm
	 * @return
	 */
	public static boolean isBeforeNow(String dateStr) {
		return dateCompare2Current(dateStr) < 0;
	}
	
	/**
	 * 是否晚于当前时间
	 * @param dateStr 要比较的字符串，格式必须为：yyyy-MM-dd HH:mm
	 * @return
	 */
	public static boolean isAfterNow(String dateStr) {
		return dateCompare2Current(dateStr) > 0;
	}

}
