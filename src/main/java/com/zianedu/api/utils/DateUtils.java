package com.zianedu.api.utils;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtils {

    public final static DateTimeZone UTC_TIME_ZONE = DateTimeZone.UTC;
    public final static DateTimeZone KOR_TIME_ZONE = DateTimeZone.UTC;
    public final static String DF_TIME_PATTERN = "yyyy-MM-dd HH:mm:ss";
    public final static String DF_YMD_PATTERN = "yyyy-MM-dd";

    /**
     * 데이트 객체를 패턴에 따라 문자로 변환한다.
     *
     * @param date 데이트 객체.
     * @param pattern 문자열 패턴
     * @return 패턴화 문자.
     */
    public static String dateToStr(Date date, String pattern, DateTimeZone timeZone){
        if(date == null){
            return null;
        }
        DateTime dt = new DateTime(date, timeZone);
        return dt.toString(pattern);
    }

    /**
     * 현재 시각의 데이트 객체를 반환한다.
     * @return
     */
    public static Date now() {
        DateTime dateTime = new DateTime();
        return dateTime.toDate();
    }

    public static String nowToStrUTC() {
        DateTime dt = new DateTime(now(), UTC_TIME_ZONE);
        return dt.toString(DF_TIME_PATTERN);
    }

    public static String nowToStrUTCNoHms(String pattern) {
        DateTime dt = new DateTime(now(), UTC_TIME_ZONE);
        return dt.toString(pattern + " 00:00:00");
    }

    public static Date stringToDate(String yyyymmdd) throws Exception {
        SimpleDateFormat transFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date to = transFormat.parse(yyyymmdd);
        return to;
    }

    public static String todayToStr() {
        Date today = new Date();
        SimpleDateFormat transFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return transFormat.format(today);
    }

    /**
     * 오늘이 두개 비교날짜 사이에 있거나 두날짜보다 작으면 true, 오늘이 두개 날짜보다 전부 크면 false
     * @param startDate
     * @param endDate
     * @return
     */
    public static boolean isBetweenDateFromToday(String startDate, String endDate) throws Exception {
        String today = todayToStr();    //현재날짜String
        Date currentDate; //현재날짜 Date
        Date date2; //시작일
        Date date3; //종료일

        DateFormat df1= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        date2 = df1.parse(startDate);
        date3 = df1.parse(endDate);
        currentDate = df1.parse(today);

        int compare = currentDate.compareTo(date2);
        int compare2 = currentDate.compareTo(date3);

        if (compare == 1 && compare2 ==1) {
            return false;
        }
        return true;
    }

    public static void main(String[] args) throws Exception {
        System.out.println(isBetweenDateFromToday("2017-07-01 13:42:00", "2018-07-21 13:42:00"));
        //System.out.println(dateToStr(d));
    }
}
