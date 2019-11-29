package com.zianedu.api.utils;

import java.util.ArrayList;
import java.util.List;

public class ZianUtils {

    public static List<String> getMonthList() {
        List<String> monthList = new ArrayList<>();

        for (int i=1; i<13; i++) {
            String month = String.valueOf(i);
            if (i < 10) {
                month = "0" + String.valueOf(i);
            }
            monthList.add(month);
        }
        return monthList;
    }

    /**
     * T_ORDER.j_id 값 만들기
     * @return
     */
    public static String getJId() {
        String jId = "";
        String yyyyMM = Util.getYearMonth();
        String[] splitHHmm = Util.split(Util.returnHourMinute(), ":");
        String hhmm = splitHHmm[0] + splitHHmm[1];
        String ranNumber = RandomUtil.getRandomNumber(6);

        jId = yyyyMM + "-" + hhmm + "-" + ranNumber;
        return jId;
    }

    public static String getTopAccumulatePercent(int totalAnswer, int userGrade) {
        double totalAnswerOnePercent = totalAnswer * 0.01;
        return String.format("%.1f", (userGrade / totalAnswerOnePercent));
    }

    public static Integer getExamSerialNumber() {
        String yy = DateUtils.getYearLastTwo();
        String ranNumber = RandomUtil.getRandomNumber(5);
        return Integer.parseInt(yy + ranNumber);
    }

    public static String getSplitFileName(String fileUrl) {
        String fileName = "";
        if (!"".equals(fileUrl)) {
            if (fileUrl.contains("\\")) {
                String splStr[] = fileUrl.split("\\\\");
                int splLen = (splStr.length) - 1;
                fileName = splStr[splLen];
            } else {
                fileName = fileUrl;
            }
        }
        return fileName;
    }

    public static int calcPercent(int price, int percent) {
        int calcPrice = 0;
        double calcPercent;

        if (price > 0 && percent > 0) {
            calcPercent = percent * 0.01;
            int calcPriceSum = (int)(price * calcPercent);
            calcPrice = price - calcPriceSum;
        }
        return calcPrice;
    }

    public static void main(String[] args) {
        //String str = getSplitFileName("bbs\\2017년 건축구조 9.7급 세부출제항목 분석.pdf");
        System.out.println(calcPercent(100000, 30));

    }
}
