package com.zianedu.api.utils;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.PostMethod;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

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

    public static String todayToStrKor() {
        Date today = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
        String yyyy =  sdf.format(today);
        SimpleDateFormat sdf2 = new SimpleDateFormat("MM");
        String mm =  sdf2.format(today);
        SimpleDateFormat sdf3 = new SimpleDateFormat("dd");
        String dd =  sdf3.format(today);

        System.out.println(yyyy);
        return yyyy + "년 " + mm + "월 " + dd + "일";
    }

    public static String getYearLastTwo(){
        Date date = new Date();
        SimpleDateFormat formatter =new SimpleDateFormat("yy", Locale.KOREA);
        return formatter.format(date);
    }

    public static String httpPost(String targetUrl, String parameters) throws Exception {
        URL url = new URL(targetUrl);
        HttpURLConnection con = (HttpURLConnection) url.openConnection();

        con.setRequestMethod("POST");// HTTP POST 메소드 설정
        //con.setRequestProperty("Content-Type", "application/json;charset=UTF-8");
        con.setDoOutput(true); // POST 파라미터 전달을 위한 설정
        // Send post request
        DataOutputStream wr = new DataOutputStream(con.getOutputStream());
        wr.writeBytes("Content-Disposition: form-data; name=\"title\"\r\n\r\n");
        //wr.writeBytes(parameters);
        wr.writeUTF(parameters);
        wr.flush();
        wr.close();

        int responseCode = con.getResponseCode();
        BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream(), "UTF-8"));
        String inputLine;
        StringBuffer response = new StringBuffer();
        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        } in.close();
        // print result
        System.out.println("HTTP 응답 코드 : " + responseCode);
        System.out.println("HTTP body : " + response.toString());

        return response.toString();
    }

    public static void main(String[] args) throws Exception {
        
    }
}
