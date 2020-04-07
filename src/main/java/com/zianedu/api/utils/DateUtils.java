package com.zianedu.api.utils;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

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
        //con.setRequestProperty("User-Agent", USER_AGENT);
        con.setDoOutput(true); // POST 파라미터 전달을 위한 설정
        // Send post request
        DataOutputStream wr = new DataOutputStream(con.getOutputStream());
        wr.writeBytes(parameters);
        wr.flush();
        wr.close();

        int responseCode = con.getResponseCode();
        BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
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
        String url = "http://118.217.181.175:8088/login/memberInsert.html";
        String name = "안지호";
        String newVodTitle = new String(name.getBytes("EUC-KR"), "utf-8");

        System.out.println(newVodTitle);
        String newVodTitle2 = new String(newVodTitle.getBytes("ksc5601"), "utf-8");
        System.out.println(newVodTitle2);

//        String word = name;
        System.out.println("utf-8(1) : " + new String(word.getBytes("utf-8"), "euc-kr"));
        System.out.println("utf-8(2) : " + new String(word.getBytes("utf-8"), "ksc5601"));
        System.out.println("utf-8(3) : " + new String(word.getBytes("utf-8"), "x-windows-949"));
        System.out.println("utf-8(4) : " + new String(word.getBytes("utf-8"), "iso-8859-1"));

        System.out.println("iso-8859-1(1) : " + new String(word.getBytes("iso-8859-1"), "euc-kr"));
        System.out.println("iso-8859-1(2) : " + new String(word.getBytes("iso-8859-1"), "ksc5601"));
        System.out.println("iso-8859-1(3) : " + new String(word.getBytes("iso-8859-1"), "x-windows-949"));
        System.out.println("iso-8859-1(4) : " + new String(word.getBytes("iso-8859-1"), "utf-8"));

        System.out.println("euc-kr(1) : " + new String(word.getBytes("euc-kr"), "ksc5601"));
        System.out.println("euc-kr(2) : " + new String(word.getBytes("euc-kr"), "utf-8"));
        System.out.println("euc-kr(3) : " + new String(word.getBytes("euc-kr"), "x-windows-949"));
        System.out.println("euc-kr(4) : " + new String(word.getBytes("euc-kr"), "iso-8859-1"));

        System.out.println("ksc5601(1) : " + new String(word.getBytes("ksc5601"), "euc-kr"));
        System.out.println("ksc5601(2) : " + new String(word.getBytes("ksc5601"), "utf-8"));
        System.out.println("ksc5601(3) : " + new String(word.getBytes("ksc5601"), "x-windows-949"));
        System.out.println("ksc5601(4) : " + new String(word.getBytes("ksc5601"), "iso-8859-1"));

        System.out.println("x-windows-949(1) : " + new String(word.getBytes("x-windows-949"), "euc-kr"));
        System.out.println("x-windows-949(2) : " + new String(word.getBytes("x-windows-949"), "utf-8"));
        System.out.println("x-windows-949(3) : " + new String(word.getBytes("x-windows-949"), "ksc5601"));
        System.out.println("x-windows-949(4) : " + new String(word.getBytes("x-windows-949"), "iso-8859-1"));


        String paramStr = "USER_ID=test1234750&USER_KEY=12389&NAME="+newVodTitle+"&PWD=c7684301-+&GENDER=1&EMAIL=anjo0070@naver.com&TELEPHONE_MOBILE=010-6258-5228&RECV_SMS=1&RECV_EMAIL=1&AUTHORITY=10&ZIPCODE=12345&ADDRESS_ROAD=경기 성남시 수정구 탄리로 81&ADDRESS=302호";
        httpPost(url, paramStr);
    }
}
