package com.sunsekey.practise.javabasic._date;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

/**
 * 国际化相关
 */
public class LocaleDemo {
    public static void main(String[] args) {
//        displayDateByTimeZone();
//        TimeZone timeZone = TimeZone.getTimeZone("America/New_York");
//        Date date = convertTimeStamp("2020-01-10 14:00:00", timeZone);
//        System.out.println(date);
        // 场景：在日本购买了一个套餐，要计算套餐的截止时间。按规则是，按当地时算（即日本时区），
        // 购买日起30天后那天的23:59:59到期（PS:日本是东九区）
        // 当前时间戳
        Date startDate = new Date();
        int days = 30;
        // 当前时间戳 + 30天后的时间戳
        Date tarEndDate = DateUtils.addDays(startDate, days);
        // 到此以上都没问题，目前时间戳是没问题的就是与UTC时间1970.01.01 00:00:00相差的毫秒数，也不涉及时区
        // 这里要注意很重要的一点是，这个时间戳对不同时区来说对应的时间是不一样的，在北京可能是 2020.02.20 22:00:00，在日本则是 2020.02.20 23:00:00
        // 那么，关键来了，对于北京来说，再过"两个小时"才到 2020.02.20 23:59:59，但对于日本来说，只需过"一个小时"就可以了，即要加的毫秒数是不一样的
        // 所以，下一步计算时间戳时必须指定日本时区
        Date endDate = get235959OfDate(tarEndDate, "Asia/Tokyo");
        // 假设这里不指定日本时区，而用了应用服务器的时区，譬如北京时区，那得到的endDate时间戳就会多了一个小时，如：
        Date wrongEndDate = getLastTimeOfDay(tarEndDate);// getLastTimeOfDay()中会调用Calendar.getInstance()，根据服务器时区获取一个Calendar实例

        // 最后解析到客户端时，按日本时区转换
        TimeZone tokyoTimeZone = TimeZone.getTimeZone("Asia/Tokyo");
        System.out.println(getDateStrByTimeZone(endDate, tokyoTimeZone));
        System.out.println(getDateStrByTimeZone(wrongEndDate, tokyoTimeZone));// 结果多了一个小时，变成00:59:59了
    }

    public static void displayDateByLocale() {
        Date date = new Date();
        Locale locale = Locale.CHINA;
        DateFormat shortDf = DateFormat.getDateTimeInstance(DateFormat.MEDIUM,DateFormat.MEDIUM, locale);
        System.out.println("中国格式："+shortDf.format(date));

        locale = Locale.ENGLISH;
        shortDf = DateFormat.getDateTimeInstance(DateFormat.MEDIUM,DateFormat.MEDIUM, locale);
        System.out.println("英国格式："+shortDf.format(date));
    }

    public static String getDateStrByTimeZone(Date date,TimeZone timeZone) {
        Locale locale = Locale.CHINA;
        DateFormat shortDf = DateFormat.getDateTimeInstance(DateFormat.MEDIUM,DateFormat.MEDIUM, locale);
        shortDf.setTimeZone(timeZone);
        return shortDf.format(date);
    }

    /**
     * 即dateStr这个日期，以timeZone为准，转换成的时间戳为timestamp
     * @param dateStr
     * @param timeZone
     * @return
     */
    public static Date convertTimeStamp(String dateStr, TimeZone timeZone) {
        SimpleDateFormat sdf_local = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        sdf_local.setTimeZone(timeZone);
        Date timestamp = null;
        try {
            timestamp = sdf_local.parse(dateStr);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return timestamp;
    }

    public static Date getLastTimeOfDay(Date date) {
        try {
            if (date == null) {
                return null;
            }
            Date roundDate = DateUtils.round(date, Calendar.DAY_OF_MONTH);
            return DateUtils.addSeconds(roundDate, -1);
        } catch (Exception e) {
            return null;
        }
    }

    public static Date get235959OfDate(Date date, String timezone) {
        LocalDateTime localDateTime = convertDateToLocalDateTime(date, timezone);
        ZoneId zoneId = StringUtils.isEmpty(timezone) ? ZoneId.systemDefault() : ZoneId.of(timezone);
        Instant instant = localDateTime.with(LocalTime.of(23, 59, 59, 0)).atZone(zoneId).toInstant();
        return Date.from(instant);
    }

    public static LocalDateTime convertDateToLocalDateTime(Date date, String timezone) {
        Instant instant = date.toInstant();
        ZoneId zoneId = StringUtils.isEmpty(timezone) ? ZoneId.systemDefault() : ZoneId.of(timezone);
        LocalDateTime localDateTime = LocalDateTime.ofInstant(instant, zoneId);
        return localDateTime;
    }
}
