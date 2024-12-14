package org.x1a0kang.compare.common.utils;

import org.joda.time.DateTime;
import org.joda.time.DateTimeConstants;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.slf4j.Logger;
import org.springframework.util.StringUtils;
import org.x1a0kang.compare.common.factory.CustomLoggerFactory;

import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;
import java.util.regex.Matcher;

/**
 * *@Author: cuixin
 * *@Date: 2021/6/4 9:25
 */

public class JodaDateUtil {
    public enum Pattern {
        //如果不够用可以自己添加
        yyyy("yyyy"),
        yyyy_MM("yyyy-MM"),
        yyyyMM("yyyyMM"),
        yyyy_MM_dd("yyyy-MM-dd"),
        yyyy_MM_dd_underscore("yyyy_MM_dd"),
        yyyy_MM_dd_slash("yyyy/MM/dd"),
        yyyyMMdd("yyyyMMdd"),
        yyyy_MM_dd_HH_mm_ss("yyyy-MM-dd HH:mm:ss"),
        yyyy_MM_dd_T_HH_mm_ss_Z("yyyy-MM-dd'T'HH:mm:ss'Z'"),
        yyyy_MM_dd_HH_mm("yyyy-MM-dd HH:mm"),
        yyyyMMddHHmmss("yyyyMMddHHmmss"),
        yyyy_MM_dd_HH_mm_ss_zh("yyyy年MM月dd日HH时mm分ss秒"),
        yyyy_MM_dd_HH_mm_ss_SSS("yyyy-MM-dd HH:mm:ss.SSS"),
        yyyy_MM_dd_zh("yyyy年MM月dd日"),
        yyyy_MM_dd_HH_mm_zh("yyyy年MM月dd日HH时mm分"),
        HH("HH"),
        HH_mm("HH:mm"),
        HH_mm_ss("HH:mm:ss");

        private final String pattern;

        Pattern(String pattern) {
            this.pattern = pattern;
        }

        public String getPattern() {
            return pattern;
        }
    }

    private static Logger LOGGER = CustomLoggerFactory.getLogger(JodaDateUtil.class);

    /**
     * 将日期字符串转换成Date
     */
    public static Date strToDate(String strTime, Pattern pattern) {
        DateTimeFormatter fmt = DateTimeFormat.forPattern(pattern.getPattern());
        DateTime dateTime = fmt.parseDateTime(strTime);
        return dateTime.toDate();
    }

    /**
     * 将时间戳改为日期字符串
     */
    public static String longToDateStr(long timestamp, Pattern pattern) {
        DateTime dateTime = new DateTime(timestamp);
        DateTimeFormatter fmt = DateTimeFormat.forPattern(pattern.getPattern());
        return fmt.print(dateTime);
    }


    public static Date strToDate(String time) {
        return strToDate(time, Pattern.yyyy_MM_dd_HH_mm_ss);
    }

    /**
     * 将Date对象改为字符串
     */
    public static String dateToStr(Date date, Pattern pattern) {
        DateTimeFormatter fmt = DateTimeFormat.forPattern(pattern.getPattern());
        DateTime dateTime = new DateTime(date);
        return fmt.print(dateTime);
    }

    /**
     * 转换成yyyy-MM-dd HH:mm:ss格式
     */
    public static String dateToStr(Date date) {
        return dateToStr(date, Pattern.yyyy_MM_dd_HH_mm_ss);
    }

    /**
     * 字符串在两种格式之间转换
     */
    public static String strToStr(String fromStr, Pattern fromPattern,
                                  Pattern toPattern) {
        if (null == fromStr) {
            return null;
        }
        Date d = strToDate(fromStr, fromPattern);
        return dateToStr(d, toPattern);
    }

    /**
     * 获取当前日期yyyy-MM-dd的形式
     */
    public static String getCur_yyyy_MM_dd() {
        return dateToStr(DateTime.now().toDate(), Pattern.yyyy_MM_dd);
    }

    /**
     * 获取当前日期yyyy_MM_dd的形式
     */
    public static String getCur_yyyy_MM_dd_underscore() {
        return dateToStr(DateTime.now().toDate(), Pattern.yyyy_MM_dd_underscore);
    }

    /**
     * 获取当前日期yyyy_MM_dd的形式
     */
    public static String getCur_yyyy_MM_dd_HH_mm_ss() {
        return dateToStr(DateTime.now().toDate(), Pattern.yyyy_MM_dd_HH_mm_ss);
    }

    /**
     * 获取当前日期yyyy_MM_dd的形式
     */
    public static String getCur_yyyy_MM_dd_HH_mm() {
        return dateToStr(DateTime.now().toDate(), Pattern.yyyy_MM_dd_HH_mm);
    }

    /**
     * 获取当前日期yyyyMMdd的形式
     */
    public static String getCuryyyyMMdd() {
        return dateToStr(DateTime.now().toDate(), Pattern.yyyyMMdd);
    }

    public static int getCuryyyyMMddInteger() {
        return Integer.parseInt(dateToStr(DateTime.now().toDate(), Pattern.yyyyMMdd));
    }

    public static long getCuryyyyyyyyMMddHHmmssL() {
        return Long.parseLong(dateToStr(DateTime.now().toDate(), Pattern.yyyyMMddHHmmss));
    }

    /**
     * 获取当前日期yyyy年MM月dd日的形式
     */
    public static String getCuryyyyMMddzh() {
        return dateToStr(DateTime.now().toDate(), Pattern.yyyy_MM_dd_zh);
    }

    /**
     * 获取当前时间HH的形式
     */
    public static String getCurHH() {
        return dateToStr(DateTime.now().toDate(), Pattern.HH);
    }

    /**
     * 获取当前时间HH:mm的形式
     */
    public static String getCurHHmm() {
        return dateToStr(DateTime.now().toDate(), Pattern.HH_mm);
    }

    /**
     * 获取当前时间HH:mm:ss的形式
     */
    public static String getCurHHmmss() {
        return dateToStr(DateTime.now().toDate(), Pattern.HH_mm_ss);
    }

    /**
     * 获取当前日期时间yyyy-MM-dd HH:mm:ss的形式
     */
    public static String getCurDateTime() {
        return dateToStr(DateTime.now().toDate(), Pattern.yyyy_MM_dd_HH_mm_ss);
    }

    /**
     * 获取当前日期时间yyyy年MM月dd日HH时mm分ss秒的形式
     */
    public static String getCurDateTimezh() {
        return dateToStr(new Date(), Pattern.yyyy_MM_dd_HH_mm_ss_zh);
    }

    public static Date addYear(Date d, int years) {
        DateTime dateTime = new DateTime(d);
        dateTime = dateTime.plusYears(years);
        return dateTime.toDate();
    }

    public static Date addSeconds(Date d, int sec) {
        DateTime dateTime = new DateTime(d);
        dateTime = dateTime.plusSeconds(sec);
        return dateTime.toDate();
    }

    public static Date addMinutes(Date d, int min) {
        DateTime dateTime = new DateTime(d);
        dateTime = dateTime.plusMinutes(min);
        return dateTime.toDate();
    }

    public static Date addHours(Date d, int hours) {
        DateTime dateTime = new DateTime(d);
        dateTime = dateTime.plusHours(hours);
        return dateTime.toDate();
    }

    /**
     * 比较两个"yyyy-MM-dd HH:mm:ss"格式的日期，之间相差多少毫秒,time2-time1
     */
    public static long compareDateStr(String time1, String time2) {
        Date d1 = strToDate(time1);
        Date d2 = strToDate(time2);
        return d1.getTime() - d2.getTime();
    }

    /**
     * 比较两个"yyyy-MM-dd HH:mm:ss"格式的日期，之间相差多少毫秒,time2-time1
     */
    public static long compareDate(Date time1, Date time2) {
        DateTime t1 = new DateTime(time1);
        DateTime t2 = new DateTime(time2);
        return t1.getMillis() - t2.getMillis();
    }

    /**
     * 获取Date中的分钟
     */
    public static int getMinute(Date d) {
        DateTime dateTime = new DateTime(d);
        return dateTime.getMinuteOfHour();
    }

    /**
     * 获取Date中的小时(24小时)
     */
    public static int getHour(Date d) {
        DateTime dateTime = new DateTime(d);
        return dateTime.getHourOfDay();
    }

    /**
     * 获取Date中的秒
     */
    public static int getSecond(Date d) {
        DateTime dateTime = new DateTime(d);
        return dateTime.getSecondOfMinute();
    }

    /**
     * 获取Date中的毫秒
     */
    public static int getMilliSecond(Date d) {
        DateTime dateTime = new DateTime(d);
        return dateTime.getMillisOfSecond();
    }

    /**
     * 获取xxxx-xx-xx的日
     */
    public static int getDay(Date d) {
        DateTime dateTime = new DateTime(d);
        return dateTime.getDayOfMonth();
    }

    /**
     * 获取月份，1-12月
     */
    public static int getMonth(Date d) {
        DateTime dateTime = new DateTime(d);
        return dateTime.getMonthOfYear();
    }

    /**
     * 获取19xx,20xx形式的年
     */
    public static int getYear(Date d) {
        DateTime dateTime = new DateTime(d);
        return dateTime.getYear();
    }

    /**
     * 得到d 的年份+月份,如200505
     */
    public static String getYearMonthOfDate(Date d) {
        return dateToStr(d, Pattern.yyyyMM);
    }

    /**
     * 得到上个月的年份+月份,如200505
     */
    public static String getYearMonthOfPreviousMonth(Date date) {

        return dateToStr(addMonth(date, -1), Pattern.yyyyMM);
    }

    /**
     * 得到当前日期的年和月如200509
     */
    public static String getCurYearMonth() {
        return dateToStr(DateTime.now().toDate(), Pattern.yyyyMM);
    }

    /**
     * 获得系统当前月份的天数
     */
    public static int getCurrentMonthDays() {
        return getMonthDays(DateTime.now().toDate());
    }

    /**
     * 获得指定日期月份的天数
     */
    public static int getMonthDays(Date date) {
        return new DateTime(date).dayOfMonth().withMaximumValue().getDayOfMonth();
    }

    /**
     * 在传入时间基础上加一定月份数
     */
    public static Date addMonth(Date oldTime, final int months) {
        DateTime dateTime = new DateTime(oldTime);
        dateTime = dateTime.plusMonths(months);
        return dateTime.toDate();
    }

    public static long addMonth(long oldTime, final int months) {
        DateTime dateTime = new DateTime(oldTime);
        dateTime = dateTime.plusMonths(months);
        return dateTime.getMillis();
    }

    /**
     * 在传入时间基础上加一定天数
     */
    public static long addDay(final long oldTime, final int day) {
        DateTime dateTime = new DateTime(oldTime);
        dateTime = dateTime.plusDays(day);
        return dateTime.getMillis();
    }

    /**
     * 在传入时间基础上加一定天数
     */
    public static Date addDay(final Date oldTime, final int day) {
        DateTime dateTime = new DateTime(oldTime);
        dateTime = dateTime.plusDays(day);
        return dateTime.toDate();
    }

    /**
     * 获取当天零时时间戳
     */
    public static long getCurDayStartTime() {
        return strToDate(getCur_yyyy_MM_dd() + " 00:00:00").getTime();
    }

    /**
     * 获得周一的日期
     */
    public static Date getMonday(Date date) {
        DateTime dateTime = new DateTime(date);
        dateTime = dateTime.withDayOfWeek(DateTimeConstants.MONDAY);
        return dateTime.toDate();
    }

    /**
     * 获得周五的日期
     */
    public static Date getFriday(Date date) {
        DateTime dateTime = new DateTime(date);
        dateTime = dateTime.withDayOfWeek(DateTimeConstants.FRIDAY);
        return dateTime.toDate();

    }

    /**
     * 得到月的第一天
     */
    public static Date getMonthFirstDay(Date date) {
        DateTime dateTime = new DateTime(date);
        dateTime = dateTime.dayOfMonth().withMinimumValue();
        return dateTime.toDate();

    }

    /**
     * 得到月的最后一天
     */
    public static Date getMonthLastDay(Date date) {
        DateTime dateTime = new DateTime(date);
        dateTime = dateTime.dayOfMonth().withMaximumValue();
        return dateTime.toDate();

    }

    public static boolean isDate(String strTime, Pattern pattern) {
        try {
            strToDate(strTime, pattern);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * 得到季度的第一天
     */
    public static Date getSeasonFirstDay(Date date) {
        DateTime dateTime = new DateTime(date);
        int curMonth = dateTime.getMonthOfYear();
        if (curMonth >= DateTimeConstants.JANUARY && curMonth <= DateTimeConstants.MARCH) {
            dateTime = dateTime.withMonthOfYear(DateTimeConstants.JANUARY);
        } else if (curMonth >= DateTimeConstants.APRIL && curMonth <= DateTimeConstants.JUNE) {
            dateTime = dateTime.withMonthOfYear(DateTimeConstants.APRIL);
        } else if (curMonth >= DateTimeConstants.JULY && curMonth <= DateTimeConstants.SEPTEMBER) {
            dateTime = dateTime.withMonthOfYear(DateTimeConstants.JULY);
        } else {
            dateTime = dateTime.withMonthOfYear(DateTimeConstants.OCTOBER);
        }
        dateTime = dateTime.dayOfMonth().withMinimumValue();
        return dateTime.toDate();
    }

    /**
     * 得到季度的最后一天
     */
    public static Date getSeasonLastDay(Date date) {
        DateTime dateTime = new DateTime(date);
        int curMonth = dateTime.getMonthOfYear();
        if (curMonth >= DateTimeConstants.JANUARY && curMonth <= DateTimeConstants.MARCH) {
            dateTime = dateTime.withMonthOfYear(DateTimeConstants.MARCH);
        } else if (curMonth >= DateTimeConstants.APRIL && curMonth <= DateTimeConstants.JUNE) {
            dateTime = dateTime.withMonthOfYear(DateTimeConstants.JUNE);
        } else if (curMonth >= DateTimeConstants.JULY && curMonth <= DateTimeConstants.SEPTEMBER) {
            dateTime = dateTime.withMonthOfYear(DateTimeConstants.SEPTEMBER);
        } else {
            dateTime = dateTime.withMonthOfYear(DateTimeConstants.DECEMBER);
        }
        dateTime = dateTime.dayOfMonth().withMaximumValue();
        return dateTime.toDate();
    }

    /**
     * 获取年第一天日期
     */
    public static Date getYearFirstDay(Date date) {
        DateTime dateTime = new DateTime(date);
        dateTime = dateTime.dayOfYear().withMinimumValue();
        return dateTime.toDate();
    }

    /**
     * 获取年最后一天日期
     */
    public static Date getYearLastDay(Date date) {
        DateTime dateTime = new DateTime(date);
        dateTime = dateTime.dayOfYear().withMaximumValue();
        return dateTime.toDate();
    }

    public static long getZeroClock(Date date, int day) {
        return getZeroClockDate(date, day).getTime();
    }

    public static Date getZeroClockDate(Date date, int addDay) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.MILLISECOND, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.add(Calendar.DATE, addDay);
        return calendar.getTime();
    }

    public static Date setClock(Date date, int hour, int minute) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.MILLISECOND, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MINUTE, minute);
        calendar.set(Calendar.HOUR_OF_DAY, hour);
        return calendar.getTime();
    }

    public static Date getZeroClockByMonth(Date date, int month) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.MILLISECOND, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.add(Calendar.MONTH, month);
        return calendar.getTime();
    }

    // 对传入的日期减days之后传回
    public static String minusDay(String date, int days) {
        java.time.format.DateTimeFormatter srcFormat = java.time.format.DateTimeFormatter.ofPattern("yyyy-MM-dd");
        java.time.format.DateTimeFormatter dstFormat = java.time.format.DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate localDate = LocalDate.parse(date, srcFormat);
        localDate = localDate.minusDays(days);
        return dstFormat.format(localDate);
    }

    // 获取传入的日期里的年份，如果传入的字符串为空，则使用当前时间的年份
    public static String getYear(String srcDate) {
        java.time.format.DateTimeFormatter srcFormat = java.time.format.DateTimeFormatter.ofPattern("yyyy-MM-dd");
        java.time.format.DateTimeFormatter yearDstFormat = java.time.format.DateTimeFormatter.ofPattern("yyyy");
        LocalDate date = StringUtils.hasText(srcDate) ? LocalDate.parse(srcDate, srcFormat) : LocalDate.now();
        return yearDstFormat.format(date);
    }

    public static LocalDate getNonEmptyDate(String date, java.time.format.DateTimeFormatter format) {
        return StringUtils.hasText(date) ? LocalDate.parse(date, format) : LocalDate.now();
    }

    /**
     * 将日期格式从srcFormat转为dstFormat
     *
     * @param time      时间字符串
     * @param srcFormat 源日期格式
     * @param dstFormat 目标日期格式
     * @return 转换后的日期字符串
     */
    public static String changeDateTimeFormat(String time, String srcFormat, String dstFormat) {
        try {
            DateTimeFormatter src = DateTimeFormat.forPattern(srcFormat);
            DateTimeFormatter dst = DateTimeFormat.forPattern(dstFormat);
            return dst.print(src.parseDateTime(time));
        } catch (Exception e) {
            LOGGER.error("日期格式转换出错：time：{}, srcFormat：{}, dstFormat：{}", time, srcFormat, dstFormat, e);
            return "";
        }
    }

    public static String adjustDate(String currentDate, String operation) {
        LocalDate date = LocalDate.parse(currentDate);
        java.util.regex.Pattern pattern = java.util.regex.Pattern.compile("([+-]\\d+)([YMD])");
        Matcher matcher = pattern.matcher(operation);

        while (matcher.find()) {
            int amount = Integer.parseInt(matcher.group(1));
            String unit = matcher.group(2);

            switch (unit) {
                case "Y":
                    date = date.plusYears(amount);
                    break;
                case "M":
                    date = date.plusMonths(amount);
                    break;
                case "D":
                    date = date.plusDays(amount);
                    break;
                default:
                    // 如果遇到未知的时间单位，可以抛出一个异常或者忽略
                    throw new IllegalArgumentException("未知的时间单位: " + unit);
            }
        }

        return date.toString();
    }

    public static String formatDate_yyyy_MM_dd(String date) {
        Date dt = strToDate(date);
        return dateToStr(dt, Pattern.yyyy_MM_dd);
    }

    public static String formatDateFull(String date) {
        Date dt = strToDate(date);
        return dateToStr(dt, Pattern.yyyy_MM_dd_HH_mm_ss);
    }
}
