package com.zianedu.api.utils;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.security.SecureRandom;
import java.text.DecimalFormat;
import java.util.*;
import java.util.regex.Pattern;

public class StringUtils {

    private static SecureRandom random = new SecureRandom();

    private static final Pattern COMMA = Pattern.compile(",");

    /**
     * 임의의 문자열을 생성한다.
     * @return
     */
    public static String genRndStr() {
        return new BigInteger(130, random).toString(32);
    }

    /**
     * url 만들기
     * @param paths
     * @return
     */
    public static String concatURI(String... paths) {
        StringBuilder sb = new StringBuilder();
        for (String path : paths) {
            if (path == null) continue;
            path = path.trim();
            path = "/" + path;
            sb.append(path);
        }
        return sb.toString();
    }

    /**
     * 입력된 오브젝트가 널이면 빈문자열("")을 반환하고
     * 널이 아니면 오브젝트의 문자열의  trim한 결과를 반환한다.
     * <pre>
     * [null] --> [""]
     * [""] --> [""]
     * ["     "] --> [""]
     * [" aaa"] --> ["aaa"]
     * ["aaa "] --> ["aaa"]
     * [" aaa "] --> ["aaa"]
     * [" aa aaa "] --> ["aa aaa"]
     * [" aa aaa aa"] --> ["aa aaa aa"]
     * </pre>
     * @param obj 문자열로 변환할 오브젝트
     * @return 빈문자열("") 또는 trim된 문자열
     * @param obj
     * @return
     */
//    public static String trimToEmpty(Object obj) {
//        return obj == null ? EMPTY : trimToEmpty(String.valueOf(obj));
//    }

    /**
     * 입력된 문자열이 널 또는 빈문자열("")이면 입력된 기본문자열을 반환하고
     * 그렇지않으면 입력된 문자열의 trim한 결과를 반환한다.
     * @param str 문자열로 변환할 오브젝트
     * @param defaultStr 기본문자열
     * @return 기본문자열 또는 trim된 문자열
     * @param str
     * @param defaultStr
     * @return
     */
//    public static String trimToDefault(String str, String defaultStr) {
//        String ts = trim(str);
//        return isEmpty(ts) ? defaultStr : ts;
//    }

    /**
     * 입력된 오브젝트가 널 또는 빈문자열("")이면 입력된 기본문자열을 반환하고
     * 그렇지않으면 오브젝트의 문자열의  trim한 결과를 반환한다.
     * @param obj
     * @param defaultStr
     * @return
     */
    public static String trimToDefault(Object obj, String defaultStr) {
        return obj == null ? defaultStr : trimToDefault(String.valueOf(obj), defaultStr);
    }

    /**
     * 문자열을 치환한다.
     * @param srcStr 원본 문자열
     * @param oldStr 변경할 문자열
     * @param newStr 대체할 문자열
     * @return
     */
    public static String replace(String srcStr, String oldStr, String newStr) {
       if (srcStr == null) return null;
       StringBuilder sb = new StringBuilder(srcStr.length());
       int len = oldStr.length();
       int srcLen = srcStr.length();
       int pos = 0;
       int oldPos = 0;

       while ((pos = srcStr.indexOf(oldStr, oldPos)) >= 0) {
           sb.append(srcStr.substring(oldPos, pos));
           sb.append(newStr);
           oldPos = pos + len;
       }
       if (oldPos < srcLen) {
           sb.append(srcStr.substring(oldPos, srcLen));
       }
       return sb.toString();
    }

    /**
     * 숫자로 구성된 문자열 체크
     * @param str
     * @return
     */
    //public static boolean isDigit(String str) { return isNumeric(str); }

    /**
     * 패턴에 따른 문자열 만들기
     * "data1","data2","data3", .........
     * @param separator
     * @param data
     * @return
     */
    public static String implode(String separator, String[] data) {
        StringJoiner sj = new StringJoiner(separator);
        for (String str : data) {
            if (!COMMA.matcher(str).matches()) {
                sj.add(str);
            }
        }
        return sj.toString();
    }

    public static String implodeList(String separator, List<String> dataList) {
        StringJoiner sj = new StringJoiner(separator);
        for (String str : dataList) {
            if (!COMMA.matcher(str).matches()) {
                sj.add(str);
            }
        }
        return sj.toString();
    }

    public static String implodeFromInteger(String separator, Integer[] data) {
        StringJoiner sj = new StringJoiner(separator);
        for (Integer number : data) {
            if (!COMMA.matcher(String.valueOf(number)).matches()) {
                sj.add(String.valueOf(number));
            }
        }
        return sj.toString();
    }

    public static String implodeFromLong(String separator, Long[] data) {
        StringJoiner sj = new StringJoiner(separator);
        for (Long number : data) {
            if (!COMMA.matcher(String.valueOf(number)).matches()) {
                sj.add(String.valueOf(number));
            }
        }
        return sj.toString();
    }

    /**
     * 콤마 자르기
     * @param srcStr
     * @return
     */
    public static String[] splitComma(String srcStr) {
        String splitStr[] = null;
        if (!"".equals(srcStr)) {
            splitStr = srcStr.split("\\.");
        }
        return splitStr;
    }

    /**
     * 이메일 유효성 체크
     * @param email
     * @return
     */
//    public static boolean isEmail(String email) {
//        if (StringUtils.isEmpty(email)) {
//            return false;
//        }
//        return Pattern.matches("[\\w\\~\\-\\.]+@[\\w\\~\\-]+(\\.[\\w\\~\\-]+)+", email.trim());
//    }

    /**
     * 빈값인경우 체크,널대체값이 있는경우
     * @param str2
     * @param nullToStr
     * @return
     */
    public static String isNullValue(String str2, String nullToStr) {
        String str = str2;
        if (str == null) return nullToStr;
        else if (str.equals("")) return nullToStr;
        return str;
    }

    /**
     * arrayList<String> -> String[] 변화
     * @param arrayList
     * @return
     */
    public static String[] arrayListToStringArray(List<String> arrayList) {
        if (arrayList.size() == 0) return null;
        return arrayList.toArray(new String[arrayList.size()]);
    }

    public static Long[] arrayLongListToStringArray(List<Long> arrayList) {
        if (arrayList.size() == 0) return null;
        return arrayList.toArray(new Long[arrayList.size()]);
    }

    public static Integer[] arrayIntegerListToStringArray(List<Integer> arrayList) {
        if (arrayList.size() == 0) return null;
        return arrayList.toArray(new Integer[arrayList.size()]);
    }

    /**
     * String[] -> String 변환
     * @param arrayStr
     * @param regx
     * @return
     */
    public static String stringArrayToString(String[] arrayStr, String regx) {
        StringBuilder sb = new StringBuilder();
        for (String str :  arrayStr) {
            sb.append(str).append(regx);
        }
        String str = sb.toString().substring(0, sb.toString().length() - 1);
        return str;
    }

    /**
     * String[] -> ArrayList<String>변환
     * @param strArray
     * @return
     */
    public static List<String> stringArrayToArrayList(String[] strArray) {
        if (strArray.length == 0) {
            return null;
        }
        ArrayList arr = new ArrayList(Arrays.asList(strArray));
        return arr;
    }

    public static List<Integer> integerArrayToArrayList(Integer[] intArray) {
        if (intArray.length == 0) {
            return null;
        }
        ArrayList arr = new ArrayList(Arrays.asList(intArray));
        return arr;
    }

    /**
     * 문자열 오른쪽 마지막 한개 제거
     * @param srcStr
     * @return
     */
    public static String leaveLastString(String srcStr) {
        String leavedStr = null;
        if (!"".equals(srcStr)) {
            leavedStr = srcStr.substring(0, srcStr.length() - 1);
        }
        return leavedStr;
    }

    /**
     * 문자열 안에 특수문자 체크
     * @param str
     * @return
     */
    public static boolean isSpecialCharacter(String str) {
        if (!str.matches("[0-9|a-z|A-Z|ㄱ-ㅎ|ㅏ-ㅣ|가-힝]*")) {
            return false;
        }
        return true;
    }

    /**
     * 문자열에 한글이 포함되어있는지 확
     * @param korStr
     * @return
     * @throws UnsupportedEncodingException
     */
    public static boolean isKorean(String korStr) throws UnsupportedEncodingException {
        korStr = new String(korStr.getBytes("UTF-8"),"EUC-KR");
        if (korStr.matches(".*[ㄱ-ㅎㅏ-ㅣ가-힣]+.*")) {
            return true;
        }
        return false;
    }

    /**
     * 천단위 콤마찍기
     * @param numStr
     * @return
     */
    public static String addThousandSeparatorCommas(String numStr) {
        int num = Integer.parseInt(numStr);
        DecimalFormat commas = new DecimalFormat("#,###");
        return commas.format(num);
    }

    /**
     * 문자배열 만들기
     * @param strs
     * @return
     */
    public static String[] getStringArray(String... strs) {
        if (strs.length == 0) {
            return null;
        }
        List<String> Arr = new ArrayList<>();
        for (String str : strs) {
            Arr.add(str);
        }
        return Arr.toArray(new String[Arr.size()]);
    }

    public static int getStringByteLength(String str) {
        if (str.length() == 0) {
            return 0;
        }
        return str.length();
    }

    public static void main(String[] args) {
        List<String> list = new ArrayList<>();
        list.add("1");
        list.add("2");
        System.out.println(implodeList(",", list));
    }

    String lottoNumbers() {

        List<Integer> lottoNum = new ArrayList<Integer>();

        // List 안에 로또번호 추가
        for (int i = 1; i <= 45; i++) {
            lottoNum.add(i);
        }

        // set안의 수를 무작위로 섞는다
        Collections.shuffle(lottoNum);

        int[] lottoNums = new int[6];
        for (int i = 0; i < 6; i++) {
            lottoNums[i] = lottoNum.get(i);
        }

        // 정렬
        Arrays.sort(lottoNums);

        return Arrays.toString(lottoNums);
    }

    public static String convertStringFromStringArrays(String[] arr) {
        StringBuilder builder = new StringBuilder();
        for(String s : arr) {
            builder.append(s);
        }
        String str = builder.toString();
        return str;
    }

    //일단위 0으로 만들기
    public static int convertLastNumberZero(int number) {
        int convertNumber = 0;
        if (number > 0) {
            BigDecimal incm_civil_amt = new BigDecimal(number);
            incm_civil_amt = incm_civil_amt.setScale(-1, BigDecimal.ROUND_DOWN);
            return incm_civil_amt.intValue();
        }
        return convertNumber;
    }

    public static int convertLongToInt(Long l) {
        Integer i = 0;
        if (l != null) {
            i = l.intValue();
        }
        return i;
    }

}
