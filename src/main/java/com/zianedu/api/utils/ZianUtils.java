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


    public static void main(String[] args) {
        System.out.println(getTopAccumulatePercent(113, 29));
    }
}
