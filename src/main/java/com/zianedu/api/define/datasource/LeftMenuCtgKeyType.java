package com.zianedu.api.define.datasource;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public enum LeftMenuCtgKeyType {
    //행정직학원 > 학원수강신청 > 좌측 메뉴
    PUBLIC_ACADEMY_LEFT_MENU(335, "행정직학원 > 학원수강신청 > 좌측 메뉴"),
    PUBLIC_ACADEMY_EXAM_LEFT_MENU(336, "행정직학원 > 모의고사 > 좌측 메뉴"),
    PUBLIC_ACADEMY_TEACHER_LEFT_MENU(337, "행정직학원 > 교수소개 > 좌측 과목 메뉴"),

    PUBLIC_VOD_LECTURE_LEFT_MENU(234, "행정직동영상 > 동영상강좌 > 죄측 과목 메뉴"),
    PUBLIC_VOD_ZIANPASS_LEFT_MENU(239, "행정직동영상 > 365지안패스 > 죄측 메뉴"),
    PUBLIC_VOD_FREE_LECTURE_LEFT_MENU(4446, "행정직동영상 > 무료강좌 > 죄측 과목 메뉴"),
    PUBLIC_VOD_PACKAGE_LEFT_MENU(240, "행정직동영상 > 패키지 > 죄측 과목 메뉴"),
    PUBLIC_VOD_EXAM_LEFT_MENU(241, "행정직동영상 > 모의고사 > 죄측 과목 메뉴"),

    TECH_ACADEMY_LEFT_MENU(394, "기술직학원 > 학원수강신청 > 좌측 메뉴"),
    TECH_ACADEMY_YEAR_LEFT_MENU(762, "기술직학원 > 연간관리반 > 좌측 메뉴"),
    ;

    int ctgKey;

    String menuDefine;

    LeftMenuCtgKeyType(int ctgKey, String menuDefine) {
        this.ctgKey = ctgKey;
        this.menuDefine = menuDefine;
    }

    public static List<HashMap<String, String>> getLeftMenuCtgKeyList() {
        List<HashMap<String, String>>list = new ArrayList<>();
        for (LeftMenuCtgKeyType leftMenuCtgKeyType : LeftMenuCtgKeyType.values()) {
            HashMap<String, String> hashMap = new HashMap<>();
            hashMap.put("ctgKey", String.valueOf(leftMenuCtgKeyType.ctgKey));
            hashMap.put("menuDefine", String.valueOf(leftMenuCtgKeyType.menuDefine));

            list.add(hashMap);
        }
        return list;
    }
}
